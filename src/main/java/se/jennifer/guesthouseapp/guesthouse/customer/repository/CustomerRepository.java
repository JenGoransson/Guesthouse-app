package se.jennifer.guesthouseapp.guesthouse.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jennifer.guesthouseapp.guesthouse.customer.model.Customer;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Customer> findByEmail(String email);
}
