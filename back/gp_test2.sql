CREATE DATABASE  IF NOT EXISTS `gp_test_2`;

USE `gp_test_2`;


CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(128) DEFAULT NULL,
  `city` varchar(128) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `sign_in` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) DEFAULT NULL unique ,
  `user_name` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `SSID` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `midle_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `sign_id` int(11) DEFAULT NULL,
  `image` text DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `langtide` double DEFAULT NULL,
  `lantide` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ADDRESSP_idx` (`address_id`),
  CONSTRAINT `FK_ADDRESSP` FOREIGN KEY (`address_id`) 
  REFERENCES `address` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  KEY `FK_SIGNP_idx` (`SIGN_id`),
  CONSTRAINT `FK_SIGNP` FOREIGN KEY (`sign_id`) 
  REFERENCES `sign_in` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
CREATE TABLE `contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(50) DEFAULT NULL,
  `contact` varchar(128) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

CREATE TABLE `patient` (
  `id` int(11) NOT NULL UNIQUE,
  `history` varchar(250) DEFAULT NULL,
  FOREIGN KEY (`id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



CREATE TABLE `doctor` (
  `id` int(11) NOT NULL UNIQUE,
  `fees` int(11) DEFAULT NULL,
  `college` varchar(128) DEFAULT NULL,
  `graduation_year` date DEFAULT NULL,
  `specialization` varchar(128) DEFAULT NULL,
  `work_place` varchar(128) DEFAULT NULL,
  FOREIGN KEY (`id`) 
  REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE `appointment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) DEFAULT NULL,
  `patient_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),

  FOREIGN KEY (`doctor_id`) 
  REFERENCES `user` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,

  FOREIGN KEY (`patient_id`) 
  REFERENCES `user` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

