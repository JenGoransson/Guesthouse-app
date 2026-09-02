package org.example.pensionarkundtjanst.Controller;

import org.example.pensionarkundtjanst.Model.Review;
import org.example.pensionarkundtjanst.Service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @GetMapping
    public List<Review> getReviews() {
        return reviewService.getReviews();
    }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review review) {
        return reviewService.updateReview(id, review);
    }

    @DeleteMapping
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
