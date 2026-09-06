package org.example.customerservice.Service;

import org.example.customerservice.Model.Customer;
import org.example.customerservice.Repo.CustomerRepo;
import org.example.customerservice.dto.CreateCustomerRequest;
import org.example.customerservice.dto.CustomerResponse;
import org.example.customerservice.error.BadRequest;
import org.example.customerservice.error.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.stream;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final BookingClient bookingClient;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepo CustomerRepo, BookingClient bookingClient, PasswordEncoder passwordEncoder) {
        this.customerRepo = CustomerRepo;
        this.bookingClient = bookingClient;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepo.findAll().stream().map(this::toDTO).toList() ;
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepo.findById(id).orElseThrow(()
                -> new NotFoundException("Customer with id " + id + " not found"));

        return toDTO(customer);
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        if (customerRepo.existsByEmail(request.email())) {
            throw new BadRequest("Customer with email " + request.email() + " already exists");
        }

        Customer customer = new Customer();
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setPhoneNumber(request.phoneNumber());
        String hashedPassword = passwordEncoder.encode(request.password());
        customer.setPasswordHash(hashedPassword);

        try {
            Customer savedCustomer = customerRepo.save(customer);
            return toDTO(savedCustomer);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequest("Customer with email " + request.email() + " already exists");
        }
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepo.findById(id).orElseThrow(()
                -> new NotFoundException("Customer with id " + id + " not found"));

        if (bookingClient.hasActiveBookings(id)) {
            throw new BadRequest("Customer with id " + id + " has active bookings and cannot be deleted");
        }

        customerRepo.delete(customer);
    }

    public CustomerResponse toDTO(Customer customer) {

        return new CustomerResponse(customer.getId(), customer.getFirstName(), customer.getLastName(),
                customer.getEmail(), customer.getPhoneNumber());
    }
}