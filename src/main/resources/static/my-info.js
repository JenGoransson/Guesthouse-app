// HÄMTA KUNDER FRÅN LOCAL STORAGE

const customer = JSON.parse(localStorage.getItem("customer"));

if (!customer) {
    window.location.href="login.html";
}

//FYLL UT FORMEN MED KUND DATA

document.getElementById("name").value = customer.firstname;
document.getElementById("email_address").value = customer.email;
document.getElementById("phone_number").value = customer.phone;

//SPARA KUND INFORMATION

document.getElementById("saveBtn").addEventListener("click", async () => {
    const updatedCustomer = {
        id: customer.id,
        firstname: document.getElementById("name").value,
        email: document.getElementById("email_address").value,
        phone: document.getElementById("phone_number").value,
    };

    const response = await fetch(`http://localhost:8080/customers/${customer.id}`,
        {
        method: "PUT",

        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedCustomer),

    });

    const successMessage = document.getElementById("success-message");

    const errorMessage = document.getElementById("error-message");

    successMessage.style.display = "none";
    errorMessage.style.display = "none";

    if (response.ok) {
        localStorage.setItem("customer", JSON.stringify(updatedCustomer));
        successMessage.innerText = "Information updated successfully.";
        successMessage.style.display = "block";
    } else {
        const text = await response.text();

        errorMessage.innerText = text;
        successMessage.style.display = "block";
    }
});
// BYTA LÖSENORD
document.getElementById("changeBtn").addEventListener("click", async () => {
    const currentPassword = document.getElementById("current_password").value;
    const newPassword = document.getElementById("new_password").value;
    const confirmPassword = document.getElementById("confirm_password").value;
    const successMessage = document.getElementById("success-message");
    const errorMessage = document.getElementById("error-message");
    successMessage.style.display = "none";
    errorMessage.style.display = "none";
    if (newPassword !== confirmPassword) {
        errorMessage.innerText = "Passwords do not match";
        errorMessage.style.display = "block";
        return;
    }
    const response = await fetch(`http://localhost:8080/customers/${customer.id}/change-password`,
        {
        method: "PUT",
            headers: {
            "Content-Type": "application/json",
            },
            body: JSON.stringify({currentPassword, newPassword}),
    })
    if (response.ok) {
        successMessage.innerText = "Password updated successfully.";
        successMessage.style.display = "block";
        document.getElementById("current_password").value = "";
        document.getElementById("new_password").value = "";
        document.getElementById("confirm_password").value = "";
    } else {
        const text = await response.text();

        if (text.includes("Incorrect password")) {
            errorMessage.innerText = "Current password is incorrect!.";
        } else {
            errorMessage.innerText = text;
        }

        errorMessage.style.display = "block";
    }
});

// LOGGA UT
document.getElementById("logoutBtn").addEventListener("click", () => {
    localStorage.removeItem("customer");
    window.location.href="login.html";
})

document.getElementById("backBtn").addEventListener("click", () => {
    window.location.href="dashboard.html";
})