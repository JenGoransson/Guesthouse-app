package se.jennifer.guesthouseapp.guesthouse.customer.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(
        @NotBlank(message = "Firstname cannot be empty")
        String firstname,

        @NotBlank(message = "Lastname cannot be empty")
        String lastname,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Email must be a valid email address")
        String email,

        @NotBlank(message="Password cannot be empty")
        String password,

        String phone

) {
}
