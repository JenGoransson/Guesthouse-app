package com.example.reviewservice.Service;

import com.example.reviewservice.client.BookingClient;
import com.example.reviewservice.client.CustomerClient;
import com.example.reviewservice.dto.ReviewDTO;
import com.example.reviewservice.model.Review;
import com.example.reviewservice.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerClient customerClient;
    private  final BookingClient bookingClient;


    public ReviewService(ReviewRepository reviewRepository,CustomerClient customerClient, BookingClient bookingClient) {

        this.reviewRepository = reviewRepository;
        this.customerClient = customerClient;
        this.bookingClient = bookingClient;

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
                   dto.roomId(),
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
    public List<Review> getReviewsByRoom(Long roomId){
        return reviewRepository.findByRoomId(roomId);
    }
}
