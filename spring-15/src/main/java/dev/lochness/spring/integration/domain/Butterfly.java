package dev.lochness.spring.integration.domain;

public class Butterfly extends Creature {

    private Butterfly() {
        super();
    }

    private Butterfly(String id) {
        super(id);
    }

    public Butterfly(Caterpillar caterpillar) {
        super(caterpillar.getId());
    }
}
