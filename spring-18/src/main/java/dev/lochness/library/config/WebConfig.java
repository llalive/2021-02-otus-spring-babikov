package dev.lochness.library.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.netflix.hystrix.HystrixCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");
    }

    @Bean
    public Customizer<HystrixCircuitBreakerFactory> defaultCircuitBreakerConfig() {
        return factory -> factory.configureDefault(id -> HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(id))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(5000)
                        .withMetricsHealthSnapshotIntervalInMilliseconds(1000)
                        .withCircuitBreakerSleepWindowInMilliseconds(10000)
                        .withMetricsRollingStatisticalWindowInMilliseconds(20000)));
    }
}
