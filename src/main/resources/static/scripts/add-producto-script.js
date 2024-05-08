var backend = "http://localhost:8080";
let productos = [];




document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("add-producto").addEventListener("submit", async function(event) {
        event.preventDefault();
        const nombre = document.getElementById("nombre").value;


        try {
            const response = await fetch("/api/producto/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json" // Ajustar el Content-Type
                },
                body: JSON.stringify({nombre})
            }); //fin de peticion

            if (response.ok) {
                alert("Producto Registrado Correctamente")
                obtenerProductos();

            } else {
                alert("Error Bro");
            }

            document.getElementById("nombre").value = "";


        } catch (error) {
            console.error("Error:", error);
        }
    });
});


async function obtenerProductos() {
    try {
        const response = await fetch(backend + "/api/productos/getProductos", {
            method: 'GET',
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            const lista = await response.json();
            productos = lista; // Llenar la lista de proveedores
            console.log("Tamaño de la lista de productos:", productos.length);
            mostrarProductosEnTabla();
        } else {
            console.error("Error al obtener la lista de proveedores:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

obtenerProductos();

function mostrarProductosEnTabla() {
    const tabla = document.getElementById("tablaProductos");
    const tbody = tabla.querySelector("tbody");
    const titulo = document.getElementById("tituloTablaProductos");
    const empty = document.getElementById("emptyTablaProductos");

    // Limpiar el contenido existente de la tabla
    tbody.innerHTML = "";

    if(productos.length === 0){
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
    productos.forEach(producto => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${producto.idProducto}</td>
            <td>${producto.nombre}</td>
            <td class="divFlex">
                 <form class="formsUgly" method="post">
                    <input type="hidden" name="idProducto" value=${producto.idProducto} />
                    <button type="submit" class="boton--primario boton" >ADD</button>
                </form>
            </td>
        `;
        tbody.appendChild(fila);
    });
}

document.addEventListener("DOMContentLoaded", function() {
    const tabla = document.getElementById("tablaProductos");
    const tbody = tabla.querySelector("tbody");
    tbody.addEventListener('click', function(event) {
        const target = event.target;

        // Verificar si el clic fue en un botón dentro de una fila
        if (target.tagName.toLowerCase() === 'button') {
            // Obtener el formulario padre del botón clicado
            const form = target.closest('form');


            const idProducto = form.querySelector('input[name="idProducto"]').value;

            selectProducto(idProducto);


            // Evitar el comportamiento por defecto del botón (enviar el formulario)
            event.preventDefault();
        }
    });

});
/*

async function selectProducto(id) {
    try {
        console.log(id);
        const response = await fetch(`${backend}/api/facturas/selectProducto/${id}`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            console.log("Producto seleccionado correctamente\n");


        } else {
            console.error("Error al seleccionar prodcuto:", response.statusText);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}*/
