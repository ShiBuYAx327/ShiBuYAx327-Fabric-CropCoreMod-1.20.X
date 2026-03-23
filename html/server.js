const express = require("express");
const mysql = require("mysql2");
const cors = require("cors");
const path = require("path");

const app = express();
app.use(cors());
app.use(express.json());

const db = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "1577",
    database: "cropcoremod_shop"
});

app.post("/register", (req, res) => {
    const { username, password, role } = req.body;

    if (!username || !password)
        return res.json({ success: false, message: "กรุณากรอกข้อมูลให้ครบ" });

    db.query("SELECT * FROM users WHERE username=?", [username], (err, result) => {
        if (err) {
            console.error("❌ Database Error:", err);
            return res.status(500).json({ success: false, message: "Database error" });
        }

        if (result.length > 0)
            return res.json({ success: false, message: "ชื่อผู้ใช้นี้ถูกใช้แล้ว" });


            res.json({ success: true, message: "สมัครสมาชิกสำเร็จ!" });
        });
    });


app.post("/login", (req, res) => {
    const { username, password } = req.body;

    if (!username || !password) {
        return res.json({ success: false, message: "กรุณากรอกชื่อผู้ใช้และรหัสผ่าน" });
    }

    const sql = "SELECT id, username, role FROM users WHERE username=? AND password=?";
    db.query(sql, [username, password], (err, result) => {
        if (err) {
            console.error("Database Error:", err);
            return res.status(500).json({ success: false, message: "Database error" });
        }

        if (result.length > 0) {
            const user = result[0];
            db.query("UPDATE users SET last_login=NOW() WHERE id=?", [user.id]);
            res.json({ success: true, user });
        } else {
            res.json({ success: false, message: "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง" });
        }
    });
});


app.get("/items", (req, res) => {
    db.query("SELECT * FROM items", (err, result) => {
        if (err) return res.status(500).send("Database error");
        res.json(result);
    });
});


app.post("/order", (req, res) => {
    const { user_id, item_id } = req.body;

    const sqlOrder =
        "INSERT INTO orders (user_id, item_id, status, created_at) VALUES (?, ?, 'pending', NOW())";
    db.query(sqlOrder, [user_id, item_id], (err, orderResult) => {

        const orderId = orderResult.insertId;
        const genCode = Math.random().toString(36).substring(2, 12).toUpperCase();

        const sqlCode =
            "INSERT INTO getcode (code, order_id, is_used, created_at) VALUES (?, ?, 0, NOW())";
        db.query(sqlCode, [genCode, orderId], err2 => {
            res.json({
                success: true,
                message: "สั่งซื้อสำเร็จ!",
                code: genCode
            });
        });
    });
});


app.post("/verifycode", (req, res) => {
    const { code, player } = req.body;

    const sql = ` SELECT g.code, g.is_used, i.name AS item_name
                    FROM getcode g
                    JOIN orders o ON g.order_id = o.id
                    JOIN items i ON o.item_id = i.id
                    WHERE g.code = ? LIMIT 1 `;

    db.query(sql, [code], (err, result) => {

        if (result.length === 0)
            return res.json({ success: false, message: "ไม่มีโค้ดนี้ในระบบ" });

        const row = result[0];

        if (row.is_used === 1)
            return res.json({ success: false, message: "โค้ดถูกใช้งานแล้ว" });

        db.query("UPDATE getcode SET is_used = 1 WHERE code = ?", [code]);

        res.json({
            success: true,
            message: `โค้ดถูกต้อง! คุณได้รับ ${row.item_name}`,
            item: row.item_name
        });
    });
});


app.use(express.static(__dirname));
app.get("/", (req, res) => {
    res.sendFile(path.join(__dirname, "login.html"));
});


app.listen(3000, () =>
    console.log("Server running on http://127.0.0.1:3000")
);