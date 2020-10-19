package com.maz.msscbeerservice.web.controller;

import com.maz.msscbeerservice.services.BeerService;
import com.maz.brewery.model.BeerDto;
import com.maz.brewery.model.BeerPagedList;
import com.maz.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("api/v1/beer")
@RestController
public class BeerController {

    private final Integer DEFAULT_PAGE_NUMBER = 0;
    private final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(name = "beerName", required = false) String beerName,
                                                   @RequestParam(name = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(name = "showInventoryOnHand", required = false, defaultValue = "false") Boolean showInventoryOnHand) {
        if (pageNumber == null || pageNumber < 1 ) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerPage = beerService.listBeers(beerName, beerStyle, showInventoryOnHand, PageRequest.of(pageNumber, pageSize));
        return new ResponseEntity<BeerPagedList>(beerPage, HttpStatus.OK);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId,
                                               @RequestParam(name = "showInventoryOnHand", required = false, defaultValue = "false") Boolean showInventoryOnHand) {

        return new ResponseEntity<>(beerService.getById(beerId, showInventoryOnHand), HttpStatus.OK);
    }

     @GetMapping("/upc/{upc}")
     public ResponseEntity<BeerDto> getBeerbyUpc(@PathVariable("upc") String upc) {
        return new ResponseEntity<>(beerService.getByUpc(upc), HttpStatus.OK);
     }

    @PostMapping
    public ResponseEntity saveNewBeer(@Validated @RequestBody BeerDto beerDto) {
        return new ResponseEntity(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDto beerDto) {

        return new ResponseEntity(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }
}
