USE BeyazEsyaDB;
GO

CREATE TABLE Customer (
    customer_id INT IDENTITY(1,1) PRIMARY KEY,
    first_name NVARCHAR(50) NOT NULL,
    last_name NVARCHAR(50) NOT NULL,
    phone NVARCHAR(20) NOT NULL UNIQUE,
    email NVARCHAR(100) NOT NULL UNIQUE,
    created_at DATETIME NOT NULL DEFAULT GETDATE()
);

CREATE TABLE Employee (
    employee_id INT IDENTITY(1,1) PRIMARY KEY,
    first_name NVARCHAR(50) NOT NULL,
    last_name NVARCHAR(50) NOT NULL,
    phone NVARCHAR(20) NOT NULL UNIQUE,
    position NVARCHAR(50) NOT NULL,
    salary DECIMAL(10,2) NOT NULL CHECK (salary >= 0),
    created_at DATETIME NOT NULL DEFAULT GETDATE()
);

CREATE TABLE Category (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Product (
    product_id INT IDENTITY(1,1) PRIMARY KEY,
    category_id INT NOT NULL,
    product_name NVARCHAR(100) NOT NULL,
    brand NVARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price > 0),
    stock_quantity INT NOT NULL CHECK (stock_quantity >= 0),

    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

CREATE TABLE Sale (
    sale_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT NOT NULL,
    employee_id INT NOT NULL,
    sale_date DATETIME NOT NULL DEFAULT GETDATE(),
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0 CHECK (total_amount >= 0),
    sale_status NVARCHAR(20) NOT NULL DEFAULT 'Pending'
        CHECK (sale_status IN ('Pending', 'Completed', 'Cancelled')),

    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
);

CREATE TABLE SaleDetail (
    sale_detail_id INT IDENTITY(1,1) PRIMARY KEY,
    sale_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL CHECK (unit_price > 0),

    FOREIGN KEY (sale_id) REFERENCES Sale(sale_id),
    FOREIGN KEY (product_id) REFERENCES Product(product_id),

    UNIQUE (sale_id, product_id)
);