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
-- Table structure for table `diag_info`
--

DROP TABLE IF EXISTS `diag_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diag_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `accr_no` varchar(16) DEFAULT NULL,
  `dm_seqno` varchar(5) DEFAULT NULL,
  `dscrd_spccd` varchar(255) DEFAULT NULL,
  `dt_mdcs_id` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `rtx_amt_eqal_yncd` varchar(255) DEFAULT NULL,
  `diag_date` varchar(255) DEFAULT NULL,
  `diag_stage` varchar(255) DEFAULT NULL,
  `dsacd` varchar(255) DEFAULT NULL,
  `dsnm` varchar(255) DEFAULT NULL,
  `mn_dgn_yn` varchar(255) DEFAULT NULL,
  `rqs_req_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diag_info`
--

LOCK TABLES `diag_info` WRITE;
/*!40000 ALTER TABLE `diag_info` DISABLE KEYS */;
INSERT INTO `diag_info` VALUES (15,'2024-07-03 17:08:24.402159','2024-07-03 17:08:24.402159','202453599','5',NULL,NULL,'3024041809330002.jpg',NULL,'20240101','최종','D01','기타 및 상세불명의 소화기관의 제자리암종','주진단','4o1thBwshR'),(16,'2024-07-03 17:08:24.408921','2024-07-03 17:08:24.408921','202453599','5',NULL,NULL,'3024041809330002.jpg',NULL,'20240101','최종','D03','제자리흑색종','부진단','4o1thBwshR');
/*!40000 ALTER TABLE `diag_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-03 17:10:42
