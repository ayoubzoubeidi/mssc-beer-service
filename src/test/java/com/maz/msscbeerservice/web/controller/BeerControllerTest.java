package com.maz.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maz.msscbeerservice.web.model.BeerDto;
import com.maz.msscbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto = getBeer();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception{
        BeerDto beerDto = getBeer();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        String id = UUID.randomUUID().toString();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/beer/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    BeerDto getBeer() {
        return BeerDto.builder()
                .beerName("Celtia")
                .beerStyle(BeerStyleEnum.LAGER)
                .upc(1000L)
                .price(new BigDecimal(25.1))
                .quantityOnHand(30)
                .build();
    }
}