-- MySQL dump 10.13  Distrib 8.0.31, for macos13.0 (x86_64)
--
-- Host: 172.16.213.92    Database: kakao
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
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doc_keyword`
--

LOCK TABLES `doc_keyword` WRITE;
/*!40000 ALTER TABLE `doc_keyword` DISABLE KEYS */;
INSERT INTO `doc_keyword` VALUES (1,'COMMON','form',1,'confirmation','confirmation'),(2,'DELAY','intent',1,'cancel','delay'),(3,'DELAY','intent',1,'delay','delay'),(4,'COMMON','type',1,'passport','passport'),(5,'COMMON','type',1,'여권','passport'),(6,'MEDICAL','intent',1,'operation','surgery'),(8,'COMMON','form',1,'receipt','receipt'),(9,'DELAY','intent',1,'scheduled','delay'),(10,'DELAY','intent',1,'boarding','boarding'),(11,'DELAY','form',1,'certificate','certificate'),(12,'DELAY','intent',1,'flight','boarding'),(13,'DELAY','intent',1,'airlines','boarding'),(14,'COMMON','type',1,'amex','card_receipt'),(15,'DELAY','intent',1,'cancellation','delay'),(16,'DELAY','type',2,'starbucks','card_receipt'),(17,'DELAY','type',2,'cashier','card_receipt'),(18,'MEDICAL','form',2,'수술기록지','surgery_certificate'),(19,'MEDICAL','intent',1,'수술','surgery'),(20,'MEDICAL','form',1,'기록지','certificate'),(21,'DELAY','type',2,'resort','card_receipt'),(22,'COMMON','intent',1,'출입국','departure'),(23,'COMMON','form',1,'사실증명','certificate'),(24,'MEDICAL','intent',1,'procedures','surgery'),(25,'MEDICAL','intent',1,'수술명','surgery'),(26,'DELAY','type',2,'restaurant','card_receipt'),(27,'MEDICAL','form',1,'record','certificate'),(28,'MEDICAL','intent',1,'진료비','medical'),(29,'MEDICAL','form',1,'세부내역서','detail'),(30,'MEDICAL','type',1,'일수','medical'),(31,'MEDICAL','type',1,'일자','medical'),(38,'DAMAGE','type',2,'수리내역서','repair_detail'),(40,'DAMAGE','intent',1,'baggage','baggage'),(41,'MEDICAL','type',2,'수술확인서','surgery_certificate'),(42,'COMMON','intent',1,'card','card'),(43,'COMMON','type',2,'아멕스','card_receipt'),(44,'DAMAGE','type',2,'apple','repair_detail'),(47,'DAMAGE','intent',1,'수리','repair'),(48,'DAMAGE','intent',1,'부품비','repair'),(49,'COMMON','intent',2,'매출전표','card_receipt'),(50,'DELAY','type',2,'운항정보확인서','delay_confirmation'),(51,'DELAY','intent',1,'지연','delay'),(52,'COMMON','intent',1,'신용카드','card'),(53,'COMMON','type',1,'매출전표','card_receipt'),(54,'COMMON','intent',1,'비씨','card'),(55,'COMMON','intent',1,'일시불','card'),(56,'DELAY','type',2,'받은금액','card_receipt'),(57,'DELAY','type',2,'받을금액','card_receipt'),(58,'COMMON','type',2,'환불','etc'),(59,'COMMON','intent',1,'가맹점','card'),(60,'COMMON','intent',1,'거래일시','card'),(61,'COMMON','intent',1,'거래일시','card'),(62,'COMMON','intent',1,'가맹점명','card');
/*!40000 ALTER TABLE `doc_keyword` ENABLE KEYS */;
UNLOCK TABLES;
