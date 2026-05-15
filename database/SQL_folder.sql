-- =========================================================
-- VERİTABANINI SIFIRDAN OLUŞTURMA
-- Bu betik çalıştırıldığında aynı isimde eski bir veritabanı
-- varsa silinir ve proje veritabanı yeniden oluşturulur.
-- =========================================================

USE master;
GO

IF DB_ID('BeyazEsyaDB') IS NOT NULL
BEGIN
    ALTER DATABASE BeyazEsyaDB
    SET SINGLE_USER WITH ROLLBACK IMMEDIATE;

    DROP DATABASE BeyazEsyaDB;
END;
GO

CREATE DATABASE BeyazEsyaDB;
GO

USE BeyazEsyaDB;
GO


-- =========================================================
-- TABLOLAR
-- =========================================================

CREATE TABLE Customer (
    customer_id INT IDENTITY(1,1) PRIMARY KEY,
    first_name NVARCHAR(50) NOT NULL,
    last_name NVARCHAR(50) NOT NULL,
    phone NVARCHAR(20) NOT NULL UNIQUE,
    email NVARCHAR(100) NOT NULL UNIQUE,
    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME()
);
GO


CREATE TABLE Employee (
    employee_id INT IDENTITY(1,1) PRIMARY KEY,
    first_name NVARCHAR(50) NOT NULL,
    last_name NVARCHAR(50) NOT NULL,
    phone NVARCHAR(20) NOT NULL UNIQUE,
    position NVARCHAR(50) NOT NULL,
    salary DECIMAL(10,2) NOT NULL CHECK (salary >= 0),
    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME()
);
GO


CREATE TABLE Category (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(100) NOT NULL UNIQUE
);
GO


CREATE TABLE Product (
    product_id INT IDENTITY(1,1) PRIMARY KEY,
    category_id INT NOT NULL,
    product_name NVARCHAR(100) NOT NULL,
    brand NVARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price > 0),
    stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),

    CONSTRAINT FK_Product_Category
        FOREIGN KEY (category_id)
        REFERENCES Category(category_id)
);
GO


CREATE TABLE Sale (
    sale_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT NOT NULL,
    employee_id INT NOT NULL,
    sale_date DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0 CHECK (total_amount >= 0),
    sale_status NVARCHAR(20) NOT NULL DEFAULT 'Pending'
        CHECK (sale_status IN ('Pending', 'Completed', 'Cancelled')),

    CONSTRAINT FK_Sale_Customer
        FOREIGN KEY (customer_id)
        REFERENCES Customer(customer_id),

    CONSTRAINT FK_Sale_Employee
        FOREIGN KEY (employee_id)
        REFERENCES Employee(employee_id)
);
GO


CREATE TABLE SaleDetail (
    sale_detail_id INT IDENTITY(1,1) PRIMARY KEY,
    sale_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL CHECK (unit_price > 0),

    CONSTRAINT FK_SaleDetail_Sale
        FOREIGN KEY (sale_id)
        REFERENCES Sale(sale_id),

    CONSTRAINT FK_SaleDetail_Product
        FOREIGN KEY (product_id)
        REFERENCES Product(product_id),

    -- Aynı ürün aynı satış içerisinde iki ayrı satır olarak tutulmaz.
    CONSTRAINT UQ_SaleDetail_Sale_Product
        UNIQUE (sale_id, product_id)
);
GO


-- =========================================================
-- INDEXLER
-- =========================================================

CREATE INDEX IX_Product_CategoryID
ON Product(category_id);
GO

CREATE INDEX IX_Sale_CustomerID
ON Sale(customer_id);
GO

CREATE INDEX IX_Sale_EmployeeID
ON Sale(employee_id);
GO

CREATE INDEX IX_Sale_Status_Date
ON Sale(sale_status, sale_date);
GO

CREATE INDEX IX_SaleDetail_SaleID
ON SaleDetail(sale_id);
GO

CREATE INDEX IX_SaleDetail_ProductID
ON SaleDetail(product_id);
GO


-- =========================================================
-- VIEWLER
-- =========================================================

