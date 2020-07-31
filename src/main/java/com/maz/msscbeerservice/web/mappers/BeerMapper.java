package com.maz.msscbeerservice.web.mappers;

import com.maz.msscbeerservice.domain.Beer;
import com.maz.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDto beerDto);
    BeerDto beerToBeerDto(Beer beer);
}
