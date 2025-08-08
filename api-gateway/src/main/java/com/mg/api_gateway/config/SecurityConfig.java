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

import static com.mg.api_gateway.constant.ServiceUrlsForSecurity.*;


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
                                USER_LOGIN_URL,
                                USER_REGISTER_URL,
                                COURSE_API_ALL_COURSES,
                                SWAGGER_UI_URL,
                                SWAGGER_API_DOCS_URL,
                                SWAGGER_WEB_JARS_URL
                        ).permitAll()
                        .pathMatchers(COURSE_API_AUTHOR_URLS).hasAnyRole("ADMIN", "AUTHOR")
                        .pathMatchers(VIDEO_API_AUTHOR_URLS).hasAnyRole("ADMIN", "AUTHOR")
                        .pathMatchers(MEDIA_URL).hasAnyRole("ADMIN", "AUTHOR")
                        .pathMatchers(USER_ADMIN_URLS).hasRole("ADMIN")
                        .pathMatchers(USER_USER_URLS).authenticated()
                        .pathMatchers(COURSE_USER_URLS).authenticated()
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