-- Ürün, kategori ve stok bilgisini birlikte gösterir.
CREATE VIEW vw_ProductStock AS
SELECT
    p.product_id,
    p.product_name,
    p.brand,
    c.category_name,
    p.price,
    p.stock_quantity
FROM Product p
INNER JOIN Category c
    ON p.category_id = c.category_id;
GO


-- Satışların detaylı rapor görünümüdür.
-- Bir satıştaki her ürün ayrı satır olarak gösterilir.
CREATE VIEW vw_SaleReport AS
SELECT
    s.sale_id,
    s.sale_date,
    s.sale_status,
    s.total_amount,
    c.customer_id,
    c.first_name + ' ' + c.last_name AS customer_name,
    e.employee_id,
    e.first_name + ' ' + e.last_name AS employee_name,
    p.product_id,
    p.product_name,
    p.brand,
    sd.quantity,
    sd.unit_price,
    sd.quantity * sd.unit_price AS line_total
FROM Sale s
INNER JOIN Customer c
    ON s.customer_id = c.customer_id
INNER JOIN Employee e
    ON s.employee_id = e.employee_id
INNER JOIN SaleDetail sd
    ON s.sale_id = sd.sale_id
INNER JOIN Product p
    ON sd.product_id = p.product_id;
GO


-- =========================================================
-- TRIGGERLAR
-- =========================================================

-- 1) SaleDetail değişikliklerinde stok kontrolü ve stok güncelleme
-- Ayrıca Completed veya Cancelled satışların detaylarının
-- değiştirilmesi engellenir.

CREATE TRIGGER trg_SaleDetail_Stock
ON SaleDetail
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;

    ---------------------------------------------------------
    -- A) Sadece Pending satış detayları değiştirilebilir
    ---------------------------------------------------------
    IF EXISTS (
        SELECT 1
        FROM inserted i
        INNER JOIN Sale s
            ON i.sale_id = s.sale_id
        WHERE s.sale_status <> 'Pending'
    )
    OR EXISTS (
        SELECT 1
        FROM deleted d
        INNER JOIN Sale s
            ON d.sale_id = s.sale_id
        WHERE s.sale_status <> 'Pending'
    )
    BEGIN
        ROLLBACK TRANSACTION;
        RAISERROR(N'Satış detayları yalnızca Pending durumundaki satışlarda değiştirilebilir.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- B) Stok yetersizliği kontrolü
    ---------------------------------------------------------
    IF EXISTS (
        SELECT 1
        FROM Product p
        INNER JOIN (
            SELECT 
                product_id,
                SUM(qty_change) AS total_change
            FROM (
                SELECT 
                    product_id,
                    SUM(quantity) AS qty_change
                FROM inserted
                GROUP BY product_id

                UNION ALL

                SELECT 
                    product_id,
                    -SUM(quantity) AS qty_change
                FROM deleted
                GROUP BY product_id
            ) AS Changes
            GROUP BY product_id
        ) AS StockChange
            ON p.product_id = StockChange.product_id
        WHERE p.stock_quantity - StockChange.total_change < 0
    )
    BEGIN
        ROLLBACK TRANSACTION;
        RAISERROR(N'Yetersiz stok. Satış detayı işlenemedi.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- C) Stokları güncelle
    ---------------------------------------------------------
    UPDATE p
    SET p.stock_quantity = p.stock_quantity - StockChange.total_change
    FROM Product p
    INNER JOIN (
        SELECT 
            product_id,
            SUM(qty_change) AS total_change
        FROM (
            SELECT 
                product_id,
                SUM(quantity) AS qty_change
            FROM inserted
            GROUP BY product_id

            UNION ALL

            SELECT 
                product_id,
                -SUM(quantity) AS qty_change
            FROM deleted
            GROUP BY product_id
        ) AS Changes
        GROUP BY product_id
    ) AS StockChange
        ON p.product_id = StockChange.product_id;
END;
GO


-- 2) SaleDetail değiştikçe Sale.total_amount otomatik güncellenir.

CREATE TRIGGER trg_SaleDetail_TotalAmount
ON SaleDetail
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE s
    SET s.total_amount = ISNULL((
        SELECT SUM(sd.quantity * sd.unit_price)
        FROM SaleDetail sd
        WHERE sd.sale_id = s.sale_id
    ), 0)
    FROM Sale s
    INNER JOIN (
        SELECT sale_id FROM inserted
        UNION
        SELECT sale_id FROM deleted
    ) AS AffectedSales
        ON s.sale_id = AffectedSales.sale_id;
END;
GO


-- 3) Satış durum geçişlerini kontrol eder.
-- Cancelled işleminde ürün stokları geri iade edilir.

