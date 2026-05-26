package se.jennifer.guesthouseapp.guesthouse.customer.dto;

public record LoginRequest(
        String email,
        String password
) {}

