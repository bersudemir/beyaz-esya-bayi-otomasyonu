USE BeyazEsyaDB;
GO

-- ürün + kategori + stok bilgisini gösterir
CREATE VIEW vw_ProductStock AS
SELECT 
    p.product_id,
    p.product_name,
    c.category_name,
    p.brand,
    p.price,
    p.stock_quantity
FROM Product p
JOIN Category c ON p.category_id = c.category_id;
GO


-- satışları müşteri, ürün ve personelle birlikte gösterir
CREATE VIEW vw_SaleReport AS
SELECT 
    s.sale_id,
    s.sale_date,
    s.sale_status,
    
    c.first_name + ' ' + c.last_name AS customer_name,
    e.first_name + ' ' + e.last_name AS employee_name,
    
    p.product_name,
    sd.quantity,
    sd.unit_price,
    
    (sd.quantity * sd.unit_price) AS line_total

FROM Sale s
JOIN Customer c ON s.customer_id = c.customer_id
JOIN Employee e ON s.employee_id = e.employee_id
JOIN SaleDetail sd ON s.sale_id = sd.sale_id
JOIN Product p ON sd.product_id = p.product_id;