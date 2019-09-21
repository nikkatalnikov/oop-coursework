-- !Ups

-- -----------------------------------------------------
-- Table `log_db`.`courier`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `log_db`.`courier` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(45) NOT NULL,
    `surname` VARCHAR(45) NOT NULL,
    `district` BIGINT NOT NULL,
    CONSTRAINT `district_c1` FOREIGN KEY (`district`) REFERENCES `log_db`.`district` (`id`))
;


INSERT INTO `log_db`.`courier` (`name`, `surname`, `district`) values ("Isaac", "Newton", 1);
INSERT INTO `log_db`.`courier` (`name`, `surname`, `district`) values ("Thomas", "More", 2);

-- !Downs
DROP TABLE `log_db`.`courier`;
