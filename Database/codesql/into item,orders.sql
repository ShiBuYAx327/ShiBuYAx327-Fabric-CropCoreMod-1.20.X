CREATE DATABASE minecraft_shop;
USE minecraft_shop;

-- ตารางสินค้า
CREATE TABLE items (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  price INT NOT NULL
);

-- ตารางคำสั่งซื้อ
CREATE TABLE orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  player VARCHAR(50) NOT NULL,
  item_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (item_id) REFERENCES items(id)
);

-- ใส่ไอเทมตัวอย่าง
INSERT INTO items (name, price) VALUES
('Diamond Sword', 100),
('Iron Pickaxe', 50),
('Golden Apple', 200);
