package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;

public class PetListRequestDTO {

    @JsonAlias("petsIds")
    private List<Long> petIds;

    public PetListRequestDTO() {
    }

    public PetListRequestDTO(List<Long> petIds) {
        this.petIds = petIds;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    @Override
    public String toString() {
        return "PetListRequestDTO{" +
                "petIds=" + petIds +
                '}';
    }
}
