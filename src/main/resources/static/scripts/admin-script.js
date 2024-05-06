//let proveedores =[];
var backend = "http://localhost:8080";
let proveedores = [];

async function obtenerProveedores() {
    try {
        const response = await fetch(backend + "/api/proveedores/getProveedores", {
            method: 'GET',
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            const lista = await response.json();
            proveedores = lista; // Llenar la lista de proveedores
            console.log("Tamaño de la lista de proveedores:", proveedores.length);
            mostrarProveedoresEnTabla();
        } else {
            console.error("Error al obtener la lista de proveedores:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

obtenerProveedores();


/*document.addEventListener("DOMContentLoaded", async function() {

    try {
        const response = await fetch("/api/proveedores/getProveedores");
        if (response.ok) {
            proveedores = await response.json();
            console.log("Tamaño de la lista de proveedores:", proveedores.length);
            mostrarProveedoresEnTabla(proveedores);
        } else {
            console.error("Error al obtener la lista de proveedores:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
});*/



function mostrarProveedoresEnTabla() {
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
            <td class="divFlex">
                <form class="formsUgly" method="post">
                    <input type="hidden" name="idProveedor" value=${proveedor.idProveedor}/>
                    <button class="boton boton--primario" type="submit">Activar</button>
                </form>
                <form class="formsUgly" method="post">
                    <input type="hidden" name="idProveedor" value=${proveedor.idProveedor} />
                    <button class="boton boton--primario" type="submit">Desactivar</button>
                </form>
            </td>
        `;
        tbody.appendChild(fila);
    });
}