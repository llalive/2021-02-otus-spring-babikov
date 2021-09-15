package dev.lochness.spring.integration.configuration;

import dev.lochness.spring.integration.domain.Creature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;

@EnableIntegration
@IntegrationComponentScan
@Configuration
public class IntegrationConfiguration {

    @Bean
    public DirectChannel room() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.setDatatypes(Creature.class);
        return directChannel;
    }

    @Bean
    public DirectChannel street() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel warmPlace() {
        return new DirectChannel();
    }
}
