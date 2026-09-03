package com.example.reviewservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookingClient {

    private final RestTemplate restTemplate;
    private final String bookingServiceUrl;


    public BookingClient(RestTemplate restTemplate,
                         @Value("${booking.service.base-url}")String bookingServiceUrl){
        this.restTemplate = restTemplate;
        this.bookingServiceUrl = bookingServiceUrl;
    }

    public boolean customerHasBooking(Long customerId, Long roomId, String authHeader){
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    bookingServiceUrl + "/booking/customer/" + customerId + "/room/" + roomId,
                    HttpMethod.GET,entity,Boolean.class
            );
            return Boolean.TRUE.equals(response.getBody());

        }catch (Exception e){
            throw new IllegalStateException("Booking service unavailable");

        }
    }
}
