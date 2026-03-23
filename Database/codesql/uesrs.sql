
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,     -- ไอดีผู้ใช้ (unique)
  username VARCHAR(50) UNIQUE NOT NULL,  -- ชื่อผู้ใช้ (ไม่ซ้ำกัน)
  password VARCHAR(255) NOT NULL,        -- รหัสผ่าน (เก็บแบบเข้ารหัส)
  email VARCHAR(100),                    -- อีเมล (ใช้ยืนยัน/กู้รหัสผ่าน)
  role ENUM('player','admin') DEFAULT 'player',  -- สิทธิ์
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- เวลาสมัคร
  last_login TIMESTAMP NULL DEFAULT NULL -- เวลาล็อกอินล่าสุด
);
