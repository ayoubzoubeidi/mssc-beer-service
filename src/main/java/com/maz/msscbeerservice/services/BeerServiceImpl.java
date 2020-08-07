package com.maz.msscbeerservice.services;

import com.maz.msscbeerservice.domain.Beer;
import com.maz.msscbeerservice.repositories.BeerRepository;
import com.maz.msscbeerservice.web.controller.NotFoundException;
import com.maz.msscbeerservice.web.mappers.BeerMapper;
import com.maz.msscbeerservice.web.model.BeerDto;
import com.maz.msscbeerservice.web.model.BeerPagedList;
import com.maz.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
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

    @Override
    public BeerDto getById(UUID beerId) {
        return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
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

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest) {
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

        pageBeer.getContent().forEach(
                beer ->  {
                    System.err.println( "BEER SERVICE IMPL" + beer.getBeerName());
                }
        );
        beerPagedList = new BeerPagedList(
                pageBeer
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList()), pageRequest, pageBeer.getTotalElements()
        );
        return beerPagedList;
    }
}
