
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
    tbody.addEventListener('click', async function(event) {
        event.preventDefault();
        const target = event.target;

        // Verificar si el clic fue en un botón dentro de una fila
        if (target.tagName.toLowerCase() === 'button') {
            // Obtener el formulario padre del botón clicado
            const form = target.closest('form');

            // Obtener el idFactura del input del formulario
            const idFactura = form.querySelector('input[name="opcion"]').value;
            if (target.textContent === 'Descargar PDF') {
                // Lógica para aumentar
                console.log("Descargando Pdf");
                await descargarPdf(idFactura);

            } else if (target.textContent === 'Descargar XML') {
                // Lógica para descargar XML
                console.log("Descargando Xml para la factura:", idFactura);
                await getFacturaforXML(idFactura);
            }
            // Evitar el comportamiento por defecto del botón (enviar el formulario)
            event.preventDefault();
        }
    });
});

async function getFacturaforXML(id) {
    try {
        const response = await fetch(`${backend}/api/facturas/renderXML/${id}`, {
            method: 'GET',
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            const factura = await response.json();
            //const xmlText = await response.text();
            const xmlText = renderXML(factura);
            const blob = new Blob([xmlText], { type: 'application/xml' });

            window.open(URL.createObjectURL(blob));

            console.log("XML renderizado correctamente\n");
        } else {
            console.error("Error al renderizar el XML:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

function renderXML(factura){
    const xmlDoc = document.implementation.createDocument("", "", null);
    const facturaElement = xmlDoc.createElement("factura");

    const idFacturaElement = xmlDoc.createElement("idFactura");
    idFacturaElement.textContent = factura.idFactura;
    facturaElement.appendChild(idFacturaElement);

    const idProveedorElement = xmlDoc.createElement("idProveedor");
    idProveedorElement.textContent = factura.idProveedor;
    facturaElement.appendChild(idProveedorElement);

    const idClienteElement = xmlDoc.createElement("idCliente");
    idClienteElement.textContent = factura.idCliente;
    facturaElement.appendChild(idClienteElement);

    const nombreClienteElement = xmlDoc.createElement("nombreCliente");
    nombreClienteElement.textContent = factura.nombreCliente;
    facturaElement.appendChild(nombreClienteElement);

    const montoElement = xmlDoc.createElement("monto");
    montoElement.textContent = factura.monto;
    facturaElement.appendChild(montoElement);

    xmlDoc.appendChild(facturaElement);

    const serializer = new XMLSerializer();
    return serializer.serializeToString(xmlDoc);

}

async function descargarPdf(id) {
    try {
        const response = await fetch(`${backend}/api/facturas/descargarPDF/${id}`, {
            method: 'GET',
            headers: {
                "Accept": "application/pdf",
                "Content-Type": "application/pdf"
            }
        });

        if (response.ok) {
            const blob = await response.blob();
            window.open(URL.createObjectURL(blob));

            console.log("PDF renderizado correctamente\n");
        } else {
            console.error("Error al descargar el Pdf:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}
