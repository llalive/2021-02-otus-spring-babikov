package dev.lochness.spring.integration.domain;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class Creature {
    protected final String id;

    Creature() {
        this.id = UUID.randomUUID().toString();
    }

    Creature(String id) {
        this.id = id;
    }
}
