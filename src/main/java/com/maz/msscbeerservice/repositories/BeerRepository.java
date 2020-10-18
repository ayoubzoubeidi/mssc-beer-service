package com.maz.msscbeerservice.repositories;

import com.maz.msscbeerservice.domain.Beer;
import com.maz.model.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyleEnum, Pageable pageRequest);
    Page<Beer> findAllByBeerName(String beerName, Pageable pageRequest);
    Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyleEnum, Pageable pageRequest);
    Optional<Beer> findBeerByUpc(String upc);
}
