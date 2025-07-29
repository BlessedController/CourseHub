package com.mg.course_service.client;

import com.mg.course_service.dto.response.UserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "identity-service", path = "/v1/user")
@Validated
public interface IdentityServiceClient {

    Logger logger = LoggerFactory.getLogger(IdentityServiceClient.class);

    @GetMapping("/{id}")
    @CircuitBreaker(name = "getUserInfoByIdCircuitBreaker", fallbackMethod = "getUserInfoByIdFallBack")
    ResponseEntity<UserResponse> getUserById(@NotNull @PathVariable UUID id);

    default ResponseEntity<UserResponse> getUserInfoByIdFallBack(UUID id, Throwable throwable) {
        logger.warn("Fallback triggered for getUserInfoById. User ID: {}, Reason: {}", id, throwable.getMessage());

        return ResponseEntity.ok(new UserResponse(
                null,
                "Author info is currently unreachable.",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ));
    }
}
