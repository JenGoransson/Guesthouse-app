package se.jennifer.guesthouseapp.guesthouse.customer.service;

import org.springframework.stereotype.Service;
import se.jennifer.guesthouseapp.guesthouse.booking.service.BookingService;
import se.jennifer.guesthouseapp.guesthouse.customer.model.CreateCustomerRequest;
import se.jennifer.guesthouseapp.guesthouse.customer.model.Customer;
import se.jennifer.guesthouseapp.guesthouse.customer.repository.CustomerRepository;
import se.jennifer.guesthouseapp.guesthouse.error.NotFoundException;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookingService bookingService;

    public CustomerService(CustomerRepository customerRepository, BookingService bookingService) {
        this.customerRepository = customerRepository;
        this.bookingService = bookingService;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer with id " + id + " not found"))
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer createCustomer(CreateCustomerRequest request){
        Customer newCustomer = new Customer(request.firstname(), request.lastname(), request.email(), request.phone());
        return customerRepository.save(newCustomer);
    }

    /*TODO:
    *  metod som uppdaterar kundens mail, får ej använda en email som redan är registrerad i systemet.
    *  metod som uppdaterar kundens telefonnummer, får ej använda ett telenummer som redan finns i systemet.
    *  metod som tar bort en kund OM hen inte har aktiva bokningar
    *  metod som hömtar kund via ID
    *  metod som kollar ifall en kund har aktiva bokningar
    *  metod som hämtar kund via email
    *  metod som hämtar alla bokningar för en kund
    *
    * */

}