CREATE TRIGGER trg_Sale_StatusControlAndStockReturn
ON Sale
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF UPDATE(sale_status)
    BEGIN
        ---------------------------------------------------------
        -- A) Geçersiz satış durumu geçişlerini engelle
        ---------------------------------------------------------
        IF EXISTS (
            SELECT 1
            FROM inserted i
            INNER JOIN deleted d
                ON i.sale_id = d.sale_id
            WHERE NOT (
                i.sale_status = d.sale_status

                OR

                (d.sale_status = 'Pending' AND i.sale_status = 'Completed')

                OR

                (d.sale_status = 'Pending' AND i.sale_status = 'Cancelled')
            )
        )
        BEGIN
            ROLLBACK TRANSACTION;
            RAISERROR(N'Geçersiz satış durumu geçişi.', 16, 1);
            RETURN;
        END;

        ---------------------------------------------------------
        -- B) Ürün detayı olmayan Pending satış Completed yapılamaz
        ---------------------------------------------------------
        IF EXISTS (
            SELECT 1
            FROM inserted i
            INNER JOIN deleted d
                ON i.sale_id = d.sale_id
            WHERE d.sale_status = 'Pending'
              AND i.sale_status = 'Completed'
              AND NOT EXISTS (
                    SELECT 1
                    FROM SaleDetail sd
                    WHERE sd.sale_id = i.sale_id
              )
        )
        BEGIN
            ROLLBACK TRANSACTION;
            RAISERROR(N'Ürün detayı olmayan satış tamamlanamaz.', 16, 1);
            RETURN;
        END;

        ---------------------------------------------------------
        -- C) Pending satış Cancelled olursa stokları geri iade et
        ---------------------------------------------------------
        UPDATE p
        SET p.stock_quantity = p.stock_quantity + CancelledItems.total_quantity
        FROM Product p
        INNER JOIN (
            SELECT
                sd.product_id,
                SUM(sd.quantity) AS total_quantity
            FROM SaleDetail sd
            INNER JOIN inserted i
                ON sd.sale_id = i.sale_id
            INNER JOIN deleted d
                ON i.sale_id = d.sale_id
            WHERE
                d.sale_status = 'Pending'
                AND i.sale_status = 'Cancelled'
            GROUP BY sd.product_id
        ) AS CancelledItems
            ON p.product_id = CancelledItems.product_id;
    END;
END;
GO


-- =========================================================
-- STORED PROCEDURELER
-- =========================================================

-- 1) Belirli müşterinin satış detaylarını getirir.

CREATE PROCEDURE sp_GetCustomerSales
    @customer_id INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        sale_id,
        sale_date,
        sale_status,
        total_amount,
        customer_id,
        customer_name,
        employee_id,
        employee_name,
        product_id,
        product_name,
        brand,
        quantity,
        unit_price,
        line_total
    FROM vw_SaleReport
    WHERE customer_id = @customer_id
    ORDER BY sale_date DESC;
END;
GO


-- 2) Ürün stok miktarını manuel olarak günceller.
-- Ancak bu ürüne ait Pending durumda satış varsa,
-- stok rezervasyonu bozulmaması için manuel güncelleme yapılmaz.

CREATE PROCEDURE sp_UpdateProductStock
    @product_id INT,
    @new_stock INT
