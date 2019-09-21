-- !Ups

-- -----------------------------------------------------
-- Table `log_db`.`operator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `log_db`.`operator` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(45) NOT NULL,
    `surname` VARCHAR(45) NOT NULL,
    `login`  VARCHAR(45) NOT NULL UNIQUE,
    `password` VARCHAR(45) NOT NULL)
;

-- !Downs
DROP TABLE `log_db`.`operator`;
