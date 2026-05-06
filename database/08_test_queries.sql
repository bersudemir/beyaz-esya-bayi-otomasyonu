USE BeyazEsyaDB;
GO

SELECT * FROM dbo.Customer;
SELECT * FROM dbo.Employee;
SELECT * FROM dbo.Category;
SELECT * FROM dbo.Product;
SELECT * FROM dbo.Sale;
SELECT * FROM dbo.SaleDetail;


-- VIEW TESTLERİ

-- Ürün + stok + kategori
SELECT * FROM vw_ProductStock;

-- Satış raporu
SELECT * FROM vw_SaleReport;


-- TRIGGER TESTİ

-- Önce mevcut durumu gör
SELECT * FROM Product WHERE product_id = 2;
SELECT * FROM Sale WHERE sale_id = 10;

-- Yeni satış detayı ekle (daha önce kullanılmamış olmalı!)
INSERT INTO SaleDetail (sale_id, product_id, quantity, unit_price)
VALUES (10, 2, 1, 21000);

-- Sonra tekrar bak
SELECT * FROM Product WHERE product_id = 2;
SELECT * FROM Sale WHERE sale_id = 10;

-- Beklenen:
-- stock_quantity 1 azalmalı
-- total_amount artmalı


-- STORED PROCEDURE TESTLERİ

-- Müşteri satışlarını getirme
EXEC sp_GetCustomerSales 1;

-- Stok günceller
EXEC sp_UpdateProductStock 1, 5;

-- Güncellendi mi kontrol etme
SELECT * FROM Product WHERE product_id = 1;

-- Yeni satış oluşturma
EXEC sp_CreateSale 3, 2;

-- Son eklenen satışları görme
SELECT * FROM Sale ORDER BY sale_id;