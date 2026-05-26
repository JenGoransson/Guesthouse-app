package se.jennifer.guesthouseapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.jennifer.guesthouseapp.guesthouse.booking.BookingStatus;
import se.jennifer.guesthouseapp.guesthouse.booking.repository.BookingRepository;
import se.jennifer.guesthouseapp.guesthouse.customer.model.CreateCustomerRequest;
import se.jennifer.guesthouseapp.guesthouse.customer.model.Customer;
import se.jennifer.guesthouseapp.guesthouse.customer.model.LoginRequest;
import se.jennifer.guesthouseapp.guesthouse.customer.repository.CustomerRepository;
import se.jennifer.guesthouseapp.guesthouse.customer.service.CustomerService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GuesthouseAppApplicationTests {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createCustomer() {
        CreateCustomerRequest request = new CreateCustomerRequest(
                "test", "user", "test@gmail.com", "test123", "123456789"
        );

        when(passwordEncoder.encode("test123")).thenReturn("hashedPassword");

        Customer customerExists = new Customer(
                "test", "user", "test@gmail.com", "test123", "123456789"
        );

        when(customerRepository.save(any(Customer.class))).thenReturn(customerExists);

        Customer serviceResult = customerService.createCustomer(request);

        assertEquals("test", serviceResult.getFirstname());
        assertEquals("test@gmail.com", serviceResult.getEmail());

        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void loginCustomer() {
        LoginRequest request = new LoginRequest(
                "test@gmail.com", "test123"
        );

        Customer customer  = new Customer(
                "test", "user", "test@gmail.com", "hashedPassword", "123456789"
        );

        when(customerRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(customer));

        when(passwordEncoder.matches("test123", "hashedPassword")).thenReturn(true);

        Customer serviceResult = customerService.login(request);

        assertEquals("test@gmail.com", serviceResult.getEmail());
    }

    @Test
    void deleteCustomer() {
        Long customerId = 1L;
        Customer customer =  new Customer();
        customer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        when(bookingRepository.existsByCustomerIdAndStatus(
                customerId, BookingStatus.ACTIVE)).thenReturn(Boolean.TRUE
        );

        assertThrows(IllegalStateException.class, () -> customerService.deleteCustomer(customerId));

        verify(customerRepository,never()).deleteById(anyLong());
    }
}
