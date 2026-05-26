package se.jennifer.guesthouseapp.guesthouse.customer.controller;

import org.springframework.web.bind.annotation.*;
import se.jennifer.guesthouseapp.guesthouse.customer.dto.ChangePasswordRequest;
import se.jennifer.guesthouseapp.guesthouse.customer.model.Customer;
import se.jennifer.guesthouseapp.guesthouse.customer.dto.LoginRequest;
import se.jennifer.guesthouseapp.guesthouse.customer.service.CustomerService;
import se.jennifer.guesthouseapp.guesthouse.customer.dto.CreateCustomerRequest;

import java.util.List;

@RestController
@RequestMapping("/customers")

public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody CreateCustomerRequest request){
        return customerService.createCustomer(request);
    }

    @PostMapping("/login")
    public Customer login(@RequestBody LoginRequest request){
        return customerService.login(request);
    }


    @PatchMapping("/{id}/email")
    public Customer updateEmail(@PathVariable Long id, @RequestParam String email){
        return customerService.updateEmail(id, email);
    }

    @PatchMapping("/{id}/phone")
    public Customer updatePhone(@PathVariable Long id, @RequestParam String phone){
        return customerService.updatePhone(id, phone);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }

    @GetMapping("/email")
    public Customer getCustomerByEmail(@RequestParam String email){
        return customerService.getCustomerByEmail(email);
    }

    @PutMapping("/{id}/change-password")
    public void changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request){
        customerService.changePassword(id,request);
    }
}
