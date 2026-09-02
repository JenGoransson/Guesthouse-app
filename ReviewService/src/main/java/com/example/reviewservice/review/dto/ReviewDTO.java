package com.example.reviewservice.review.dto;

public record ReviewDTO (Long customerId, Long bookingId, int rating,String comment){
}
