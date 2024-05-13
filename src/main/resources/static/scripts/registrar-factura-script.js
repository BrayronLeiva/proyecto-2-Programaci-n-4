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
    carrito.forEach((item, index) => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${item.nombreProducto}</td>
            <td>${item.idProducto}</td>
            <td>${item.costo}</td>
            <td>${item.cantidad}</td>
            <td class="divFlex">
                <form class="formsUgly">
                    <input id="aumentar" type="hidden" name="opcion" value="${index}" />
                    <button class="boton boton--secundario" type="submit">+</button>
                </form>
                <form class="formsUgly" >
                    <input id="disminuir" type="hidden" name="opcion" value="${index}" />
                    <button class="boton boton--terciario" >-</button>
                </form>
            </td>
        `;
        tbody.appendChild(fila);

    });
}


document.addEventListener("DOMContentLoaded", function() {
    const tabla = document.getElementById("tablaCarrito");
    const tbody = tabla.querySelector("tbody");
    tbody.addEventListener('click', function(event) {
        const target = event.target;

        // Verificar si el clic fue en un botón dentro de una fila
        if (target.tagName.toLowerCase() === 'button') {
            // Obtener el formulario padre del botón clicado
            const form = target.closest('form');

            // Obtener el idProveedor del input del formulario
            const idItem = form.querySelector('input[name="opcion"]').value;

            // Verificar si el botón fue de activar o desactivar
            if (target.textContent === '+') {
                // Lógica para aumentar
                console.log("Aumentar cantidad para el índice:", idItem);
                aumentarCantidad(idItem);
            } else if (target.textContent === '-') {
                // Lógica para disminuir
                console.log("Disminuir cantidad para el índice:", idItem);
                disminuirCantidad(idItem);
            }

            // Evitar el comportamiento por defecto del botón (enviar el formulario)
            event.preventDefault();
        }
    });

});


async function aumentarCantidad(index) {
    try {
        console.log(index);
        const response = await fetch(`${backend}/api/facturas/aumentarCantidad/${index}`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            console.log("Item aumentado correctamente\n");
            getCarrito();

        } else {
            console.error("Error al aumentar cantidad:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

async function disminuirCantidad(index) {
    try {
        console.log(index);
        const response = await fetch(`${backend}/api/facturas/disminuirCantidad/${index}`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            console.log("Item aumentado correctamente\n");
            getCarrito();

        } else {
            console.error("Error al disminui cantidad:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}
