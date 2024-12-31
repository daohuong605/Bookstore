USE TienThoBookk

CREATE TABLE Employee (
    EmployeeID INT IDENTITY(1, 1) PRIMARY KEY,
    Employee_Name VARCHAR(255) NOT NULL,
    Sex VARCHAR (10),
    DOB DATE,
    Citizen_ID VARCHAR(20),
    Position VARCHAR(255),
    Phone_Number VARCHAR(15)
);

INSERT INTO Employee (Employee_Name, Sex, DOB, Citizen_ID, Position, Phone_Number)
VALUES
('John Doe', 'Male', '1985-03-15', 'CIT123456', 'Manager', '1234567890'),
('Jane Smith', 'Female', '1990-07-20', 'CIT234567', 'HR Officer', '0987654321'),
('Mike Brown', 'Male', '1982-11-12', 'CIT345678', 'Software Engineer', '1122334455'),
('Emily Davis', 'Female', '1995-04-30', 'CIT456789', 'Accountant', '2233445566'),
('Chris Wilson', 'Male', '1988-02-18', 'CIT567890', 'Sales Representative', '3344556677'),
('Laura Johnson', 'Female', '1993-09-25', 'CIT678901', 'Marketing Specialist', '4455667788'),
('Daniel Lee', 'Male', '1987-01-05', 'CIT789012', 'Support Engineer', '5566778899'),
('Sophia White', 'Female', '1991-06-14', 'CIT890123', 'Designer', '6677889900'),
('James Taylor', 'Male', '1983-12-08', 'CIT901234', 'Operations Manager', '7788990011'),
('Megan Hall', 'Female', '1997-08-19', 'CIT012345', 'Content Writer', '8899001122'),
('Henry Clark', 'Male', '1986-10-02', 'CIT123890', 'IT Analyst', '9900112233'),
('Anna Lewis', 'Female', '1992-03-11', 'CIT234901', 'Project Manager', '1011223344'),
('Matthew Adams', 'Male', '1984-05-21', 'CIT345012', 'Team Leader', '1122334455'),
('Olivia Martin', 'Female', '1996-12-15', 'CIT456123', 'Recruiter', '2233445566'),
('Ethan Wright', 'Male', '1989-07-07', 'CIT567234', 'Network Engineer', '3344556677'),
('Grace Walker', 'Female', '1994-09-03', 'CIT678345', 'Data Analyst', '4455667788'),
('Samuel Green', 'Male', '1981-02-28', 'CIT789456', 'Field Officer', '5566778899'),
('Ava King', 'Female', '1998-11-22', 'CIT890567', 'Social Media Specialist', '6677889900'),
('Andrew Scott', 'Male', '1990-01-17', 'CIT901678', 'Trainer', '7788990011'),
('Chloe Moore', 'Female', '1993-04-26', 'CIT012789', 'Business Analyst', '8899001122');


CREATE TABLE Genre (
    GenreID INT IDENTITY(1,1) PRIMARY KEY,
    GenreName VARCHAR(255) NOT NULL
);
-- Inserting 20 rows into Genre table
INSERT INTO Genre (GenreName)
VALUES
('Fiction'),
('Science Fiction'),
('Fantasy'),
('Mystery'),
('Romance'),
('Historical'),
('Thriller'),
('Non-Fiction'),
('Biography'),
('Autobiography'),
('Self-Help'),
('Poetry'),
('Children'),
('Young Adult'),
('Health'),
('Business'),
('Cooking'),
('Travel'),
('Art'),
('Science');

-- Bảng Sách
CREATE TABLE Book (
    BookID INT IDENTITY(1,1) PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    Author VARCHAR(255),
    GenreID INT,
    Publisher VARCHAR(255),
    Price DECIMAL(10, 2) NOT NULL,
    Stock INT NOT NULL,
	Image VARCHAR(255),
    FOREIGN KEY (GenreID) REFERENCES Genre(GenreID)
);


