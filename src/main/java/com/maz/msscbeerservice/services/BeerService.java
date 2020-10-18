package com.maz.msscbeerservice.services;

import com.maz.model.BeerDto;
import com.maz.model.BeerPagedList;
import com.maz.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, Boolean showInventoryOnHand, PageRequest pageRequest);

    BeerDto getByUpc(String upc );
}
