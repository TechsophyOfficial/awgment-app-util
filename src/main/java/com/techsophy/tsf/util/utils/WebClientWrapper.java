package com.techsophy.tsf.util.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.exception.ExternalServiceErrorException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.Objects;

import static com.techsophy.tsf.util.constants.ErrorConstants.SERVICE_NOT_AVAILABLE;
import static com.techsophy.tsf.util.constants.FileUploadConstants.SERVICE;
import static com.techsophy.tsf.util.constants.PropertiesConstants.*;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebClientWrapper {
    private final GlobalMessageSource globalMessageSource;
    private final ObjectMapper objectMapper;
    private final WebClient client;

    @CircuitBreaker(name = SERVICE, fallbackMethod = "availableMethod")
    public String webclientRequest(String token, String url, @NotBlank String requestType, Object data) {
        if (requestType.equalsIgnoreCase(GET)) {
            return Objects.requireNonNull(client.get()
                            .uri(url)
                            .header(AUTHORIZATION,BEARER+token)
                            .retrieve()
                            .onStatus(HttpStatus::isError, clientResponse -> {
                                Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
                                return errorMessage.flatMap(msg -> {
                                    throw new ExternalServiceErrorException(msg, msg);
                                });
                            }))
                    .bodyToMono(String.class)
                    .block();
        } else {
            if (requestType.equalsIgnoreCase(DELETE)) {
                if (data == null) {
                    return Objects.requireNonNull(client.method(HttpMethod.DELETE)
                                    .uri(url)
                                    .header(AUTHORIZATION,BEARER+token)
                                    .retrieve()
                                    .onStatus(HttpStatus::isError, clientResponse -> {
                                        Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
                                        return errorMessage.flatMap(msg -> {
                                            throw new ExternalServiceErrorException(msg, msg);
                                        });
                                    }))
                            .bodyToMono(String.class)
                            .block();
                } else {
                    return Objects.requireNonNull(client.method(HttpMethod.DELETE)
                                    .uri(url)
                                    .header(AUTHORIZATION,BEARER+token)
                                    .bodyValue(data)
                                    .retrieve()
                                    .onStatus(HttpStatus::isError, clientResponse -> {
                                        Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
                                        return errorMessage.flatMap(msg -> {
                                            throw new ExternalServiceErrorException(msg,msg);
                                        });
                                    }))
                            .bodyToMono(String.class)
                            .block();
                }
            }
            if(requestType.equalsIgnoreCase(PUT))
            {
                return Objects.requireNonNull(client.put()
                                .uri(url)
                                .bodyValue(data)
                                .header(AUTHORIZATION,BEARER+token)
                                .retrieve()
                                .onStatus(HttpStatus::isError, clientResponse -> {
                                    Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
                                    return errorMessage.flatMap(msg -> {
                                        throw new ExternalServiceErrorException(msg,msg);
                                    });
                                }))
                        .bodyToMono(String.class)
                        .block();
            }
            return Objects.requireNonNull(client.post()
                            .uri(url)
                            .header(AUTHORIZATION,BEARER+token)
                            .bodyValue(data)
                            .retrieve()
                            .onStatus(HttpStatus::isError, clientResponse -> {
                                Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
                                return errorMessage.flatMap(msg -> {
                                    throw new ExternalServiceErrorException(msg,msg);
                                });
                            }))
                    .bodyToMono(String.class)
                    .block();
        }
    }

    public ResponseEntity<String> webclientRequestwithHeaderResponse(String token, String url, @NotBlank String requestType) {
        if (requestType.equalsIgnoreCase(GET)) {
                return Objects.requireNonNull(client.get()
                        .uri(url)
                        .header(AUTHORIZATION,BEARER+token)
                        .retrieve()
                        .onStatus(HttpStatus::isError, clientResponse -> {
                            Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
                            return errorMessage.flatMap(msg -> {
                                throw new ExternalServiceErrorException(msg, msg);
                            });
                        })
                        .toEntity(String.class)
                        .block());
        }
        return null;
    }

    public String availableMethod(Exception ex) throws JsonProcessingException {
        String abc = ex.getMessage();
        Map<String,Object> map = this.objectMapper.readValue(abc, Map.class);
        throw new ExternalServiceErrorException(SERVICE_NOT_AVAILABLE,globalMessageSource.get(SERVICE_NOT_AVAILABLE, String.valueOf(map.get("path"))));
    }
}


