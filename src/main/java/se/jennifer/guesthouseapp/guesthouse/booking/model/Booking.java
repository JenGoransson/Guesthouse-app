package se.jennifer.guesthouseapp.guesthouse.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
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
    private LocalDate date;

    @NotNull(message = "Enddate is required")
    @FutureOrPresent(message = "Enddate cannot be in the past")
    private LocalDate enddate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    protected Booking(){
    }

    public Booking(Customer customer, Room room, LocalDate date, BookingStatus status){
        this.customer = customer;
        this.room = room;
        this.date = date;
        this.status = status;
    }

    @AssertTrue(message = "Enddate must be after startdate")
    public boolean isEndDataValid(){
        return enddate != null && startdate != null && !enddate.isBefore(startdate);
    }

    public long getId(){
        return id;
    }
    public Customer getCustomer(){
        return customer;
    }
    public void setCustomer(Customer customer){
        this.customer = customer;
    }
    public Room getRoom(){
        return room;
    }
    public void setRoom(Room room){
        this.room = room;
    }
    public LocalDate getStartdate(){
        return startdate;
    }
    public void setStartdate(LocalDate startdate){
        this.startdate = startdate;
    }
    public LocalDate getEnddate(){
        return enddate;
    }
    public void setEnddate(LocalDate enddate){
        this.enddate = enddate;
    }
    public BookingStatus getStatus(){
        return status;
    }
    public void setStatus(BookingStatus status){
        this.status = status;
    }

}
