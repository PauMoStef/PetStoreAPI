package com.example.demo.service;

import com.example.demo.client.PetStoreClient;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.Pet;
import com.example.demo.dto.PetRequestDto;
import com.example.demo.dto.PetResponseDto;
import com.example.demo.dto.PetCreatedResponseDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class PetStoreService {

    private static final Logger log = LoggerFactory.getLogger(PetStoreService.class);
    private final PetStoreClient petStoreClient;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

    public PetStoreService(PetStoreClient petStoreClient) {
        this.petStoreClient = petStoreClient;
    }

    public PetResponseDto getPet(Long id) {
        Pet response = petStoreClient.getPetById(id);
        log.info("Pet obtenido con id: {}", id);
        return new PetResponseDto(
                response.getId(),
                response.getName(),
                response.getStatus());
    }

    public PetCreatedResponseDto savePet(PetRequestDto request) {
        Pet pet = new Pet();
        pet.setId(request.id());
        pet.setName(request.name());
        pet.setStatus(request.status());

        Pet response = petStoreClient.postPet(pet);
        log.info("Pet guardado: {}", response);

        String transactionId = UUID.randomUUID().toString();
        String created = LocalDateTime.now().format(FORMATTER);

        log.info("Registro con UUID: {} creado con fecha: {}", transactionId, created);

        return new PetCreatedResponseDto(
                response.getName(),
                response.getStatus(),
                transactionId,
                created);
    }
}
