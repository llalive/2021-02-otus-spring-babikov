package dev.lochness.spring.integration;

import dev.lochness.spring.integration.domain.*;
import dev.lochness.spring.integration.gateway.OpenWindow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@IntegrationComponentScan
@ConfigurationPropertiesScan
@SpringBootApplication
public class IntegrationApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(IntegrationApplication.class)
                        .web(WebApplicationType.NONE)
                        .run(args);

        OpenWindow openedWindow = (OpenWindow) context.getBean("openWindow");

        List<Creature> creatures = new ArrayList<>();
        creatures.add(openedWindow.enter(new Cat()));
        creatures.add(openedWindow.enter(new Bug()));
        creatures.add(openedWindow.enter(new Worm()));
        creatures.add(openedWindow.enter(new Caterpillar()));

        long butterfliesCount = creatures.stream()
                .filter(Butterfly.class::isInstance)
                .count();

        log.warn("Butterflies growth: " + butterfliesCount);
    }

}
