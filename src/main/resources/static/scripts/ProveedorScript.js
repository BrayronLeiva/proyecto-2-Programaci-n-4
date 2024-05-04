// login.js
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("add-proveedor").addEventListener("submit", async function(event) {
        event.preventDefault();
        const nombre = document.getElementById("nombre").value;
        const tipo = document.getElementById("tipo").value;
        const usuario = document.getElementById("usuario").value;
        const clave = document.getElementById("clave").value;

        try {
            const response = await fetch("/api/proveedor/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json" // Ajustar el Content-Type
                },
                body: JSON.stringify({nombre, tipo, usuario, clave})
            }); //fin de peticion

            if (response.ok) {
                alert("Proveedor Registrado Correctamente")
                window.location.href = "/add-proveedor";
            } else {
                alert("Error Bro");
                window.location.href = "/add-proveedor";
            }
        } catch (error) {
            console.error("Error:", error);
        }
    });
});