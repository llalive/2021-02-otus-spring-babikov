DROP TABLE IF EXISTS book_authors;
DROP TABLE IF EXISTS book_genres;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;

CREATE TABLE authors
(
    `author_id`  bigint       NOT NULL AUTO_INCREMENT,
    `first_name` varchar(128) NOT NULL,
    `last_name`  varchar(128),
    PRIMARY KEY (`author_id`)
);

CREATE TABLE books
(
    `book_id` bigint       NOT NULL AUTO_INCREMENT,
    `title`   varchar(255) NOT NULL,
    `isbn`    varchar(64),
    PRIMARY KEY (`book_id`)
);

CREATE TABLE comments
(
    `comment_id`   bigint       NOT NULL AUTO_INCREMENT,
    `commented_by` varchar(255),
    `text`         varchar(255) NOT NULL,
    `book_id`      bigint,
    FOREIGN KEY (`book_id`) REFERENCES books (`book_id`) ON DELETE CASCADE,
    PRIMARY KEY (`comment_id`)
);

CREATE TABLE genres
(
    `genre_id` bigint NOT NULL AUTO_INCREMENT,
    `name`     varchar(64),
    PRIMARY KEY (`genre_id`)
);

CREATE TABLE book_authors
(
    `book_id`   bigint NOT NULL,
    `author_id` bigint NOT NULL,
    FOREIGN KEY (`book_id`) REFERENCES books (`book_id`) ON DELETE CASCADE,
    FOREIGN KEY (`author_id`) REFERENCES authors (`author_id`) ON DELETE CASCADE,
    PRIMARY KEY (`book_id`, `author_id`)
);

CREATE TABLE book_genres
(
    `book_id`  bigint NOT NULL,
    `genre_id` bigint NOT NULL,
    FOREIGN KEY (`book_id`) REFERENCES books (`book_id`) ON DELETE CASCADE,
    FOREIGN KEY (`genre_id`) REFERENCES genres (`genre_id`) ON DELETE CASCADE,
    PRIMARY KEY (`book_id`, `genre_id`)
);

CREATE TABLE users
(
    `user_id`  bigint       NOT NULL AUTO_INCREMENT,
    `username` varchar(64)  NOT NULL,
    `email`    varchar(128) NOT NULL,
    `password` varchar(64)  NOT NULL,
    `role`     varchar(32),
    PRIMARY KEY (`user_id`)
);