package com.example.demo.controller;

import com.example.demo.dto.PetListRequestDTO;
import com.example.demo.dto.PetResponseDto;
import com.example.demo.service.PetStoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DemoController.class)
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PetStoreService petStoreService;

    @Test
    void listPetsWithPetIdsPayload() throws Exception {
        when(petStoreService.getPets(any(PetListRequestDTO.class)))
                .thenReturn(List.of(new PetResponseDto(1L, "Firulais", "available")));

        mockMvc.perform(get("/api/listpets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"petIds\": [1]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Firulais"));
    }

    @Test
    void listPetsWithPetsIdsPayloadAlias() throws Exception {
        when(petStoreService.getPets(any(PetListRequestDTO.class)))
                .thenReturn(List.of(new PetResponseDto(2L, "Michi", "available")));

        mockMvc.perform(get("/api/listpets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"petsIds\": [2]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Michi"));
    }
}
