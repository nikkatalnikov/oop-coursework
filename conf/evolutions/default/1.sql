-- !Ups

-- -----------------------------------------------------
-- Table `log_db`.`district`
-- -----------------------------------------------------
CREATE TABLE `log_db`.`district` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(45) NOT NULL,
    `city` VARCHAR(45) NOT NULL)
;

INSERT INTO `log_db`.`district` (`title`, `city`) values ("Pechecrsky_West", "Kyiv");
INSERT INTO `log_db`.`district` (`title`, `city`) values ("Holosiivsky_North", "Kyiv");

-- !Downs
DROP TABLE `log_db`.`district`