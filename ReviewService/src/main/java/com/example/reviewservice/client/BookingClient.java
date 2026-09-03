package com.example.reviewservice.client;

import org.springframework.beans.factory.annotation.Value;
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
    public boolean customerHasBooking(Long customerId, Long roomId){
        try{
            Boolean result = restTemplate.getForObject(
                    bookingServiceUrl + "/booking/customer/" + customerId + "/room/" + roomId,
                    Boolean.class);
            return result != null && result;

        }catch (Exception e){
            throw new IllegalStateException("Booking service unavailable");

        }
    }
}
