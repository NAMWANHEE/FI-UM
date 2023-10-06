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
-- Table structure for table `review_comment`
--

DROP TABLE IF EXISTS `review_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review_comment` (
  `comment_no` int NOT NULL AUTO_INCREMENT,
  `comment_content` varchar(255) NOT NULL,
  `create_time` decimal(19,2) NOT NULL,
  `review_no` int NOT NULL,
  `user_no` int NOT NULL,
  PRIMARY KEY (`comment_no`),
  KEY `FK25hv0b1fnd810gp1lu7eoiea2` (`review_no`),
  KEY `FKgj01j16w4wrh50x1sdwa2t509` (`user_no`),
  CONSTRAINT `FK25hv0b1fnd810gp1lu7eoiea2` FOREIGN KEY (`review_no`) REFERENCES `review_board` (`review_no`) ON DELETE CASCADE,
  CONSTRAINT `FKgj01j16w4wrh50x1sdwa2t509` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review_comment`
--

LOCK TABLES `review_comment` WRITE;
/*!40000 ALTER TABLE `review_comment` DISABLE KEYS */;
INSERT INTO `review_comment` VALUES (1,'잘봤습니다!! 좋아요',1695785756317.00,18,72),(6,'aaa',1695795787659.00,18,56),(8,'aawfaagwag',1695795952833.00,18,56),(10,'dadaadsdasfgegseg\nsgesgse\ngesgseg',1695796242747.00,18,56),(11,'segsegsegsgsegse',1695796331169.00,18,56),(12,'sefsefseges',1695796335820.00,18,56),(13,'segsegsegsegse',1695796339359.00,18,56),(14,'egsegse',1695796340795.00,18,56),(15,'gesgseg',1695796341968.00,18,56),(17,'gesgseag',1695796343914.00,18,56),(19,'esfgsegseg',1695796406529.00,18,56),(23,'1',1696378335995.00,18,61);
/*!40000 ALTER TABLE `review_comment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-04  9:20:37
