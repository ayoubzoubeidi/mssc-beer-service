package com.maz.msscbeerservice.services.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class InventoryServiceFeignClientFailOver implements BeerInventoryFeignClient {

    private final BeerInventoryFailOverFeignClient beerInventoryFailOverFeignClient;

    @Override
    public ResponseEntity<List<BeerInventoryDto>> getQOH(UUID beerId) {
        return beerInventoryFailOverFeignClient.getQOH();    }
}
