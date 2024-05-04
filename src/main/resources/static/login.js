// login.js
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
                window.location.href = "/index.html"; // Redireccionar al dashboard después del inicio de sesión exitoso
            } else {
                alert("Usuario o contraseña incorrectos");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    });
});

