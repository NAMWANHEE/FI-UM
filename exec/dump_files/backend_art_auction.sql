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
-- Table structure for table `art_auction`
--

DROP TABLE IF EXISTS `art_auction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `art_auction` (
  `auction_no` int NOT NULL AUTO_INCREMENT,
  `auction_price` int NOT NULL,
  `content` varchar(255) NOT NULL,
  `created_time` decimal(19,2) NOT NULL,
  `image_path` varchar(255) NOT NULL,
  `instant_price` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `view_count` int NOT NULL,
  `user_no` int DEFAULT NULL,
  `winner_no` int DEFAULT NULL,
  PRIMARY KEY (`auction_no`),
  KEY `FK6j7feckc8m0ik442tu2wy44o1` (`user_no`),
  KEY `FKmn6hvek27i6k07gi8i7q166nu` (`winner_no`),
  CONSTRAINT `FK6j7feckc8m0ik442tu2wy44o1` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `FKmn6hvek27i6k07gi8i7q166nu` FOREIGN KEY (`winner_no`) REFERENCES `user` (`user_no`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `art_auction`
--

LOCK TABLES `art_auction` WRITE;
/*!40000 ALTER TABLE `art_auction` DISABLE KEYS */;
INSERT INTO `art_auction` VALUES (13,1000,'큐티피라미',3.00,'image/d4ac6c44-cdcf-4365-af94-a64dc861a1b3.jpg',30000,'귀여운 물고기',0,55,57),(14,1000,'무지개가 무지 이뻐요',3.00,'image/600fcbcf-880b-436f-9447-9e417d5a7ea6.jpg',123123,'무지개',0,54,57),(15,10000,'귀여운 강아지 사진을 올렸습니다~',1695703032273.00,'image/2ae02bfe-ac69-40a1-bf01-fdd98473c404.jpg',25000,'귀여운 강아쥐',0,64,57),(16,12000,'야경이 너무 예뻐요',1695703283804.00,'image/f4926828-ba95-4647-9e61-f86edce2bd2a.jpg',30000,'야경 구경하세요',0,66,57),(17,3000,'미술관에서 본 작품이에요',1695703421445.00,'image/067a47cc-26fe-49e8-b5ef-2b9aadc3f56a.jpg',10000,'미술관에 갔어요.',0,73,57),(18,1000,'원장 선생님한테 물어봐서 쿠키 값으로 올려요!',1695705783137.00,'image/52e4386b-a688-4d63-9de0-66f8f9f5fd6b.jfif',2500,'간식으로 먹은 쿠키사진',0,55,57),(19,100,'저의 저금을 도와주세요',1696378122830.00,'image/b76e2ae0-35f6-42e3-b205-1afa0a49723a.gif',50000,'저금하는 사진',0,55,NULL),(20,100,'멋진 제주 바다 사진입니다~',1696378376400.00,'image/e527feb2-8cbb-4212-8649-2d202e4f5d24.jpg',300000,'제주바다',0,54,NULL);
/*!40000 ALTER TABLE `art_auction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-04  9:20:32
