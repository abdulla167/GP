CREATE DATABASE  IF NOT EXISTS `gp_test`;

USE `gp_test`;


CREATE TABLE `patient_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(128) DEFAULT NULL,
  `city` varchar(128) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

CREATE TABLE `patient_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) DEFAULT NULL unique ,
  `user_name` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

CREATE TABLE `patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `SSID` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `midle_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `sign_id` int(11) DEFAULT NULL,
  `image` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ADDRESSP_idx` (`address_id`),
  CONSTRAINT `FK_ADDRESSP` FOREIGN KEY (`address_id`) 
  REFERENCES `patient_address` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  KEY `FK_SIGNP_idx` (`SIGN_id`),
  CONSTRAINT `FK_SIGNP` FOREIGN KEY (`sign_id`) 
  REFERENCES `patient_sign` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE `patient_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(50) DEFAULT NULL,
  `contact` varchar(128) DEFAULT NULL,
  `patient_id` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`),
  KEY `FK_PATIENT_idx` (`patient_id`),
  CONSTRAINT `FK_PATIENT` 
  FOREIGN KEY (`patient_id`) 
  REFERENCES `patient` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

CREATE TABLE `doctor_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(128) DEFAULT NULL,
  `city` varchar(128) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

CREATE TABLE `doctor_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) DEFAULT NULL UNIQUE,
  `user_name` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `role` varchar(50) Not NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

CREATE TABLE `doctor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `SSID` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `midle_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `fees` int(11) DEFAULT NULL,
  `college` varchar(128) DEFAULT NULL,
  `graduation_year` date DEFAULT NULL,
  `specialization` varchar(128) DEFAULT NULL,
  `work_place` varchar(128) DEFAULT NULL,
  `image` text DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL, 
  `sign_id` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`),
  KEY `FK_ADDRESSD_idx` (`address_id`),
  CONSTRAINT `FK_ADDRESSD` FOREIGN KEY (`address_id`) 
  REFERENCES `doctor_address` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  KEY `FK_SIGND_idx` (`sign_id`),
  CONSTRAINT `FK_SIGND` FOREIGN KEY (`sign_id`) 
  REFERENCES `doctor_sign` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `doctor_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(50) DEFAULT NULL,
  `contact` varchar(128) DEFAULT NULL,
  `doctor_id` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`),
  KEY `FK_DOCTOR_idx` (`doctor_id`),
  CONSTRAINT `FK_DOCTOR` 
  FOREIGN KEY (`doctor_id`) 
  REFERENCES `doctor` (`id`) 
  
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;


CREATE TABLE `appointment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) DEFAULT NULL,
  `patient_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  #KEY `FK_DOCTOR_idx` (`doctor_id`),
  #CONSTRAINT `FK_DOCTOR` 
  FOREIGN KEY (`doctor_id`) 
  REFERENCES `doctor` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
 # KEY `FK_PATIENT_idx` (`patient_id`),
 # CONSTRAINT `FK_DOCTOR` 
  FOREIGN KEY (`patient_id`) 
  REFERENCES `patient` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

