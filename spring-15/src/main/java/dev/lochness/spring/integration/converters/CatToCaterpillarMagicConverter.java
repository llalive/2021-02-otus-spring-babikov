package dev.lochness.spring.integration.converters;

import dev.lochness.spring.integration.domain.Cat;
import dev.lochness.spring.integration.domain.Caterpillar;
import org.springframework.core.convert.converter.Converter;
import org.springframework.integration.config.IntegrationConverter;
import org.springframework.stereotype.Component;

@Component
@IntegrationConverter
public class CatToCaterpillarMagicConverter implements Converter<Cat, Caterpillar> {

    public Caterpillar convert(Cat cat) {
        return new Caterpillar(cat.getId());
    }
}
