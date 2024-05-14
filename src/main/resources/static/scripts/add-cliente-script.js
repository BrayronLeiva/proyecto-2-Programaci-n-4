
var backend = "http://localhost:8080";
let clientes = [];

document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("add-cliente").addEventListener("submit", async function(event) {
        event.preventDefault();
        const nombre = document.getElementById("nombre").value;


        try {
            const response = await fetch("/api/clientes/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json" // Ajustar el Content-Type
                },
                body: JSON.stringify({nombre})
            }); //fin de peticion

            if (response.ok) {
                alert("Cliente Registrado Correctamente")
                obtenerClientes();

            } else {
                alert("Error Bro");
            }

            document.getElementById("nombre").value = "";


        } catch (error) {
            console.error("Error:", error);
        }
    });
});


async function obtenerClientes() {
    try {
        const response = await fetch(backend + "/api/clientes/getClientes", {
            method: 'GET',
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            const lista = await response.json();
            clientes = lista; // Llenar la lista de proveedores
            console.log("Tamaño de la lista de clientes:", clientes.length);
            mostrarClientesEnTabla();
        } else {
            console.error("Error al obtener la lista de proveedores:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

obtenerClientes();

function mostrarClientesEnTabla() {
    const tabla = document.getElementById("tablaClientes");
    const tbody = tabla.querySelector("tbody");
    const titulo = document.getElementById("tituloTablaClientes");
    const empty = document.getElementById("emptyTablaClientes");

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
    clientes.forEach(cliente => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${cliente.idCliente}</td>
            <td>${cliente.nombre}</td>
            <td class="divFlex">
                 <form class="formsUgly" method="post">
                    <input type="hidden" name="idCliente" value=${cliente.idCliente} />
                    <button type="submit" class="boton--primario boton" >ADD</button>
                </form>
            </td>
        `;
        tbody.appendChild(fila);
    });
}

document.addEventListener("DOMContentLoaded", function() {
    const tabla = document.getElementById("tablaClientes");
    const tbody = tabla.querySelector("tbody");
    tbody.addEventListener('click', function(event) {
        const target = event.target;

        // Verificar si el clic fue en un botón dentro de una fila
        if (target.tagName.toLowerCase() === 'button') {
            // Obtener el formulario padre del botón clicado
            const form = target.closest('form');


            const idCliente = form.querySelector('input[name="idCliente"]').value;

            selectCliente(idCliente);


            // Evitar el comportamiento por defecto del botón (enviar el formulario)
            event.preventDefault();
        }
    });

});

async function selectCliente(id) {
    try {
        console.log(id);
        const response = await fetch(`${backend}/api/facturas/selectCliente/${id}`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            console.log("Cliente seleccionado correctamente\n");
            //getNombreClienteFactura();
            window.location.href = "/registrar-factura.html";


        } else {
            console.error("Error al seleccionar cliente:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}


