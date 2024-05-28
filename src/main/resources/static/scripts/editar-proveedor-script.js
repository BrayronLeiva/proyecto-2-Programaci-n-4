// scripts/editar-proveedor-script.js
document.addEventListener("DOMContentLoaded", function() {
    obtenerDatosProveedor();

    document.getElementById("editar-proveedor-form").addEventListener("submit", async function(event) {
        event.preventDefault();

        const nombre = document.getElementById("nombre").value;
        const tipo = document.getElementById("tipo").value;
        const usuario = document.getElementById("usuario").value;
        const clave = document.getElementById("clave").value;

        try {
            const response = await fetch("/api/proveedores/update", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ nombre, tipo, usuario, clave })
            });

            if (response.ok) {
                alert("Datos del proveedor actualizados correctamente.");
            } else {
                alert("Error al actualizar los datos del proveedor.");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    });
});

async function obtenerDatosProveedor() {
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
            document.getElementById("nombre").value = proveedor.nombre;
            document.getElementById("tipo").value = proveedor.tipo;
            document.getElementById("usuario").value = proveedor.usuario;
            document.getElementById("clave").value = proveedor.clave;
        } else {
            alert("Error al obtener los datos del proveedor.");
        }
    } catch (error) {
        console.error("Error:", error);
    }
}
