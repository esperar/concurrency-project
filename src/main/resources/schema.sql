CREATE TABLE IF NOT EXISTS `tbl_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(10) NOT NULL,
    `password` CHAR(60) NOT NULL,
    PRIMARY KEY (`id`),
    );

CREATE TABLE IF NOT EXISTS `tbl_board` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BINARY(16) NOT NULL,
    `title` VARCHAR(50) NOT NULL,
    `content` VARCHAR(1000) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_tbl_user_TO_tbl_board_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user`(`id`)
    );

CREATE TABLE IF NOT EXISTS `tbl_selfstudy` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BINARY(16) NOT NULL,
    `room_number` INT NOT NULL
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_tbl_user_TO_tbl_selfstudy_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user`(`id`)
    );