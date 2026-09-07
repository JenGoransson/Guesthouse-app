package se.jennifer.guesthouseapp.guesthouse.dto;


public record CustomerDto(
        Long id,
        String firstname,
        String lastname,
        String email
) {}

