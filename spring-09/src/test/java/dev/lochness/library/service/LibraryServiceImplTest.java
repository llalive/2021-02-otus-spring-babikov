package dev.lochness.library.service;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Class LibraryServiceImpl")
class LibraryServiceImplTest {
/*
    private static final String TEST_BOOK_TITLE = "Test book";
    private static final String TEST_BOOK_ISBN = "0123456789";
    private static final String TEST_AUTHOR_FIRST_NAME = "Sam";
    private static final String TEST_AUTHOR_LAST_NAME = "Sepiol";
    private static final String COMMENT_AUTHOR = "Charles Dickens";
    private static final String COMMENT_TEXT = "I can do better";

    @MockBean
    private Printer printer;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private LibraryService service;

    @Test
    void shouldPrintOneBook() {
        service.listBooks();
        verify(printer, times(1)).printBookInfo(any(Book.class), eq(false));
    }

    @Test
    void shouldPrintBookInfo() {
        service.printBookInfo(1);
        verify(printer, times(1)).printBookInfo(any(Book.class), eq(true));
    }

    @Test
    void shouldPrintOneAuthor() {
        service.listAuthors();
        verify(printer, times(1)).printAuthor(any(Author.class), eq(false));
    }

    @Test
    void shouldPrintAllGenres() {
        service.listGenres();
        verify(printer, times(8)).printGenre(any(Genre.class), eq(false));
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldAddBooksCorrectly() {
        Book book = Book.builder()
                .title(TEST_BOOK_TITLE)
                .isbn(TEST_BOOK_ISBN)
                .build();
        service.addBook(book);
        Optional<Book> addedBook = bookRepository.findById(2L);
        assertTrue(addedBook.isPresent() && addedBook.get().equals(book));
    }

    @Test
    @DirtiesContext
    void shouldCorrectlyDeleteBookFromLibrary() {
        service.deleteBook(1);
        assertEquals(0, bookRepository.count());
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldSetBookGenresCorrectly() {
        var id = List.of(1L, 3L);
        service.setBookGenres(1, id);
        var book = bookRepository.findById(1L);
        assertTrue(book.isPresent() &&
                book.get().getGenres().containsAll(genreRepository.findAllById(id)));
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldSetBookAuthorsCorrectly() {
        var author = authorRepository.save(Author.builder()
                .firstName(TEST_AUTHOR_FIRST_NAME)
                .lastName(TEST_AUTHOR_LAST_NAME)
                .build());
        service.setBookAuthors(1, List.of(author.getId()));
        var book = bookRepository.findById(1L);
        assertTrue(book.isPresent()
                && book.get().getAuthors().get(0).equals(author));
    }

    @Test
    @DirtiesContext
    void shouldCorrectlyUpdateBookInfo() {
        service.updateBook(1L, TEST_BOOK_TITLE, TEST_BOOK_ISBN);
        var book = bookRepository.findById(1L);
        assertTrue(book.isPresent()
                && book.get().getTitle().equals(TEST_BOOK_TITLE)
                && book.get().getIsbn().equals(TEST_BOOK_ISBN));
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldCorrectlyAddCommentsForBook() {
        var comment = new Comment(0L, COMMENT_AUTHOR, COMMENT_TEXT);
        service.addComment(1L, comment);
        var book = bookRepository.findById(1L);
        assertTrue(book.isPresent() && !book.get().getComments().isEmpty()
                && book.get().getComments().get(0).getCommentedBy().equals(COMMENT_AUTHOR)
                && book.get().getComments().get(0).getText().equals(COMMENT_TEXT));

    }*/
}