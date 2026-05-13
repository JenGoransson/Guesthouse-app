package se.jennifer.guesthouseapp.guesthouse.room.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Roomnumber cannot be empty")
    private String roomNumber;

    @Min(value=1, message="A room must have at least one bed")
    private int beds;

    @Min(value=1, message = "Price per night needs to be greater than 0")
    private int pricePerNight;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    protected Room() {
    }

    public Room(String roomNumber, int beds, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.beds = beds;
        this.pricePerNight = pricePerNight;
    }

    public Long getId(){
        return id;
    }
    public String getRoomNumber(){
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber){
        this.roomNumber = roomNumber;
    }
    public int getBeds(){
        return beds;
    }
    public void setBeds(int beds){
        this.beds = beds;
    }
    public int getPricePerNight(){
        return pricePerNight;
    }
    public void setPricePerNight(int pricePerNight){
        this.pricePerNight = pricePerNight;
    }
    public List<Booking> getBookings(){
        return bookings;
    }
    public void setBookings(List<Booking> bookings){
        this.bookings = bookings;
    }

}
