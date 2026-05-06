USE BeyazEsyaDB;
GO

INSERT INTO Category (category_name) VALUES
('Buzdolabı'),
('Çamaşır Makinesi'),
('Bulaşık Makinesi'),
('Fırın'),
('Mikrodalga'),
('Kurutma Makinesi'),
('Klima'),
('Televizyon'),
('Süpürge'),
('Derin Dondurucu');

INSERT INTO Customer (first_name, last_name, phone, email) VALUES
('Ahmet','Yılmaz','05321234567','ahmet.yilmaz@gmail.com'),
('Ayşe','Demir','05439876543','ayse.demir@hotmail.com'),
('Mehmet','Kaya','05551234567','mehmet.kaya@gmail.com'),
('Fatma','Çelik','05339871234','fatma.celik@yahoo.com'),
('Ali','Şahin','05421239876','ali.sahin@gmail.com'),
('Zeynep','Koç','05539874561','zeynep.koc@hotmail.com'),
('Hasan','Arslan','05327654321','hasan.arslan@gmail.com'),
('Elif','Aydın','05439871200','elif.aydin@gmail.com'),
('Murat','Öztürk','05551239876','murat.ozturk@hotmail.com'),
('Cem','Kurt','05321112233','cem.kurt@gmail.com');

INSERT INTO Employee (first_name, last_name, phone, position, salary) VALUES
('Kemal','Ak','05330000001','Satış Temsilcisi',14000),
('Burcu','Tan','05330000002','Satış Temsilcisi',14500),
('Emre','Güneş','05330000003','Kasiyer',12000),
('Derya','Yıldız','05330000004','Mağaza Müdürü',22000),
('Serkan','Polat','05330000005','Satış Temsilcisi',15000),
('Gizem','Kara','05330000006','Kasiyer',12500),
('Tolga','Yaman','05330000007','Depo Sorumlusu',11000),
('Ece','Aslan','05330000008','Satış Temsilcisi',15500),
('Okan','Er','05330000009','Kasiyer',11800),
('Deniz','Koç','05330000010','Bölge Müdürü',25000);

INSERT INTO Product (category_id, product_name, brand, price, stock_quantity) VALUES
(1,'No Frost Buzdolabı 600L','Arçelik',18500,15),
(1,'Çift Kapılı Buzdolabı','Bosch',21000,10),
(2,'9kg Çamaşır Makinesi','Samsung',13500,20),
(2,'7kg Çamaşır Makinesi','LG',12000,18),
(3,'A++ Bulaşık Makinesi','Beko',9500,25),
(4,'Ankastre Fırın','Vestel',8000,12),
(5,'Dijital Mikrodalga','Samsung',3500,30),
(6,'Kurutma Makinesi 8kg','Bosch',15000,9),
(7,'12000 BTU Klima','Daikin',22000,7),
(9,'Kablosuz Süpürge','Dyson',25000,5);

INSERT INTO Sale (customer_id, employee_id, sale_status) VALUES
(1,1,'Completed'),
(2,2,'Completed'),
(3,3,'Pending'),
(4,4,'Completed'),
(5,5,'Cancelled'),
(6,6,'Completed'),
(7,7,'Pending'),
(8,8,'Completed'),
(9,9,'Completed'),
(10,10,'Pending');

INSERT INTO SaleDetail (sale_id, product_id, quantity, unit_price) VALUES
(1,1,1,18500),
(1,3,1,13500),
(2,2,1,21000),
(3,4,2,12000),
(4,5,1,9500),
(5,6,1,8000),
(6,7,2,3500),
(7,8,1,15000),
(8,9,1,22000),
(9,10,1,25000);




