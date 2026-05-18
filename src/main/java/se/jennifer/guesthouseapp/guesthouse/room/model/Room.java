package se.jennifer.guesthouseapp.guesthouse.room.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;
import se.jennifer.guesthouseapp.guesthouse.room.RoomType;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Roomnumber cannot be empty")
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private boolean extraBedAllowed;

    @Min(value=1, message="A room must have at least one bed")
    private int beds;

    @Min(value=1, message = "Price per night needs to be greater than 0")
    private int pricePerNight;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    protected Room() {
    }

    public Room(String roomNumber, RoomType type, boolean extraBedAllowed, int beds, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.extraBedAllowed = extraBedAllowed;
        this.beds = beds;
        this.pricePerNight = pricePerNight;
    }


    public Long getId(){
        return id;
    }
    public String getRoomNumber(){
        return roomNumber;
    }
    public RoomType getType(){ return type; }
    public boolean isExtraBedAllowed(){ return extraBedAllowed; }
    public int getBeds(){
        return beds;
    }
    public int getPricePerNight(){
        return pricePerNight;
    }
    public List<Booking> getBookings(){
        return bookings;
    }


    public void setId(Long id){ this.id = id; }
    public void setRoomNumber(String roomNumber){
        this.roomNumber = roomNumber;
    }
    public void setType(RoomType type){ this.type = type; }
    public void setExtraBedAllowed(boolean extraBedAllowed){ this.extraBedAllowed = extraBedAllowed; }
    public void setBeds(int beds){
        this.beds = beds;
    }
    public void setPricePerNight(int pricePerNight){
        this.pricePerNight = pricePerNight;
    }
    public void setBookings(List<Booking> bookings){
        this.bookings = bookings;
    }


}
