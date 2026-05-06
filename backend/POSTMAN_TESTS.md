# Beyaz Esya Satis Otomasyonu - Postman Testleri

Base URL:

```text
http://localhost:8080
```

Tum JSON cevaplari basarili islemlerde genel olarak su formattadir:

```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": {},
  "timestamp": "2026-05-06T19:45:00"
}
```

## Calistirma Adimlari

1. SQL Server/SSMS uzerinde `BeyazEsyaDB` veritabaninin calistigindan emin ol.
2. `src/main/resources/application.properties` icindeki kullanici adi ve sifreyi kendi SQL Server bilgilerine gore kontrol et.
3. Backend klasorunde uygulamayi baslat:

```powershell
mvn spring-boot:run
```

4. Postman'de `Content-Type: application/json` header'i ile istek gonder.

## Customer

### GET /api/customers

URL:

```text
http://localhost:8080/api/customers
```

Method: `GET`

Body: Yok

Beklenen status: `200 OK`

Response:

```json
{
  "success": true,
  "message": "Customers listed successfully",
  "data": [],
  "timestamp": "2026-05-06T19:45:00"
}
```

### POST /api/customers

URL:

```text
http://localhost:8080/api/customers
```

Method: `POST`

Body:

```json
{
  "firstName": "Ahmet",
  "lastName": "Yilmaz",
  "phone": "05551234567",
  "email": "ahmet.yilmaz@example.com"
}
```

Beklenen status: `201 CREATED`

Response:

```json
{
  "success": true,
  "message": "Customer created successfully",
  "data": {
    "customerId": 1,
    "firstName": "Ahmet",
    "lastName": "Yilmaz",
    "phone": "05551234567",
    "email": "ahmet.yilmaz@example.com",
    "createdAt": "2026-05-06T19:45:00"
  },
  "timestamp": "2026-05-06T19:45:00"
}
```

### PUT /api/customers/{id}

URL:

```text
http://localhost:8080/api/customers/1
```

Method: `PUT`

Body:

```json
{
  "firstName": "Ahmet",
  "lastName": "Yilmaz",
  "phone": "05550001122",
  "email": "ahmet.new@example.com"
}
```

Beklenen status: `200 OK`

### DELETE /api/customers/{id}

URL:

```text
http://localhost:8080/api/customers/1
```

Method: `DELETE`

Body: Yok

Beklenen status: `204 NO CONTENT`

## Employee

### POST /api/employees

URL:

```text
http://localhost:8080/api/employees
```

Method: `POST`

Body:

```json
{
  "firstName": "Mehmet",
  "lastName": "Kaya",
  "phone": "05557654321",
  "position": "Sales Representative",
  "salary": 35000
}
```

Beklenen status: `201 CREATED`

Ana testler:

```text
GET    /api/employees
GET    /api/employees/1
POST   /api/employees
PUT    /api/employees/1
DELETE /api/employees/1
```

## Category

### POST /api/categories

URL:

```text
http://localhost:8080/api/categories
```

Method: `POST`

Body:

```json
{
  "categoryName": "Buzdolabi"
}
```

Beklenen status: `201 CREATED`

Ana testler:

```text
GET    /api/categories
GET    /api/categories/1
POST   /api/categories
PUT    /api/categories/1
DELETE /api/categories/1
```

## Product

### POST /api/products

URL:

```text
http://localhost:8080/api/products
```

Method: `POST`

Body:

```json
{
  "categoryId": 1,
  "productName": "No Frost Buzdolabi",
  "brand": "Arcelik",
  "price": 24999.90,
  "stockQuantity": 12
}
```

Beklenen status: `201 CREATED`

### GET /api/products/category/{categoryId}

URL:

```text
http://localhost:8080/api/products/category/1
```

Method: `GET`

Body: Yok

Beklenen status: `200 OK`

### PUT /api/products/{productId}/stock

URL:

```text
http://localhost:8080/api/products/1/stock
```

