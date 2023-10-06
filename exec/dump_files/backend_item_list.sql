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
-- Table structure for table `item_list`
--

DROP TABLE IF EXISTS `item_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_list` (
  `item_no` int NOT NULL AUTO_INCREMENT,
  `funding_amount` int NOT NULL,
  `is_completed` bit(1) NOT NULL,
  `item_count` int NOT NULL,
  `item_description` varchar(255) NOT NULL,
  `item_image_path` varchar(255) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `item_unit_price` int NOT NULL,
  `sponsorship_amount` int NOT NULL,
  PRIMARY KEY (`item_no`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_list`
--

LOCK TABLES `item_list` WRITE;
/*!40000 ALTER TABLE `item_list` DISABLE KEYS */;
INSERT INTO `item_list` VALUES (15,30000,_binary '',1000,'피라미 파닥파닥 피라미 파닥파닥 피라미 파닥파닥 피라미 파닥파닥 피라미 파닥파닥','/image/147e160d-8d3e-4166-9f22-6a2320ee21c2.jpg','피라미',100,100000),(16,682020,_binary '',10000000,'새싹을 누가 개수로 팔아요','/image/46d4f7f7-d8b1-4a5d-b1d2-99546f21985c.png','비빔밥용 새싹',10,235000),(17,13500,_binary '',3,'아이들이 공부를 하다 보니 쿠키를 먹고 싶다네요^^\n간식으로 줘야겠어요~','image/8f4fd713-fbbd-4117-bff1-e781fb1baeb3.jfif','마법의 쿠키 세트',15000,45000),(18,1500,_binary '',5,'설명설명','/image/46d4f7f7-d8b1-4a5d-b1d2-99546f21985c.png','아이템',1000,5000),(19,0,_binary '\0',20,'운동화를 사주세요.','image/079be89b-48d1-4f50-a9e5-66805d2611b6.jpg','운동화',50000,50000),(20,0,_binary '\0',30,'치킨을 사주세요','image/f212a18b-3a8d-4d52-95bf-145b895977f7.png','치킨',20000,120000),(21,0,_binary '\0',25,'바람막이가 필요합니다.','image/140cb3a9-231f-4bbf-bb6b-6d7f8ed1b5c9.jpg','바람막이',70000,50000);
/*!40000 ALTER TABLE `item_list` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-04  9:20:34
