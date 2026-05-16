package se.jennifer.guesthouseapp.guesthouse.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import se.jennifer.guesthouseapp.guesthouse.booking.BookingStatus;
import se.jennifer.guesthouseapp.guesthouse.customer.model.Customer;
import se.jennifer.guesthouseapp.guesthouse.room.model.Room;

import java.time.LocalDate;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(optional = false) //En kund kan ha flera bokningar
    private Customer customer;

    @ManyToOne(optional = false)
    private Room room;

    @NotNull(message="Start date is required")
    @FutureOrPresent(message = "Start date cannot be in the past")
    private LocalDate startDate;

    @NotNull(message="End date is required")
    @FutureOrPresent(message="End date cannot be in the past")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    protected Booking(){
    }

    public Booking(Customer customer, Room room, LocalDate startDate, LocalDate endDate, BookingStatus status){
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public @NotNull(message = "Start date is required") @FutureOrPresent(message = "Start date cannot be in the past") LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull(message = "Start date is required") @FutureOrPresent(message = "Start date cannot be in the past") LocalDate startDate) {
        this.startDate = startDate;
    }

    public @NotNull(message = "End date is required") @FutureOrPresent(message = "End date cannot be in the past") LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull(message = "End date is required") @FutureOrPresent(message = "End date cannot be in the past") LocalDate endDate) {
        this.endDate = endDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
