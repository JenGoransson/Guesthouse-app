package com.example.reviewservice.dto;

public record ReviewDTO (Long customerId, Long roomId, int rating,String comment){
}
