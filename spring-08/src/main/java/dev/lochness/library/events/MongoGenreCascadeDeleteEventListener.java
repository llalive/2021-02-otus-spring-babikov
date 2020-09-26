package dev.lochness.library.events;

import dev.lochness.library.domain.Genre;
import dev.lochness.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoGenreCascadeDeleteEventListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Genre> event){
        super.onAfterDelete(event);
        bookRepository.removeGenreArrayElementsById(event.getSource().get("_id").toString());
    }
}
