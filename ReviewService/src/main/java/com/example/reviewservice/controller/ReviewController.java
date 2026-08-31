package com.example.reviewservice.controller;

import com.example.reviewservice.Service.ReviewService;
import com.example.reviewservice.dto.ReviewDTO;
import com.example.reviewservice.model.Review;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }
    @PostMapping
    public Review createReview(@RequestBody ReviewDTO dto){
        return reviewService.createReview(dto);
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id){
        return reviewService.getReview(id);
    }
}
