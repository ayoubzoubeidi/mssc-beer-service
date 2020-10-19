package com.maz.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maz.msscbeerservice.bootstrap.BeerLoader;
import com.maz.msscbeerservice.domain.Beer;
import com.maz.msscbeerservice.repositories.BeerRepository;
import com.maz.msscbeerservice.services.BeerService;
import com.maz.brewery.model.BeerDto;
import com.maz.brewery.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    BeerRepository beerRepository;

    @MockBean
    BeerService beerService;

    private final UUID BEER_ID = UUID.randomUUID();


    @Test
    void getBeerById() throws Exception {

        when(beerRepository.findById(any())).thenReturn(Optional.of(getBeer()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + BEER_ID.toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveNewBeer() throws Exception {
        when(beerRepository.save(any())).thenReturn(getBeer());

        BeerDto beerDto = getBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception{
        when(beerRepository.findById(any())).thenReturn(Optional.of(getBeer()));
        when(beerRepository.save(any())).thenReturn(getBeer());


        BeerDto beerDto = getBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/beer/" + BEER_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }


    BeerDto getBeerDto() {
        return BeerDto.builder()
                .beerName("Celtia")
                .beerStyle(BeerStyleEnum.LAGER)
                .upc(BeerLoader.BEER_1_UPC)
                .price(new BigDecimal(25.1))
                .quantityOnHand(30)
                .build();
    }

    Beer getBeer() {
        return Beer.builder()
                .beerName("Celtia")
                .beerStyle(BeerStyleEnum.LAGER.name())
                .upc(BeerLoader.BEER_1_UPC)
                .price(new BigDecimal(25.1))
                .quantityToBrew(30)
                .build();
    }
}