package com.finalchecks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRetry
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.feign.clients")
@SpringBootApplication(exclude = KafkaAutoConfiguration.class, scanBasePackages = {
        "com.finalchecks",
        "com.scoring.commons"
})
public class FinalChecksApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalChecksApplication.class, args);
    }
}
