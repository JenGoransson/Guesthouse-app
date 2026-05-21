// TESTA API-KONTAKT
fetch("http://localhost:8080/bookings")
    .then(res => res.json())
    .then(data => console.log("API FUNKAR! Backend svarade:", data))
    .catch(err => console.error("API FEL:", err));


// LOGIN HANDLING
const loginForm = document.getElementById("loginForm");

if (loginForm) {
    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        const response = await fetch("http://localhost:8080/customers/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            window.location.href = "dashboard.html";
        } else {
            document.getElementById("errorMessage").style.display = "block";
        }
    });
}

// REGISTER HANDLING
const registerForm = document.getElementById("registerForm");

if (registerForm) {
    registerForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const firstname = document.getElementById("firstname").value;
        const lastname = document.getElementById("lastname").value;
        const email = document.getElementById("email").value;
        const phone = document.getElementById("phone").value;
        const password = document.getElementById("password").value;

        const response = await fetch("http://localhost:8080/customers", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ firstname, lastname, email, phone, password })
        });

        const errorMessage= document.getElementById("errorMessage");
        const successMessage = document.getElementById("successMessage");

        // Rensa gamla meddelanden
        errorMessage.style.display = "none";
        successMessage.style.display = "none";

        if (response.ok) {
            successMessage.innerText = "User created successfully!";
            successMessage.style.display = "block";

            setTimeout(() => {
                window.location.href = "login.html";
            }, 4000);

        } else {
            const text = await response.text();
            errorMessage.innerText = text;
            errorMessage.style.display = "block";
        }
    });
}

