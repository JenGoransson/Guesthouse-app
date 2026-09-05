package org.example.customerservice.Service;

import org.example.customerservice.Model.Customer;
import org.example.customerservice.Repo.CustomerRepo;
import org.example.customerservice.dto.CustomerDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.stream;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final BookingClient bookingClient;

    public CustomerService(CustomerRepo CustomerRepo, BookingClient bookingClient) {
        this.customerRepo = CustomerRepo;
        this.bookingClient = bookingClient;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepo.findAll().stream().map(this::toDTO).toList() ;
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepo.findById(id).orElseThrow(()
                -> new RuntimeException("Customer with id " + id + " not found"));

        return toDTO(customer);
    }

    public Customer createCustomer(Customer customer) {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new RuntimeException("Customer with email " + customer.getEmail() + " already exists");
        }

        try {
            return customerRepo.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Customer with email " + customer.getEmail() + " already exists");
        }
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepo.findById(id).orElseThrow(()
                -> new RuntimeException("Customer with id " + id + " not found"));

        if (bookingClient.hasActiveBookings(id)) {
            throw new RuntimeException("Customer with id " + id + " has active bookings and cannot be deleted");
        }

        customerRepo.delete(customer);
    }

    public CustomerDTO toDTO(Customer customer) {

        return new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(),
                customer.getEmail(), customer.getPhoneNumber());
    }
}