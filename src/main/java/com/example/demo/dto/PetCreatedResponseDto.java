package com.example.demo.dto;

public record PetCreatedResponseDto(
        String name,
        String status,
        String transactionId,
        String createdAt
) {
}
