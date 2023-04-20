package com.startscoring.process.configuration;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InternalConfiguration {

    @Bean
    public Capability createCapability(MeterRegistry meterRegistry) {
        // this @Bean will allow tracing to use the same trace_id when OpenFeign is used
        return new MicrometerCapability(meterRegistry);
    }
}
