package org.example.pensionarkundtjanst.Service;

import org.example.pensionarkundtjanst.dto.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CustomerClient {

    private final RestClient restClient;

    public CustomerClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8080/login").build();
    }

    public CustomerDTO getCustomer(Long id){
        return restClient.get().uri("/customers/{id}", id).retrieve().body(CustomerDTO.class);
    }
}
