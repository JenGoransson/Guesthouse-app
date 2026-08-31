package se.jennifer.guesthouseapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import se.jennifer.guesthouseapp.guesthouse.booking.model.BookingStatus;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;
import se.jennifer.guesthouseapp.guesthouse.booking.repository.BookingRepository;
import se.jennifer.guesthouseapp.guesthouse.booking.service.BookingService;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    BookingRepository bookingRepository;

    @Mock
    BookingService bookingService;


    @Test
    void cancelBooking_ShouldSetStatusToCanceled(){

        //Arrange
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStatus(BookingStatus.ACTIVE);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        //Act
        bookingService.cancelBooking(1L);

        //Assert
        assertEquals(BookingStatus.CANCELLED,booking.getStatus());
        verify(bookingRepository).findById(1L);
        verify(bookingRepository).save(booking);

    }
}
