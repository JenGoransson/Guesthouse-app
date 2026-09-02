package com.example.reviewservice.review.repository;

import com.example.reviewservice.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCustomerId(Long customerId);
    List<Review> findByBookingId(Long bookingId);

}
