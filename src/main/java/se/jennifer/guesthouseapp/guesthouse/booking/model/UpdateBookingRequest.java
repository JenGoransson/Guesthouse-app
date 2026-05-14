package se.jennifer.guesthouseapp.guesthouse.booking.model;

import java.time.LocalDate;

public record UpdateBookingRequest(
        LocalDate startDate,
        LocalDate endDate,
        Long roomId
) {
}
