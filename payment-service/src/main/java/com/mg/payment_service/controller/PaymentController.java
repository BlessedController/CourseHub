package com.mg.payment_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    @PostMapping("/pay")
    ResponseEntity<Boolean> pay() {
        return ResponseEntity.ok(true);
    }
}
