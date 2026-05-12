package se.jennifer.guesthouseapp.guesthouse.room.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

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

    protected Room() {
    }

    public Room(String roomNumber, int beds, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.beds = beds;
        this.pricePerNight = pricePerNight;
    }

}
