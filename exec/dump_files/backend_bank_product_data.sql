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
-- Table structure for table `bank_product_data`
--

DROP TABLE IF EXISTS `bank_product_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_product_data` (
  `bank_product_data_no` int NOT NULL AUTO_INCREMENT,
  `interest_rate` int DEFAULT NULL,
  `prime_condition` varchar(255) DEFAULT NULL,
  `prime_interest_rate` int DEFAULT NULL,
  `bank_no` int DEFAULT NULL,
  `product_no` int DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`bank_product_data_no`),
  KEY `FK1byxxku1hyjibrntniurrmrvj` (`bank_no`),
  KEY `FKbekb0kfdvdgdlmq9v9p7w09sl` (`product_no`),
  CONSTRAINT `FK1byxxku1hyjibrntniurrmrvj` FOREIGN KEY (`bank_no`) REFERENCES `bank_list` (`bank_no`) ON DELETE CASCADE,
  CONSTRAINT `FKbekb0kfdvdgdlmq9v9p7w09sl` FOREIGN KEY (`product_no`) REFERENCES `product_type` (`product_no`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_product_data`
--

LOCK TABLES `bank_product_data` WRITE;
/*!40000 ALTER TABLE `bank_product_data` DISABLE KEYS */;
INSERT INTO `bank_product_data` VALUES (1,3,'기부 10만원이상 했을시',2,1,1,'\"햇살 예금\"은 매일 자정마다 24시간 이전에 입금한 금액에 대해 일 3%의 이자를 지급합니다. 안정적이고 높은 수익을 원하는 고객들에게 적합한 선택입니다. \"햇살은행\"은 고객 중에서 기부를 10만원 이상 한 경우에 대해 우대이자 2%를 추가로 제공합니다. 고객의 선행으로 사회에 기여하고 우대이자 혜택도 누릴 수 있습니다.'),(2,3,'퀴즈 80점이상 받았을시',5,2,1,'\"유니콘 예금\"은 햇살은행과 동일하게 매일 자정마다 24시간 이전에 입금한 금액에 대해 일 3%의 이자를 지급합니다. 안전하고 안정된 예금 상품입니다. \"유니콘은행\"은 고객 중에서 퀴즈를 80점 이상 맞은 경우에 대해 우대이자 5%를 추가로 제공합니다. 지적인 호기심을 가진 고객들을 위한 특별 혜택입니다.'),(3,20,'기부 10만원이상 했을시',2,1,2,'\"햇살 적금\"은 만기일 7일로 설정되어 있으며, 만기 시에 20%의 이자를 제공합니다. 단기 목표를 위한 저축에 최적화된 상품입니다. \"햇살은행\"은 고객 중에서 기부를 10만원 이상 한 경우에 대해 우대이자 2%를 추가로 제공합니다. 고객의 선행으로 사회에 기여하고 우대이자 혜택도 누릴 수 있습니다.'),(4,30,'퀴즈 80점이상 받았을시',5,2,2,'\"유니콘 적금\"은 만기일 10일로 설정되어 있으며, 만기 시에 30%의 이자를 지급합니다. 고수익을 원하는 고객을 위한 선택입니다. \"유니콘은행\"은 고객 중에서 퀴즈를 80점 이상 맞은 경우에 대해 우대이자 5%를 추가로 제공합니다. 지적인 호기심을 가진 고객들을 위한 특별 혜택입니다.');
/*!40000 ALTER TABLE `bank_product_data` ENABLE KEYS */;
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
