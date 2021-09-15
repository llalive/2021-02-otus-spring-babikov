drop table if exists comments;

drop table if exists book_authors;

drop table if exists authors;

drop table if exists book_genres;

drop table if exists books;

drop table if exists genres;

drop sequence if exists authors_seq;

drop sequence if exists books_seq;

drop sequence if exists comments_seq;

drop sequence if exists genres_seq;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create sequence authors_seq;

create table if not exists authors
(
    author_id  BIGINT default nextval('authors_seq')
        primary key,
    first_name VARCHAR(128) not null,
    last_name  VARCHAR(128)
);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create sequence books_seq;

create table if not exists books
(
    book_id BIGINT default nextval('books_seq')
        primary key,
    title   VARCHAR(255) not null,
    isbn    VARCHAR(64)
);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create sequence comments_seq;

create table if not exists comments
(
    comment_id   BIGINT default nextval('comments_seq')
        primary key,
    commented_by VARCHAR(255),
    text         VARCHAR(255) not null,
    book_id      BIGINT
        references books (book_id)
            on delete cascade
);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create sequence genres_seq;

create table if not exists genres
(
    genre_id BIGINT default nextval('genres_seq')
        primary key,
    name     VARCHAR(64)
);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create table if not exists book_authors
(
    book_id   BIGINT not null
        references books (book_id)
            on delete cascade,
    author_id BIGINT not null
        references authors (author_id)
            on delete cascade,
    primary key (book_id, author_id)
);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create table if not exists book_genres
(
    book_id  BIGINT not null
        references books (book_id)
            on delete cascade,
    genre_id BIGINT not null
        references genres (genre_id)
            on delete cascade,
    primary key (book_id, genre_id)
);