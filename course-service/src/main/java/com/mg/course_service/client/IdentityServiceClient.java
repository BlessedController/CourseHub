package com.mg.course_service.client;

import com.mg.course_service.dto.response.UserInfoResponse;
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

    @GetMapping("/info/{id}")
    @CircuitBreaker(name = "getUserInfoByIdCircuitBreaker", fallbackMethod = "getUserInfoByIdFallBack")
    ResponseEntity<UserInfoResponse> getUserInfoById(@NotNull @PathVariable UUID id);

    default ResponseEntity<UserInfoResponse> getUserInfoByIdFallBack(UUID id, Throwable throwable) {
        logger.warn("Fallback triggered for getUserInfoById. User ID: {}, Reason: {}", id, throwable.getMessage());

        return ResponseEntity.ok(new UserInfoResponse(
                UUID.randomUUID(),
                "Random Username",
                "Service Unavailable",
                "Identity service is currently unreachable.",
                "Please try again later.",
                null,
                null
        ));

    }


}
