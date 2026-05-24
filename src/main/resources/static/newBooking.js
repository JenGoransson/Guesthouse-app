
document.getElementById("date-form").addEventListener("submit", async function(e) {
    e.preventDefault();

    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    const response = await fetch(`/bookings/available-range?start=${startDate}&end=${endDate}`);
    const rooms = await response.json();

    const roomList = document.getElementById("available-rooms");
    roomList.innerHTML = "<h3>Available rooms</h3>";

    rooms.forEach(room=> {
        const label = document.createElement("label");
        label.classList.add("room-option");

        label.innerHTML = `
<input type="radio" name="roomId" value="${room.id}" data-type="${room.type}">
Room ${room.roomNumber} – ${room.type}
`;

        roomList.appendChild(label);
    });
    roomList.classList.remove("hidden");
    });

//Visa/dölj extrasäng när man väljer rum.
document.addEventListener("change", function(e) {
    if(e.target.name=="roomId") {
        const roomType = e.target.getAttribute("data-type");
        const extraBedSection = document.getElementById("extra-bed-section");

        if(roomType ==="DOUBLE") {
            extraBedSection.classList.remove("hidden");
        } else {
            extraBedSection.classList.add("hidden");
        }

        document.getElementById("confirm-form").classList.remove("hidden");
    }
});

//Bekräfta bokning
document.getElementById("confirm-form").addEventListener("submit", async function (e) {
    e.preventDefault();

    const selectedRoom = document.querySelector("input[name='roomId']:checked");
    const roomId = selectedRoom.value;

    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    const customerId = localStorage.getItem("customerId");

    const bookingData = {
        roomId,
        customerId,
        startDate,
        endDate
    };

    const response = await fetch("/bookings", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(bookingData)
    });

    if (response.ok) {
        document.getElementById("success-message").classList.remove("hidden");
    } else {
        alert("Something went wrong with your booking.");
    }
});

document.getElementById("back-to-dashboard").addEventListener("click", function() {
    window.location.href = "dashboard.html";
});

// Cancel booking
document.getElementById("cancel-booking").addEventListener("click", function () {
    // Göm confirm-form
    document.getElementById("confirm-form").classList.add("hidden");

    // Göm extrasäng
    document.getElementById("extra-bed-section").classList.add("hidden");

    // Avmarkera valt rum
    const selected = document.querySelector("input[name='roomId']:checked");
    if (selected) selected.checked = false;
});



