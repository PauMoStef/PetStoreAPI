package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.example.demo.model.Pet;
import com.example.demo.dto.PetResponseDto;
import com.example.demo.dto.PetCreatedResponseDto;
import com.example.demo.service.PetStoreService;

@RestController
@RequestMapping("/api")
public class DemoController {

    private final PetStoreService petStoreService;

    public DemoController(PetStoreService petStoreService) {
        this.petStoreService = petStoreService;
    }

    @GetMapping("/pet/{id}")
    public PetResponseDto getPet(@PathVariable Integer id) {
        return petStoreService.getPet(id);
    }

    @PostMapping("/pet")
    public PetCreatedResponseDto savePet(@Valid @RequestBody Pet pet) {
        return petStoreService.savePet(pet);
    }
}
