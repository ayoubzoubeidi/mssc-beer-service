package com.maz.msscbeerservice.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignConfig {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(@Value("${maz.brewery.inventory-username}") String username,
                                                                      @Value("${maz.brewery.inventory-password}") String password) {

        return new BasicAuthRequestInterceptor(username, password);

    }

}
