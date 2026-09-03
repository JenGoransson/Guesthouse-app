package com.example.reviewservice.controller;

import com.example.reviewservice.Service.ReviewService;
import com.example.reviewservice.dto.ReviewDTO;
import com.example.reviewservice.model.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> create(@RequestBody ReviewDTO dto) {
        Review review = reviewService.createReview(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReview(id));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Review>> getByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(reviewService.getReviewsByRoom(roomId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Review>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(reviewService.getReviewsByCustomer(customerId));
    }


}

