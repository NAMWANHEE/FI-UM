-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: j9a308.p.ssafy.io    Database: backend
-- ------------------------------------------------------
-- Server version	8.1.0

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

--
-- Table structure for table `sponsor_funding_history`
--

DROP TABLE IF EXISTS `sponsor_funding_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sponsor_funding_history` (
  `funding_no` int NOT NULL AUTO_INCREMENT,
  `price` int NOT NULL,
  `item_no` int NOT NULL,
  `user_no` int NOT NULL,
  PRIMARY KEY (`funding_no`),
  KEY `FK6xrlfiv62v1my3p270n2ar1tk` (`user_no`),
  KEY `FK77pulhmpyqvf0bgo46xff4sqi` (`item_no`),
  CONSTRAINT `FK6xrlfiv62v1my3p270n2ar1tk` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`) ON DELETE CASCADE,
  CONSTRAINT `FK77pulhmpyqvf0bgo46xff4sqi` FOREIGN KEY (`item_no`) REFERENCES `item_list` (`item_no`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor_funding_history`
--

LOCK TABLES `sponsor_funding_history` WRITE;
/*!40000 ALTER TABLE `sponsor_funding_history` DISABLE KEYS */;
INSERT INTO `sponsor_funding_history` VALUES (62,45,18,55),(63,-10000,16,57),(64,-50000,16,57),(65,-50000,16,57),(66,-100000,16,57),(67,20000,16,65),(68,2000,16,65),(69,-50000,19,57),(70,-10000,20,57),(71,-50000,21,57),(72,-100000,20,57),(73,50000,16,54),(74,10,18,54),(75,100000,16,48),(76,40000,16,66),(77,40000,16,66),(78,90000,16,66),(79,90000,16,66),(80,20000,16,66),(81,90000,16,66),(82,30000,16,66),(83,50000,16,61),(84,20,18,55),(85,-10000,20,57),(86,10,18,56),(87,10,16,56),(88,10,16,56),(89,50000,16,56),(90,10000,16,55);
/*!40000 ALTER TABLE `sponsor_funding_history` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-04  9:20:31