-- Inserting 20 rows into Book table
INSERT INTO Book (Title, Author, GenreID, Publisher, Price, Stock)
VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 1, 'Scribner', 10.99, 50),
('Dune', 'Frank Herbert', 2, 'Chilton Books', 15.99, 40),
('The Hobbit', 'J.R.R. Tolkien', 3, 'Houghton Mifflin', 12.99, 30),
('Sherlock Holmes', 'Arthur Conan Doyle', 4, 'Penguin Books', 9.99, 60),
('Pride and Prejudice', 'Jane Austen', 5, 'T. Egerton', 7.99, 70),
('1984', 'George Orwell', 6, 'Secker & Warburg', 14.99, 20),
('The Da Vinci Code', 'Dan Brown', 7, 'Doubleday', 11.99, 25),
('The Diary of a Young Girl', 'Anne Frank', 8, 'Contact Publishing', 8.99, 55),
('Becoming', 'Michelle Obama', 9, 'Crown Publishing', 17.99, 10),
('The Catcher in the Rye', 'J.D. Salinger', 10, 'Little, Brown and Company', 6.99, 65),
('Educated', 'Tara Westover', 11, 'Random House', 13.99, 35),
('Where the Crawdads Sing', 'Delia Owens', 12, 'Putnam', 16.99, 40),
('Harry Potter and the Sorcerer Stone', 'J.K. Rowling', 13, 'Bloomsbury', 10.99, 75),
('The Alchemist', 'Paulo Coelho', 14, 'HarperOne', 9.99, 30),
('The Subtle Art of Not Giving a F*ck', 'Mark Manson', 15, 'HarperOne', 12.99, 50),
('The Art of War', 'Sun Tzu', 16, 'Oxford University Press', 5.99, 40),
('Sapiens', 'Yuval Noah Harari', 17, 'Harvill Secker', 18.99, 20),
('The Lean Startup', 'Eric Ries', 18, 'Crown Business', 14.99, 30),
('The Road', 'Cormac McCarthy', 19, 'Alfred A. Knopf', 13.99, 60),
('Astrophysics for People in a Hurry', 'Neil deGrasse Tyson', 20, 'W.W. Norton & Company', 11.99, 25);


CREATE TABLE Customer (
    CustomerID INT IDENTITY(1,1) PRIMARY KEY,
    Customer_Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE,
    Phone VARCHAR(15),
    Address VARCHAR(255)
);

-- Inserting 20 rows into Customer table
INSERT INTO Customer (Customer_Name, Email, Phone, Address)
VALUES
('John Adams', 'john.adams@example.com', '1234567890', '123 Elm Street'),
('Mary Brown', 'mary.brown@example.com', '2345678901', '456 Oak Street'),
('James Green', 'james.green@example.com', '3456789012', '789 Pine Street'),
('Linda White', 'linda.white@example.com', '4567890123', '101 Maple Street'),
('Michael Black', 'michael.black@example.com', '5678901234', '202 Birch Street'),
('Jennifer Blue', 'jennifer.blue@example.com', '6789012345', '303 Cedar Street'),
('David Red', 'david.red@example.com', '7890123456', '404 Redwood Street'),
('Elizabeth Grey', 'elizabeth.grey@example.com', '8901234567', '505 Willow Street'),
('Joseph Silver', 'joseph.silver@example.com', '9012345678', '606 Cedar Street'),
('Sarah Gold', 'sarah.gold@example.com', '0123456789', '707 Pine Street'),
('Daniel Orange', 'daniel.orange@example.com', '1234567890', '808 Maple Street'),
('Emily Yellow', 'emily.yellow@example.com', '2345678901', '909 Oak Street'),
('Jacob Purple', 'jacob.purple@example.com', '3456789012', '101 Birch Street'),
('Ava Pink', 'ava.pink@example.com', '4567890123', '202 Cedar Street'),
('Sophia White', 'sophia.white@example.com', '5678901234', '303 Willow Street'),
('Benjamin Green', 'benjamin.green@example.com', '6789012345', '404 Pine Street'),
('Charlotte Blue', 'charlotte.blue@example.com', '7890123456', '505 Redwood Street'),
('Henry Brown', 'henry.brown@example.com', '8901234567', '606 Oak Street'),
('Isabella Black', 'isabella.black@example.com', '9012345678', '707 Maple Street');

DROP DATABASE IF EXISTS TienThoBookk
-- Bảng Đơn hàng
CREATE TABLE Orders (
    OrderID INT IDENTITY(1,1) PRIMARY KEY,
    CustomerID INT,
    OrderDate DATE NOT NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    EmployeeID INT,
    Status VARCHAR(50),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);

ALTER TABLE Orders
ALTER COLUMN Status VARCHAR(255);

select * from Book


-- Inserting 20 rows into Orders table
INSERT INTO Orders (CustomerID, OrderDate, TotalAmount, EmployeeID, Status)
VALUES
(1, '2024-01-15', 59.99, 1, 'Completed'),
(2, '2024-01-16', 99.99, 2, 'Completed'),
(3, '2024-01-17', 29.99, 2, 'Pending'),
(4, '2024-01-18', 19.99, 4, 'Completed'),
(5, '2024-01-19', 79.99, 5, 'Pending'),
(6, '2024-01-20', 49.99, 6, 'Completed'),
(7, '2024-01-21', 119.99, 7, 'Pending'),
(8, '2024-01-22', 39.99, 8, 'Completed'),
(9, '2024-01-23', 89.99, 9, 'Completed'),
(10, '2024-01-24', 59.99, 6, 'Pending'),
(11, '2024-01-25', 69.99, 6, 'Completed'),
(12, '2024-01-26', 39.99, 5, 'Completed'),
(13, '2024-01-27', 99.99, 4, 'Pending'),
(14, '2024-01-28', 79.99, 4, 'Completed'),
(15, '2024-01-29', 119.99, 4, 'Completed'),
(16, '2024-01-30', 29.99, 3, 'Pending'),
(17, '2024-01-31', 89.99, 3, 'Completed'),
(6, '2024-02-01', 49.99, 2, 'Pending'),
(5, '2024-02-02', 59.99, 2, 'Completed'),
(4, '2024-02-03', 99.99, 2, 'Completed');