AS
BEGIN
    SET NOCOUNT ON;

    ---------------------------------------------------------
    -- A) Yeni stok miktarı negatif olamaz
    ---------------------------------------------------------
    IF @new_stock < 0
    BEGIN
        RAISERROR(N'Stok miktarı negatif olamaz.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- B) Ürün var mı?
    ---------------------------------------------------------
    IF NOT EXISTS (
        SELECT 1
        FROM Product
        WHERE product_id = @product_id
    )
    BEGIN
        RAISERROR(N'Belirtilen ürün bulunamadı.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- C) Bu ürüne ait Pending satış var mı?
    ---------------------------------------------------------
    IF EXISTS (
        SELECT 1
        FROM SaleDetail sd
        INNER JOIN Sale s
            ON sd.sale_id = s.sale_id
        WHERE sd.product_id = @product_id
          AND s.sale_status = 'Pending'
    )
    BEGIN
        RAISERROR(
            N'Bu ürüne ait Pending durumda satış bulunduğu için manuel stok güncelleme yapılamaz.',
            16,
            1
        );
        RETURN;
    END;

    ---------------------------------------------------------
    -- D) Stok güncelle
    ---------------------------------------------------------
    UPDATE Product
    SET stock_quantity = @new_stock
    WHERE product_id = @product_id;
END;
GO


-- 3) Yeni satış oluşturur.
-- Oluşan sale_id SELECT sonucu olarak döndürülür.
-- Bu yapı backend tarafında karşılamayı kolaylaştırır.

CREATE PROCEDURE sp_CreateSale
    @customer_id INT,
    @employee_id INT
AS
BEGIN
    SET NOCOUNT ON;

    ---------------------------------------------------------
    -- A) Müşteri var mı?
    ---------------------------------------------------------
    IF NOT EXISTS (
        SELECT 1
        FROM Customer
        WHERE customer_id = @customer_id
    )
    BEGIN
        RAISERROR(N'Belirtilen müşteri bulunamadı.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- B) Çalışan var mı?
    ---------------------------------------------------------
    IF NOT EXISTS (
        SELECT 1
        FROM Employee
        WHERE employee_id = @employee_id
    )
    BEGIN
        RAISERROR(N'Belirtilen çalışan bulunamadı.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- C) Yeni satış oluştur
    ---------------------------------------------------------
    INSERT INTO Sale (customer_id, employee_id, sale_status)
    VALUES (@customer_id, @employee_id, 'Pending');

    ---------------------------------------------------------
    -- D) Oluşan satış ID'sini döndür
    ---------------------------------------------------------
    SELECT CAST(SCOPE_IDENTITY() AS INT) AS new_sale_id;
END;
GO


-- 4) Pending satışa yeni ürün ekler.
-- Birim fiyat frontend'den alınmaz.
-- Product tablosundaki güncel fiyat otomatik alınır.

