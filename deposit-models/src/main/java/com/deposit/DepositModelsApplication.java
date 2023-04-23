package com.deposit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DepositModelsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DepositModelsApplication.class, args);
    }
}
