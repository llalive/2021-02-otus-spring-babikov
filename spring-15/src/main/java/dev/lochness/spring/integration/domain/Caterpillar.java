package dev.lochness.spring.integration.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;

@Slf4j
@Configuration
public class Caterpillar extends Creature {

    private static final int TRANSFORMATION_DELAY = 1000;

    public Caterpillar() {
        super();
    }

    public Caterpillar(String id) {
        super(id);
    }

    @ServiceActivator(inputChannel = "warmPlace", outputChannel = "street")
    public Butterfly transformIntoButterfly(Caterpillar caterpillar) {
        Butterfly butterfly;
        try {
            Thread.sleep(TRANSFORMATION_DELAY);
            butterfly = new Butterfly(caterpillar);
            log.warn("A new batterfly with id {} growth in a warm place", butterfly.getId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            butterfly = null;
        }
        return butterfly;
    }
}
