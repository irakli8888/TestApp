-- MySQL Script generated by MySQL Workbench
-- Tue Aug  4 19:34:09 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema medical_centr_database
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema medical_centr_database
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `medical_centr_database` DEFAULT CHARACTER SET utf8 ;
USE `medical_centr_database` ;

-- -----------------------------------------------------
-- Table `medical_centr_database`.`doctors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medical_centr_database`.`doctors` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `specialization` VARCHAR(45) NOT NULL,
  `patronymic` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medical_centr_database`.`patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medical_centr_database`.`patients` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `patronymic` VARCHAR(45) NOT NULL,
  `phone_number` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medical_centr_database`.`recipes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medical_centr_database`.`recipes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(145) NOT NULL,
  `priority` ENUM("Normal", "Cito", "Statim") NOT NULL,
  `creation_date` DATE NOT NULL,
  `validity` DATE NOT NULL,
  `doctors_id` INT NOT NULL,
  `patients_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_recipes_doctors_idx` (`doctors_id` ASC) VISIBLE,
  INDEX `fk_recipes_patients1_idx` (`patients_id` ASC) VISIBLE,
  CONSTRAINT `fk_recipes_doctors`
    FOREIGN KEY (`doctors_id`)
    REFERENCES `medical_centr_database`.`doctors` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_recipes_patients1`
    FOREIGN KEY (`patients_id`)
    REFERENCES `medical_centr_database`.`patients` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medical_centr_database`.`doctors_has_patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medical_centr_database`.`doctors_has_patients` (
  `doctors_id` INT NOT NULL,
  `patients_id` INT NOT NULL,
  PRIMARY KEY (`doctors_id`, `patients_id`),
  INDEX `fk_doctors_has_patients_patients1_idx` (`patients_id` ASC) VISIBLE,
  INDEX `fk_doctors_has_patients_doctors1_idx` (`doctors_id` ASC) VISIBLE,
  CONSTRAINT `fk_doctors_has_patients_doctors1`
    FOREIGN KEY (`doctors_id`)
    REFERENCES `medical_centr_database`.`doctors` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_doctors_has_patients_patients1`
    FOREIGN KEY (`patients_id`)
    REFERENCES `medical_centr_database`.`patients` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
