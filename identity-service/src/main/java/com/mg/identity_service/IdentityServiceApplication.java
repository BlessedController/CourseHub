package com.mg.identity_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
public class IdentityServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);
    }


}
