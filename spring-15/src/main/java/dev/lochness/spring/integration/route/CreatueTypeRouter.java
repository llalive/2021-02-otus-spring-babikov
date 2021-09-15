package dev.lochness.spring.integration.route;

import dev.lochness.spring.integration.domain.Cat;
import dev.lochness.spring.integration.domain.Caterpillar;
import dev.lochness.spring.integration.domain.Creature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

import java.util.Objects;
import java.util.Random;

@Slf4j
@MessageEndpoint
public class CreatueTypeRouter {

    private final Converter<Cat, Caterpillar> catCaterpillarConverter;
    private final Random random;

    public CreatueTypeRouter(Converter<Cat, Caterpillar> catCaterpillarConverter) {
        this.random = new Random();
        this.catCaterpillarConverter = catCaterpillarConverter;
    }

    @Router(inputChannel = "room")
    public String routeUnwantedGuests(Creature creature) {
        Objects.requireNonNull(creature);
        if (creature instanceof Cat) {
            creature = doTheMagic((Cat) creature);
        }
        boolean isFutureButterfly = creature instanceof Caterpillar;
        if (!isFutureButterfly) {
            log.warn("Creature of type {} with id {} was punished to the street",
                    creature.getClass().getSimpleName(),
                    creature.getId());
        }
        return isFutureButterfly ? "warmPlace" : "street";
    }

    private Creature doTheMagic(Cat luckyCat) {
        int magicNumber = random.ints(1, 30).findFirst().getAsInt();
        if (magicNumber < 15) {
            return catCaterpillarConverter.convert(luckyCat);
        } else {
            return luckyCat;
        }
    }
}
