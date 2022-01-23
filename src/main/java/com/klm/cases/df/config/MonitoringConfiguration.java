package com.klm.cases.df.config;

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;

@Configuration
public class MonitoringConfiguration {
	@Bean
    MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {
        return registry -> {
        	registry.timer("http.server.requests");
        	registry.config()
            .meterFilter(MeterFilter.deny(id -> {
                String uri = id.getTag("uri");
                return uri != null && !uri.startsWith("/api");
            }));
        };
        
    }
	
	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
	    return new TimedAspect(registry);
	}
}