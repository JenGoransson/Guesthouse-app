package org.example.customerservice.Repo;

import org.example.customerservice.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phoneNumber);
    Optional<Customer> findByEmail(String email);
}
