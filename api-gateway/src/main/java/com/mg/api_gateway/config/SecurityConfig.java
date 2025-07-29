package com.mg.api_gateway.config;

import com.mg.api_gateway.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "http://localhost:5500"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/v1/user/login",
                                "/v1/user/register",
                                "/v1/client/**",
                                "/v1/course/all-courses",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/webjars/**"
                        ).permitAll()
                        .pathMatchers("/v1/course/author/**").hasAnyRole("ADMIN", "AUTHOR")
                        .pathMatchers("/v1/video/author/**").hasAnyRole("ADMIN", "AUTHOR")
                        .pathMatchers("/v1/media/**").hasAnyRole("ADMIN", "AUTHOR")
                        .pathMatchers("/v1/user/admin/**").hasRole("ADMIN")
                        .pathMatchers("/v1/user/**").authenticated()
                        .pathMatchers("/v1/course/**").authenticated()
                        .anyExchange().denyAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((exchange, ex) -> {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            String body = """
                                    {
                                      "error": "Unauthorized",
                                      "message": "Please Login First"
                                    }""";
                            byte[] bytes = body.getBytes();
                            return exchange.getResponse().writeWith(
                                    Mono.just(exchange.getResponse().bufferFactory().wrap(bytes))
                            );
                        })
                        .accessDeniedHandler((exchange, denied) -> {
                            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                            String body = """
                                    {
                                      "error": "Forbidden",
                                      "message": "You have no right to access this resource."
                                    }""";
                            byte[] bytes = body.getBytes();
                            return exchange.getResponse().writeWith(
                                    Mono.just(exchange.getResponse().bufferFactory().wrap(bytes))
                            );
                        })
                )
                .build();
    }

}
