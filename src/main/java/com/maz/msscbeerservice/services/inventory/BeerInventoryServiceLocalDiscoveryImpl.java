package com.maz.msscbeerservice.services.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Profile("local-discovery")
@Slf4j
@RequiredArgsConstructor
@Service
public class BeerInventoryServiceLocalDiscoveryImpl implements BeerInventoryService {

    private final BeerInventoryFeignClient beerInventoryFeignClient;

    @Override
    public Integer getOnHandInventory(UUID beerId) {

        log.debug("Calling Inventory Service for Beer Id " + beerId.toString());

        ResponseEntity<List<BeerInventoryDto>> responseEntity = beerInventoryFeignClient.getQOH(beerId);

        Integer quantityOnHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();

        log.debug("The Quantity On Hand for " + beerId.toString() + " : " + quantityOnHand);

        return quantityOnHand;
    }
}
