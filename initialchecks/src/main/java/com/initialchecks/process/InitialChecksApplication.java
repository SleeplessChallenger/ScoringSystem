package com.initialchecks.process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class InitialChecksApplication {
    public static void main(String[] args) {
        SpringApplication.run(InitialChecksApplication.class, args);
    }
}
