-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: transferapp
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DELETE FROM `transaction`;
DELETE FROM `userRelation`;
DELETE FROM `bankAccount`;
DELETE FROM `userAccount`;

--
-- Dumping data for table `user_account`
--
INSERT INTO `userAccount` (id,email, money_amount, name, password, role)
VALUES
(1,'test@test.com', 100,'myName','$2a$12$WvixoMt1YDabMN47SqksdOtzxXOIczUHZRdE9nPmDxerqk2SzU5we','ADMIN'),
(2,'friend@test.com', 100,'hisName','$2a$12$WvixoMt1YDabMN47SqksdOtzxXOIczUHZRdE9nPmDxerqk2SzU5we','USER'),
(3,'friend2@test.com', 100,'hisName2','$2a$12$WvixoMt1YDabMN47SqksdOtzxXOIczUHZRdE9nPmDxerqk2SzU5we','USER'),
(4,'friend3@test.com', 100,'hisName3','$2a$12$WvixoMt1YDabMN47SqksdOtzxXOIczUHZRdE9nPmDxerqk2SzU5we','USER');

--
-- Dumping data for table `bank_account`
--
INSERT INTO `bankAccount` (account_Iban, account_Name, useraccount_id)
VALUES ('5555','myBank',1);

--
-- Dumping data for table `relation_email`
--
INSERT INTO `userRelation` (relativeaccount_id, useraccount_id)
VALUES (2,1);

--
-- Dumping data for table `transaction`
--
INSERT INTO `transaction` (amount, date, description, is_Receiving, perceive_Amount_For_App, target, useraccount_id)
VALUES ('-50','2020-06-24 11:00:22.539473','aTransaction', false, '-2.5','friend@test.com',1);