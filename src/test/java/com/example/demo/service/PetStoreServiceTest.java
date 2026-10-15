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

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void getPetsSuccess() {
        Pet pet1 = new Pet(1L, null, "Doggo", Collections.emptyList(), Collections.emptyList(), "available");
        Pet pet2 = new Pet(2L, null, "Kitty", Collections.emptyList(), Collections.emptyList(), "pending");

        when(petStoreClient.getPetById(1L)).thenReturn(pet1);
        when(petStoreClient.getPetById(2L)).thenReturn(pet2);

        com.example.demo.dto.PetListRequestDTO request = new com.example.demo.dto.PetListRequestDTO(java.util.List.of(1L, 2L));
        java.util.List<com.example.demo.dto.PetResponseDto> results = petStoreService.getPets(request);

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Doggo", results.get(0).name());
        assertEquals("Kitty", results.get(1).name());
    }

    @Test
    void getPetsEmptyOrNull() {
        java.util.List<com.example.demo.dto.PetResponseDto> resultNull = petStoreService.getPets(null);
        assertNotNull(resultNull);
        assertEquals(0, resultNull.size());

        com.example.demo.dto.PetListRequestDTO requestEmpty = new com.example.demo.dto.PetListRequestDTO(Collections.emptyList());
        java.util.List<com.example.demo.dto.PetResponseDto> resultEmpty = petStoreService.getPets(requestEmpty);
        assertNotNull(resultEmpty);
        assertEquals(0, resultEmpty.size());
    }

    @Test
    void getPetsPartiallyFoundExcludesNotFound() {
        Pet pet1 = new Pet(1L, null, "Doggo", Collections.emptyList(), Collections.emptyList(), "available");

        when(petStoreClient.getPetById(1L)).thenReturn(pet1);
        when(petStoreClient.getPetById(999L)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Pet not found"));

        com.example.demo.dto.PetListRequestDTO request = new com.example.demo.dto.PetListRequestDTO(java.util.List.of(1L, 999L));
        java.util.List<com.example.demo.dto.PetResponseDto> results = petStoreService.getPets(request);

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Doggo", results.get(0).name());
        assertEquals(1L, results.get(0).id());
    }

    @Test
    void getPetsOtherErrorThrowsException() {
        when(petStoreClient.getPetById(500L))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"));

        com.example.demo.dto.PetListRequestDTO request = new com.example.demo.dto.PetListRequestDTO(java.util.List.of(500L));

        assertThrows(HttpClientErrorException.class, () -> petStoreService.getPets(request));
    }
}
