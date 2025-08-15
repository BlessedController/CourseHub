package com.mg.course_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "payment-service", path = "/v1/payment")
public interface PaymentServiceClient {


    @PostMapping("/pay")
    @CircuitBreaker(name = "payCircuitBreaker", fallbackMethod = "payFallBack")
    ResponseEntity<Boolean> pay();

    default ResponseEntity<Boolean> payFallBack(Throwable throwable) {
        Logger log = LoggerFactory.getLogger(PaymentServiceClient.class);
        log.error("Payment service unavailable", throwable);

        return ResponseEntity.ok(false);
    }

}
