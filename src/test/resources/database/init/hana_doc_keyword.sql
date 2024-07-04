-- MySQL dump 10.13  Distrib 8.1.0, for macos14.0 (x86_64)
--
-- Host: 172.16.213.92    Database: hana
-- ------------------------------------------------------
-- Server version	5.5.5-10.6.14-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `doc_keyword`
--

DROP TABLE IF EXISTS `doc_keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doc_keyword` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accident_type` varchar(255) DEFAULT NULL,
  `attribute` varchar(45) DEFAULT NULL,
  `confidence` int(11) DEFAULT NULL,
  `word` varchar(45) DEFAULT NULL,
  `word_category` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doc_keyword`
--

LOCK TABLES `doc_keyword` WRITE;
/*!40000 ALTER TABLE `doc_keyword` DISABLE KEYS */;
INSERT INTO `doc_keyword` VALUES (1,'COMMON','type',2,'지급내역','car_insurance_payment_statement'),(2,'COMMON','type',2,'지급결의','car_insurance_payment_statement'),(3,'COMMON','type',2,'상해급수','car_insurance_payment_statement'),(4,'COMMON','type',2,'상해등급','car_insurance_payment_statement'),(5,'COMMON','type',2,'사고사실','car_insurance_payment_statement'),(6,'COMMON','type',2,'보상처리','car_insurance_payment_statement'),(7,'COMMON','type',2,'보험금지급내역확인서','car_insurance_payment_statement'),(8,'COMMON','type',2,'수술기록지','surgery_record'),(9,'COMMON','intent',1,'procedures','surgery_record'),(10,'COMMON','intent',1,'수술명','surgery'),(11,'COMMON','form',1,'record','medical_surgery'),(12,'COMMON','type',2,'세부내역서','medical_detail'),(13,'COMMON','type',2,'수술확인서','medical_surgery'),(14,'COMMON','type',1,'처방전','medical_pharmacy'),(15,'COMMON','type',2,'진료증명서','medical_consult_confirmation'),(16,'COMMON','type',2,'진료확인서','medical_consult_confirmation'),(17,'COMMON','type',2,'퇴원확인서','medical_confirmation_in'),(18,'COMMON','type',2,'진단서','medical_diagnosis'),(19,'COMMON','type',2,'처방의약품','medical_pharmacy'),(20,'COMMON','type',2,'소견서','medical_opinion'),(21,'COMMON','type',2,'소견합니다','medical_opinion'),(22,'COMMON','type',2,'통원확인서','medical_confirmation_out');
/*!40000 ALTER TABLE `doc_keyword` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-03 12:31:27
