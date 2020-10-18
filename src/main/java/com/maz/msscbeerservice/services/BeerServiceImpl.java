package com.maz.msscbeerservice.services;

import com.maz.msscbeerservice.domain.Beer;
import com.maz.msscbeerservice.repositories.BeerRepository;
import com.maz.msscbeerservice.web.controller.NotFoundException;
import com.maz.msscbeerservice.web.mappers.BeerMapper;
import com.maz.model.BeerDto;
import com.maz.model.BeerPagedList;
import com.maz.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;


    @Cacheable(cacheNames = "beerCache", condition = "#showInventoryOnHand == false ")

    @Override
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
        BeerDto dto;
        if (showInventoryOnHand)
            dto = beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        else
            dto = beerMapper.beerDtoToBeerWithoutInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        return dto;
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {

        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }


    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, Boolean showInventoryOnHand, PageRequest pageRequest) {
        BeerPagedList beerPagedList;
        Page<Beer> pageBeer;
        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            pageBeer = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            pageBeer = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            pageBeer = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else {
            pageBeer = beerRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand) {
            beerPagedList = new BeerPagedList(
                    pageBeer
                            .stream()
                            .map(beerMapper::beerToBeerDto)
                            .collect(Collectors.toList()), pageRequest, pageBeer.getTotalElements()
            );
        } else {
            beerPagedList = new BeerPagedList(
                    pageBeer
                            .stream()
                            .map(beerMapper::beerDtoToBeerWithoutInventory)
                            .collect(Collectors.toList()), pageRequest, pageBeer.getTotalElements()
            );
        }
        return beerPagedList;

    }


    @Cacheable(cacheNames = "beerCacheByUpc", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDto getByUpc(String upc) {
        return  beerMapper.beerDtoToBeerWithoutInventory(beerRepository.findBeerByUpc(upc).orElseThrow(NotFoundException::new));
    }
}
