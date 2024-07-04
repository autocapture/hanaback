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
-- Table structure for table `detail_keyword`
--

DROP TABLE IF EXISTS `detail_keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detail_keyword` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `detail_keyword` varchar(255) DEFAULT NULL,
  `detail_keyword_category` varchar(255) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detail_keyword`
--

LOCK TABLES `detail_keyword` WRITE;
/*!40000 ALTER TABLE `detail_keyword` DISABLE KEYS */;
INSERT INTO `detail_keyword` VALUES (1,'의사성명','MDDG_DOCTOR_NAME',1),(2,'의료인의','MDDG_DOCTOR_NAME',3),(3,'원장','MDDG_DOCTOR_NAME',4),(4,'면허번호','MDDG_LICENCE_NO',1),(5,'부상등급','CIPS_INJURY_GRADE',1),(6,'상해등급','CIPS_INJURY_GRADE',2),(7,'상해용급','CIPS_INJURY_GRADE',3),(8,'의료기관명칭','HSP_NAME',1),(9,'요양기관명칭','HSP_NAME',2),(10,'진단일','MDDG_DIAG_DATE',1),(11,'진단연월일','MDDG_DIAG_DATE',2),(12,'발행일','MDDG_DIAG_DATE',3),(13,'발행연월일','MDDG_DIAG_DATE',4),(14,'부상급/항','CIPS_INJURY_GRADE',3);
/*!40000 ALTER TABLE `detail_keyword` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-04 19:30:59
