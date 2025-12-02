-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema trip_dummy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema trip_dummy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `trip_dummy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `trip_dummy` ;

-- -----------------------------------------------------
-- Table `trip_dummy`.`sidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`sidos` (
  `id` INT NOT NULL,
  `sido_code` INT NOT NULL COMMENT '시도코드',
  `sido_name` VARCHAR(20) NULL DEFAULT NULL COMMENT '시도명',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `sido_code_UNIQUE` (`sido_code` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`guguns`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`guguns` (
  `id` INT NOT NULL,
  `sido_code` INT NOT NULL,
  `gugun_code` INT NOT NULL,
  `gugun_name` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `guguns_sido_to_sidos_code_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `gugun_code_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `guguns_sido_to_sidos_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `trip_dummy`.`sidos` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`contenttypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`contenttypes` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`attractions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`attractions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content_id` INT NOT NULL,
  `title` VARCHAR(500) NULL DEFAULT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `area_code` INT NULL DEFAULT NULL,
  `si_gun_gu_code` INT NULL DEFAULT NULL,
  `first_image1` VARCHAR(100) NULL DEFAULT NULL,
  `first_image2` VARCHAR(100) NULL DEFAULT NULL,
  `map_level` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `tel` VARCHAR(20) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(100) NULL DEFAULT NULL,
  `homepage` VARCHAR(1000) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `attractions_content_id_UNIQUE` (`content_id` ASC) VISIBLE,
  INDEX `attractions_typeid_to_types_typeid_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attractions_sido_to_sidos_code_fk_idx` (`area_code` ASC) VISIBLE,
  INDEX `attractions_sigungu_to_guguns_gugun_fk_idx` (`si_gun_gu_code` ASC) VISIBLE,
  CONSTRAINT `attractions_area_to_sidos_code_fk`
    FOREIGN KEY (`area_code`)
    REFERENCES `trip_dummy`.`sidos` (`sido_code`),
  CONSTRAINT `attractions_sigungu_to_guguns_gugun_fk`
    FOREIGN KEY (`si_gun_gu_code`)
    REFERENCES `trip_dummy`.`guguns` (`gugun_code`),
  CONSTRAINT `attractions_typeid_to_types_typeid_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `trip_dummy`.`contenttypes` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 256
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`feature_codes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`feature_codes` (
  `id` INT NOT NULL,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`attraction_features`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`attraction_features` (
  `id` INT NOT NULL,
  `attraction_id` INT NOT NULL COMMENT '관광지 번호',
  `feature_id` INT NULL DEFAULT NULL,
  `vote` INT NULL DEFAULT NULL,
  `score` DECIMAL(3,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`),
  INDEX `idx_attraction_no` (`attraction_id` ASC) VISIBLE,
  INDEX `fk_attraction_features_attraction` (`feature_id` ASC) VISIBLE,
  CONSTRAINT `fk_attraction_features_attractions`
    FOREIGN KEY (`attraction_id`)
    REFERENCES `trip_dummy`.`attractions` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_attraction_features_feature_codes`
    FOREIGN KEY (`feature_id`)
    REFERENCES `trip_dummy`.`feature_codes` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`members` (
  `id` VARCHAR(20) NOT NULL,
  `pw` VARCHAR(1024) NULL DEFAULT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(100) NOT NULL,
  `join_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `role` ENUM('USER', 'ADMIN') NULL DEFAULT 'USER',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`plans`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`plans` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` VARCHAR(20) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `start_date` DATE NULL DEFAULT NULL,
  `end_date` DATE NULL DEFAULT NULL,
  `description` VARCHAR(1000) NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_plans_member_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_plans_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `trip_dummy`.`members` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`storybooks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`storybooks` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` VARCHAR(20) NOT NULL,
  `title` VARCHAR(200) NOT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `hit` INT NULL DEFAULT '0',
  `is_public` CHAR(1) NULL DEFAULT 'N',
  `thumbnail_path` VARCHAR(300) NULL DEFAULT NULL,
  `plan_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_stories_member_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_storybooks_plans1_idx` (`plan_id` ASC) VISIBLE,
  CONSTRAINT `fk_stories_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `trip_dummy`.`members` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_storybooks_plans1`
    FOREIGN KEY (`plan_id`)
    REFERENCES `trip_dummy`.`plans` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`boards`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`boards` (
  `id` INT NOT NULL,
  `member_id` VARCHAR(20) NOT NULL,
  `story_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(100) NOT NULL,
  `content` TEXT NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT '0',
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `storybooks_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_boards_member_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_boards_storybooks1_idx` (`storybooks_id` ASC) VISIBLE,
  CONSTRAINT `fk_boards_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `trip_dummy`.`members` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_boards_storybooks1`
    FOREIGN KEY (`storybooks_id`)
    REFERENCES `trip_dummy`.`storybooks` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`member_travel_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`member_travel_types` (
  `type_id` INT NOT NULL AUTO_INCREMENT,
  `mbti_code` VARCHAR(10) NULL DEFAULT NULL,
  `keywords` VARCHAR(200) NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`type_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`plan_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`plan_details` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `plan_id` INT NOT NULL,
  `attraction_id` INT NOT NULL,
  `plan_date` INT NULL DEFAULT '1',
  `sequence` INT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `fk_plan_details_plan_idx` (`plan_id` ASC) VISIBLE,
  INDEX `fk_plan_details_attraction_idx` (`attraction_id` ASC) VISIBLE,
  CONSTRAINT `fk_plan_details_attraction`
    FOREIGN KEY (`attraction_id`)
    REFERENCES `trip_dummy`.`attractions` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_plan_details_plan`
    FOREIGN KEY (`plan_id`)
    REFERENCES `trip_dummy`.`plans` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`story_pages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`story_pages` (
  `id` INT NOT NULL,
  `story_id` INT NOT NULL,
  `attraction_id` INT NOT NULL,
  `image_path` VARCHAR(300) NULL DEFAULT NULL,
  `user_memo` TEXT NULL DEFAULT NULL,
  `ai_generated_text` TEXT NULL DEFAULT NULL,
  `page_order` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_story_pages_story_idx` (`story_id` ASC) VISIBLE,
  INDEX `fk_story_pages_attraction_idx` (`attraction_id` ASC) VISIBLE,
  CONSTRAINT `fk_story_pages_attraction`
    FOREIGN KEY (`attraction_id`)
    REFERENCES `trip_dummy`.`attractions` (`id`),
  CONSTRAINT `fk_story_pages_story`
    FOREIGN KEY (`story_id`)
    REFERENCES `trip_dummy`.`storybooks` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`survey`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`survey` (
  `id` INT NOT NULL COMMENT '문항 ID',
  `question` VARCHAR(100) NOT NULL COMMENT '질문 내용',
  `type` VARCHAR(10) NOT NULL COMMENT '대분류,중분류 구분',
  `parent_id` INT NULL DEFAULT NULL COMMENT '부모 문항 ID',
  PRIMARY KEY (`id`),
  INDEX `fk_survey_parent` (`parent_id` ASC) VISIBLE,
  CONSTRAINT `fk_survey_parent`
    FOREIGN KEY (`parent_id`)
    REFERENCES `trip_dummy`.`survey` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trip_dummy`.`survey_feature_weight`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trip_dummy`.`survey_feature_weight` (
  `survey_id` INT NOT NULL COMMENT '설문 문항 ID',
  `attraction_features_id` INT NOT NULL COMMENT '관광지-특징 매핑 ID',
  `weight` DECIMAL(6,3) NOT NULL DEFAULT '0.000' COMMENT '가중치',
  PRIMARY KEY (`survey_id`, `attraction_features_id`),
  INDEX `fk_sfw_attraction_features` (`attraction_features_id` ASC) VISIBLE,
  CONSTRAINT `fk_sfw_attraction_features`
    FOREIGN KEY (`attraction_features_id`)
    REFERENCES `trip_dummy`.`attraction_features` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_sfw_survey`
    FOREIGN KEY (`survey_id`)
    REFERENCES `trip_dummy`.`survey` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
