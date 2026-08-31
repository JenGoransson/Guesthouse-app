package com.example.reviewservice.dto;

public record ReviewDTO (Long customerId, Long bookingId, int rating,String comment){
}
