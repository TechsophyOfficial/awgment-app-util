package com.techsophy.tsf.util.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import static com.techsophy.tsf.util.constants.CSVtoJsonConstants.WEBCLIENT_BUFFER_SIZE;
@Configuration
class WebClientConfig {

    @Value(WEBCLIENT_BUFFER_SIZE)
    int webClientBufferSize;

    @Bean
    public WebClient createWebClient() {
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(webClientBufferSize))
                .build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }}