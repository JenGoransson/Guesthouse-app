package se.jennifer.guesthouseapp.guesthouse.customer.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.jennifer.guesthouseapp.guesthouse.booking.BookingStatus;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;
import se.jennifer.guesthouseapp.guesthouse.booking.repository.BookingRepository;
import se.jennifer.guesthouseapp.guesthouse.booking.service.BookingService;
import se.jennifer.guesthouseapp.guesthouse.customer.model.CreateCustomerRequest;
import se.jennifer.guesthouseapp.guesthouse.customer.model.Customer;
import se.jennifer.guesthouseapp.guesthouse.customer.model.LoginRequest;
import se.jennifer.guesthouseapp.guesthouse.customer.repository.CustomerRepository;
import se.jennifer.guesthouseapp.guesthouse.error.BadRequest;
import se.jennifer.guesthouseapp.guesthouse.error.NotFoundException;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, BookingRepository bookingRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer with id " + id + " not found"));
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer createCustomer(CreateCustomerRequest request){
        String hashedPassword = passwordEncoder.encode(request.password());

        Customer newCustomer = new Customer(
                request.firstname(),
                request.lastname(),
                request.email(),
                hashedPassword,
                request.phone());
        try {
            return customerRepository.save(newCustomer);

    } catch (DataIntegrityViolationException e) {
        throw new BadRequest("Email already exists");
        }
    }

    public Customer login(LoginRequest request) {

        Customer customer = customerRepository.findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException("No customer with that email"));

        if (!passwordEncoder.matches(request.password(), customer.getPassword())) {
            throw new BadRequest("Incorrect password");
        }

        return customer;
    }


    public Customer updateEmail(Long customerId, String newEmail){
        Customer customer = getCustomerById(customerId);

        boolean emailExists = customerRepository.existsByEmail(newEmail);
        if(emailExists && !customer.getEmail().equals(newEmail)){
            throw new IllegalArgumentException(newEmail + " already in use!");
        }

        customer.setEmail(newEmail);
        return customerRepository.save(customer);
    }

    public Customer updatePhone(Long customerId, String newPhone){
        Customer customer = getCustomerById(customerId);

        boolean phoneExists = customerRepository.existsByPhone(newPhone);
        if(phoneExists && !customer.getPhone().equals(newPhone)){
            throw new IllegalStateException(newPhone + " already in use!");
        }

        customer.setPhone(newPhone);
        return customerRepository.save(customer);
    }

    public boolean hasActiveBookings(Long customerId){
        return bookingRepository.existsByCustomerIdAndStatus(customerId, BookingStatus.ACTIVE);
    }

    public void deleteCustomer(Long customerId){
        Customer customer = getCustomerById(customerId);

        if (hasActiveBookings(customerId)) {
            throw new IllegalStateException
                    ("Customer " + customer + " has active bokings and can therefore not be deleted.");
        }

        customerRepository.delete(customer);
    }

    public Customer getCustomerByEmail(String email){
        return customerRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Customer with email " + email + " could not be found."));
    }

    public List<Booking> getCustomerBookings(Long customerId){
        getCustomerById(customerId);
        return bookingRepository.findByCustomerId(customerId);
    }
}
