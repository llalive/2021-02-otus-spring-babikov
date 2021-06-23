INSERT INTO genres(`genre_id`, `name`)
VALUES (1, 'комедия'),
       (2, 'трагедия'),
       (3, 'драма'),
       (4, 'ужасы'),
       (5, 'рассказ'),
       (6, 'роман'),
       (7, 'повесть'),
       (8, 'пьеса');

INSERT INTO books(`book_id`, `title`, `isbn`)
VALUES (1, 'Молчание ягнят', '5-17-017776-3'), (2, 'Жираф – гроза пингвинов', '978-5-04-113895-0');

INSERT INTO authors(`author_id`, `first_name`, `last_name`)
VALUES (1, 'Томас', 'Харрис'), (2, 'Дарья', 'Донцова');

INSERT INTO book_authors(`book_id`, `author_id`)
VALUES (1, 1), (2, 2);

INSERT INTO book_genres(`book_id`, `genre_id`)
VALUES (1, 3), (1, 6), (2, 4);

INSERT INTO comments(`commented_by`, `text`, `book_id`)
VALUES ('Генри Форд', 'Прочёл на одном дыхании!', 2), ('Карл Маркс', 'Не впечатлило!', 1);