const express = require("express");
const bodyParser = require("body-parser");
const mysql = require("mysql2/promise");
const crypto = require("crypto");
const path = require("path");

const app = express();
const PORT = 3000;

// Database config
const dbConfig = {
    host: "localhost",
    user: "root",
    password: "1577",
    database: "cropcoremod_shop"
};

// Middleware
app.use(bodyParser.json());
app.use(express.static("public"));
app.use("/image", express.static(path.join(__dirname, "shop/image"))); // รูปสินค้า

// ฟังก์ชันสุ่มโค้ด Redeem
function generateCode() {
    return crypto.randomBytes(5).toString("hex").toUpperCase();
}

// =====================================
// 📦 โหลดรายการสินค้า
// =====================================
app.get("/api/items", async(req, res) => {
    try {
        const conn = await mysql.createConnection(dbConfig);
        const [rows] = await conn.query("SELECT * FROM items");
        await conn.end();
        res.json(rows);
    } catch (err) {
        console.error("❌ Error /api/items:", err);
        res.status(500).json({ message: "Server error" });
    }
});

// =====================================
// 🛒 ซื้อสินค้า
// =====================================
app.post("/api/buy", async(req, res) => {
    const { itemId, userId } = req.body;

    if (!itemId) {
        return res.status(400).json({ message: "❌ Missing itemId" });
    }

    try {
        const conn = await mysql.createConnection(dbConfig);

        // หาไอเทม
        const [itemRows] = await conn.query("SELECT * FROM items WHERE id = ?", [itemId]);
        if (itemRows.length === 0) {
            await conn.end();
            return res.status(404).json({ message: "❌ Item not found" });
        }

        const item = itemRows[0];

        // บันทึก order (ถ้าไม่มี user → ใส่ null)
        const [orderResult] = await conn.query(
            "INSERT INTO orders (user_id, item_id, status) VALUES (?, ?, 'pending')", [userId || null, itemId]
        );

        const orderId = orderResult.insertId;

        // บันทึก payment (mock)
        await conn.query(
            "INSERT INTO payments (order_id, amount, method, status) VALUES (?, ?, 'QR', 'waiting')", [orderId, item.price]
        );

        // สร้าง redeem code
        const code = generateCode();
        await conn.query(
            "INSERT INTO getcode (code, order_id) VALUES (?, ?)", [code, orderId]
        );

        await conn.end();

        res.json({
            message: `✅ Order created for ${item.name}`,
            order_id: orderId,
            amount: item.price,
            code: code
        });

    } catch (err) {
        console.error("❌ Error /api/buy:", err);
        res.status(500).json({ message: "Server error" });
    }
});

// =====================================
// 🎟️ Redeem โค้ด
// =====================================
app.post("/api/redeem", async(req, res) => {
    const { code } = req.body;

    if (!code) return res.status(400).json({ message: "❌ Missing code" });

    try {
        const conn = await mysql.createConnection(dbConfig);

        const [rows] = await conn.query(
            `SELECT getcode.id, getcode.is_used, items.name, orders.status
             FROM getcode
             JOIN orders ON getcode.order_id = orders.id
             JOIN items ON orders.item_id = items.id
             WHERE getcode.code = ?`, [code]
        );

        if (rows.length === 0) {
            await conn.end();
            return res.status(404).json({ message: "❌ Invalid code" });
        }

        const redeem = rows[0];

        if (redeem.is_used) {
            await conn.end();
            return res.status(400).json({ message: "❌ Code already used" });
        }

        if (redeem.status !== "paid" && redeem.status !== "pending") {
            await conn.end();
            return res.status(400).json({ message: "❌ Order not valid for redeem" });
        }

        // mark code as used
        await conn.query("UPDATE getcode SET is_used=TRUE WHERE code=?", [code]);

        await conn.end();
        res.json({ message: `✅ Redeem success! You received ${redeem.name}` });

    } catch (err) {
        console.error("❌ Error /api/redeem:", err);
        res.status(500).json({ message: "Server error" });
    }
});

// =====================================
// 🚀 Start server
// =====================================
app.listen(PORT, () => {
    console.log(`🚀 Server running at http://localhost:${PORT}`);
});