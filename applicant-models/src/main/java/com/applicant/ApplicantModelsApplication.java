package com.applicant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {
        "com.amqp",
        "com.applicant"
})
public class ApplicantModelsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplicantModelsApplication.class, args);
    }
}
