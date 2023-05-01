package com.startscoring.process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.feign.clients")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {
        "com.startscoring.process",
        "com.scoring.commons"
})
public class StartScoringApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartScoringApplication.class, args);
    }
}
