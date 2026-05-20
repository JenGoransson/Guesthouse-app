package se.jennifer.guesthouseapp.guesthouse.customer.service;

import org.springframework.stereotype.Service;
import se.jennifer.guesthouseapp.guesthouse.booking.BookingStatus;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;
import se.jennifer.guesthouseapp.guesthouse.booking.repository.BookingRepository;
import se.jennifer.guesthouseapp.guesthouse.booking.service.BookingService;
import se.jennifer.guesthouseapp.guesthouse.customer.model.CreateCustomerRequest;
import se.jennifer.guesthouseapp.guesthouse.customer.model.Customer;
import se.jennifer.guesthouseapp.guesthouse.customer.repository.CustomerRepository;
import se.jennifer.guesthouseapp.guesthouse.error.NotFoundException;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;

    public CustomerService(CustomerRepository customerRepository, BookingRepository bookingRepository) {
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer with id " + id + " not found"));
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer createCustomer(CreateCustomerRequest request){
        Customer newCustomer = new Customer(request.firstname(), request.lastname(), request.email(), request.phone());
        return customerRepository.save(newCustomer);
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
