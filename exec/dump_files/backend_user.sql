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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_no` int NOT NULL AUTO_INCREMENT,
  `cash` int NOT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `is_primed1` bit(1) NOT NULL,
  `is_primed2` bit(1) NOT NULL,
  `join_date` decimal(19,2) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `point` int NOT NULL,
  `rival` int DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_type` int DEFAULT NULL,
  PRIMARY KEY (`user_no`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (48,0,'image/81533c46-3c05-495f-aef5-cb2d4d26a66e.bmp',_binary '\0',_binary '\0',_binary '\0',1695184416238.00,'$2a$10$n/kBL5X/qCiu1l/pX5.rQ.PSex8zOaC4B2KX/FpcyYbgfR0QkwaCO','010-2579-4325',151000000,48,'test111','test111',2),(53,0,NULL,_binary '\0',_binary '\0',_binary '\0',1695700866428.00,'$2a$10$7IbmQnLES5Z5EUqTmU1h6uTnRxYzUl.0hunJDPlF3uNY1GzCSnWHi','010-1234-1234',0,NULL,'admin','원장쌤',1),(54,0,'image/e0165e5f-9bbb-4146-a4d1-ba6ebd14b95f.jpg',_binary '\0',_binary '\0',_binary '',1695700895525.00,'$2a$10$h8QHZ.GfjaEN9TdLjV1e3eVh42pqnTQZextBPTM2a6LuFn/KgKMIK','010-2849-2381',345590,66,'seungwooc','김승우',2),(55,0,'image/24a37aac-8540-4cd5-8c88-31b0c419fd09.jpg',_binary '\0',_binary '\0',_binary '\0',1695700955451.00,'$2a$10$GZbxjgyOlTM7slNwdrbr2uY.M0ocDy9G5uy4mQVNWWCHDW671CLhO','010-1111-1111',23135,73,'gijeongC','기정짱123',2),(56,0,'image/29d94a13-18cb-43d7-a474-895eea644356.png',_binary '\0',_binary '\0',_binary '\0',1695700965561.00,'$2a$10$bbZXNqDGdRQNtIunkAszZeLYmeSMAZxc1JCpOahvJL13l3Vy3wEfK','010-0000-0000',646170,48,'taegnA','노태균',2),(57,570000,'image/4862fa06-3c15-4272-9261-04c1f3a1070f.gif',_binary '\0',_binary '\0',_binary '\0',1695700978403.00,'$2a$10$97FKO34a0BAKoa5SAcKiyuVw1DQKQ4T5H0TLFoLgP6lmkmxSmVynG','010-1111-1111',10000,NULL,'gijeongS','어른기정',3),(58,1000000,NULL,_binary '\0',_binary '\0',_binary '\0',1695701017633.00,'$2a$10$U40T/NV1Lg1gCplD8GkToejFGRqSrj.TbtEAkBwJ0a19i7CfUCEe6','010-0000-0000',0,NULL,'taegnB','노태균B',3),(59,0,'image/7d29d41b-b664-4cd4-b47d-cfbfd0f57156.jpg',_binary '\0',_binary '\0',_binary '\0',1695701027590.00,'$2a$10$6HWnl97U0aqfEfhE7V9LxuR.6EBnKInck/rJaAwbcFB5ubxhBhVD6','010-0000-0000',0,NULL,'taegnC','노태균C',1),(60,100000,NULL,_binary '\0',_binary '\0',_binary '\0',1695701050588.00,'$2a$10$E6mDQ6rOTNT7NxjXiNIqFObcuNvQvT5aL3TIRUyd8f3op/QYUTkbq','010-2387-1278',0,NULL,'seungwoos','오진선',3),(61,0,NULL,_binary '\0',_binary '\0',_binary '\0',1695701140702.00,'$2a$10$P.X4KCPDrvwueRuK7YfXB.oebesgQ.C3oU5vdYg0Vq5H.zWmItlsy','010-1234-1234',0,64,'coach1','실습코치_아이들',2),(62,500000,NULL,_binary '\0',_binary '\0',_binary '\0',1695701153603.00,'$2a$10$95a1S2hv/bDhcmKoHdltTu4Z.0PZ.mL3sWhj/V.jhzvVM2WQgKdDa','010-1234-1234',0,NULL,'coach2','실습코치_후원자',3),(63,0,NULL,_binary '\0',_binary '\0',_binary '\0',1695701163618.00,'$2a$10$ow/N0.96DWaXUoBh5gb/jeQYWJWdtoyDQsvsi.AfyRVfx6UPT14na','010-1234-1234',0,NULL,'coach3','실습코치_원장님',1),(64,0,'image/36cf0a84-4260-4a1c-b774-d5ca71e064ed.PNG',_binary '\0',_binary '\0',_binary '\0',1695701671622.00,'$2a$10$xDCPHdxDzKWJXj9znrdBpekQQPqQw8xeumY1U999k33LVMCVfw/Ge','010-1234-1234',570000,NULL,'ide1','김승우',2),(65,0,'image/63cc52b4-5428-436e-a077-8c7cf1a52636.PNG',_binary '\0',_binary '\0',_binary '\0',1695701679399.00,'$2a$10$Axp7dAjAZja6VypLskO/8O2GuynlHMekVdpzGqq3/XT5iDVBx.Ace','010-1234-1234',520,NULL,'ide2','남완희',2),(66,0,'image/8272af5f-5fca-4a94-9fb0-ebfb83ba578b.PNG',_binary '\0',_binary '',_binary '\0',1695701687188.00,'$2a$10$e5FexoLcM4J6u/15KkAbnO4T.sxFDpDCCjauYuhdRWqSjZAsg.pdq','010-1234-1234',12000,NULL,'ide3','이승준',2),(67,0,'image/033c5adc-4ae6-40aa-b981-e6c7f9a6aa28.png',_binary '\0',_binary '\0',_binary '\0',1695701695750.00,'$2a$10$Pq8Mzkv60RV1FasYyS8/q.eftAOuKPcBWXV.pcn6c3MjxQAWl14Qq','010-1234-1234',500000,NULL,'ide4','김영우',2),(68,0,'image/962c5fde-0ca2-4ff5-b6d5-29aaffb4b85a.png',_binary '\0',_binary '\0',_binary '\0',1695701704916.00,'$2a$10$/btA18qFMmEBXWZKX/e3R.OgH0DIj8oEsrp/BxAOcOS26ry0dU5iW','010-1234-1234',500000,NULL,'ide5','노태균',2),(69,0,'image/b180ec01-63d5-461c-90b1-9201a6bd6392.png',_binary '\0',_binary '\0',_binary '\0',1695701717215.00,'$2a$10$S70d07OgaSLjGmTq/HbTRO/goFdQFGnEVUlgOCZIvTUn/R0e5D.vq','010-1234-1234',500000,NULL,'ide6','신기정',2),(70,0,'image/97c31415-62aa-4601-afc0-3201b0c31264.png',_binary '\0',_binary '\0',_binary '\0',1695701739818.00,'$2a$10$0U2sU1Hk/t8qtvC3drcwbOiLX1c12n0.49jHfGCquSDyQmYE3S/62','010-1234-1234',7700,NULL,'ide7','채가을',2),(71,0,'image/0d3762a9-85db-4b48-8768-e7cadf4784b1.png',_binary '\0',_binary '\0',_binary '\0',1695701748912.00,'$2a$10$t5MQFOLtsTTFcbpqYI.lEu.Glb/xyyD.ac43oG3d7fDLwwIQ/5gDm','010-1234-1234',500000,NULL,'ide8','최치훈',2),(72,0,'image/28b94cc0-c721-403b-bfd9-2d8edf31bbcc.png',_binary '\0',_binary '\0',_binary '\0',1695701757247.00,'$2a$10$Jf9VeiD97IB9/7vaiiOmQezK2tN8ynE/EfPb72qaLpFYyl8xq2lQ.','010-1234-1234',500000,NULL,'ide9','김도훈',2),(73,0,'image/409f2b7b-e9fb-4271-9df3-45055ad20c8a.png',_binary '\0',_binary '\0',_binary '\0',1695701765995.00,'$2a$10$/DRjDNa0l1RVgqIWR8Nc9ugpzQdgVVvc2R0YdAdXBX4psyWvJ55ny','010-1234-1234',503000,NULL,'ide10','이석훈',2),(74,0,'image/5fdf3d1e-3716-4bea-8c48-d8482f2d82e0.png',_binary '\0',_binary '\0',_binary '\0',1695701773210.00,'$2a$10$788yej6/JmDNdsmrOPDQwOgAXiB/PFVNZiePD3P.BX3HYZwNp3XyO','010-1234-1234',500000,NULL,'ide11','이민정',2),(75,0,'image/bf7b1836-5303-4433-955a-2b20d86635fb.png',_binary '\0',_binary '\0',_binary '\0',1695701782927.00,'$2a$10$mMEeI8WHnc2.URohWG1EH.TKc2igHnJF5wa6xk/DImHU3M6n4GLO.','010-1234-1234',27500,NULL,'ide12','김민희',2),(76,0,'image/ab2897e0-2d74-4854-9af3-42aa3bf1a6fe.png',_binary '\0',_binary '\0',_binary '\0',1695701797511.00,'$2a$10$8N.DjWYWUDMC0SXP4qgfE.fcJY3dTayAOdUQ0NoTJD50PFjGiMFG.','010-1234-1234',450000,NULL,'ide13','최혜지',2),(77,0,'image/27e527cc-043a-4e01-97c9-b980a1a7fb47.png',_binary '\0',_binary '\0',_binary '',1695701809314.00,'$2a$10$YWUXJg2vY6oS1TufGxqW7Oq0vUZGnT7l06sc2Vh/O3RTFHHNt/4.C','010-1234-1234',680,NULL,'ide14','박지은',2),(78,0,'image/52314bf8-e402-45f2-b637-8b1a613e7964.png',_binary '\0',_binary '\0',_binary '\0',1695701822555.00,'$2a$10$GeukQq1.glRCmJ557l78Xei9tn0liUjE1wgikrt0S4Vpz8SUUHJce','010-1234-1234',480000,NULL,'ide15','남상은',2),(79,500000,NULL,_binary '\0',_binary '\0',_binary '\0',1695701958911.00,'$2a$10$cCXqtJhMYBFiU9OOawAqXO0hGdmjmFn2MWB9JUW.LjElMFUF5qXKi','010-1234-1234',0,NULL,'sponsor1','키다리아저씨',3),(80,500000,NULL,_binary '\0',_binary '\0',_binary '\0',1695701980920.00,'$2a$10$MGtLrlVH/yqghVVGBbFTc.swgrLDdb8ToWf2ltCZVG3ZemPSLpZEC','010-1234-1234',0,NULL,'sponsor2','마포구착한사람',3),(81,500000,NULL,_binary '\0',_binary '\0',_binary '\0',1695701997751.00,'$2a$10$nyn8EzdfCyzHK5IzbrTIF.xKRbw/.WQh2o3OaGK9tdm1NkixroVWG','010-1234-1234',0,NULL,'sponsor3','천사투자',3),(82,500000,NULL,_binary '\0',_binary '\0',_binary '\0',1695702009251.00,'$2a$10$fz4ZU6QPPI1fFSxFKYAVH.yXbAYX3WtvdKpITXXiiCnX/BDmetYae','010-1234-1234',0,NULL,'sponsor4','아이사랑',3),(83,500000,NULL,_binary '\0',_binary '\0',_binary '\0',1695702041544.00,'$2a$10$4C1ixFFiRSjzp5YsRQgoM.aiEW22u8P5Elw54TBRzYvIfRFb3JeeC','010-1234-1234',0,NULL,'sponsor5','따뜻한할아버지',3),(84,0,NULL,_binary '\0',_binary '\0',_binary '\0',1695775548533.00,'$2a$10$S1iHOAo7g7EsJ9it242EX.CFizZKyNqEsJ/jPxlyiWuDorKOh6BLi','010-1234-5678',0,NULL,'coach01','코치코치',3);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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
