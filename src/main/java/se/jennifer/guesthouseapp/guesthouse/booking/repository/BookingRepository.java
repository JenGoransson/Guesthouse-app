package se.jennifer.guesthouseapp.guesthouse.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
