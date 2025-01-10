package com.rosy.framework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(WebClient.Builder builder, ObjectMapper objectMapper) {
        return builder
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> {
                            configurer.customCodecs().registerWithDefaultConfig(new Jackson2JsonDecoder(objectMapper, MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
                        })
                        .build())
                .build();
    }
}