package org.example.pensionarkundtjanst.Service;

import org.example.pensionarkundtjanst.Model.Review;
import org.example.pensionarkundtjanst.Repo.ReviewRepo;
import org.example.pensionarkundtjanst.dto.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepo reviewRepo;
    private final CustomerClient customerClient;

    public ReviewService(ReviewRepo reviewRepo, CustomerClient customerClient) {
        this.reviewRepo = reviewRepo;
        this.customerClient = customerClient;
    }

    public Review createReview(Review review) {
        CustomerDTO customer = customerClient.getCustomer(review.getCustomerId());
        return reviewRepo.save(review);
    }

    public List<Review> getReviews(){
        return reviewRepo.findAll();
    }

    public Review getReview(Long id){
        return reviewRepo.findById(id).orElseThrow();
    }

    public Review updateReview(Long id, Review updateReview){

        Review review = reviewRepo.findById(id).orElseThrow();

        review.setRating(updateReview.getRating());

        review.setComment(updateReview.getComment());

        return reviewRepo.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepo.deleteById(id);
    }
}
