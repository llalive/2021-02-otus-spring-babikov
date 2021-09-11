INSERT INTO authors (first_name, last_name)
VALUES ('Томас', 'Харрис');
INSERT INTO authors (first_name, last_name)
VALUES ('Дарья', 'Донцова');

INSERT INTO books (title, isbn)
VALUES ('Молчание ягнят', '5-17-017776-3');
INSERT INTO books (title, isbn)
VALUES ('Жираф – гроза пингвинов', '978-5-04-113895-0');

INSERT INTO genres (name)
VALUES ('комедия');
INSERT INTO genres (name)
VALUES ('трагедия');
INSERT INTO genres (name)
VALUES ('драма');
INSERT INTO genres (name)
VALUES ('ужасы');
INSERT INTO genres (name)
VALUES ('рассказ');
INSERT INTO genres (name)
VALUES ('роман');
INSERT INTO genres (name)
VALUES ('повесть');
INSERT INTO genres (name)
VALUES ('пьеса');

INSERT INTO book_authors (book_id, author_id)
VALUES (1, 1);
INSERT INTO book_authors (book_id, author_id)
VALUES (2, 2);
INSERT INTO book_genres (book_id, genre_id)
VALUES (1, 3);
INSERT INTO book_genres (book_id, genre_id)
VALUES (1, 6);
INSERT INTO book_genres (book_id, genre_id)
VALUES (2, 4);

INSERT INTO comments (commented_by, text, book_id)
VALUES ('Генри Форд', 'Прочёл на одном дыхании!', 2);
INSERT INTO comments (commented_by, text, book_id)
VALUES ('Карл Маркс', 'Не впечатлило!', 1);