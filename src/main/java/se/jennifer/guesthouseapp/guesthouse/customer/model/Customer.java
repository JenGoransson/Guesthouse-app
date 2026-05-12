package se.jennifer.guesthouseapp.guesthouse.customer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotBlank(message = "Firstname cannot be empty")
    private String firstname;

    @NotBlank(message = "Lastname cannot be empty")
    private String lastname;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be a valid email address")
    private String email;

    private String phone;

    public Customer() {
    }

    public Customer(String firstname, String lastname, String email, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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
