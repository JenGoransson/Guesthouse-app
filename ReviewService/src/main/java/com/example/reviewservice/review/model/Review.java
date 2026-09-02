package com.example.reviewservice.review.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private Long bookingId;
    private int rating;
    private String comment;

    public Review(){}

    public Review(Long customerId,Long bookingId,int rating,String comment){
        this.customerId = customerId;
        this.bookingId = bookingId;
        this.rating = rating;
        this.comment = comment;
    }
}
