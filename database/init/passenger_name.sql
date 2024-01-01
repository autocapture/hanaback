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
-- Table structure for table `passenger_name`
--

DROP TABLE IF EXISTS `passenger_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passenger_name` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attribute` varchar(50) DEFAULT NULL,
  `name` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passenger_name`
--

LOCK TABLES `passenger_name` WRITE;
/*!40000 ALTER TABLE `passenger_name` DISABLE KEYS */;
INSERT INTO `passenger_name` VALUES (1,'last','KIM'),(2,'last','LEE'),(3,'last','PARK'),(4,'last','LIM'),(5,'last','CHOI'),(6,'last','YOON'),(7,'last','JUNG'),(8,'last','JEONG'),(9,'last','JANG'),(10,'last','KANG'),(11,'last','HAN'),(12,'last','HONG'),(13,'last','OH'),(14,'last','KWON'),(15,'last','CHO'),(16,'last','SEO'),(17,'last','SHIN'),(18,'last','HWANG'),(19,'last','SONG'),(20,'last','JUN'),(21,'last','JEON'),(22,'last','AHN'),(23,'last','KO'),(24,'last','KOH'),(25,'last','BAE'),(26,'last','HEO'),(27,'last','YANG'),(28,'last','YOO'),(29,'last','YU'),(30,'last','BAEK'),(31,'last','NAM'),(32,'last','NO'),(33,'last','NOH'),(34,'last','HA'),(35,'last','KWAK'),(36,'last','SUNG'),(37,'last','SEONG'),(38,'last','MIN'),(39,'last','CHA'),(40,'last','JOO'),(41,'last','WOO'),(42,'last','KOO'),(43,'last','RYU'),(44,'last','JI'),(45,'last','NA'),(46,'last','NAH'),(47,'last','UM'),(48,'last','CHAE'),(49,'last','WON'),(50,'last','CHEON'),(51,'last','CHUN'),(52,'last','BANG'),(53,'last','KONG'),(54,'last','GONG'),(55,'last','YEO'),(56,'last','YEOM'),(57,'last','YUM'),(58,'last','BYUN'),(59,'last','BYEON'),(60,'last','CHOO'),(62,'last','RHEE'),(63,'last','YI'),(64,'last','ROH');
/*!40000 ALTER TABLE `passenger_name` ENABLE KEYS */;
UNLOCK TABLES;
