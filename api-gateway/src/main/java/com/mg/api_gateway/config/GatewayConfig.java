package com.mg.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.mg.api_gateway.constant.CircuitBreakerConstants.*;
import static com.mg.api_gateway.constant.ServiceUrlsForGatewayConfig.*;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route(IDENTITY_ID, r -> r
                        .path(IDENTITY_PATH)
                        .filters(f -> f
                                .circuitBreaker(c -> c
                                        .setName(IDENTITY_CIRCUIT_BREAKER_NAME)
                                        .setFallbackUri(IDENTITY_FALLBACK)))
                        .uri(IDENTITY_LB_URI))

                .route(COURSE_ID, r -> r
                        .path(COURSE_PATH)
                        .filters(f -> f
                                .circuitBreaker(c -> c
                                        .setName(COURSE_CIRCUIT_BREAKER_NAME)
                                        .setFallbackUri(COURSE_FALLBACK)))
                        .uri(COURSE_LB_URI))

                .route(MEDIA_ID, r -> r
                        .path(MEDIA_PATH)
                        .filters(f -> f
                                .circuitBreaker(c -> c
                                        .setName(MEDIA_CIRCUIT_BREAKER_NAME)
                                        .setFallbackUri(MEDIA_FALLBACK)))
                        .uri(MEDIA_LB_URI))

                .build();
    }
}
