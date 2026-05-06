USE BeyazEsyaDB;
GO

-- müşterinin satışlarını getirir

CREATE PROCEDURE sp_GetCustomerSales
    @customer_id INT
AS
BEGIN
    SELECT 
        s.sale_id,
        s.sale_date,
        s.sale_status,
        s.total_amount,
        c.first_name + ' ' + c.last_name AS customer_name
    FROM Sale s
    JOIN Customer c ON s.customer_id = c.customer_id
    WHERE s.customer_id = @customer_id;
END;
GO

-- ürüne stok ekler

CREATE PROCEDURE sp_UpdateProductStock
    @product_id INT,
    @added_quantity INT
AS
BEGIN
    UPDATE Product
    SET stock_quantity = stock_quantity + @added_quantity
    WHERE product_id = @product_id;
END;
GO

-- yeni satış oluşturur

CREATE PROCEDURE sp_CreateSale
    @customer_id INT,
    @employee_id INT
AS
BEGIN
    INSERT INTO Sale (customer_id, employee_id)
    VALUES (@customer_id, @employee_id);
END;
GO