CREATE PROCEDURE sp_AddSaleDetail
    @sale_id INT,
    @product_id INT,
    @quantity INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @unit_price DECIMAL(10,2);

    ---------------------------------------------------------
    -- A) Satış var mı?
    ---------------------------------------------------------
    IF NOT EXISTS (
        SELECT 1
        FROM Sale
        WHERE sale_id = @sale_id
    )
    BEGIN
        RAISERROR(N'Belirtilen satış bulunamadı.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- B) Satış Pending mi?
    ---------------------------------------------------------
    IF EXISTS (
        SELECT 1
        FROM Sale
        WHERE sale_id = @sale_id
          AND sale_status <> 'Pending'
    )
    BEGIN
        RAISERROR(N'Yalnızca Pending durumundaki satışlara ürün eklenebilir.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- C) Ürün var mı?
    ---------------------------------------------------------
    IF NOT EXISTS (
        SELECT 1
        FROM Product
        WHERE product_id = @product_id
    )
    BEGIN
        RAISERROR(N'Belirtilen ürün bulunamadı.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- D) Miktar kontrolü
    ---------------------------------------------------------
    IF @quantity <= 0
    BEGIN
        RAISERROR(N'Ürün miktarı sıfırdan büyük olmalıdır.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- E) Aynı ürün aynı satışta zaten var mı?
    ---------------------------------------------------------
    IF EXISTS (
        SELECT 1
        FROM SaleDetail
        WHERE sale_id = @sale_id
          AND product_id = @product_id
    )
    BEGIN
        RAISERROR(N'Bu ürün ilgili satışa zaten eklenmiş.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- F) Ürünün güncel fiyatını al
    ---------------------------------------------------------
    SELECT @unit_price = price
    FROM Product
    WHERE product_id = @product_id;

    ---------------------------------------------------------
    -- G) Satış detayını ekle
    -- Stok düşme ve toplam hesaplama trigger ile yapılır.
    ---------------------------------------------------------
    INSERT INTO SaleDetail (sale_id, product_id, quantity, unit_price)
    VALUES (@sale_id, @product_id, @quantity, @unit_price);
END;
GO


-- 5) Pending satıştaki mevcut ürünün miktarını günceller.
-- Stok yeterlilik kontrolü, stok güncelleme ve satış toplamı
-- ilgili triggerlar tarafından otomatik yönetilir.

CREATE PROCEDURE sp_UpdateSaleDetailQuantity
    @sale_id INT,
    @product_id INT,
    @new_quantity INT
AS
BEGIN
    SET NOCOUNT ON;

    ---------------------------------------------------------
    -- A) Satış var mı?
    ---------------------------------------------------------
    IF NOT EXISTS (
        SELECT 1
        FROM Sale
        WHERE sale_id = @sale_id
    )
    BEGIN
        RAISERROR(N'Belirtilen satış bulunamadı.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- B) Satış Pending mi?
    ---------------------------------------------------------
    IF EXISTS (
        SELECT 1
        FROM Sale
        WHERE sale_id = @sale_id
          AND sale_status <> 'Pending'
    )
    BEGIN
        RAISERROR(N'Yalnızca Pending durumundaki satışların ürün miktarı güncellenebilir.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- C) Yeni miktar sıfırdan büyük olmalı
    ---------------------------------------------------------
    IF @new_quantity <= 0
    BEGIN
        RAISERROR(N'Yeni ürün miktarı sıfırdan büyük olmalıdır.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- D) İlgili satış detayı var mı?
    ---------------------------------------------------------
    IF NOT EXISTS (
        SELECT 1
        FROM SaleDetail
        WHERE sale_id = @sale_id
          AND product_id = @product_id
    )
    BEGIN
        RAISERROR(N'Güncellenecek ürün bu satışta bulunamadı.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- E) Miktarı güncelle
    -- Bu UPDATE işlemi sonrası:
    -- - Stok fark kadar trigger ile güncellenir.
    -- - Stok yetersizse trigger işlemi geri alır.
    -- - Sale.total_amount trigger ile yeniden hesaplanır.
    ---------------------------------------------------------
    UPDATE SaleDetail
    SET quantity = @new_quantity
    WHERE sale_id = @sale_id
      AND product_id = @product_id;
END;
GO


-- 6) Satış durumunu günceller.
-- Ürün detayı olmayan satış Completed yapılamaz.
-- Bu kontrol trigger tarafında da ayrıca yapılmaktadır.

CREATE PROCEDURE sp_UpdateSaleStatus
    @sale_id INT,
    @new_status NVARCHAR(20)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @current_status NVARCHAR(20);

    ---------------------------------------------------------
    -- A) Yeni durum geçerli mi?
    ---------------------------------------------------------
    IF @new_status IS NULL
       OR @new_status NOT IN ('Completed', 'Cancelled')
    BEGIN
        RAISERROR(N'Satış durumu yalnızca Completed veya Cancelled olarak güncellenebilir.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- B) Satış var mı?
    ---------------------------------------------------------
    SELECT @current_status = sale_status
    FROM Sale
    WHERE sale_id = @sale_id;

    IF @current_status IS NULL
    BEGIN
        RAISERROR(N'Belirtilen satış bulunamadı.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- C) Yalnızca Pending satışlar güncellenebilir
    ---------------------------------------------------------
    IF @current_status <> 'Pending'
    BEGIN
        RAISERROR(N'Yalnızca Pending durumundaki satışlar güncellenebilir.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- D) Ürün detayı olmayan satış Completed yapılamaz
    ---------------------------------------------------------
    IF @new_status = 'Completed'
       AND NOT EXISTS (
            SELECT 1
            FROM SaleDetail
            WHERE sale_id = @sale_id
       )
    BEGIN
        RAISERROR(N'Ürün detayı olmayan satış tamamlanamaz.', 16, 1);
        RETURN;
    END;

    ---------------------------------------------------------
    -- E) Durumu güncelle
    -- Cancelled olursa stok iadesi trigger ile yapılır.
    ---------------------------------------------------------
    UPDATE Sale
    SET sale_status = @new_status
    WHERE sale_id = @sale_id;
END;
GO


-- =========================================================
-- DUMMY DATA
-- =========================================================

INSERT INTO Category (category_name)
VALUES
('Buzdolabı'),
('Çamaşır Makinesi'),
('Bulaşık Makinesi'),
('Fırın'),
('Klima'),
('Televizyon'),
('Derin Dondurucu'),
('Kurutma Makinesi'),
('Ocak'),
('Mikrodalga');
GO


INSERT INTO Product (category_id, product_name, brand, price, stock_quantity)
VALUES
(1, 'No Frost Buzdolabı', 'Arçelik', 32000, 20),
(2, '9 KG Çamaşır Makinesi', 'Beko', 21000, 25),
(3, '5 Programlı Bulaşık Makinesi', 'Vestel', 18500, 18),
(4, 'Ankastre Fırın', 'Bosch', 16000, 15),
(5, '12000 BTU Klima', 'Samsung', 28000, 12),
(6, '55 İnç Smart TV', 'LG', 30000, 14),
(7, 'Sandık Tipi Derin Dondurucu', 'Uğur', 17500, 10),
(8, '8 KG Kurutma Makinesi', 'Siemens', 26000, 13),
(9, 'Cam Ankastre Ocak', 'Profilo', 9500, 30),
(10, 'Dijital Mikrodalga Fırın', 'Arçelik', 7000, 22);
GO


INSERT INTO Customer (first_name, last_name, phone, email)
VALUES
('Ahmet', 'Yılmaz', '05551112233', 'ahmet@email.com'),
('Ayşe', 'Demir', '05552223344', 'ayse@email.com'),
('Mehmet', 'Kaya', '05553334455', 'mehmet@email.com'),
('Fatma', 'Çelik', '05554445566', 'fatma@email.com'),
('Mustafa', 'Şahin', '05555556677', 'mustafa@email.com'),
('Zeynep', 'Yıldız', '05556667788', 'zeynep@email.com'),
('Ali', 'Öztürk', '05557778899', 'ali@email.com'),
('Elif', 'Aydın', '05558889900', 'elif@email.com'),
('Hasan', 'Özdemir', '05559990011', 'hasan@email.com'),
('Hülya', 'Arslan', '05550001122', 'hulya@email.com');
GO


INSERT INTO Employee (first_name, last_name, phone, position, salary)
VALUES
('Burak', 'Can', '05051112233', 'Mağaza Müdürü', 45000),
('Cansu', 'Tekin', '05052223344', 'Satış Danışmanı', 25000),
('Emre', 'Kurt', '05053334455', 'Satış Danışmanı', 25000),
('Gizem', 'Şen', '05054445566', 'Kasiyer', 22000),
('Oğuz', 'Gül', '05055556677', 'Satış Danışmanı', 25000),
('Selin', 'Koç', '05056667788', 'Kasiyer', 22000),
('Tolga', 'Ak', '05057778899', 'Depo Sorumlusu', 24000),
('Merve', 'Sarı', '05058889900', 'Müşteri Temsilcisi', 23000),
('Kerem', 'Işık', '05059990011', 'Satış Danışmanı', 25000),
('Deniz', 'Gök', '05050001122', 'Teknik Servis', 28000);
GO


-- Satışlar önce Pending olarak oluşturulur.
INSERT INTO Sale (customer_id, employee_id, sale_status)
VALUES
(1, 2, 'Pending'),
(2, 3, 'Pending'),
(3, 5, 'Pending'),
(4, 2, 'Pending'),
(5, 9, 'Pending'),
(6, 3, 'Pending'),
(7, 5, 'Pending'),
(8, 2, 'Pending'),
(9, 9, 'Pending'),
(10, 3, 'Pending');
GO


-- Satış detayları eklenir.
-- Bu aşamada stoklar trigger ile otomatik düşer.
-- Sale.total_amount trigger ile otomatik hesaplanır.
INSERT INTO SaleDetail (sale_id, product_id, quantity, unit_price)
VALUES
(1, 1, 1, 32000),
(1, 9, 1, 9500),
(2, 2, 1, 21000),
(3, 3, 1, 18500),
(4, 5, 1, 28000),
(5, 4, 1, 16000),
(6, 6, 1, 30000),
(7, 7, 1, 17500),
(8, 8, 1, 26000),
(9, 10, 2, 7000),
(10, 1, 1, 32000),
(10, 3, 1, 18500);
GO


-- Satış durumları iş akışına uygun olarak güncellenir.
EXEC sp_UpdateSaleStatus @sale_id = 1, @new_status = 'Completed';
EXEC sp_UpdateSaleStatus @sale_id = 2, @new_status = 'Completed';
EXEC sp_UpdateSaleStatus @sale_id = 4, @new_status = 'Completed';
EXEC sp_UpdateSaleStatus @sale_id = 5, @new_status = 'Cancelled';
EXEC sp_UpdateSaleStatus @sale_id = 6, @new_status = 'Completed';
EXEC sp_UpdateSaleStatus @sale_id = 7, @new_status = 'Completed';
EXEC sp_UpdateSaleStatus @sale_id = 9, @new_status = 'Completed';
EXEC sp_UpdateSaleStatus @sale_id = 10, @new_status = 'Completed';
GO


-- =========================================================
-- TEMEL TEST SORGULARI
-- =========================================================

SELECT * FROM Customer;
SELECT * FROM Employee;
SELECT * FROM Category;
SELECT * FROM Product;
SELECT * FROM Sale;
SELECT * FROM SaleDetail;

SELECT * FROM vw_ProductStock;
SELECT * FROM vw_SaleReport;

EXEC sp_GetCustomerSales 1;
GO


-- =========================================================
-- PROCEDURE TESTİ: YENİ SATIŞ OLUŞTURMA
-- =========================================================

DECLARE @newSaleId INT;

-- sp_CreateSale yeni satış ID'sini SELECT sonucu olarak döndürür.
-- Bu sonucu tablo değişkenine alıyoruz.
DECLARE @CreatedSale TABLE (
    new_sale_id INT
);

INSERT INTO @CreatedSale (new_sale_id)
EXEC sp_CreateSale
    @customer_id = 1,
    @employee_id = 2;

-- Oluşan satış ID'sini değişkene aktar
SELECT @newSaleId = new_sale_id
FROM @CreatedSale;

-- Satışa ürün eklenir.
-- Stok düşme ve toplam tutar hesaplama işlemleri trigger ile yapılır.
EXEC sp_AddSaleDetail
    @sale_id = @newSaleId,
    @product_id = 2,
    @quantity = 1;

-- Aynı üründen daha fazla alınmak istenirse yeni satır eklenmez;
-- mevcut satış detayındaki quantity değeri güncellenir.
-- Bu örnekte miktar 1'den 2'ye çıkarılır.
EXEC sp_UpdateSaleDetailQuantity
    @sale_id = @newSaleId,
    @product_id = 2,
    @new_quantity = 2;

-- Satış tamamlanır.
EXEC sp_UpdateSaleStatus
    @sale_id = @newSaleId,
    @new_status = 'Completed';

-- Sonuçları kontrol et
SELECT @newSaleId AS new_sale_id;
SELECT * FROM Sale WHERE sale_id = @newSaleId;
SELECT * FROM SaleDetail WHERE sale_id = @newSaleId;
SELECT * FROM Product WHERE product_id = 2;
GO


-- =========================================================
-- EK TESTLER
-- =========================================================

-- NULL satış durumu gönderme denemesi -> HATA vermeli
/*
EXEC sp_UpdateSaleStatus
    @sale_id = 3,
    @new_status = NULL;
*/


-- 5 numaralı satış Cancelled mı?
SELECT * FROM Sale WHERE sale_id = 5;


-- 5 numaralı satışın detayları duruyor mu?
SELECT * FROM SaleDetail WHERE sale_id = 5;


-- 4 numaralı ürünün stoğu iptal sonrası geri geldi mi?
-- Başlangıç stoğu 15'tir.
-- 5 numaralı satış iptal edildiği için tekrar 15 olmalıdır.
SELECT * FROM Product WHERE product_id = 4;


-- İptal edilmiş satışa ürün ekleme denemesi -> HATA vermeli
/*
EXEC sp_AddSaleDetail
    @sale_id = 5,
    @product_id = 2,
    @quantity = 1;
*/


-- Tamamlanmış satışa ürün ekleme denemesi -> HATA vermeli
/*
EXEC sp_AddSaleDetail
    @sale_id = 1,
    @product_id = 3,
    @quantity = 1;
*/


-- Tamamlanmış satıştaki ürün miktarını güncelleme denemesi -> HATA vermeli
/*
EXEC sp_UpdateSaleDetailQuantity
    @sale_id = 1,
    @product_id = 1,
    @new_quantity = 2;
*/


-- Pending satıştaki ürün miktarını 0 yapma denemesi -> HATA vermeli
/*
EXEC sp_UpdateSaleDetailQuantity
    @sale_id = 3,
    @product_id = 3,
    @new_quantity = 0;
*/


-- Completed satış tekrar Cancelled yapılamaz -> HATA vermeli
/*
EXEC sp_UpdateSaleStatus
    @sale_id = 1,
    @new_status = 'Cancelled';
*/


-- Cancelled satış tekrar Completed yapılamaz -> HATA vermeli
/*
EXEC sp_UpdateSaleStatus
    @sale_id = 5,
    @new_status = 'Completed';
*/


-- Ürün eklenmemiş boş satış Completed yapılamaz.
-- Bu hata procedure seviyesinde engellenir.
/*
DECLARE @emptySaleId INT;

DECLARE @EmptyCreatedSale TABLE (
    new_sale_id INT
);

INSERT INTO @EmptyCreatedSale (new_sale_id)
EXEC sp_CreateSale
    @customer_id = 2,
    @employee_id = 3;

SELECT @emptySaleId = new_sale_id
FROM @EmptyCreatedSale;

EXEC sp_UpdateSaleStatus
    @sale_id = @emptySaleId,
    @new_status = 'Completed';
*/


-- Ürün eklenmemiş boş satış doğrudan UPDATE ile Completed
-- yapılmaya çalışılırsa da HATA vermeli.
-- Bu hata trigger seviyesinde engellenir.
/*
DECLARE @emptySaleId2 INT;

DECLARE @EmptyCreatedSale2 TABLE (
    new_sale_id INT
);

INSERT INTO @EmptyCreatedSale2 (new_sale_id)
EXEC sp_CreateSale
    @customer_id = 4,
    @employee_id = 2;

SELECT @emptySaleId2 = new_sale_id
FROM @EmptyCreatedSale2;

UPDATE Sale
SET sale_status = 'Completed'
WHERE sale_id = @emptySaleId2;
*/


-- Pending satışta kullanılan ürünün stoğu manuel değiştirilemez.
/*
DECLARE @pendingSaleId INT;

DECLARE @PendingCreatedSale TABLE (
    new_sale_id INT
);

INSERT INTO @PendingCreatedSale (new_sale_id)
EXEC sp_CreateSale
    @customer_id = 3,
    @employee_id = 2;

SELECT @pendingSaleId = new_sale_id
FROM @PendingCreatedSale;

EXEC sp_AddSaleDetail
    @sale_id = @pendingSaleId,
    @product_id = 5,
    @quantity = 1;

-- Product 5 Pending satışta olduğu için HATA vermeli
EXEC sp_UpdateProductStock
    @product_id = 5,
    @new_stock = 50;
*/
GO