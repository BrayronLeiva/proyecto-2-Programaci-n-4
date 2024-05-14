
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("loginForm").addEventListener("submit", async function(event) {
        event.preventDefault();
        const usuario = document.getElementById("username").value;
        const clave = document.getElementById("password").value;

        try {
            const response = await fetch("/api/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json" // Ajustar el Content-Type
                },
                body: JSON.stringify({usuario, clave})
            }); //fin de peticion

            if (response.ok) {
                const data = await response.json();
                if (data.admin) {
                    // Si el usuario es administrador, redirigir a la sección de administrador
                    window.location.href = "/admin.html";
                } else {
                    // Si el usuario es normal, redirigir al dashboard normal
                    sessionStorage.setItem("nombreClienteFactura", "Nada");
                    alert(sessionStorage.getItem("nombreClienteFactura"));
                    window.location.href = "/registrar-factura.html";
                }
            } else {
                alert("Usuario o contraseña incorrectos");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    });
});

