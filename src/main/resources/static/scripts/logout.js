document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("logout-link").addEventListener("click", async function(event) {
        event.preventDefault();

        try {
            const response = await fetch("/api/logOut", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            if (response.ok) {
                alert("Logout successful");
                window.location.href = "index.html"; // Redirige a la página de inicio o a la página de login
            } else {
                alert("Error during logout");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    });
});


