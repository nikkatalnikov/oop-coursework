-- !Ups

-- -----------------------------------------------------
-- Table `log_db`.`parcel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `log_db`.`parcel` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(200) NOT NULL,
    `recipient_number` BIGINT NOT NULL,
    `courier_id` BIGINT NOT NULL,
    `district_id` BIGINT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `district_c2` FOREIGN KEY (`district_id`) REFERENCES `log_db`.`district` (`id`),
    CONSTRAINT `courier_c1` FOREIGN KEY (`courier_id`) REFERENCES `log_db`.`courier` (`id`))
;

INSERT INTO `log_db`.`parcel` (`title`, `recipient_number`, `courier_id`, `district_id`) VALUES ("Philosophi Naturalis Principia Mathematica", 1111111111, 1, 1);

-- !Downs
DROP TABLE `log_db`.`parcel`;
