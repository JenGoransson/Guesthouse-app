package com.example.reviewservice.Service;

import com.example.reviewservice.client.BookingClient;
import com.example.reviewservice.client.CustomerClient;
import com.example.reviewservice.dto.ReviewDTO;
import com.example.reviewservice.exception.BadRequestException;
import com.example.reviewservice.exception.NotFoundException;
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
    public Review createReview(ReviewDTO dto,String authHeader){
           if(dto.rating() < 1 || dto.rating() > 5){
               throw new IllegalArgumentException(" Rating must be between 1 and 5");
           }
           if(dto.comment() == null || dto.comment().isBlank()){
               throw new IllegalArgumentException(" Comment cannot be empty");
           }
           if(!customerClient.customerExists(dto.customerId().toString(), authHeader)){
               throw new NotFoundException("Customer does not exist");
           }
           if(!bookingClient.customerHasBooking(dto.customerId(), dto.roomId(), authHeader)){
               throw new BadRequestException("customer has not booked this room");
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
        return reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review not found"));
    }

    public List<Review> getReviewsByCustomer(Long customerId){
        return reviewRepository.findByCustomerId(customerId);
    }
    public List<Review> getReviewsByRoom(Long roomId){
        return reviewRepository.findByRoomId(roomId);
    }
}
