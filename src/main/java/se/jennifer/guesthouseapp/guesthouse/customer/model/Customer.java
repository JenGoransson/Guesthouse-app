package se.jennifer.guesthouseapp.guesthouse.customer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotBlank(message = "Firstname cannot be empty")
    private String firstname;

    @NotBlank(message = "Lastname cannot be empty")
    private String lastname;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be a valid email address")
    private String email;

    private String phone;

    @NotBlank(message="Password cannot be empty")
    private String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    public Customer() {
    }

    public Customer(String firstname, String lastname, String email, String password, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password=password;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotBlank(message = "Firstname cannot be empty") String getFirstname() {
        return firstname;
    }

    public void setFirstname(@NotBlank(message = "Firstname cannot be empty") String firstname) {
        this.firstname = firstname;
    }

    public @NotBlank(message = "Lastname cannot be empty") String getLastname() {
        return lastname;
    }

    public void setLastname(@NotBlank(message = "Lastname cannot be empty") String lastname) {
        this.lastname = lastname;
    }

    public @NotBlank(message = "Password cannot be empty") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password cannot be empty") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Email cannot be empty") @Email(message = "Email must be a valid email address") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be empty") @Email(message = "Email must be a valid email address") String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
