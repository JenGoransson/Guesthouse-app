const customer = JSON.parse(localStorage.getItem("customer"));
const customerId = customer.id;

if (!customerId){
    window.location.href= "login.html";
}

const bookingsList = document.getElementById("bookingsList");
const noBookingsMessage = document.getElementById("noBookingsMessage");

async function loadBookings() {
    bookingsList.innerHTML = "";
    noBookingsMessage.style.display = "none";

    const res = await fetch(`http://localhost:8080/bookings/customer/${customerId}`);
    let bookings = await res.json();

    const today = new Date().toISOString().split("T")[0];
    bookings = bookings.filter(b => b.startDate >= today);

    if (!Array.isArray(bookings) || bookings.length === 0) {
        noBookingsMessage.style.display = "block";
        return;
    }

    bookings.forEach(b => {
        const div = document.createElement("div");
        div.classList.add("booking-item");
        div.style.marginBottom = "20px";

        div.innerHTML = `
            <p><strong>Room:</strong> ${b.room.roomNumber}</p>
            <p><strong>From:</strong> ${b.startDate}</p>
            <p><strong>To:</strong> ${b.endDate}</p>

            <button onclick="deleteBooking(${b.id})">Delete</button>
            <button onclick="toggleChangeFields(${b.id})">Change</button>

            <div id="changeFields-${b.id}" style="display:none; margin-top:10px;">
                <label>New start date:</label>
                <input type="date" id="newStart-${b.id}">

                <label>New end date:</label>
                <input type="date" id="newEnd-${b.id}">

                <button onclick="saveBookingChange(${b.id})">Save</button>

                <p id="changeError-${b.id}" style="color:red;"></p>
                <p id="changeSuccess-${b.id}" style="color:green;"></p>
            </div>
        `;

        bookingsList.appendChild(div);
    });
}

function toggleChangeFields(id) {
    const box = document.getElementById(`changeFields-${id}`);
    box.style.display = box.style.display === "none" ? "block" : "none";
}

async function saveBookingChange(id) {
    const start = document.getElementById(`newStart-${id}`).value;
    const end = document.getElementById(`newEnd-${id}`).value;

    const errorBox = document.getElementById(`changeError-${id}`);
    const successBox = document.getElementById(`changeSuccess-${id}`);

    errorBox.textContent = "";
    successBox.textContent = "";

    if (!start || !end) {
        errorBox.textContent = "Please select both dates.";
        return;
    }

    const response = await fetch(`http://localhost:8080/bookings/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            startDate: start,
            endDate: end,
            roomId: null
        })
    });

    if (response.ok) {
        successBox.textContent = "Booking updated successfully!";
        setTimeout(() => loadBookings(), 1500);
    } else {
        const text = await response.text();
        errorBox.textContent = text || "Could not update booking.";
    }
}

async function deleteBooking(id){
    if (!confirm("Are you sure you want to delete this booking?")) return;

    await fetch(`http://localhost:8080/bookings/${id}`, {
        method: "DELETE"
    });

    loadBookings();
}

loadBookings();
