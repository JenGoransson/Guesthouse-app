package se.jennifer.guesthouseapp.guesthouse.booking.service;

import org.springframework.stereotype.Service;
import se.jennifer.guesthouseapp.guesthouse.booking.BookingStatus;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;
import se.jennifer.guesthouseapp.guesthouse.booking.model.CreateBookingRequest;
import se.jennifer.guesthouseapp.guesthouse.booking.repository.BookingRepository;
import se.jennifer.guesthouseapp.guesthouse.customer.model.Customer;
import se.jennifer.guesthouseapp.guesthouse.customer.repository.CustomerRepository;
import se.jennifer.guesthouseapp.guesthouse.customer.service.CustomerService;
import se.jennifer.guesthouseapp.guesthouse.error.BadRequest;
import se.jennifer.guesthouseapp.guesthouse.error.NotFoundException;
import se.jennifer.guesthouseapp.guesthouse.room.model.Room;
import se.jennifer.guesthouseapp.guesthouse.room.repository.RoomRepository;
import se.jennifer.guesthouseapp.guesthouse.room.service.RoomService;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final RoomService roomService;
    private final CustomerService customerService;

    public BookingService(BookingRepository bookingRepo, RoomService roomService, CustomerService customerService) {
        this.bookingRepo = bookingRepo;
        this.roomService = roomService;
        this.customerService = customerService;
    }

    public List<Booking> getAllBookings(){
        return bookingRepo.findAll();
    }

    public Booking getBookingById(Long id){
        return bookingRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking with id " + id + " not found"));
    }

    public List<Booking> getBookingsForCustomer(Long customerId) {
        return bookingRepo.findByCustomerId(customerId);
    }

    public List<Booking> getBookingsForRoom(Long roomId) {
        return bookingRepo.findByRoomId(roomId);
    }

    //Kanske göra en transactional här..?
    public Booking createBooking(CreateBookingRequest request) {
        Room room = roomService.getRoomById(request.roomId());
        Customer customer = customerService.getCustomerById(request.customerId());

        if (isRoomBooked(room.getId(), request.date())) {
            throw new BadRequest("Room is already booked on " + request.date());
        }

        Booking booking = new Booking(
                customer,
                room,
                request.date(),
                BookingStatus.ACTIVE
        );
        return bookingRepo.save(booking);
    }

    public boolean isRoomBooked(Long roomId, LocalDate date) {
        return bookingRepo.existByRoomIdAndDateAndStatus(roomId, date, BookingStatus.ACTIVE);
    }

    public boolean roomHasActiveBookings(Long roomId) {
        return bookingRepo.existsByRoomIdAndStatus(roomId, BookingStatus.ACTIVE);
    }

    public boolean customerHasActiveBookings(Long customerId) {
        return bookingRepo.existsByCustomerIdAndStatus(customerId, BookingStatus.ACTIVE);
    }

    public void cancelBooking(Long id){
        Booking booking = getBookingById(id);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequest("Booking with id " + id + " is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepo.save(booking);
    }


    /*  TODO:
    *    DONE - Skapa metod getBookingById()
    *    DONE - Skapa metod createBooking() --> kolla om kunden som gör bokningen faktiskt finns + om rummet är ledigt. Transactional kan vara bra här!
    *    DONE - Skapa metod isRoomBooked(roomId, date), denna metod ska RoomService anropa.
    *    DONE - Skapa metod customerHasActiveBookings(customerId)
    *    DONE - Skapa metod cancelBooking
    *    DONE - Skapa metod getBookingsForCustomer(customerId)
    *    DONE - Skapa metod getBookingsForRoom
    *
    * */

}
