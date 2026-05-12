package se.jennifer.guesthouseapp.guesthouse.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jennifer.guesthouseapp.guesthouse.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
