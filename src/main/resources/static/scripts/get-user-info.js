// scripts/get-user-info.js
document.addEventListener("DOMContentLoaded", async function() {
    try {
        const response = await fetch("/api/getUser", {
            method: "GET",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            const proveedor = await response.json();
            document.getElementById("nombre-proveedor").textContent = proveedor.nombre;
        } else {
            console.error("Error al obtener los datos del proveedor.");
        }
    } catch (error) {
        console.error("Error:", error);
    }
});
