package com.maz.msscbeerservice.services.brewing;

import com.maz.msscbeerservice.config.JmsConfig;
import com.maz.msscbeerservice.domain.Beer;
import com.maz.model.events.BrewBeerEvent;
import com.maz.model.events.NewInventoryEvent;
import com.maz.msscbeerservice.repositories.BeerRepository;
import com.maz.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrewBeerListner {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event) {

        BeerDto beerDto = event.getBeerDto();

        Beer beer = beerRepository.getOne(beerDto.getId());

        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        log.debug("Brewed Beer " + newInventoryEvent.getBeerDto().getBeerName() + " QOH: " + newInventoryEvent.getBeerDto().getQuantityOnHand());

        System.err.println(newInventoryEvent);

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);

    }

}
