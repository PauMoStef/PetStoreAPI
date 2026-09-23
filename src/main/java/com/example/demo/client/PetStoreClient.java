package com.example.demo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.example.demo.model.Pet;

@Component
public class PetStoreClient {

    private final RestClient restClient;

    public PetStoreClient(@Value("${petstore.api.url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Pet getPetById(Integer id) {
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
