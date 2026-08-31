// Hämta kund från localStorage
const customer = JSON.parse(localStorage.getItem("customer"));

// Om ingen kund finns → skicka tillbaka till login
if (!customer) {
    window.location.href = "login.html";
}

// Sätt namnet i välkomsttexten
document.getElementById("customerName").textContent = customer.firstname;

document.getElementById("logoutBtn").addEventListener("click", () => {
    localStorage.removeItem("customer");
    window.location.href="login.html";
})