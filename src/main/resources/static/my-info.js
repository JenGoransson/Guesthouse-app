// HÄMTA KUNDER FRÅN LOCAL STORAGE

const customer = JSON.parse(localStorage.getItem("customer"));

if (!customer) {
    window.location.href="login.html";
}

//FYLL UT FORMEN MED KUND DATA

document.getElementById("firstname").value = customer.firstname;
document.getElementById("lastname").value = customer.lastname;
document.getElementById("email_address").value = customer.email;
document.getElementById("phone_number").value = customer.phone;

//SPARA KUND INFORMATION

document.getElementById("saveBtn").addEventListener("click", async () => {
    const updatedCustomer = {
        id: customer.id,
        firstname: document.getElementById("firstname").value,
        lastname: document.getElementById("lastname").value,
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

    const successMessage = document.getElementById("successMessage");

    const errorMessage = document.getElementById("errorMessage");

    successMessage.style.display = "none";
    errorMessage.style.display = "none";

    if (response.ok) {
        localStorage.setItem("customer", JSON.stringify(updatedCustomer));
        successMessage.innerText = "Information updated successfully.";
        successMessage.style.display = "block";
    } else {
        const text = await response.text();

        errorMessage.innerText = text;
        errorMessage.style.display = "block";
    }
});
// BYTA LÖSENORD
document.getElementById("changeBtn").addEventListener("click", async () => {
    const currentPassword = document.getElementById("current_password").value;
    const newPassword = document.getElementById("new_password").value;
    const confirmPassword = document.getElementById("confirm_password").value;
    const successMessage = document.getElementById("successMessage");
    const errorMessage = document.getElementById("errorMessage");
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
        errorMessage.innerText = text;
        errorMessage.style.display = "block";
    }
});

// TA BORT ANVÄNDARE
document.getElementById("deleteBtn").addEventListener("click", async () => {
    const confirmDeletion = confirm("Are you sure you want to delete your account?");
    const successMessage = document.getElementById("successMessage");
    const errorMessage = document.getElementById("errorMessage");

    successMessage.style.display = "none";
    errorMessage.style.display = "none";

    if (!confirmDeletion) return;

    const response = await fetch(`http://localhost:8080/customers/${customer.id}`, {
        method: "DELETE",
    });

    if (response.ok) {
        successMessage.innerText = "User deleted successfully.";
        successMessage.style.display = "block";

        setTimeout(() => {
            localStorage.removeItem("customer");
            window.location.href = "login.html";
        }, 2000);
    } else {
        const text = await response.text();
        errorMessage.innerText = text || "Cannot delete user.";
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