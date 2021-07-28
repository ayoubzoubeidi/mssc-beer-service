package com.maz.msscbeerservice.web.mappers;

import com.maz.brewery.model.BeerDto;
import com.maz.brewery.model.BeerStyleEnum;
import com.maz.msscbeerservice.domain.Beer;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-28T12:29:39+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Amazon.com Inc.)"
)
@Component
@Qualifier("delegate")
public class BeerMapperImpl_ implements BeerMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        if ( beerDto == null ) {
            return null;
        }

        Beer beer = new Beer();

        beer.setId( beerDto.getId() );
        if ( beerDto.getVersion() != null ) {
            beer.setVersion( beerDto.getVersion().longValue() );
        }
        beer.setCreatedDate( dateMapper.asTimeStamp( beerDto.getCreatedDate() ) );
        beer.setLastModifiedDate( dateMapper.asTimeStamp( beerDto.getLastModifiedDate() ) );
        beer.setBeerName( beerDto.getBeerName() );
        if ( beerDto.getBeerStyle() != null ) {
            beer.setBeerStyle( beerDto.getBeerStyle().name() );
        }
        beer.setUpc( beerDto.getUpc() );
        beer.setPrice( beerDto.getPrice() );

        return beer;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        if ( beer == null ) {
            return null;
        }

        BeerDto beerDto = new BeerDto();

        beerDto.setId( beer.getId() );
        if ( beer.getVersion() != null ) {
            beerDto.setVersion( beer.getVersion().intValue() );
        }
        beerDto.setCreatedDate( dateMapper.asOffsetDateTime( beer.getCreatedDate() ) );
        beerDto.setLastModifiedDate( dateMapper.asOffsetDateTime( beer.getLastModifiedDate() ) );
        beerDto.setBeerName( beer.getBeerName() );
        if ( beer.getBeerStyle() != null ) {
            beerDto.setBeerStyle( Enum.valueOf( BeerStyleEnum.class, beer.getBeerStyle() ) );
        }
        beerDto.setUpc( beer.getUpc() );
        beerDto.setPrice( beer.getPrice() );

        return beerDto;
    }

    @Override
    public BeerDto beerDtoToBeerWithoutInventory(Beer beer) {
        if ( beer == null ) {
            return null;
        }

        BeerDto beerDto = new BeerDto();

        beerDto.setId( beer.getId() );
        if ( beer.getVersion() != null ) {
            beerDto.setVersion( beer.getVersion().intValue() );
        }
        beerDto.setCreatedDate( dateMapper.asOffsetDateTime( beer.getCreatedDate() ) );
        beerDto.setLastModifiedDate( dateMapper.asOffsetDateTime( beer.getLastModifiedDate() ) );
        beerDto.setBeerName( beer.getBeerName() );
        if ( beer.getBeerStyle() != null ) {
            beerDto.setBeerStyle( Enum.valueOf( BeerStyleEnum.class, beer.getBeerStyle() ) );
        }
        beerDto.setUpc( beer.getUpc() );
        beerDto.setPrice( beer.getPrice() );

        return beerDto;
    }
}
