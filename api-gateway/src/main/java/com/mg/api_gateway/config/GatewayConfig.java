package com.mg.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("identity-service", r -> r
                        .path("/v1/user/**")
                        .filters(f -> f.retry(1))
                        .uri("lb://identity-service"))

                .route("course-service-course", r -> r
                        .path("/v1/course/**")
                        .filters(f -> f.retry(1))
                        .uri("lb://course-service"))

                .route("course-service-video", r -> r
                        .path("/v1/video/**")
                        .filters(f -> f.retry(1))
                        .uri("lb://course-service"))

                .route("media-stock-service", r -> r
                        .path("/v1/media/**")
                        .filters(f -> f.retry(1))
                        .uri("lb://media-stock-service"))


                .build();
    }
}
