package se.jennifer.guesthouseapp.guesthouse.customer.model;

public record LoginRequest(
        String email,
        String password
) {}

