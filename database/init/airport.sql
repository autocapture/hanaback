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
-- Table structure for table `airport`
--

DROP TABLE IF EXISTS `airport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `airport` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `airport_cd` varchar(5) DEFAULT NULL,
  `airport_nm_eng` varchar(50) DEFAULT NULL,
  `airport_nm_kor` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airport`
--

LOCK TABLES `airport` WRITE;
/*!40000 ALTER TABLE `airport` DISABLE KEYS */;
INSERT INTO `airport` VALUES (1,'ADD','Addis Ababa','아디스아바바'),(2,'AFI','Amalfi','공역'),(3,'AKL','Auckland','오클랜드'),(4,'ALA','Almaty','알마티'),(5,'AMS','Amsterdam','암스테르담'),(6,'ANC','Anchorage','앵커리지'),(7,'ATH','Athens','아테네'),(8,'ATL','Atlanta','애틀랜타'),(9,'AUH','Abu Dhabi','아부다비 국제공항'),(10,'BCN','Barcelona','바르셀로나'),(11,'BKI','Kota Kinabalu','코타키나발루'),(12,'BKK','Bangkok','수안나폼(방콕)'),(13,'BNE','Brisbane','브리즈번'),(14,'BOS','Boston','보스턴'),(15,'BTH','Batam','바탐'),(16,'BUD','Budapest','부다페스트'),(17,'BWN','BandarSeriBegawan','반다르스리'),(18,'CAN','Guangzhou','광저우'),(19,'CBR','Canberra','캔버라'),(20,'CDG','Paris','파리'),(21,'CEB','Cebu','세부'),(22,'CGK','Jakarta','자카르타(수카르노 하타) 국제 공항'),(23,'CGO','Zhengzhou','정저우'),(24,'CGQ','Changchun','장춘'),(25,'CJJ','Cheongju','청주'),(26,'CJU','Jeju','제주'),(27,'CKG','Chongqing','충칭'),(28,'CMB','Colombo','콜롬보'),(29,'CNX','ChiangMai','치앙마이'),(30,'CRK','Luzon','클라크 국제공항'),(31,'CSX','Changsa','창사(장사)'),(32,'CTS','Sapporo','삿포로'),(33,'CXR','NhaTrang','깜라인(나트랑)'),(34,'DAC','Dhaka','다카'),(35,'DAD','DaNang','다낭'),(36,'DEL','Delhi','델리'),(37,'DFW','Dallas','댈러스'),(38,'DLC','Dalian','대련'),(39,'DLI','Dalat','달랏'),(40,'DMK','Bangkok','돈므앙(방콕)'),(41,'DOH','Doha','도하'),(42,'DPS','Denpasar','덴파사(응우라 라이)'),(43,'DTW','Detroit','디트로이트'),(44,'DXB','Dubai','두바이'),(45,'DYG','Zhangjiajie','대용/장가계'),(46,'EWR','NewYork','뉴어크'),(47,'FCO','Rome','로마'),(48,'FRA','Frankfurt','프랑크푸르트'),(49,'FSZ','Shizuoka','시즈오카'),(50,'FUK','Fukuoka','후쿠오카'),(51,'GUM','Guam','괌'),(52,'HAK','Haikou','하이코우/하이커우'),(53,'HAN','Hanoi','노이바이(하노이)'),(54,'HEL','Helsinki','헬싱키'),(55,'HGH','Hangzhou','항저우'),(56,'HKG','HongKong','홍콩'),(57,'HKT','Phuket','푸껫'),(58,'HND','Tokyo','하네다'),(59,'HNL','Honolulu','호놀룰루'),(60,'HPH','Haiphong','카트비'),(61,'HRB','Harbin','하얼빈'),(62,'IAD','WashingtonDC','워싱톤'),(63,'IST','Istanbul','이스탄불'),(64,'JED','Jeddah','제다'),(65,'JFK','NewYork','뉴욕'),(66,'JMU','Jiamusi','가목사'),(67,'KHH','Kaohsiung','가오슝'),(68,'KIX','Osaka','간사이'),(69,'KKJ','KitaKyushu','기타규슈'),(70,'KLO','Kalibo','칼리보'),(71,'KMJ','Kumamoto','구마모토'),(72,'KTM','Kathmandu','카트만두'),(73,'KUL','KualaLumpur','쿠알라룸푸르'),(74,'LAS','LasVegas','라스베이거스'),(75,'LAX','LosAngeles','로스앤젤레스'),(76,'LHR','London','런던히드로'),(77,'MAD','Madrid','마드리드'),(78,'MDC','Manado','마나도'),(79,'MDG','Mudanjiang','목단강'),(80,'MFM','Macau','마카오'),(81,'MNL','Manila','마닐라'),(82,'MSP','Minneapolis','미니애폴리스'),(83,'MUC','Munich','뮌헨'),(84,'MXP','Milan','밀라노'),(85,'MYJ','Matsuyama','마쓰야마'),(86,'NGB','Ningbo','닝보'),(87,'NGO','Nagoya','나고야'),(88,'NKG','Nanjing','남경(난징)'),(89,'NRT','Tokyo','도쿄/나리타'),(90,'OKA','Okinawa','오키나와'),(91,'OKO','Yokota','요코다'),(92,'ORD','Chicago','시카고'),(93,'OSL','Oslo','오슬로'),(94,'PEK','Beijing','베이징'),(95,'PKX','Beijing','베이징 다싱 국제공항'),(96,'PNH','PhnomPenh','프놈펜'),(97,'PQC','Phu Quoc','푸꾸옥'),(98,'PRG','Prague','프라하'),(99,'PUS','Busan','부산'),(100,'PVG','Shanghai','상하이/푸동'),(101,'RGN','Yangon','양곤'),(102,'RMQ','Taichung','타이쭝/타이중/칭촨강'),(103,'ROR','Koror','코로르'),(104,'RUH','Riyadh','리야드'),(105,'SDJ','Sendai','센다이'),(106,'SEA','Seattle','시애틀'),(107,'SFO','SanFrancisco','샌프란시스코'),(108,'SGN','HoChiMinhCity','떤 선?(호찌민)'),(109,'SHA','Shanghai','상하이/홍차오'),(110,'SHE','Shenyang','심양'),(111,'SIN','Singapore','싱가포르'),(112,'SJW','Shijiazhuang','쉬지아쭈앙(석가장'),(113,'SPN','Saipan','사이판'),(114,'SYD','Sydney','시드니'),(115,'SYX','Sanya','산야/싼야'),(116,'SZX','Shenzhen','선전(심천)'),(117,'TAG','Panglao','팡라로'),(118,'TAK','Takamatsu','다카마쓰'),(119,'TAO','Qingdao','청도'),(120,'TAS','Tashkent','타슈켄트'),(121,'TFU','Chengdu','청두 티안푸국제공항'),(122,'TLV','TelAviv','텔아비브'),(123,'TNA','Jinan','지난'),(124,'TOY','Toyama','도야마'),(125,'TPE','Taipei','타이완 타오위안 국제 공항'),(126,'TSA','Taipei','타이페이/쑹산'),(127,'TSN','Tianjin','천진(톈진)'),(128,'UBN','Ulaanbaatar','칭기즈 칸(신 울란바타르) 국제공항'),(129,'VCA','Can Tho','껀터 국제공항'),(130,'VIE','Vienna','비엔나'),(131,'VTE','Vientiane','비엔티안'),(132,'WAW','Warsaw','바르샤바'),(133,'WEH','Weihai','위해'),(134,'XIY','Xi\'an','시안'),(135,'XMN','Xiamen','샤먼 가오치 국제공항'),(136,'YNJ','Yanji','연길'),(137,'YNT','Yantai','연대'),(138,'YVR','Vancouver','밴쿠버'),(139,'YYZ','Toronto','토론토'),(140,'ZRH','Zurich','취리히');
/*!40000 ALTER TABLE `airport` ENABLE KEYS */;
UNLOCK TABLES;
