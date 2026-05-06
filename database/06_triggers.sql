USE BeyazEsyaDB;
GO

-- SaleDetail’e ürün eklendiğinde product stok otomatik azalır

CREATE TRIGGER trg_DecreaseProductStock
ON SaleDetail
AFTER INSERT
AS
BEGIN
    UPDATE p
    SET p.stock_quantity = p.stock_quantity - i.quantity
    FROM Product p
    JOIN inserted i ON p.product_id = i.product_id;
END;
GO


-- SaleDetail’e ürün eklendiğinde total_amount otomatik güncellenir

CREATE TRIGGER trg_UpdateSaleTotalAmount
ON SaleDetail
AFTER INSERT
AS
BEGIN
    UPDATE s
    SET s.total_amount = s.total_amount + saleTotals.total_added_amount
    FROM Sale s
    JOIN (
        SELECT 
            sale_id,
            SUM(quantity * unit_price) AS total_added_amount
        FROM inserted
        GROUP BY sale_id
    ) saleTotals ON s.sale_id = saleTotals.sale_id;
END;
GO