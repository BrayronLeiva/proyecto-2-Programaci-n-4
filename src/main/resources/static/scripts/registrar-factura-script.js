var backend = "http://localhost:8080";
let carrito = [];
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("registrarFacturaForm").addEventListener("submit", async function(event) {
        event.preventDefault();
        // Obtener los valores del formulario
        var productoId = document.getElementById("productoId").value;
        var cantidad = document.getElementById("cantidad").value;

        try {
            const response = await fetch(`/api/facturas/addItem/${productoId}/${cantidad}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                alert("Exito");
                getCarrito();

            } else {
                alert("Error bro");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    });
});

async function getCarrito() {
    try {
        const response = await fetch(backend + "/api/facturas/getCarrito", {
            method: 'GET',
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            const lista = await response.json();
            carrito = lista; // Llenar la lista de proveedores
            console.log("Tamaño de la lista de carrito:", carrito.length);
            mostrarCarritoEnTabla();
        } else {
            console.error("Error al obtener la lista de proveedores:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

getCarrito();

function mostrarCarritoEnTabla() {
    const tabla = document.getElementById("tablaCarrito");
    const tbody = tabla.querySelector("tbody");
    const titulo = document.getElementById("tituloTablaCarrito");
    const empty = document.getElementById("emptyTablaCarrito");

    // Limpiar el contenido existente de la tabla
    tbody.innerHTML = "";

    if(carrito.length === 0){
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
    carrito.forEach(item => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${item.nombreProducto}</td>
            <td>${item.idProducto}</td>
            <td>${item.costo}</td>
            <td>${item.cantidad}</td>
            <td class="divFlex">
                <form class="formsUgly">
                    <input id="aumentar" type="hidden" name="opcion" value="${item.id_detalle}"/>
                    <button class="boton boton--secundario" type="submit">+</button>
                </form>
                <form class="formsUgly" >
                    <input id="disminuir" type="hidden" name="opcion" value="${item.id_detalle}"/>
                    <button class="boton boton--terciario" type="submit">-</button>
                </form>
            </td>
        `;
        tbody.appendChild(fila);

    });
}

document.addEventListener("DOMContentLoaded", function() {
    const tabla = document.getElementById("tablaCarrito");
    const tbody = tabla.querySelector("tbody");
    // Asignar un evento clic al tbody de la tabla
    tbody.addEventListener("click", function(event) {
        const target = event.target;
        // Verificar si el clic fue en un botón dentro de la clase "divFlex"
        if (target.tagName === "BUTTON" && target.parentElement.classList.contains("divFlex")) {
            event.preventDefault(); // Prevenir el comportamiento por defecto del botón (submit)
            const form = target.parentElement.querySelector("form");
            const index = form.querySelector("input[name='opcion']").value;
            if (target.id === "aumentar") {
                // Lógica para aumentar
                console.log("Aumentar cantidad para el índice:", index);
            } else if (target.id === "disminuir") {
                // Lógica para disminuir
                console.log("Disminuir cantidad para el índice:", index);
            }
        }
    });

});