-- Bảng Chi tiết đơn hàng
CREATE TABLE OrderDetail (
    OrderDetailID INT IDENTITY(1,1) PRIMARY KEY,
    OrderID INT,
    BookID INT,
    Quantity INT NOT NULL,
    SubTotal DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (BookID) REFERENCES Book(BookID)
);

INSERT INTO OrderDetail (OrderID, BookID, Quantity, SubTotal)
VALUES
(21, 32, 2, 21.98),  
(22, 22, 3, 47.97),  
(23, 22, 1, 12.99),  
(24, 21, 2, 19.98),  
(25, 20, 1, 7.99),  
(26, 20, 5, 49.95),  
(27, 20, 3, 35.97),  
(28, 18, 1, 8.99),  
(29, 17, 2, 23.98),  
(30, 16, 1, 6.99), 
(31, 15, 4, 55.96), 
(32, 14, 2, 27.98), 
(33, 13, 3, 32.97), 
(34, 19, 1, 9.99),  
(35, 18, 5, 64.95), 
(36, 17, 3, 17.97), 
(37, 16, 2, 27.98), 
(38, 15, 4, 59.96), 
(39, 14, 2, 23.98), 
(40, 13, 3, 35.97);

SELECT * FROM OrderDetail
SELECT * FROM Orders
CREATE VIEW RevenuePerDay AS
SELECT 
    o.OrderDate,
    SUM(od.SubTotal) AS TotalRevenue
FROM 
    Orders o
JOIN 
    OrderDetail od ON o.OrderID = od.OrderID
GROUP BY 
    o.OrderDate;

SELECT * FROM RevenuePerDay

--------------------------------
CREATE VIEW BooksSoldPerCustomerPerDay AS
SELECT 
    c.Customer_Name,
    SUM(od.Quantity) AS TotalBooksSold
FROM 
    OrderDetail od
JOIN 
    Orders o ON od.OrderID = o.OrderID
JOIN 
    Customer c ON o.CustomerID = c.CustomerID
GROUP BY c.Customer_Name;

SELECT * FROM BooksSoldPerCustomerPerDay

---------------------------------------
CREATE VIEW StockByGenre AS
SELECT 
    g.GenreName,
    SUM(b.Stock) AS TotalStock
FROM 
    Book b
JOIN 
    Genre g ON b.GenreID = g.GenreID
GROUP BY 
    g.GenreName;

SELECT * 
FROM StockByGenre

------------------------------------------------
CREATE VIEW v_BooksSalesInfo AS
WITH 
CTE_TotalSales AS (
    SELECT 
        od.BookID,
        SUM(od.Quantity) AS TotalSold,
        SUM(od.Quantity * b.Price) AS TotalRevenue  -- Sử dụng cột Price từ bảng Book
    FROM 
        OrderDetail od
    JOIN
        Book b ON od.BookID = b.BookID  -- Thêm phép JOIN với bảng Book để lấy giá
    GROUP BY 
        od.BookID
),
CTE_Stock AS (
    SELECT 
        b.BookID,
        b.Stock
    FROM 
        Book b
),
CTE_Genre AS (
    SELECT 
        b.BookID,
        g.GenreName
    FROM 
        Book b
    JOIN 
        Genre g ON b.GenreID = g.GenreID
)
SELECT 
    g.GenreName,
    COALESCE(ts.TotalSold, 0) AS TotalSold,
    COALESCE(ts.TotalRevenue, 0) AS TotalRevenue,
    COALESCE(s.Stock, 0) AS TotalStock
FROM 
    CTE_Genre g
LEFT JOIN 
    CTE_TotalSales ts ON g.BookID = ts.BookID
LEFT JOIN 
    CTE_Stock s ON g.BookID = s.BookID;

SELECT * FROM v_BooksSalesInfo

SELECT GenreName, TotalStock, TotalSold, TotalRevenue FROM v_BooksSalesInfo;

SELECT * FROM fn_my_permissions('v_BooksSalesInfo', 'OBJECT');

SELECT SUM(BookID) AS Books FROM OrderDetail od JOIN Orders o ON od.OrderID = o.OrderID WHERE o.Status = 'Completed'

SELECT * FROM Book
