package com.example.reviewservice.review.Service;

import com.example.reviewservice.review.dto.ReviewDTO;
import com.example.reviewservice.review.model.Review;
import com.example.reviewservice.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;


    public ReviewService(ReviewRepository reviewRepository) {

        this.reviewRepository = reviewRepository;

    }
    public Review createReview(ReviewDTO dto){
           if(dto.rating() < 1 || dto.rating() > 5){
               throw new IllegalArgumentException(" Rating must be between 1 and 5");
           }
           if(dto.comment() == null || dto.comment().isBlank()){
               throw new IllegalArgumentException(" Comment cannot be empty");
           }
           Review review = new Review(
                   dto.customerId(),
                   dto.bookingId(),
                   dto.rating(),
                   dto.comment()
           );
           return reviewRepository.save(review);
        }


    public Review getReview(Long id){
        return reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public List<Review> getReviewsByCustomer(Long customerId){
        return reviewRepository.findByCustomerId(customerId);
    }
    public List<Review> getReviewsByBooking(Long bookingId){
        return reviewRepository.findByBookingId(bookingId);
    }
}
