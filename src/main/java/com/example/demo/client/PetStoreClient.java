package com.example.demo.client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.example.demo.model.Pet;

@Component
public class PetStoreClient {

    private final RestClient restClient;

    public PetStoreClient(@Value("${petstore.api.url}") String baseUrl) {
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory();
        requestFactory.setReadTimeout(Duration.ofSeconds(30));

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(requestFactory)
                .build();
    }

    public Pet getPetById(Long id) {
        return restClient.get()
                .uri("/pet/{id}", id)
                .retrieve()
                .body(Pet.class);
    }

    public Pet postPet(Pet pet) {
        return restClient.post()
                .uri("/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .body(pet)
                .retrieve()
                .body(Pet.class);
    }
}
