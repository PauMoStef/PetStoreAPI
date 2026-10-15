package com.example.demo.service;

import com.example.demo.client.PetStoreClient;
import com.example.demo.dto.PetListRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.Pet;
import com.example.demo.dto.PetRequestDto;
import com.example.demo.dto.PetResponseDto;
import com.example.demo.dto.PetCreatedResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

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

    @Autowired
    @Lazy
    private PetStoreService self = this;

    @Async("petTaskExecutor")
    public CompletableFuture<PetResponseDto> getPetAsync(Long id) {
        try {
            return CompletableFuture.completedFuture(getPet(id));
        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                log.warn("Mascota con id {} no encontrada (404): {}", id, ex.getMessage());
                return CompletableFuture.completedFuture(new PetResponseDto(id, "No encontrado", ""));
            }
            throw ex;
        }
    }

    public List<PetResponseDto> getPets(PetListRequestDTO petsIds) {
        if (petsIds == null || petsIds.getPetIds() == null || petsIds.getPetIds().isEmpty()) {
            return Collections.emptyList();
        }

        log.info("Buscando ids: {}", petsIds);
        List<CompletableFuture<PetResponseDto>> futures = petsIds.getPetIds().stream()
                .map(id -> self.getPetAsync(id))
                .toList();

        try {
            return futures.stream()
                    .map(CompletableFuture::join)
                    .filter(Objects::nonNull)
                    .toList();
        } catch (CompletionException ex) {
            if (ex.getCause() instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            throw ex;
        }
    }

}