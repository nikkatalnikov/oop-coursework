-- !Ups

-- -----------------------------------------------------
-- Table `log_db`.`district`
-- -----------------------------------------------------
CREATE TABLE `log_db`.`district` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(45) NOT NULL,
    `city` VARCHAR(45) NOT NULL)
;


-- !Downs
DROP TABLE `log_db`.`district`