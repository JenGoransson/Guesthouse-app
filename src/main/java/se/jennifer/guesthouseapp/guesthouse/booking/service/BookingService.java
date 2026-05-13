package se.jennifer.guesthouseapp.guesthouse.booking.service;

import org.springframework.stereotype.Service;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;
import se.jennifer.guesthouseapp.guesthouse.booking.repository.BookingRepository;
import se.jennifer.guesthouseapp.guesthouse.customer.repository.CustomerRepository;
import se.jennifer.guesthouseapp.guesthouse.room.repository.RoomRepository;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final CustomerRepository customerRepo;
    private final RoomRepository roomRepo;

    public BookingService(BookingRepository bookingRepo, CustomerRepository customerRepo, RoomRepository roomRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.roomRepo = roomRepo;
    }

    public List<Booking> getAllBookings(){
        return bookingRepo.findAll();
    }

    /*  TODO:
    *    Skapa metod getBookingById()
    *    Skapa metod createBooking() --> kolla om kunden som gör bokningen faktiskt finns + om rummet är ledigt. Transactional kan vara bra här!
    *    Skapa metod isRoomBooked(roomId, date), denna metod ska RoomService anropa.
    *    Skapa metod customerHasActiveBookings(customerId)
    *    Skapa metod cancelBooking
    *    Skapa metod getBookingsForCustomer(customerId)
    *    Skapa metod getBookingsForRoom
    *
    * */

}
