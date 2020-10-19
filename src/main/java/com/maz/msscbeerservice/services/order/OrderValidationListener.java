package com.maz.msscbeerservice.services.order;

import com.maz.brewery.model.BeerOrderDto;
import com.maz.brewery.model.events.OrderValidationResponse;
import com.maz.brewery.model.events.ValidateBeerOrderRequest;
import com.maz.msscbeerservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderValidationListener {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderValidator beerOrderValidator;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateBeerOrderRequest request) {

        BeerOrderDto beerOrderDto = request.getBeerOrder();

        Boolean isValid = beerOrderValidator.validateOrder(beerOrderDto);

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                OrderValidationResponse
                        .builder().orderId(beerOrderDto.getId()).isValid(isValid));

    }

}
