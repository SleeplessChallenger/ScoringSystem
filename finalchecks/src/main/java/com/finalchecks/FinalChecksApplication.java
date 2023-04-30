package com.finalchecks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = KafkaAutoConfiguration.class, scanBasePackages = {
        "com.finalchecks",
        "com.scoring.commons"
})
public class FinalChecksApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalChecksApplication.class, args);
    }
}
