
var backend = "http://localhost:8080";
let facturas = [];

document.addEventListener("DOMContentLoaded", function() {
    obtenerFacturas()
});


async function obtenerFacturas() {
    try {
        const response = await fetch(backend + "/api/facturas/getFacturas", {
            method: 'GET',
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            const lista = await response.json();
            clientes = lista; // Llenar la lista de proveedores
            console.log("Tamaño de la lista de facturas:", clientes.length);
            mostrarFacturasEnTabla();
        } else {
            console.error("Error al obtener la lista de proveedores:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}


function mostrarFacturasEnTabla() {
    const tabla = document.getElementById("tablaFacturas");
    const tbody = tabla.querySelector("tbody");
    const titulo = document.getElementById("tituloTablaFacturas");
    const empty = document.getElementById("emptyTablaFacturas");

    // Limpiar el contenido existente de la tabla
    tbody.innerHTML = "";

    if(clientes.length === 0){
        tabla.style.display = "none";
        titulo.style.display = "none";
        empty.style.display = "block";
    }
    else{
        tabla.style.display = "table";
        titulo.style.display = "block";
        empty.style.display = "none";
    }

    // Iterar sobre la lista de proveedores y agregar filas a la tabla
    clientes.forEach(factura => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${factura.idFactura}</td>
            <td>${factura.nombreCliente}</td>
            <td>${factura.monto}</td>
            <td class="divFlex">
                <form class="formsUgly">
                    <input id="PDF" type="hidden" name="opcion" value="${factura.idFactura}" />
                    <button class="boton--primario boton">Descargar PDF</button>
                </form>
                <form class="formsUgly" >
                    <input id="XML" type="hidden" name="opcion" value="${factura.idFactura}" />
                     <button class="boton--primario boton">Descargar XML</button>
                </form>       
               
            </td>
        `;
        tbody.appendChild(fila);
    });
}

document.addEventListener("DOMContentLoaded", function() {
    const tabla = document.getElementById("tablaFacturas");
    const tbody = tabla.querySelector("tbody");
    tbody.addEventListener('click', function(event) {
        const target = event.target;

        // Verificar si el clic fue en un botón dentro de una fila
        if (target.tagName.toLowerCase() === 'button') {
            // Obtener el formulario padre del botón clicado
            const form = target.closest('form');

            // Obtener el idProveedor del input del formulario
            const idAction = form.querySelector('input[name="opcion"]').value;

            // Verificar si el botón fue de activar o desactivar
            if (target.textContent === 'Descargar PDF') {
                // Lógica para aumentar
                console.log("Descargando Pdf");

            } else if (target.textContent === 'Descargar XML') {
                // Lógica para disminuir
                console.log("Descargando Xml");
            }
            // Evitar el comportamiento por defecto del botón (enviar el formulario)
            event.preventDefault();
        }
    });

});