var backend = "http://localhost:8080";
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
                    sessionStorage.setItem("nombreClienteFactura", "No hay ningun cliente seleccionado");
                    getUser();
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


async function getUser() {
    try {
        const response = await fetch(`${backend}/api/getUser`, {
            method: 'GET',
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            const user = await response.json();
            //alert(user.nombre);

        } else {
            console.error("Error al activar proveedor:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}
