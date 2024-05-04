document.addEventListener("DOMContentLoaded", async function() {
    try {
        const response = await fetch("/api/proveedores/getProveedores");
        if (response.ok) {
            const proveedores = await response.json();
            mostrarProveedoresEnTabla(proveedores);
        } else {
            console.error("Error al obtener la lista de proveedores:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
});

function mostrarProveedoresEnTabla(proveedores) {
    const tabla = document.getElementById("tablaProveedores");
    const tbody = tabla.querySelector("tbody");

    // Limpiar el contenido existente de la tabla
    tbody.innerHTML = "";

    // Iterar sobre la lista de proveedores y agregar filas a la tabla
    proveedores.forEach(proveedor => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${proveedor.idProveedor}</td>
            <td>${proveedor.tipo}</td>
            <td>${proveedor.nombre}</td>
            <td>${proveedor.usuario}</td>
            <td>${proveedor.estado}</td>
        `;
        tbody.appendChild(fila);
    });
}