Method: `PUT`

Body:

```json
{
  "newStock": 25
}
```

Beklenen status: `200 OK`

## Sale

### POST /api/sales

URL:

```text
http://localhost:8080/api/sales
```

Method: `POST`

Body:

```json
{
  "customerId": 1,
  "employeeId": 1,
  "saleDate": "2026-05-06T19:45:00",
  "totalAmount": 24999.90,
  "saleStatus": "COMPLETED"
}
```

Beklenen status: `201 CREATED`

### POST /api/sales/create-basic

URL:

```text
http://localhost:8080/api/sales/create-basic
```

Method: `POST`

Body:

```json
{
  "customerId": 1,
  "employeeId": 1
}
```

Beklenen status: `201 CREATED`

### GET /api/sales/customer/{customerId}

URL:

```text
http://localhost:8080/api/sales/customer/1
```

Method: `GET`

Beklenen status: `200 OK`

## SaleDetail

### POST /api/sale-details

URL:

```text
http://localhost:8080/api/sale-details
```

Method: `POST`

Body:

```json
{
  "saleId": 1,
  "productId": 1,
  "quantity": 2,
  "unitPrice": 24999.90
}
```

Beklenen status: `201 CREATED`

Ana testler:

```text
GET    /api/sale-details
GET    /api/sale-details/1
GET    /api/sale-details/sale/1
POST   /api/sale-details
PUT    /api/sale-details/1
DELETE /api/sale-details/1
```

## View Endpointleri

### GET /api/views/product-stock

URL:

```text
http://localhost:8080/api/views/product-stock
```

Method: `GET`

Beklenen status: `200 OK`

Response data ornegi:

```json
[
  {
    "productId": 1,
    "productName": "No Frost Buzdolabi",
    "categoryName": "Buzdolabi",
    "brand": "Arcelik",
    "price": 24999.90,
    "stockQuantity": 25
  }
]
```

### GET /api/views/sale-report

URL:

```text
http://localhost:8080/api/views/sale-report
```

Method: `GET`

Beklenen status: `200 OK`

Response data ornegi:

```json
[
  {
    "saleId": 1,
    "saleDate": "2026-05-06T19:45:00",
    "saleStatus": "COMPLETED",
    "customerName": "Ahmet Yilmaz",
    "employeeName": "Mehmet Kaya",
    "productName": "No Frost Buzdolabi",
    "quantity": 2,
    "unitPrice": 24999.90,
    "lineTotal": 49999.80
  }
]
```

## Report Endpointi

### GET /api/reports/customer-sales/{customerId}

URL:

```text
http://localhost:8080/api/reports/customer-sales/1
```

Method: `GET`

Beklenen status: `200 OK`

Response data ornegi:

```json
[
  {
    "saleId": 1,
    "saleDate": "2026-05-06T19:45:00",
    "totalAmount": 24999.90,
    "saleStatus": "COMPLETED"
  }
]
```

## Hata Testleri

### 404 NOT FOUND

```text
GET http://localhost:8080/api/products/999999
```

Beklenen status: `404 NOT FOUND`

### 400 BAD REQUEST

```text
POST http://localhost:8080/api/products
```

Body:

```json
{
  "categoryId": null,
  "productName": "",
  "brand": "",
  "price": -1,
  "stockQuantity": -5
}
```

Beklenen status: `400 BAD REQUEST`

## Ilk Kontrol Edilecek Yerler

- SQL Server servisinin calistigini kontrol et.
- `BeyazEsyaDB` adinin dogru oldugunu kontrol et.
- `application.properties` icindeki kullanici adi ve sifreyi kontrol et.
- SQL Server TCP/IP ayari ve `1433` portunu kontrol et.
- View kolon adlari projection/entity alanlariyla uyumlu mu kontrol et.
- Stored procedure parametreleri `@customer_id`, `@product_id`, `@new_stock`, `@employee_id` olarak tanimli mi kontrol et.
