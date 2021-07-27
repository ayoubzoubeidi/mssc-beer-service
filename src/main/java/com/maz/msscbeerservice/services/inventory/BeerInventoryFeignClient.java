package com.maz.msscbeerservice.services.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

import static com.maz.msscbeerservice.services.inventory.BeerInventoryRestTemplate.INVENTORY_PATH;

@FeignClient(name = "inventory-service")
public interface BeerInventoryFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = INVENTORY_PATH)
    ResponseEntity<List<BeerInventoryDto>> getQOH(@PathVariable UUID beerId);

}
