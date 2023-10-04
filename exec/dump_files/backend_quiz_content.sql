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
-- Table structure for table `quiz_content`
--

DROP TABLE IF EXISTS `quiz_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_content` (
  `quiz_no` int NOT NULL AUTO_INCREMENT,
  `quiz_answer` bit(1) NOT NULL,
  `quiz_content` longtext NOT NULL,
  PRIMARY KEY (`quiz_no`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_content`
--

LOCK TABLES `quiz_content` WRITE;
/*!40000 ALTER TABLE `quiz_content` DISABLE KEYS */;
INSERT INTO `quiz_content` VALUES (1,_binary '\0','돈은 물건을 살 때만 필요하다.'),(2,_binary '\0','돈으로 교환은 가능하지만 저장은 가능하지 않다.'),(3,_binary '','돈은 사용하기 쉽고 편리한 형태로 계속 변화해 왔다.'),(4,_binary '','물건을 만들고 판매하고 소비하는 모든 활동을 경제 활동이라 한다.'),(5,_binary '','금융이란 돈을 빌리고 빌려주는 것을 말한다.'),(6,_binary '\0','금융 회사로는 은행이 유일하다.'),(7,_binary '\0','금융 거래를 할 때는 개인 정보가 필요하지 않다.'),(8,_binary '\0','다른 사람이 쉽게 추측할 수 없는 비밀번호를 사용한다.'),(9,_binary '','소득은 한정되어 있어서 가지고 싶은 것을 모두 가질 수 없다.'),(10,_binary '','돈을 합리적으로 사용하려면 쓰기 전에 필요한 것인지를 따져본다.'),(11,_binary '','돈이 들어오는 것을 \'수입\', 돈이 나가는 것을 \'지출\' 이라고 한다.'),(12,_binary '\0','용돈 기입장을 적을 때는 금액이 큰 순서대로 적는다.'),(13,_binary '\0','퇴직해서 국가로부터 받는 돈은 근로 소득에 해당한다.'),(14,_binary '','가게나 회사를 직접 운영하여 얻는 소득을 사업소득이라고 한다.'),(15,_binary '','여러 상품을 비교하면 우수한 품질의 상품을 사는데 유리하다.'),(16,_binary '\0','상품을 파는 장소를 알아보는 것은 시간 낭비이다.'),(17,_binary '\0','체크 카드를 사용하면 통장에 돈이 부족하더라도 물건값을 지불할 수 있다.'),(18,_binary '','주로 직업 활동을 통해 사람들이 번 돈을 소득이라 한다.'),(19,_binary '','은행에 저축하는 것은 미래의 좀 더 나은 생활을 위해서다.'),(20,_binary '','약속한 기간 동안 매달 일정한 돈을 꾸준히 맡기는 저축 상품은 정기 적금이다.'),(21,_binary '\0','돈이 적으면 통장과 계좌를 만들 수 없다.'),(22,_binary '\0','돈의 가치는 시간이 지나도 전혀 변하지 않는다.'),(23,_binary '','지금 만 원으로 살 수 있는 물건의 양이 10년 전보다 적어진 이유는 물가가 올랐기 때문이다.'),(24,_binary '','가지고 있는 주식의 가격이 오르면 팔아서 이익을 얻을 수 있다.'),(25,_binary '','신용을 이용한 거래 수단으로 신용카드를 사용한다.'),(26,_binary '\0','신용카드는 국내에서만 사용할 수 있다.'),(27,_binary '','물건을 사고 물건값을 몇 개월에 걸쳐 나누어 지불하는 것도 신용거래이다.'),(28,_binary '\0','신용을 잃어도 금방 다시 신용을 얻을 수 있다.'),(29,_binary '','소득에 비해 부채가 많으면 신용도가 높아진다.'),(30,_binary '','정해진 날짜에 이자를 내지 않으면 신용도가 낮아진다.'),(31,_binary '','신용 관리를 위해서 신용카드 이용 금액은 소득을 넘지 않도록 한다.'),(32,_binary '','예상하지 못한 큰일을 당하는 경우 갑작스럽게 큰돈을 지출해야 할 수도 있다.'),(33,_binary '\0','위험은 피할 수 없으므로 대비할 필요가 없다.'),(34,_binary '\0','우리 가족에게는 갑작스러운 위험이 발생할 리 없다.'),(35,_binary '','여러 사람이 돈을 모아 공동 재산을 마련하고 사고를 당한 사람을 도와주는 제도를 보험이라 한다.'),(36,_binary '','개인에게 발생할 수 있는 위험에 대비하려고 보험을 이용할 수 있다.'),(37,_binary '','국가가 법으로 만들어 놓은 보험도 있다.'),(38,_binary '\0','건강 보험은 개인적으로 자유롭게 가입 여부를 결정한다.'),(39,_binary '\0','정보통신기술의 발달로 금융생활은 더 단순해졌다.'),(40,_binary '\0','인터넷상에서는 기억하기 쉽도록 항상 같은 비밀번호를 사용한다.'),(41,_binary '','인간의 생애주기 중 일반적으로 가장 많은 소득을 얻는 시기는 중-장년기이다.'),(42,_binary '','일반적으로 중-장년기 시기에 주택 구입을 위해 돈이 필요한 상황이 생길 수 있다.'),(43,_binary '','최근 스마트폰의 사용 증가로 활성화된 금융결제 방식을 모바일 결제라고 한다.'),(44,_binary '\0','현재와 미래의 소득에 영향을 미치는 요인은 \'학력\' 하나 뿐이다.'),(45,_binary '','물건을 구매할 때는 \'합리적 선택\'의 절차에 따라 의사 결정을 해야 한다.'),(46,_binary '','효과적인 예산관리를 위해 필요한 예산관리도구를 용돈기입장이라 한다.'),(47,_binary '\0','현재의 보유 자산은 미래 소득에 영향을 미치지 않는다.'),(48,_binary '','지출보다 수입이 많은 것을 \'흑자\'라고 한다.'),(49,_binary '\0','개인이 은행에 저축을 많이 할 수록 금융시장에 돈이 부족해서 기업들이 자금에 대한 어려움을 겪는다.'),(50,_binary '','저축을 통해 얻을 수 있는 혜택으로 저축 시 원금 이외에 추가로 받을 수 있는 돈을 이자라고 한다.'),(51,_binary '\0','금융투자는 은행예금보다 수익은 낮지만 안정적이다.'),(52,_binary '','다음중 투자 시 고려해야 할 점으로 안정성이 있다.'),(53,_binary '','보통예금은 출금이 자유롭다.'),(54,_binary '','짧은 기간 안에 높은 수익을 얻을 수 있다고 하는 것은 금융 피라미드 등 사기 수법일 가능성이 높다.'),(55,_binary '\0','신용대출은 담보대출보다 일반적으로 이자율이 낮다.'),(56,_binary '\0','일반적으로 대부업체의 대출 이자가 일반 은행의 대출 이자보다 낮다.'),(57,_binary '','공과금을 제때 내지 않으면 개인 신용이 낮아지고 더 많은 이자를 내게 될 수 있다.'),(58,_binary '','금융회사로부터 빌린 돈은 반드시 갚아야 한다.'),(59,_binary '\0','위험은 언제 어디서 찾아올 지 모르므로 전혀 대비할 수 없다.'),(60,_binary '','정보통신기술의 발달로 인해 가정에서 인터넷으로 보험 가입을 할 수 있다.'),(61,_binary '\0','경제적 손실이 대상이 되는 보험으로 화재,자동차,사고 등 물적 위험에 대비하기 위한 보험을 상해보험이라고 한다.'),(62,_binary '','고령화가 가속화되고 있는 상황에서 은퇴준비는 빠르면 빠를수록 바람직하다.');
/*!40000 ALTER TABLE `quiz_content` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-04  9:20:38
