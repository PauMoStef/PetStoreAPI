package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record PetRequestDto(
                Long id,
                @NotBlank(message = "El campo 'Name' es obligatorio") String name,
                @NotBlank(message = "El campo 'Status' es obligatorio") String status) {
}
