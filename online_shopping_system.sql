CREATE TABLE `Clothing` (
  `productID` varchar(50) PRIMARY KEY NOT NULL,
  `productName` varchar(100) DEFAULT NULL,
  `availableItems` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `size` varchar(20) DEFAULT NULL,
  `color` varchar(20) DEFAULT NULL
);


CREATE TABLE `Electronics` (
  `productID` varchar(50) PRIMARY KEY NOT NULL,
  `productName` varchar(100) DEFAULT NULL,
  `availableItems` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `brand` varchar(50) DEFAULT NULL,
  `warrantyPeriod` int(11) DEFAULT NULL
);

CREATE TABLE `users` (
  `username` varchar(60) NOT NULL PRIMARY KEY,
  `password` varchar(100) NOT NULL,
  `firstPurchaseCompleted` tinyint(1) NOT NULL DEFAULT 0,
  `shoppingCart` longblob DEFAULT NULL
);

INSERT INTO `Clothing` (`productID`, `productName`, `availableItems`, `price`, `size`, `color`) VALUES
('c1001', 'Summer Dress', 27, 49.99, 'Medium', 'Blue'),
('c1002', 'T-Shirt', 2, 19.99, 'Large', 'Black'),
('c1003', 'Jeans', 20, 39.99, 'Small', 'Indigo'),
('c1004', 'Hoodie', 25, 29.99, 'Medium', 'Gray'),
('c1005', 'Skirt', 1, 24.99, 'Small', 'Red'),
('c1006', 'Sweater', 10, 34.99, 'Large', 'Green'),
('c1007', 'Coat', 18, 59.99, 'Medium', 'Black'),
('c1008', 'Leggings', 22, 14.99, 'Small', 'Navy'),
('c1009', 'Blouse', 27, 19.99, 'Medium', 'White'),
('c1010', 'Shorts', 40, 29.99, 'Medium', 'Khaki'),
('c1011', 'Dress Shirt', 20, 44.99, 'Large', 'Blue'),
('c1012', 'Socks', 60, 4.99, 'OneSize', 'Assorted'),
('c1013', 'Belt', 50, 9.99, 'OneSize', 'Brown'),
('c1014', 'Swimsuit', 8, 39.99, 'Small', 'Striped'),
('c1015', 'Pajamas', 12, 49.99, 'Large', 'Patterned'),
('c1016', 'Tank Top', 30, 12.99, 'Small', 'Gray'),
('c1017', 'Underwear', 50, 6.99, 'Small', 'Assorted'),
('c1018', 'Scarf', 20, 19.99, 'OneSize', 'Blue');

INSERT INTO `Electronics` (`productID`, `productName`, `availableItems`, `price`, `brand`, `warrantyPeriod`) VALUES
('e1001', 'Smartphone', 0, 599.99, 'ABC Electronics', 12),
('e1002', 'Laptop', 9, 1299.99, 'XYZ Tech', 24),
('e1003', 'Tablet', 38, 399.99, 'DEF Tech', 18),
('e1004', 'Headphones', 59, 79.99, 'SoundMasters', 6),
('e1005', 'Smartwatch', 35, 149.99, 'GHI Tech', 12),
('e1006', 'Speaker', 44, 129.99, 'AudioTech', 12),
('e1007', 'Printer', 55, 199.99, 'PrintMaster', 24),
('e1008', 'Camera', 10, 899.99, 'CapturePro', 24),
('e1009', 'Gaming Console', 15, 499.99, 'GameTech', 12),
('e1010', 'Router', 30, 79.99, 'NetConnect', 12),
('e1011', 'External Hard Drive', 25, 129.99, 'DataStore', 24),
('e1012', 'Wireless Mouse', 40, 19.99, 'Peripherals', 6),
('e1013', 'Bluetooth Earbuds', 50, 59.99, 'AudioTech', 12),
('e1014', 'Keyboard', 20, 49.99, 'Peripherals', 12),
('e1015', 'Monitor', 10, 299.99, 'DisplayPro', 24),
('e1016', 'VR Headset', 5, 199.99, 'ImmerseTech', 12),
('e1017', 'Portable Charger', 25, 29.99, 'PowerUp', 12);


INSERT INTO `users` (`username`, `password`, `firstPurchaseCompleted`, `shoppingCart`) VALUES
('dhanuja', 'dhanuja', 0, NULL),
('hiru', '2001', 1, NULL),
('qq', 'www', 0, NULL);