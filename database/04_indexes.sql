USE BeyazEsyaDB;
GO

-- Müşterinin satışlarını hızlı bulmak için
CREATE INDEX IX_Sale_CustomerId
ON Sale(customer_id);

-- Personelin yaptığı satışları hızlı bulmak için
CREATE INDEX IX_Sale_EmployeeId
ON Sale(employee_id);

-- Kategoriye göre ürünleri hızlı listelemek için
CREATE INDEX IX_Product_CategoryId
ON Product(category_id);

-- Bir ürünün hangi satış detaylarında geçtiğini hızlı bulmak için
CREATE INDEX IX_SaleDetail_ProductId
ON SaleDetail(product_id);