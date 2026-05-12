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

    @NotNull(message="Startdate is requierd")
    @FutureOrPresent(message = "Startdate cannot be in the past")
    private LocalDate startdate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    protected Booking(){
    }

    public Booking(Customer customer, Room room, LocalDate startdate, BookingStatus status){
        this.customer = customer;
        this.room = room;
        this.startdate = startdate;
        this.status = status;
    }
}
