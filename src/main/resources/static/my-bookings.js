const customer = JSON.parse(localStorage.getItem("customer"));
const customerId = customer.id;

if (!customerId){
    window.location.href= "login.html";
}

const bookingsList = document.getElementById("bookingsList");
const noBookingsMessage = document.getElementById("noBookingsMessage");

async function loadBookings() {
    bookingsList.innerHTML = "";

    const res = await fetch(`http://localhost:8080/bookings/customer/${customerId}`);
    const bookings = await res.json();

    if (!Array.isArray(bookings) || bookings.length === 0) {
        noBookingsMessage.style.display = "block";
        return;
    }
    bookings.forEach(b => {
        const div = document.createElement("div");
        div.classList.add("booking-item");
        div.style.marginBottom = "15px";

        div.innerHTML = `
        <p><strong>Room:</strong> ${b.room.roomNumber}</p>
        <p><strong>From:</strong> ${b.startDate}</p>
        <p><strong>To:</strong> ${b.endDate}</p>
        <button onclick="deleteBooking(${b.id})">Delete</button>
    `;
        bookingsList.appendChild(div);
    });
}
async function deleteBooking(id){
    if (!confirm("Are you sure you want to delete this booking?")) return;
    await  fetch(`http://localhost:8080/bookings/${id}`,{
            method: "DELETE"
    });
    loadBookings();

}
loadBookings();