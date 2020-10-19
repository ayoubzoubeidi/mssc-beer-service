package com.maz.msscbeerservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public final static String BREWING_REQUEST_QUEUE = "brewing-request";
    public final static String NEW_INVENTORY_QUEUE = "new-inventory";
    public final static String VALIDATE_ORDER_QUEUE = "validate-order";
    public final static String VALIDATE_ORDER_RESPONSE_QUEUE = "validate-order-result";

    @Bean //Message serialization to json
    public MessageConverter messageConverter(ObjectMapper objectMapper) {

        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);
        return converter;

    }
}
