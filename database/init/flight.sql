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
-- Table structure for table `flight`
--

DROP TABLE IF EXISTS `flight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flight` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `arrival` varchar(20) DEFAULT NULL,
  `departure` varchar(20) DEFAULT NULL,
  `flight_cd` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=722 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight`
--

LOCK TABLES `flight` WRITE;
/*!40000 ALTER TABLE `flight` DISABLE KEYS */;
INSERT INTO `flight` VALUES (1,'HND','GMP','JL90'),(2,'KIX','GMP','OZ114'),(3,'KIX','GMP','7C138'),(4,'PEK','GMP','OZ335'),(5,'HND','GMP','OZ108'),(6,'HND','GMP','KE210'),(7,'KIX','GMP','KE211'),(8,'PEK','GMP','KE220'),(9,'TSA','GMP','TW667'),(10,'SHA','GMP','MU512'),(11,'HND','GMP','JL92'),(12,'PKX','GMP','CZ318'),(13,'HND','GMP','NH864'),(14,'PEK','GMP','CA138'),(15,'TSA','GMP','CI261'),(16,'TSA','GMP','BR155'),(17,'KIX','GMP','7C138'),(18,'HND','GMP','OZ104'),(19,'SHA','GMP','OZ361'),(20,'SHA','GMP','KE220'),(21,'HND','GMP','KE210'),(22,'KIX','GMP','KE211'),(23,'HND','GMP','NH866'),(24,'KIX','GMP','OZ116'),(25,'HND','GMP','KE210'),(26,'HND','GMP','JL94'),(27,'SHA','GMP','FM824'),(28,'HND','GMP','OZ106'),(29,'HND','GMP','NH868'),(30,'FUK','PUS','7C145'),(31,'FUK','PUS','BX148'),(32,'CXR','PUS','VJ991'),(33,'SIN','PUS','SQ615'),(34,'NRT','PUS','BX112'),(35,'GUM','PUS','LJ647'),(36,'HAN','PUS','VJ981'),(37,'KIX','PUS','7C135'),(38,'KIX','PUS','BX124'),(39,'DYG','PUS','BX371'),(40,'HKG','PUS','BX397'),(41,'TPE','PUS','KE224'),(42,'DAD','PUS','VJ993'),(43,'UBN','PUS','BX411'),(44,'SGN','PUS','VJ869'),(45,'CTS','PUS','BX182'),(46,'YNJ','PUS','BX337'),(47,'FUK','PUS','LJ271'),(48,'KIX','PUS','TW221'),(49,'NRT','PUS','KE212'),(50,'DAD','PUS','7C295'),(51,'DLI','PUS','VJ987'),(52,'FUK','PUS','BX142'),(53,'SJW','PUS','7C885'),(54,'SGN','PUS','VN423'),(55,'TAO','PUS','BX321'),(56,'NRT','PUS','7C115'),(57,'TPE','PUS','7C265'),(58,'HAN','PUS','VN427'),(59,'DYG','PUS','7C825'),(60,'TPE','PUS','BX793'),(61,'KUL','PUS','D7519'),(62,'KIX','PUS','BX126'),(63,'KHH','PUS','BX795'),(64,'SHE','PUS','CZ666'),(65,'CTS','PUS','LJ237'),(66,'TPE','PUS','CI189'),(67,'PVG','PUS','MU504'),(68,'PEK','PUS','CA130'),(69,'FUK','PUS','7C145'),(70,'UBN','PUS','OM312'),(71,'HKG','PUS','UO675'),(72,'FUK','PUS','BX146'),(73,'CAN','PUS','CZ303'),(74,'FUK','PUS','BX144'),(75,'NRT','PUS','KE213'),(76,'RMQ','PUS','IT863'),(77,'KIX','PUS','BX122'),(78,'KIX','PUS','7C135'),(79,'NRT','PUS','7C117'),(80,'KIX','PUS','BX122'),(81,'SIN','PUS','7C405'),(82,'FUK','PUS','BX144'),(83,'BKK','PUS','BX725'),(84,'PEK','PUS','CA730'),(85,'BKK','PUS','LJ011'),(86,'CNX','PUS','BX723'),(87,'YNJ','PUS','CZ833'),(88,'BKI','PUS','BX761'),(89,'KLO','PUS','Z2059'),(90,'BKK','PUS','7C225'),(91,'KLO','PUS','BX743'),(92,'CXR','PUS','BX751'),(93,'TPE','PUS','IT607'),(94,'MNL','PUS','PR419'),(95,'DAD','PUS','BX773'),(96,'CEB','PUS','LJ037'),(97,'CXR','PUS','LJ071'),(98,'CRK','PUS','BX757'),(99,'CEB','PUS','7C245'),(100,'GUM','PUS','7C315'),(101,'SPN','PUS','7C345'),(102,'UBN','PUS','7C525'),(103,'HKG','PUS','BX391'),(104,'TPE','PUS','7C265'),(105,'HAK','PUS','BX379'),(106,'CRK','PUS','LJ031'),(107,'XIY','PUS','BX341'),(108,'SYX','PUS','BX373'),(109,'MFM','PUS','BX381'),(110,'TPE','PUS','BX797'),(111,'DAD','PUS','LJ075'),(112,'SIN','CJU','TR815'),(113,'HKG','CJU','UO697'),(114,'SIN','CJU','TR813'),(115,'TPE','CJU','IT655'),(116,'PVG','CJU','9C857'),(117,'NKG','CJU','HO161'),(118,'PVG','CJU','9C886'),(119,'TPE','CJU','TW687'),(120,'TSN','CJU','BK271'),(121,'PVG','CJU','MU506'),(122,'SHE','CJU','9C870'),(123,'UBN','CJU','MR608'),(124,'PEK','CJU','KE225'),(125,'KIX','CJU','TW245'),(126,'CSX','CJU','BK272'),(127,'PVG','CJU','MU502'),(128,'NGB','CJU','GJ890'),(129,'PVG','CJU','9C856'),(130,'HRB','CJU','CZ840'),(131,'PVG','CJU','LJ101'),(132,'SHE','CJU','CZ694'),(133,'PVG','CJU','HO137'),(134,'NGB','CJU','9C862'),(135,'PEK','CJU','CA712'),(136,'HGH','CJU','GJ894'),(137,'XIY','CJU','LJ171'),(138,'PEK','CJU','7C813'),(139,'DLC','CJU','CZ609'),(140,'CGQ','CJU','CZ831'),(141,'DAD','TAE','VJ871'),(142,'KIX','TAE','TW287'),(143,'NRT','TAE','TW201'),(144,'DYG','TAE','TW681'),(145,'PVG','TAE','MU505'),(146,'CTS','TAE','TW926'),(147,'FUK','TAE','TW233'),(148,'DYG','TAE','3U370'),(149,'CXR','TAE','TW915'),(150,'TPE','TAE','IT611'),(151,'CEB','TAE','7C272'),(152,'DAD','TAE','TW129'),(153,'BKK','TAE','TW105'),(154,'TPE','TAE','TW663'),(155,'TPE','TAE','LJ083'),(156,'KIX','CJJ','RF312'),(157,'KIX','CJJ','TW249'),(158,'KIX','CJJ','RF318'),(159,'DYG','CJJ','3U370'),(160,'DMK','CJJ','TW183'),(161,'DAD','CJJ','TW181'),(162,'CXR','CJJ','TW155'),(163,'DLI','MWX','BL663'),(164,'CXR','MWX','BL662'),(165,'DAD','MWX','VJ855'),(166,'KKJ','MWX','4H230'),(167,'PVG','MWX','MU505'),(168,'DYG','MWX','3U371'),(169,'NRT','YNY','4V313'),(170,'TPE','YNY','4V203'),(171,'SGN','YNY','4V253'),(172,'HAN','YNY','4V251'),(173,'DXB','ICN','EK323'),(174,'MNL','ICN','Z2889'),(175,'ADD','ICN','ET673'),(176,'MNL','ICN','5J187'),(177,'DOH','ICN','QR859'),(178,'PQC','ICN','VJ975'),(179,'CXR','ICN','VJ839'),(180,'PQC','ICN','VJ979'),(181,'TPE','ICN','IT601'),(182,'MNL','ICN','PR546'),(183,'DLI','ICN','VJ945'),(184,'HKG','ICN','KD396'),(185,'NRT','ICN','7C110'),(186,'ANC','ICN','KD395'),(187,'DAD','ICN','VJ881'),(188,'CXR','ICN','VJ837'),(189,'CXR','ICN','VN441'),(190,'FUK','ICN','7C140'),(191,'HAN','ICN','VJ963'),(192,'SGN','ICN','VJ865'),(193,'DAD','ICN','VJ879'),(194,'KIX','ICN','7C130'),(195,'HPH','ICN','VJ925'),(196,'FUK','ICN','LJ221'),(197,'KIX','ICN','RS711'),(198,'CTS','ICN','7C190'),(199,'TAG','ICN','7C440'),(200,'NRT','ICN','LJ201'),(201,'KUL','ICN','D7505'),(202,'FSZ','ICN','7C126'),(203,'KIX','ICN','MM12'),(204,'CXR','ICN','LJ055'),(205,'SGN','ICN','OZ731'),(206,'MNL','ICN','OZ701'),(207,'DAD','ICN','TW125'),(208,'KIX','ICN','LJ211'),(209,'NRT','ICN','TW211'),(210,'FUK','ICN','BX156'),(211,'MNL','ICN','KE621'),(212,'TPE','ICN','LJ081'),(213,'NRT','ICN','BX164'),(214,'NRT','ICN','LJ207'),(215,'MNL','ICN','5J185'),(216,'BKI','ICN','LJ065'),(217,'TAO','ICN','KE845'),(218,'KIX','ICN','TW281'),(219,'OKA','ICN','KE755'),(220,'HAN','ICN','KE441'),(221,'FUK','ICN','KE787'),(222,'KIX','ICN','OZ112'),(223,'SHE','ICN','KE831'),(224,'HKG','ICN','KE171'),(225,'UBN','ICN','KE197'),(226,'KLO','ICN','TW139'),(227,'MNL','ICN','PR467'),(228,'BUD','ICN','LO200'),(229,'NGO','ICN','OZ122'),(230,'PEK','ICN','KE859'),(231,'FUK','ICN','RS727'),(232,'PEK','ICN','OZ331'),(233,'TAO','ICN','7C840'),(234,'CTS','ICN','LJ231'),(235,'KLO','ICN','PR489'),(236,'NRT','ICN','7C110'),(237,'PVG','ICN','KE893'),(238,'CEB','ICN','PR485'),(239,'GUM','ICN','TW303'),(240,'TPE','ICN','LJ087'),(241,'GUM','ICN','LJ643'),(242,'FUK','ICN','7C147'),(243,'CAN','ICN','OZ369'),(244,'SZX','ICN','KE827'),(245,'FUK','ICN','OZ132'),(246,'TAK','ICN','RS741'),(247,'CAN','ICN','KE865'),(248,'CEB','ICN','TW173'),(249,'KMJ','ICN','TW275'),(250,'NRT','ICN','YP731'),(251,'SIN','ICN','SQ607'),(252,'NRT','ICN','OZ102'),(253,'PVG','ICN','OZ361'),(254,'DLC','ICN','OZ301'),(255,'SGN','ICN','KE469'),(256,'CDG','ICN','AF267'),(257,'YNJ','ICN','OZ351'),(258,'TPE','ICN','7C260'),(259,'TSN','ICN','KE805'),(260,'KIX','ICN','7C130'),(261,'XIY','ICN','KE811'),(262,'GUM','ICN','LJ641'),(263,'DFW','ICN','KE031'),(264,'ATL','ICN','KE035'),(265,'KLO','ICN','TW913'),(266,'VTE','ICN','QV924'),(267,'UBN','ICN','OZ567'),(268,'BKK','ICN','KE657'),(269,'SPN','ICN','7C340'),(270,'KIX','ICN','BX172'),(271,'KIX','ICN','KE723'),(272,'YNJ','ICN','KE117'),(273,'YYZ','ICN','KE073'),(274,'FUK','ICN','7C140'),(275,'BKK','ICN','TG659'),(276,'FRA','ICN','OZ541'),(277,'NRT','ICN','RS701'),(278,'OKA','ICN','OZ172'),(279,'NRT','ICN','LJ205'),(280,'GUM','ICN','KE421'),(281,'JFK','ICN','OZ222'),(282,'TPE','ICN','OZ711'),(283,'SZX','ICN','OZ371'),(284,'JFK','ICN','KE081'),(285,'KIX','ICN','7C137'),(286,'FCO','ICN','OZ561'),(287,'HAN','ICN','VN417'),(288,'CTS','ICN','KE765'),(289,'FUK','ICN','TW291'),(290,'TAS','ICN','HY512'),(291,'HKG','ICN','CX417'),(292,'KIX','ICN','LJ281'),(293,'NRT','ICN','KE703'),(294,'WEH','ICN','7C850'),(295,'CTS','ICN','TW251'),(296,'SGN','ICN','VN409'),(297,'BKK','ICN','TG657'),(298,'NKG','ICN','KE101'),(299,'IAD','ICN','KE093'),(300,'HAN','ICN','OZ729'),(301,'TPE','ICN','KE185'),(302,'NRT','ICN','7C110'),(303,'DTW','ICN','DL158'),(304,'GUM','ICN','7C310'),(305,'DAD','ICN','7C290'),(306,'YNT','ICN','7C870'),(307,'NGO','ICN','KE741'),(308,'PEK','ICN','KE855'),(309,'CDG','ICN','OZ501'),(310,'OKA','ICN','LJ245'),(311,'SIN','ICN','KE643'),(312,'LHR','ICN','KE907'),(313,'NRT','ICN','TW213'),(314,'KUL','ICN','MH067'),(315,'PRG','ICN','KE969'),(316,'DAD','ICN','KE457'),(317,'HAN','ICN','VJ961'),(318,'PVG','ICN','KE897'),(319,'NGO','ICN','7C160'),(320,'KIX','ICN','7C137'),(321,'DAD','ICN','VN431'),(322,'OKA','ICN','TW271'),(323,'DLC','ICN','CZ686'),(324,'DPS','ICN','GA871'),(325,'ALA','ICN','KC910'),(326,'VIE','ICN','KE937'),(327,'CDG','ICN','KE901'),(328,'FUK','ICN','BX158'),(329,'SFO','ICN','UA806'),(330,'IST','ICN','TK21'),(331,'HAN','ICN','QH451'),(332,'SGN','ICN','VJ863'),(333,'BKK','ICN','XJ701'),(334,'MUC','ICN','LH719'),(335,'TSN','ICN','GS799'),(336,'TNA','ICN','SC409'),(337,'DEL','ICN','OZ767'),(338,'BCN','ICN','KE915'),(339,'FUK','ICN','LJ223'),(340,'TPE','ICN','BR169'),(341,'SIN','ICN','TR843'),(342,'HGH','ICN','KE107'),(343,'PNH','ICN','ZA216'),(344,'KIX','ICN','TW283'),(345,'HRB','ICN','OZ339'),(346,'NKG','ICN','MU580'),(347,'LHR','ICN','OZ521'),(348,'NKG','ICN','OZ349'),(349,'CTS','ICN','7C190'),(350,'FRA','ICN','LH713'),(351,'KHH','ICN','CI165'),(352,'FUK','ICN','OZ134'),(353,'SHE','ICN','CZ682'),(354,'TPE','ICN','CI161'),(355,'BUD','ICN','KE961'),(356,'HKG','ICN','UO619'),(357,'PVG','ICN','MU504'),(358,'NRT','ICN','ZG42'),(359,'MNL','ICN','Z2885'),(360,'DEL','ICN','KE497'),(361,'DLC','ICN','KE869'),(362,'PEK','ICN','CA124'),(363,'KIX','ICN','RS713'),(364,'KIX','ICN','BX174'),(365,'NRT','ICN','RS705'),(366,'HGH','ICN','CA140'),(367,'CGQ','ICN','CZ688'),(368,'FCO','ICN','KE931'),(369,'KIX','ICN','7C137'),(370,'OKA','ICN','7C180'),(371,'DXB','ICN','KE951'),(372,'LAX','ICN','YP101'),(373,'IST','ICN','KE955'),(374,'KHH','ICN','TW671'),(375,'XMN','ICN','MF872'),(376,'KIX','ICN','LJ213'),(377,'KTM','ICN','KE695'),(378,'HKG','ICN','HB521'),(379,'NRT','ICN','KL861'),(380,'PVG','ICN','OZ365'),(381,'FUK','ICN','KE789'),(382,'KIX','ICN','OZ114'),(383,'WEH','ICN','MU201'),(384,'MFM','ICN','NX821'),(385,'TAO','ICN','MU502'),(386,'YNJ','ICN','CA144'),(387,'TPE','ICN','OZ713'),(388,'UBN','ICN','OM302'),(389,'FUK','ICN','RS723'),(390,'LAX','ICN','KE017'),(391,'YNJ','ICN','CZ607'),(392,'TLV','ICN','KE957'),(393,'LAX','ICN','OZ202'),(394,'CKG','ICN','CA440'),(395,'TFU','ICN','CA402'),(396,'FUK','ICN','TW293'),(397,'NRT','ICN','LJ203'),(398,'SZX','ICN','ZH903'),(399,'PEK','ICN','OZ335'),(400,'NRT','ICN','TW215'),(401,'NRT','ICN','7C110'),(402,'CGK','ICN','KE627'),(403,'KIX','ICN','MM6'),(404,'HKG','ICN','CX411'),(405,'NRT','ICN','LJ209'),(406,'NRT','ICN','ZG44'),(407,'MYJ','ICN','7C170'),(408,'KIX','ICN','BX176'),(409,'KIX','ICN','KE725'),(410,'CGK','ICN','OZ761'),(411,'PEK','ICN','CA132'),(412,'TAO','ICN','SC408'),(413,'FUK','ICN','7C140'),(414,'KIX','ICN','TW285'),(415,'YNJ','ICN','7C890'),(416,'NRT','ICN','BX166'),(417,'NRT','ICN','OZ106'),(418,'YNT','ICN','MU268'),(419,'FUK','ICN','LJ227'),(420,'SFO','ICN','KE023'),(421,'LAX','ICN','PR112'),(422,'FUK','ICN','7C140'),(423,'NRT','ICN','7C110'),(424,'TPE','ICN','KE187'),(425,'KIX','ICN','LJ283'),(426,'SIN','ICN','OZ751'),(427,'HKG','ICN','UO631'),(428,'TAS','ICN','OZ573'),(429,'KUL','ICN','KE671'),(430,'TAO','ICN','MU560'),(431,'SZX','ICN','ZH903'),(432,'SEA','ICN','KE041'),(433,'CAN','ICN','CZ306'),(434,'SIN','ICN','SQ601'),(435,'HKT','ICN','LJ015'),(436,'SFO','ICN','UA892'),(437,'KIX','ICN','7C130'),(438,'NRT','ICN','RS703'),(439,'FUK','ICN','LJ225'),(440,'BKK','ICN','BX747'),(441,'BKI','ICN','LJ061'),(442,'DAD','ICN','LJ079'),(443,'AKL','ICN','KE411'),(444,'BKK','ICN','TG653'),(445,'DFW','ICN','AA280'),(446,'DPS','ICN','KE629'),(447,'PEK','ICN','CA126'),(448,'HRB','ICN','CZ684'),(449,'AUH','ICN','EY857'),(450,'PVG','ICN','CZ314'),(451,'SGN','ICN','VN405'),(452,'CNX','ICN','7C420'),(453,'SGN','ICN','TW121'),(454,'NGO','ICN','7C160'),(455,'YVR','ICN','AC064'),(456,'HAN','ICN','VN415'),(457,'HKT','ICN','KE663'),(458,'BKK','ICN','KE651'),(459,'SEA','ICN','OZ272'),(460,'DMK','ICN','TW103'),(461,'NRT','ICN','KE705'),(462,'CNX','ICN','KE667'),(463,'FUK','ICN','OZ136'),(464,'FUK','ICN','TW295'),(465,'NRT','ICN','OZ108'),(466,'DAD','ICN','KE461'),(467,'FUK','ICN','KE781'),(468,'ATL','ICN','DL26'),(469,'SIN','ICN','KE645'),(470,'PNH','ICN','KE689'),(471,'YVR','ICN','KE071'),(472,'MNL','ICN','7C230'),(473,'DAD','ICN','OZ755'),(474,'MNL','ICN','KE623'),(475,'SYD','ICN','KE401'),(476,'KIX','ICN','KE721'),(477,'NGO','ICN','KE743'),(478,'MNL','ICN','OZ703'),(479,'YYZ','ICN','AC062'),(480,'HAN','ICN','KE455'),(481,'BKI','ICN','7C250'),(482,'SIN','ICN','TW171'),(483,'SGN','ICN','KE475'),(484,'KLO','ICN','RS531'),(485,'SEA','ICN','DL196'),(486,'KIX','ICN','OZ116'),(487,'SGN','ICN','OZ735'),(488,'PNH','ICN','OZ739'),(489,'HAN','ICN','OZ733'),(490,'BKK','ICN','OZ741'),(491,'KLO','ICN','TW189'),(492,'GUM','ICN','KE423'),(493,'MSP','ICN','DL170'),(494,'LAX','ICN','KE011'),(495,'JFK','ICN','KE085'),(496,'VTE','ICN','7C430'),(497,'BKK','ICN','KE659'),(498,'TPE','ICN','BR159'),(499,'HKG','ICN','KE177'),(500,'BKK','ICN','TW101'),(501,'CXR','ICN','RS527'),(502,'HKG','ICN','OZ745'),(503,'BKK','ICN','LJ001'),(504,'TFU','ICN','OZ323'),(505,'SYD','ICN','OZ601'),(506,'BKK','ICN','7C220'),(507,'BNE','ICN','KE407'),(508,'CEB','ICN','KE615'),(509,'SGN','ICN','KE477'),(510,'DAD','ICN','BX777'),(511,'HKG','ICN','CX419'),(512,'DAD','ICN','TW191'),(513,'HNL','ICN','OZ232'),(514,'CEB','ICN','TW917'),(515,'GUM','ICN','7C310'),(516,'CEB','ICN','LJ025'),(517,'DAD','ICN','RS511'),(518,'SPN','ICN','TW307'),(519,'CXR','ICN','KE467'),(520,'HND','ICN','KE719'),(521,'MNL','ICN','PR469'),(522,'CXR','ICN','LJ053'),(523,'LAS','ICN','KE005'),(524,'BKK','ICN','7C220'),(525,'LAX','ICN','OZ204'),(526,'TPE','ICN','CI163'),(527,'DAD','ICN','LJ059'),(528,'CEB','ICN','7C240'),(529,'SGN','ICN','VJ861'),(530,'SFO','ICN','OZ212'),(531,'KIX','ICN','MM10'),(532,'BKI','ICN','7C257'),(533,'SGN','ICN','7C470'),(534,'HNL','ICN','KE053'),(535,'HND','ICN','OZ178'),(536,'HAN','ICN','7C280'),(537,'CSX','ICN','OZ321'),(538,'HNL','ICN','HA460'),(539,'CRK','ICN','PR493'),(540,'DAD','ICN','7C290'),(541,'CRK','ICN','LJ023'),(542,'CRK','ICN','7C460'),(543,'DAD','ICN','TW127'),(544,'CEB','ICN','5J129'),(545,'HEL','ICN','AY42'),(546,'MFM','ICN','NX825'),(547,'MFM','ICN','LJ121'),(548,'SPN','ICN','7C340'),(549,'CXR','ICN','7C490'),(550,'YNT','ICN','SC470'),(551,'SYD','ICN','TW501'),(552,'HKG','ICN','UO627'),(553,'HND','ICN','MM808'),(554,'DAD','ICN','VJ875'),(555,'AMS','ICN','KL862'),(556,'SIN','ICN','TR897'),(557,'TAO','ICN','SC477'),(558,'SIN','ICN','KE647'),(559,'IST','ICN','TK91'),(560,'SIN','ICN','SQ605'),(561,'JED','ICN','SV899'),(562,'AKL','ICN','NZ76'),(563,'CMB','ICN','UL471'),(564,'KUL','ICN','D7509'),(565,'RGN','ICN','UB830'),(566,'HKG','ICN','AC232'),(567,'CTS','ICN','7C197'),(568,'CGQ','ICN','OZ303'),(569,'BOS','ICN','KE091'),(570,'SDJ','ICN','OZ152'),(571,'XIY','ICN','OZ347'),(572,'MAD','ICN','KE913'),(573,'XMN','ICN','KE125'),(574,'ORD','ICN','KE037'),(575,'FRA','ICN','KE945'),(576,'ZRH','ICN','KE917'),(577,'TOY','ICN','OZ128'),(578,'WAW','ICN','LO98'),(579,'AMS','ICN','KE925'),(580,'DEL','ICN','AI313'),(581,'BWN','ICN','BI652'),(582,'ANC','ICN','N8507'),(583,'NRT','ICN','7C111'),(584,'HKG','ICN','HX629'),(585,'BCN','ICN','OZ511'),(586,'KIX','ICN','KL867'),(587,'TFU','ICN','3U397'),(588,'TAS','ICN','KE991'),(589,'RGN','ICN','8M802'),(590,'PVG','ICN','9C856'),(591,'ALA','ICN','OZ577'),(592,'SGN','ICN','YP651'),(593,'TAO','ICN','7C840'),(594,'KHH','ICN','BR171'),(595,'DAC','ICN','LJ701'),(596,'SYD','ICN','QF88'),(597,'RUH','ICN','SV897'),(598,'AMS','ICN','KL868'),(599,'TAO','ICN','QW990'),(600,'BKK','ICN','XJ703'),(601,'UBN','ICN','OM308'),(602,'VCA','ICN','VJ955'),(603,'TNA','ICN','TW605'),(604,'HRB','ICN','7C890'),(605,'TAO','ICN','OZ317'),(606,'CTS','ICN','OZ174'),(607,'IST','ICN','OZ551'),(608,'CGK','ICN','GA879'),(609,'YNT','ICN','MU550'),(610,'HGH','ICN','OZ359'),(611,'TSN','ICN','CA172'),(612,'PEK','ICN','OZ333'),(613,'FUK','ICN','RS729'),(614,'MXP','ICN','KE927'),(615,'YVR','ICN','AC232'),(616,'FSZ','ICN','7C128'),(617,'UBN','ICN','OM304'),(618,'RGN','ICN','KE489'),(619,'TPE','ICN','KE290'),(620,'BKK','ICN','YP562'),(621,'NRT','ICN','ET672'),(622,'SIN','ICN','OZ753'),(623,'SGN','ICN','TW123'),(624,'CEB','ICN','Z2904'),(625,'CXR','ICN','BX787'),(626,'SPN','ICN','OZ625'),(627,'CEB','ICN','OZ709'),(628,'SYD','ICN','JQ48'),(629,'TAS','ICN','HY514'),(630,'HKG','ICN','CX439'),(631,'TPE','ICN','KL845'),(632,'DPS','ICN','KE633'),(633,'AMS','ICN','KL846'),(634,'ATH','ICN','KE993'),(635,'DLC','ICN','KE869'),(636,'NRT','ICN','OZ102'),(637,'NGO','ICN','OZ122'),(638,'FUK','ICN','OZ132'),(639,'CRK','ICN','5J177'),(640,'KUL','ICN','D7507'),(641,'UBN','ICN','OM730'),(642,'TPE','ICN','OZ901'),(643,'JMU','ICN','7C890'),(644,'WAW','ICN','LO109'),(645,'SIN','ICN','SQ611'),(646,'KKJ','ICN','LJ261'),(647,'KIX','ICN','LJ285'),(648,'OKO','ICN','KIWI0'),(649,'PVG','ICN','MU503'),(650,'PKX','ICN','MU207'),(651,'BKK','ICN','XJ970'),(652,'DLC','ICN','UAF12'),(653,'CGO','ICN','M7391'),(654,'ANC','ICN','M7392'),(655,'JFK','ICN','OZ224'),(656,'PVG','ICN','UAF12'),(657,'OSL','ICN','YP521'),(658,'CGO','ICN','M7393'),(659,'ANC','ICN','M7394'),(660,'NGO','ICN','7C169'),(661,'TAG','ICN','7C446'),(662,'DLC','ICN','UAF12'),(663,'CGO','ICN','M7395'),(664,'GUM','ICN','KIWI0'),(665,'HKG','ICN','KD394'),(666,'ANC','ICN','M7396'),(667,'ANC','ICN','KD394'),(668,'KIX','ICN','LJ921'),(669,'HKG','ICN','KD395'),(670,'TAO','ICN','MU203'),(671,'CEB','ICN','LJ029'),(672,'DLC','ICN','UAF12'),(673,'TOY','ICN','TW923'),(674,'MNL','ICN','KE292'),(675,'MDC','ICN','7C277'),(676,'BTH','ICN','7C277'),(677,'HKG','ICN','KD393'),(678,'DLC','ICN','UAF12'),(679,'ANC','ICN','N8506'),(680,'ANC','ICN','N8508'),(681,'CAN','ICN','DL997'),(682,'HND','ICN','KE290'),(683,'ANC','ICN','N8510'),(684,'TAO','ICN','MU204'),(685,'EWR','ICN','YP131'),(686,'GUM','ICN','7C310'),(687,'AFI','ICN','TW105'),(688,'DLC','ICN','UAF12'),(689,'NRT','ICN','LJ905'),(690,'SPN','ICN','7C349'),(691,'SPN','ICN','TW930'),(692,'GUM','ICN','TW930'),(693,'GUM','ICN','7C319'),(694,'PVG','ICN','4V121'),(695,'GUM','ICN','KE423'),(696,'GUM','ICN','LJ947'),(697,'GUM','ICN','LJ941'),(698,'SGN','ICN','YP365'),(699,'SPN','ICN','OZ623'),(700,'BKK','ICN','YP601'),(701,'ROR','ICN','7C330'),(702,'HKG','ICN','KD393'),(703,'CGK','ICN','TW101'),(704,'DLC','ICN','UAF12'),(705,'GUM','ICN','LJ941'),(706,'GUM','ICN','TW930'),(707,'GUM','ICN','KE842'),(708,'ANC','ICN','N8512'),(709,'GUM','ICN','KE842'),(710,'GUM','ICN','LJ943'),(711,'HEL','ICN','AY42A'),(712,'ANC','ICN','7L334'),(713,'GUM','ICN','TW930'),(714,'CBR','ICN','ASY33'),(715,'CJJ','ICN','TW300'),(716,'NRT','ICN','KD587'),(717,'UBN','ICN','7C520'),(718,'MDG','ICN','CZ608'),(719,'SHE','ICN','CZ672'),(720,'CRK','ICN','OZ707'),(721,'DAD','ICN','TW912');
/*!40000 ALTER TABLE `flight` ENABLE KEYS */;
UNLOCK TABLES;
