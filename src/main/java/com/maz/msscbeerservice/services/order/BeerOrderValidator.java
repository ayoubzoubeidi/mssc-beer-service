package com.maz.msscbeerservice.services.order;

import com.maz.brewery.model.BeerOrderDto;
import com.maz.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidator {

    private final BeerRepository beerRepository;

    Boolean validateOrder(BeerOrderDto beerOrderDto) {

        AtomicBoolean isValid = new AtomicBoolean(true);

        beerOrderDto.getBeerOrderLines().forEach(
                orderLine -> {
                    if (beerRepository.existsByUpc(orderLine.getUpc()))
                        isValid.set(false);
                }
        );

        return isValid.get();

    }

}
