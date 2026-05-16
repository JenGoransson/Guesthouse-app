package se.jennifer.guesthouseapp.guesthouse.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;
import se.jennifer.guesthouseapp.guesthouse.booking.model.CreateBookingRequest;
import se.jennifer.guesthouseapp.guesthouse.booking.model.UpdateBookingRequest;
import se.jennifer.guesthouseapp.guesthouse.booking.service.BookingService;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Booking> getBookingsForCustomer(@PathVariable Long customerId){
        return bookingService.getBookingsForCustomer(customerId);
    }

    @GetMapping("/room/{roomId}")
    public List<Booking>getBookingsForRoom(@PathVariable Long roomId){
        return bookingService.getBookingsForRoom(roomId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@RequestBody CreateBookingRequest request) {
        return bookingService.createBooking(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable Long id){
        bookingService.cancelBooking(id);
    }


    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id,
                                 @RequestBody UpdateBookingRequest request) {
        return bookingService.updateBooking(id, request);
    }
}
