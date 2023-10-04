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
-- Table structure for table `stock_account`
--

DROP TABLE IF EXISTS `stock_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_account` (
  `account_no` int NOT NULL AUTO_INCREMENT,
  `stock_average` int DEFAULT NULL,
  `stock_count` int DEFAULT NULL,
  `stock_no` int NOT NULL,
  `user_no` int NOT NULL,
  PRIMARY KEY (`account_no`),
  KEY `FKixqn9hwvqdp2ph13a6fu9x9m6` (`stock_no`),
  KEY `FKmrvsmwckv6oequhbms5t2s8wo` (`user_no`),
  CONSTRAINT `FKixqn9hwvqdp2ph13a6fu9x9m6` FOREIGN KEY (`stock_no`) REFERENCES `stock_event` (`stock_no`) ON DELETE CASCADE,
  CONSTRAINT `FKmrvsmwckv6oequhbms5t2s8wo` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_account`
--

LOCK TABLES `stock_account` WRITE;
/*!40000 ALTER TABLE `stock_account` DISABLE KEYS */;
INSERT INTO `stock_account` VALUES (12,0,0,1,48),(13,0,0,3,48),(14,54265,6052,4,48),(16,63296,863,2,48),(17,22300,600,5,48),(23,5740,60,4,55),(24,0,0,1,55),(25,5200,16,1,56),(26,0,0,3,55),(27,0,0,2,65),(28,29400,1,5,65),(29,5100,50,1,65),(30,54700,9,4,70),(31,71200,3,2,75),(32,86300,3,3,75),(33,0,0,2,54),(34,3130,110,5,55),(35,0,0,1,61),(36,7800,19,3,54),(37,5300,14,4,54),(38,400,229,1,54),(39,410,2,1,77);
/*!40000 ALTER TABLE `stock_account` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-04  9:20:30
