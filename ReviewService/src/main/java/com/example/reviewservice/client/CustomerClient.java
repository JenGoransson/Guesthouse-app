package com.example.reviewservice.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerClient {
    private final RestTemplate restTemplate;
    private final String customerServiceUrl;

    public CustomerClient(RestTemplate restTemplate,
                          @Value("${customer.service.base-url}")String customerServiceUrl){
        this.restTemplate = restTemplate;
        this.customerServiceUrl = customerServiceUrl;

    }
    public boolean customerExists(String customerId){
        try{
            restTemplate.getForObject(
                    customerServiceUrl + "/api/customer/" + customerId, Object.class
            );
            return true;
        }catch (HttpClientErrorException.NotFound e){
            return false;
        }catch (Exception e){
            throw new IllegalStateException("Customer service unavailable");
        }
    }
}
