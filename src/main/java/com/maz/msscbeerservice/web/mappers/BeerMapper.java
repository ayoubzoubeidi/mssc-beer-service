package com.maz.msscbeerservice.web.mappers;

import com.maz.msscbeerservice.domain.Beer;
import com.maz.msscbeerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(value = BeerMapperDecorator.class)
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDto beerDto);
    BeerDto beerToBeerDto(Beer beer);
    BeerDto beerDtoToBeerWithoutInventory(Beer beer);
}
