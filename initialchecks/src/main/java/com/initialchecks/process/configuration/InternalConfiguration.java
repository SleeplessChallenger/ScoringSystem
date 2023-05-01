package com.initialchecks.process.configuration;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class InternalConfiguration {

    @Bean
    public Capability createCapability(MeterRegistry meterRegistry) {
        // this @Bean will allow tracing to use the same trace_id when OpenFeign is used
        return new MicrometerCapability(meterRegistry);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
