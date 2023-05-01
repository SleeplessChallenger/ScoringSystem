package com.finalchecks.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ScheduledConfiguration {

    @Value("${app.executor.threadsPerProcessor}")
    private int threadsPerProcessor;

    @Bean
    public TaskScheduler makeTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(getAvailableProcessors() * threadsPerProcessor);
        taskScheduler.setThreadNamePrefix("ScheduledPool");
        return taskScheduler;
    }

    public int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
