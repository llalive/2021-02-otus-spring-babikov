DROP TABLE IF EXISTS book_authors;
DROP TABLE IF EXISTS book_genres;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS acl_entry;
DROP TABLE IF EXISTS acl_object_identity;
DROP TABLE IF EXISTS acl_class;
DROP TABLE IF EXISTS acl_sid;

CREATE TABLE authors
(
    `author_id`  bigint       NOT NULL AUTO_INCREMENT,
    `first_name` varchar(128),
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

CREATE TABLE acl_sid (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  principal tinyint(1) NOT NULL,
  sid varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_1 (sid,principal)
);

CREATE TABLE acl_class (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  class varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_2 (class)
);

CREATE TABLE acl_entry (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  acl_object_identity bigint(20) NOT NULL,
  ace_order int(11) NOT NULL,
  sid bigint(20) NOT NULL,
  mask int(11) NOT NULL,
  granting tinyint(1) NOT NULL,
  audit_success tinyint(1) NOT NULL,
  audit_failure tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
);

CREATE TABLE acl_object_identity (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  object_id_class bigint(20) NOT NULL,
  object_id_identity bigint(20) NOT NULL,
  parent_object bigint(20) DEFAULT NULL,
  owner_sid bigint(20) DEFAULT NULL,
  entries_inheriting tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
);

ALTER TABLE acl_entry
ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);