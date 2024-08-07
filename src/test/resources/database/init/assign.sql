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
-- Table structure for table `assign`
--

DROP TABLE IF EXISTS `assign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assign` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `accept_message` varchar(255) DEFAULT NULL,
  `accept_status` varchar(255) DEFAULT NULL,
  `accept_time` datetime(6) DEFAULT NULL,
  `accident_type` varchar(255) DEFAULT NULL,
  `accr_no` varchar(16) DEFAULT NULL,
  `acd_caus_lctg_cd` varchar(255) DEFAULT NULL,
  `acd_dt` varchar(255) DEFAULT NULL,
  `auto_return` tinyint(1) DEFAULT 0,
  `clm_tp_cd` varchar(255) DEFAULT NULL,
  `dm_seqno` varchar(5) DEFAULT NULL,
  `download_time` datetime(6) DEFAULT NULL,
  `endpoint` varchar(50) DEFAULT NULL,
  `gd_flg_list` longtext DEFAULT NULL,
  `img_cnt` int(11) DEFAULT NULL,
  `nrd_birth` varchar(50) DEFAULT NULL,
  `nrd_nm` varchar(50) DEFAULT NULL,
  `process_response_code` varchar(255) DEFAULT NULL,
  `process_response_code_detail` varchar(255) DEFAULT NULL,
  `qa_assign_time` datetime(6) DEFAULT NULL,
  `qa_owner` varchar(12) DEFAULT NULL,
  `request_json` longtext DEFAULT NULL,
  `response_json` longtext DEFAULT NULL,
  `result_accept_code` varchar(255) DEFAULT NULL,
  `result_accept_time` datetime(6) DEFAULT NULL,
  `result_delivery_time` datetime(6) DEFAULT NULL,
  `rqs_req_id` varchar(255) DEFAULT NULL,
  `rqst_time` datetime(6) DEFAULT NULL,
  `step` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assign`
--

LOCK TABLES `assign` WRITE;
/*!40000 ALTER TABLE `assign` DISABLE KEYS */;
INSERT INTO `assign` VALUES (148,'2024-06-25 17:37:51.992200','2024-06-25 17:38:06.106147',NULL,'OK','2024-06-25 17:37:51.988687',NULL,'202453603','AC102','20240405',NULL,'CT1','5','2024-06-25 17:37:54.472650',NULL,NULL,5,'A2A292CB691FF67F659F8C9DEF3EEFA0','C0CE6C4AD1B67BD9D4E6F8AE8021FE9D','SUCCESS',NULL,'2024-06-25 17:38:06.105973','AIP','{\"RQS_REQ_ID\":\"4o1gFhBBev\",\"ACD_NO\":\"202453603\",\"RCT_SEQ\":\"5\",\"ACD_DT\":\"20240405\",\"CLM_TP_CD\":\"1\",\"ACD_CAUS_LCTG_CD\":\"102\",\"RQS_TP_CD\":\"T00\",\"DMPE_NM\":\"C0CE6C4AD1B67BD9D4E6F8AE8021FE9D\",\"INSPE_BDT\":\"A2A292CB691FF67F659F8C9DEF3EEFA0\",\"REQ_DTM\":\"20240625173748\",\"IMG_CNT\":5,\"IMG_LST\":[{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024061716405601\",\"IMG_FILE_NM\":\"3024061716405601.tif\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024061716405500\",\"IMG_FILE_NM\":\"3024061716405500.tif\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024061716405602\",\"IMG_FILE_NM\":\"3024061716405602.tif\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024061716405600\",\"IMG_FILE_NM\":\"3024061716405600.tif\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024061716405603\",\"IMG_FILE_NM\":\"3024061716405603.tif\"}],\"ADD_OFR_INF\":[{\"INF_KEY\":\"\",\"INF_VAL\":\"\"}]}',NULL,NULL,NULL,'2024-06-25 17:38:06.095705','4o1gFhBBev','2024-06-25 17:37:48.000000','ASSIGN'),(149,'2024-06-25 17:38:20.456609','2024-06-25 17:38:49.201459',NULL,'OK','2024-06-25 17:38:20.455341',NULL,'202453605','AC102','20240305',NULL,'CT1','1','2024-06-25 17:38:38.905962',NULL,NULL,5,'D64877675D8F18B20FCC0813D596CFAE','1F52EB54F5145DD2DB26BFAA8290F069','SUCCESS',NULL,'2024-06-25 17:38:49.201310','박희정','{\"RQS_REQ_ID\":\"4o1gFilZIM\",\"ACD_NO\":\"202453605\",\"RCT_SEQ\":\"1\",\"ACD_DT\":\"20240305\",\"CLM_TP_CD\":\"1\",\"ACD_CAUS_LCTG_CD\":\"102\",\"RQS_TP_CD\":\"T00\",\"DMPE_NM\":\"1F52EB54F5145DD2DB26BFAA8290F069\",\"INSPE_BDT\":\"D64877675D8F18B20FCC0813D596CFAE\",\"REQ_DTM\":\"20240625173812\",\"IMG_CNT\":5,\"IMG_LST\":[{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024050817034901\",\"IMG_FILE_NM\":\"3024050817034901.jpg\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024050817183302\",\"IMG_FILE_NM\":\"3024050817183302.tif\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024050817183301\",\"IMG_FILE_NM\":\"3024050817183301.tif\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024050817034900\",\"IMG_FILE_NM\":\"3024050817034900.jpg\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024050817183300\",\"IMG_FILE_NM\":\"3024050817183300.tif\"}],\"ADD_OFR_INF\":[{\"INF_KEY\":\"\",\"INF_VAL\":\"\"}]}',NULL,NULL,NULL,'2024-06-25 17:38:49.189127','4o1gFilZIM','2024-06-25 17:38:12.000000','ASSIGN'),(150,'2024-07-03 13:44:25.709945','2024-07-03 13:44:41.463114',NULL,'OK','2024-07-03 13:44:25.698166',NULL,'202453599','AC102','20231105',NULL,'CT1','5','2024-07-03 13:44:34.720726',NULL,NULL,3,'2B9540A46A36E2247B6D1AB461DE8045','AD34778D164A1C37CFE1D1745408658C','SUCCESS',NULL,'2024-07-03 13:44:41.462829','AIP','{\"RQS_REQ_ID\":\"4o1thBwshR\",\"ACD_NO\":\"202453599\",\"RCT_SEQ\":\"5\",\"ACD_DT\":\"20231105\",\"CLM_TP_CD\":\"1\",\"ACD_CAUS_LCTG_CD\":\"102\",\"RQS_TP_CD\":\"T00\",\"DMPE_NM\":\"AD34778D164A1C37CFE1D1745408658C\",\"INSPE_BDT\":\"2B9540A46A36E2247B6D1AB461DE8045\",\"REQ_DTM\":\"20240703134422\",\"IMG_CNT\":3,\"IMG_LST\":[{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024041809330002\",\"IMG_FILE_NM\":\"3024041809330002.tif\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024041809221200\",\"IMG_FILE_NM\":\"3024041809221200.jpg\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024041809330003\",\"IMG_FILE_NM\":\"3024041809330003.tif\"}],\"ADD_OFR_INF\":[{\"INF_KEY\":\"\",\"INF_VAL\":\"\"}]}',NULL,NULL,NULL,'2024-07-03 13:44:41.417044','4o1thBwshR','2024-07-03 13:44:22.000000','ASSIGN'),(151,'2024-07-03 16:19:50.027436','2024-07-03 16:19:52.367714',NULL,'OK','2024-07-03 16:19:50.014480',NULL,'202486318','AC101','20240701',NULL,'CT2','1','2024-07-03 16:19:51.519488',NULL,NULL,1,'A63644B92EDB2FAED31BD6790F43CB85','7E014236561C2DF46C8B269B29474FEF','SUCCESS',NULL,'2024-07-03 16:19:52.367431','박희정','{\"RQS_REQ_ID\":\"4o1trNA8m9\",\"ACD_NO\":\"202486318\",\"RCT_SEQ\":\"1\",\"ACD_DT\":\"20240701\",\"CLM_TP_CD\":\"2\",\"ACD_CAUS_LCTG_CD\":\"101\",\"RQS_TP_CD\":\"T00\",\"DMPE_NM\":\"7E014236561C2DF46C8B269B29474FEF\",\"INSPE_BDT\":\"A63644B92EDB2FAED31BD6790F43CB85\",\"REQ_DTM\":\"20240703161949\",\"IMG_CNT\":1,\"IMG_LST\":[{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024070316194800\",\"IMG_FILE_NM\":\"3024070316194800.jpg\"}],\"ADD_OFR_INF\":[{\"INF_KEY\":\"\",\"INF_VAL\":\"\"}]}',NULL,NULL,NULL,'2024-07-03 16:19:52.333151','4o1trNA8m9','2024-07-03 16:19:49.000000','ASSIGN'),(152,'2024-07-03 17:15:23.780876','2024-07-03 17:15:42.160969',NULL,'OK','2024-07-03 17:15:23.767524',NULL,'202486321','AC101','20240103',NULL,'CT2','1','2024-07-03 17:15:38.004786',NULL,NULL,2,'210A252A5FFFB69A96F49F44A9F8CEEF','E050F921A9D47244A8EC26640A2DD0C5','SUCCESS',NULL,'2024-07-03 17:15:42.160708','박희정','{\"RQS_REQ_ID\":\"4o1tv0jH5L\",\"ACD_NO\":\"202486321\",\"RCT_SEQ\":\"1\",\"ACD_DT\":\"20240103\",\"CLM_TP_CD\":\"2\",\"ACD_CAUS_LCTG_CD\":\"101\",\"RQS_TP_CD\":\"T00\",\"DMPE_NM\":\"E050F921A9D47244A8EC26640A2DD0C5\",\"INSPE_BDT\":\"210A252A5FFFB69A96F49F44A9F8CEEF\",\"REQ_DTM\":\"20240703171522\",\"IMG_CNT\":2,\"IMG_LST\":[{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024070317151901\",\"IMG_FILE_NM\":\"3024070317151901.jpg\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024070317151900\",\"IMG_FILE_NM\":\"3024070317151900.jpg\"}],\"ADD_OFR_INF\":[{\"INF_KEY\":\"\",\"INF_VAL\":\"\"}]}',NULL,NULL,NULL,'2024-07-03 17:15:42.117585','4o1tv0jH5L','2024-07-03 17:15:22.000000','ASSIGN'),(153,'2024-07-03 17:16:56.591101','2024-07-03 17:17:12.679113',NULL,'OK','2024-07-03 17:16:56.590652',NULL,'202469636','AC101','20240320',NULL,'CT1','2','2024-07-03 17:17:00.169061',NULL,NULL,5,'A30C172E23527EE8A80E194D088E5034','CA68C3B8140B01A6B85E8613BFCBC70F','SUCCESS',NULL,'2024-07-03 17:17:12.678835','박희정','{\"RQS_REQ_ID\":\"4o1tv6vTIx\",\"ACD_NO\":\"202469439\",\"RCT_SEQ\":\"1\",\"ACD_DT\":\"20240320\",\"CLM_TP_CD\":\"1\",\"ACD_CAUS_LCTG_CD\":\"101\",\"RQS_TP_CD\":\"T00\",\"DMPE_NM\":\"CA68C3B8140B01A6B85E8613BFCBC70F\",\"INSPE_BDT\":\"A30C172E23527EE8A80E194D088E5034\",\"REQ_DTM\":\"20240703171653\",\"IMG_CNT\":5,\"IMG_LST\":[{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024070317162601\",\"IMG_FILE_NM\":\"3024070317162601.tif\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024070317162604\",\"IMG_FILE_NM\":\"3024070317162604.tif\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024070317162602\",\"IMG_FILE_NM\":\"3024070317162602.tif\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024070317162600\",\"IMG_FILE_NM\":\"3024070317162600.tif\"},{\"IMG_DCM_NO\":\"\",\"IMG_DCMFL_NO\":\"\",\"IMG_ID\":\"3024070317162603\",\"IMG_FILE_NM\":\"3024070317162603.tif\"}],\"ADD_OFR_INF\":[{\"INF_KEY\":\"\",\"INF_VAL\":\"\"}]}',NULL,NULL,NULL,'2024-07-03 17:17:12.659561','4o1wc8GEgf','2024-07-03 17:16:53.000000','ASSIGN');
/*!40000 ALTER TABLE `assign` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-03 17:18:22
