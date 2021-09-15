package dev.lochness.spring.integration;

import dev.lochness.spring.integration.domain.Bug;
import dev.lochness.spring.integration.domain.Caterpillar;
import dev.lochness.spring.integration.domain.Creature;
import dev.lochness.spring.integration.domain.Worm;
import dev.lochness.spring.integration.gateway.OpenWindow;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class IntegrationApplicationTests {

    @Autowired
    private OpenWindow gateway;

    @Test
    void shouldGrowthAtLeast1Butterfly() {
        List<Creature> creatures = List.of(new Worm(), new Caterpillar(), new Bug());
        List<Creature> actual = creatures.stream()
                .map(gateway::enter)
                .collect(Collectors.toList());
        Assertions.assertThat(actual).hasSizeGreaterThan(0);
    }

    @Test
    void shouldReturnEmptyListIfSourceIsEmpty() {
        List<Creature> creatures = List.of();
        List<Creature> actual = creatures.stream()
                .map(gateway::enter)
                .collect(Collectors.toList());
        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    void shouldThrowIfSomeCreatureIsNull() {
        assertThrows(IllegalArgumentException.class, () -> gateway.enter(null));
    }

}
