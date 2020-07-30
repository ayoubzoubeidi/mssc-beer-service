package com.maz.msscbeerservice.bootstrap;

import com.maz.msscbeerservice.domain.Beer;
import com.maz.msscbeerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerobjects();
    }

    public void loadBeerobjects() {
        if (beerRepository.count() == 0) {
            beerRepository.save(Beer.builder()
                    .beerName("Celtia")
                    .quantityToBrew(200)
                    .beerStyle("biere")
                    .upc(32566165161L)
                    .minOnHand(3)
                    .price(new BigDecimal(22.3))
                    .build());
            beerRepository.save(Beer.builder()
                    .beerName("Becks")
                    .quantityToBrew(400)
                    .beerStyle("biere")
                    .upc(325661651421L)
                    .minOnHand(7)
                    .price(new BigDecimal(11.5))
                    .build());
            beerRepository.save(Beer.builder()
                    .beerName("Golden")
                    .quantityToBrew(200)
                    .beerStyle("biere")
                    .upc(32566165167L)
                    .minOnHand(7)
                    .price(new BigDecimal(20.3))
                    .build());
        }
    }

}
