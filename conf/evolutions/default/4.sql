-- !Ups

-- -----------------------------------------------------
-- Table `log_db`.`parcel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `log_db`.`parcel` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `recipient_number` BIGINT NOT NULL,
    `courier_id` BIGINT NOT NULL,
    `district` BIGINT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `district_c2` FOREIGN KEY (`district`) REFERENCES `log_db`.`district` (`id`),
    CONSTRAINT `courier_c1` FOREIGN KEY (`courier_id`) REFERENCES `log_db`.`courier` (`id`))
;

-- !Downs
DROP TABLE `log_db`.`parcel`;
