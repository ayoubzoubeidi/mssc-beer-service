package com.maz.msscbeerservice.repositories;

import com.maz.msscbeerservice.domain.Beer;
import com.maz.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyleEnum, Pageable pageRequest);
    Page<Beer> findAllByBeerName(String beerName, Pageable pageRequest);
    Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyleEnum, Pageable pageRequest);
}
