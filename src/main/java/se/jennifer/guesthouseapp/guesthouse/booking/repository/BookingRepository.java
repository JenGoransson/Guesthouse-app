package se.jennifer.guesthouseapp.guesthouse.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jennifer.guesthouseapp.guesthouse.booking.BookingStatus;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    // Finns det en aktiv bokning som överlappar intervallet?
    boolean existsByRoomIdAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long roomId,
            BookingStatus status,
            LocalDate endDate,
            LocalDate startDate
    );

    boolean existByRoomIdAndDateAndStatus(Long roomId, LocalDate date, BookingStatus status);

    boolean existsByRoomIdAndStatus(Long roomId, BookingStatus status);

    boolean existsByCustomerIdAndStatus(Long customerId, BookingStatus status);

    List<Booking> findByCustomerId(Long customerId);

    List<Booking> findByRoomId(Long roomId);
}
