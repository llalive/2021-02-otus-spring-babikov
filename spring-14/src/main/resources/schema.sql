DROP DATABASE IF EXISTS `test`;

CREATE DATABASE IF NOT EXISTS `test` CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `test`;

CREATE TABLE IF NOT EXISTS `assembly`
(
    `id`   Int(0) AUTO_INCREMENT                                   NOT NULL,
    `name` VarChar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `unique_id` UNIQUE (`id`)
)
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS `shooting_peoples`
(
    `shooting_id` Int(0)     NOT NULL,
    `people_id`   Int(0)     NOT NULL,
    `on_place`    TinyInt(1) NOT NULL DEFAULT '0'
)
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ENGINE = InnoDB;

CREATE INDEX `peoples_id` USING BTREE ON `shooting_peoples` (`people_id`);

CREATE INDEX `shootings_id` USING BTREE ON `shooting_peoples` (`shooting_id`);

CREATE TABLE IF NOT EXISTS `shooting_set`
(
    `shooting_id` Int(0) NOT NULL,
    `set_id`      Int(0) NOT NULL
)
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ENGINE = InnoDB;

CREATE INDEX `set` USING BTREE ON `shooting_set` (`set_id`);

CREATE INDEX `shootings` USING BTREE ON `shooting_set` (`shooting_id`);

CREATE TABLE IF NOT EXISTS `shootings`
(
    `id`            Int(0) AUTO_INCREMENT NOT NULL,
    `program_id`    Int(0)                NULL,
    `send_time`     Timestamp             NULL,
    `start_time`    Timestamp             NULL,
    `return_time`   Timestamp             NULL,
    `complete_time` Timestamp             NULL,
    `status_id`     TinyInt(0)            NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
)
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;

CREATE INDEX `program_id` USING BTREE ON `shootings` (`program_id`);

CREATE INDEX `status_id` USING BTREE ON `shootings` (`status_id`);

CREATE TABLE IF NOT EXISTS `assembly_task`
(
    `id`          Int(0) AUTO_INCREMENT                                   NOT NULL,
    `program_id`  Int(0)                                                  NULL,
    `assembly_id` Int(0)                                                  NULL,
    `theme`       VarChar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
    `start_date`  Timestamp                                               NULL DEFAULT CURRENT_TIMESTAMP,
    `end_date`    Timestamp                                               NULL,
    `comment`     Text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL,
    `job_type`    Int(0)                                                  NULL,
    `created_by`  Int(0)                                                  NOT NULL,
    `status_id`   TinyInt(0)                                              NULL,
    PRIMARY KEY (`id`)
)
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;

CREATE INDEX `lnk_assembly_assembly_task` USING BTREE ON `assembly_task` (`assembly_id`);

CREATE INDEX `lnk_assembly_task_job_types_assembly_task` USING BTREE ON `assembly_task` (`job_type`);

CREATE INDEX `lnk_peoples_assembly_task` USING BTREE ON `assembly_task` (`created_by`);

CREATE INDEX `lnk_statuses_assembly_task` USING BTREE ON `assembly_task` (`status_id`);

CREATE TABLE IF NOT EXISTS `assembly_task_programs`
(
    `id`      Int(0) AUTO_INCREMENT                                   NOT NULL,
    `program` VarChar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `code`    VarChar(16) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `order`   Int(0)                                                  NOT NULL DEFAULT 99,
    PRIMARY KEY (`id`)
)
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS `sets`
(
    `id`   Int(0) AUTO_INCREMENT                                   NOT NULL,
    `name` VarChar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `set_name` UNIQUE (`name`)
)
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS `shooting_cars`
(
    `shooting_id` Int(0) NOT NULL,
    `car_id`      Int(0) NOT NULL
)
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ENGINE = InnoDB;

CREATE INDEX `car` USING BTREE ON `shooting_cars` (`car_id`);

CREATE INDEX `shootingid` USING BTREE ON `shooting_cars` (`shooting_id`);

CREATE TABLE `peoples`
(
    `id`        Int(0) AUTO_INCREMENT                                   NOT NULL,
    `shortname` VarChar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `role_id`   TinyInt(0)                                              NOT NULL,
    PRIMARY KEY (`id`)
)
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;

CREATE INDEX `role_id` USING BTREE ON `peoples` (`role_id`);

CREATE TABLE IF NOT EXISTS `programs`
(
    `id`             Int(0) AUTO_INCREMENT                                               NOT NULL,
    `program`        VarChar(255) CHARACTER SET utf8 COLLATE utf8_general_ci             NOT NULL,
    `desc`           Text CHARACTER SET utf8 COLLATE utf8_general_ci                     NULL,
    `color`          VarChar(6) CHARACTER SET utf8 COLLATE utf8_general_ci               NULL     DEFAULT '0073EA',
    `news_program`   Enum ( 'true', 'false' ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'false',
    `order`          Int(0)                                                              NOT NULL DEFAULT 99,
    `studio_program` TinyInt(1)                                                          NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
)
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS `cars`
(
    `id`        Int(0) AUTO_INCREMENT                                   NOT NULL,
    `model`     VarChar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `places`    TinyInt(0)                                              NOT NULL,
    `with_psss` TinyInt(1)                                              NOT NULL DEFAULT '0',
    `number`    VarChar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`id`)
)
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ENGINE = InnoDB
    AUTO_INCREMENT = 1;