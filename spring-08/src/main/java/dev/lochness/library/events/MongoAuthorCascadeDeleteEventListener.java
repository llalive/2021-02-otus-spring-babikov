package dev.lochness.library.events;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoAuthorCascadeDeleteEventListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Author> event){
        super.onAfterDelete(event);
        bookRepository.removeAuthorArrayElementsById(event.getSource().get("_id").toString());
    }
}
