
CREATE TABLE IF NOT EXISTS `tbl_selfstudy` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `room_count` INT NOT NULL,
    `limit` INT NOT NULL,
     PRIMARY KEY (`id`)
    );

CREATE TABLE IF NOT EXISTS `tbl_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(10) NOT NULL,
    `password` CHAR(60) NOT NULL,
    PRIMARY KEY (`id`)
    );

CREATE TABLE IF NOT EXISTS `tbl_selfstudy_user`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `selfstudy_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    CONSTRAINT `FK_tbl_selfstudy_TO_tbl_selfstudy_user_1` FOREIGN KEY (`selfstudy_id`) REFERENCES `tbl_selfstudy`(`id`),
    CONSTRAINT `FK_tbl_user_TO_tbl_selfstudy_user_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user`(`id`)
)

CREATE TABLE IF NOT EXISTS `tbl_board` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `title` VARCHAR(50) NOT NULL,
    `content` VARCHAR(1000) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_tbl_user_TO_tbl_board_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user`(`id`)
    );
