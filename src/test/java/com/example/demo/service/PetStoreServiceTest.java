package com.example.demo.service;

import com.example.demo.client.PetStoreClient;
import com.example.demo.dto.PetCreatedResponseDto;
import com.example.demo.dto.PetRequestDto;
import com.example.demo.model.Category;
import com.example.demo.model.Pet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetStoreServiceTest {

    @Mock
    private PetStoreClient petStoreClient;

    @InjectMocks
    private PetStoreService petStoreService;

    @Test
    void savePetSuccess() {

        PetRequestDto inputPet = new PetRequestDto(100L, "Firulais", "available");
        Pet savedPet = new Pet(100L, new Category(1, "Dogs"), "Firulais", Collections.emptyList(),
                Collections.emptyList(), "available");

        when(petStoreClient.postPet(any(Pet.class))).thenReturn(savedPet);

        PetCreatedResponseDto result = petStoreService.savePet(inputPet);

        assertNotNull(result);
        assertEquals("Firulais", result.name());
        assertEquals("available", result.status());
        assertNotNull(result.transactionId());
        assertNotNull(result.createdAt());
    }
}
