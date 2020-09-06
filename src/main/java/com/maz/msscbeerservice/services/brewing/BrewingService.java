package com.maz.msscbeerservice.services.brewing;

import com.maz.msscbeerservice.config.JmsConfig;
import com.maz.msscbeerservice.domain.Beer;
import com.maz.common.events.BrewBeerEvent;
import com.maz.msscbeerservice.repositories.BeerRepository;
import com.maz.msscbeerservice.services.inventory.BeerInventoryService;
import com.maz.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewingService {


    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedDelay = 5000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(
                beer -> {
                    Integer beerQOH = beerInventoryService.getOnHandInventory(beer.getId());
                    if (beer.getQuantityToBrew() >= beerQOH) {

                        log.debug("Minimum Quantity on hand " + beer.getQuantityToBrew());
                        log.debug("Inventory is " + beerQOH);

                        jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
                    }
                }
        );
    }
}
