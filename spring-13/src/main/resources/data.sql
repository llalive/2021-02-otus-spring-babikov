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
VALUES (1, 'Молчание ягнят', '5-17-017776-3'),
       (2, 'Жираф – гроза пингвинов', '978-5-04-113895-0'),
       (3, 'Оно', '9785170654956');

INSERT INTO authors(`author_id`, `first_name`, `last_name`)
VALUES (1, 'Томас', 'Харрис'),
       (2, 'Дарья', 'Донцова'),
       (3, 'Стивен', 'Кинг');

INSERT INTO book_authors(`book_id`, `author_id`)
VALUES (1, 1),
       (2, 2),
       (3, 3);

INSERT INTO book_genres(`book_id`, `genre_id`)
VALUES (1, 3),
       (1, 6),
       (2, 4),
       (3, 6);

INSERT INTO comments(`commented_by`, `text`, `book_id`)
VALUES ('Генри Форд', 'Прочёл на одном дыхании!', 2),
       ('Карл Маркс', 'Не впечатлило!', 1);

INSERT INTO acl_sid (id, principal, sid)
VALUES (1, 0, 'GUEST'),
       (2, 0, 'USER'),
       (3, 0, 'ADMIN');

INSERT INTO acl_class (id, class)
VALUES (1, 'dev.lochness.library.domain.Book');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES (1, 1, 1, NULL, 3, 0),
       (2, 1, 2, NULL, 3, 0),
       (3, 1, 3, NULL, 3, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES (1, 1, 1, 3, 1, 1, 1, 1),
       (2, 1, 2, 3, 2, 1, 1, 1),
       (3, 1, 3, 3, 8, 1, 1, 1),
       (4, 2, 1, 3, 1, 1, 1, 1),
       (5, 2, 2, 3, 2, 1, 1, 1),
       (6, 2, 3, 3, 8, 1, 1, 1),
       (7, 3, 1, 3, 1, 1, 1, 1),
       (8, 3, 2, 3, 2, 1, 1, 1),
       (9, 3, 3, 3, 8, 1, 1, 1),
       (10, 1, 4, 2, 1, 1, 1, 1),
       (11, 2, 4, 2, 1, 1, 1, 1),
       (12, 3, 4, 2, 1, 1, 1, 1);