drop table if exists COMMENTS;

drop table if exists BOOK_AUTHORS;

drop table if exists AUTHORS;

drop table if exists BOOK_GENRES;

drop table if exists BOOKS;

drop table if exists GENRES;

drop sequence if exists AUTHORS_seq;

drop sequence if exists BOOKS_seq;

drop sequence if exists COMMENTS_seq;

drop sequence if exists GENRES_seq;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create sequence AUTHORS_seq;

create table if not exists AUTHORS
(
    AUTHOR_ID  BIGINT default nextval ('AUTHORS_seq')
        primary key,
    FIRST_NAME VARCHAR(128) not null,
    LAST_NAME  VARCHAR(128)
);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create sequence BOOKS_seq;

create table if not exists BOOKS
(
    BOOK_ID BIGINT default nextval ('BOOKS_seq')
        primary key,
    TITLE   VARCHAR(255) not null,
    ISBN    VARCHAR(64)
);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create sequence COMMENTS_seq;

create table if not exists COMMENTS
(
    COMMENT_ID   BIGINT default nextval ('COMMENTS_seq')
        primary key,
    COMMENTED_BY VARCHAR(255),
    TEXT         VARCHAR(255) not null,
    BOOK_ID      BIGINT
        references BOOKS (BOOK_ID)
            on delete cascade
);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create sequence GENRES_seq;

create table if not exists GENRES
(
    GENRE_ID BIGINT default nextval ('GENRES_seq')
        primary key,
    NAME     VARCHAR(64)
);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create table if not exists BOOK_AUTHORS
(
    BOOK_ID   BIGINT not null
        references BOOKS (BOOK_ID)
            on delete cascade,
    AUTHOR_ID BIGINT not null
        references AUTHORS (AUTHOR_ID)
            on delete cascade,
    primary key (BOOK_ID, AUTHOR_ID)
);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
create table if not exists BOOK_GENRES
(
    BOOK_ID  BIGINT not null
        references BOOKS (BOOK_ID)
            on delete cascade,
    GENRE_ID BIGINT not null
        references GENRES (GENRE_ID)
            on delete cascade,
    primary key (BOOK_ID, GENRE_ID)
);