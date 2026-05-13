package se.jennifer.guesthouseapp.guesthouse.booking.model;

import java.time.LocalDate;

public record CreateBookingRequest(
        Long roomId,
        Long customerId,
        LocalDate date
) {}
