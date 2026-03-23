// กำหนด userId แบบ fix ชั่วคราว (สมมุติ userId = 1)
let currentUserId = 1;

// โหลดสินค้า
function loadItems() {
    fetch("/api/items")
        .then(res => res.json())
        .then(items => {
            const container = document.getElementById("items");
            container.innerHTML = "";

            items.forEach(item => {
                let imageName = item.name.toLowerCase().replace(/ /g, "_") + ".png";
                const div = document.createElement("div");
                div.innerHTML = `
                    <img src="/image/${imageName}" width="32" height="32" style="vertical-align:middle; margin-right:10px;">
                    ${item.name} - ${item.price} coins 
                    <button onclick="buyItem(${item.id})">ซื้อ</button>
                `;
                container.appendChild(div);
            });
        })
        .catch(err => alert("⚠️ Error loading items: " + err));
}

// ซื้อสินค้า
function buyItem(itemId) {
    fetch("/api/buy", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ itemId, userId: currentUserId }) // ส่ง userId = 1
        })
        .then(res => res.json())
        .then(data => {
            if (data.code) {
                alert(`${data.message}\n🎟️ โค้ดของคุณ: ${data.code}`);
            } else {
                alert("⚠️ " + data.message);
            }
        })
        .catch(err => alert("⚠️ Error: " + err));
}