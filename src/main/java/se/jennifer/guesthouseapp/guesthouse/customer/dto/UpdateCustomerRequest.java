package se.jennifer.guesthouseapp.guesthouse.customer.dto;

public record UpdateCustomerRequest(
        String firstname, String lastname, String email, String phone)
{
}
