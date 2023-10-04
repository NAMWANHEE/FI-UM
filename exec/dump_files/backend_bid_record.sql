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
-- Table structure for table `bid_record`
--

DROP TABLE IF EXISTS `bid_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bid_record` (
  `bid_no` int NOT NULL AUTO_INCREMENT,
  `bid_price` int DEFAULT NULL,
  `bid_time` decimal(19,2) DEFAULT NULL,
  `auction_no` int DEFAULT NULL,
  `user_no` int DEFAULT NULL,
  PRIMARY KEY (`bid_no`),
  KEY `FKkkldp84qhbxkum0c3wtlesw1e` (`user_no`),
  KEY `FKm5k3hpaowl8e5lp4nr5i5c57d` (`auction_no`),
  CONSTRAINT `FKkkldp84qhbxkum0c3wtlesw1e` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`) ON DELETE CASCADE,
  CONSTRAINT `FKm5k3hpaowl8e5lp4nr5i5c57d` FOREIGN KEY (`auction_no`) REFERENCES `art_auction` (`auction_no`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bid_record`
--

LOCK TABLES `bid_record` WRITE;
/*!40000 ALTER TABLE `bid_record` DISABLE KEYS */;
INSERT INTO `bid_record` VALUES (103,1000,1695701887417.00,13,57),(104,1000,1695701902763.00,14,57),(105,10000,1695710448325.00,15,57),(106,1000,1695714203422.00,18,57),(107,3000,1695714324329.00,17,57),(108,2500,1695714428137.00,16,57),(109,3000,1695715495897.00,16,57),(110,12000,1695797059796.00,16,57);
/*!40000 ALTER TABLE `bid_record` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-04  9:20:36
