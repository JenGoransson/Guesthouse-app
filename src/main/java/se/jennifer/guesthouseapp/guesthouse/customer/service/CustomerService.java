package se.jennifer.guesthouseapp.guesthouse.customer.service;

import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.jennifer.guesthouseapp.guesthouse.booking.BookingStatus;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;
import se.jennifer.guesthouseapp.guesthouse.booking.repository.BookingRepository;
import se.jennifer.guesthouseapp.guesthouse.customer.dto.ChangePasswordRequest;
import se.jennifer.guesthouseapp.guesthouse.customer.dto.CreateCustomerRequest;
import se.jennifer.guesthouseapp.guesthouse.customer.dto.UpdateCustomerRequest;
import se.jennifer.guesthouseapp.guesthouse.customer.model.Customer;
import se.jennifer.guesthouseapp.guesthouse.customer.dto.LoginRequest;
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

    public void changePassword(Long id, ChangePasswordRequest request){
        Customer customer = getCustomerById(id);

        if (!passwordEncoder.matches(request.currentPassword(), customer.getPassword())) {
            throw new BadRequest("Incorrect password");
        }

        customer.setPassword(passwordEncoder.encode(request.newPassword()));

        customerRepository.save(customer);
    }
    public Customer updateCustomer(Long id, UpdateCustomerRequest request){
        Customer customer = getCustomerById(id);

        boolean emailExists = customerRepository.existsByEmail(request.email());

        if(emailExists && !customer.getEmail().equals(request.email())){
            throw new IllegalStateException(request.email() + " already in use!");
        }

        boolean phoneExists = customerRepository.existsByPhone(request.phone());

        if(phoneExists && customer.getPhone() != null && !customer.getPhone().equals(request.phone())){
            throw new IllegalStateException(request.phone() + " already in use!");
        }

        customer.setFirstname(request.firstname());
        customer.setLastname(request.lastname());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());

        return customerRepository.save(customer);
    }
}
