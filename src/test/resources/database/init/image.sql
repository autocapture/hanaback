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
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `accr_no` varchar(16) DEFAULT NULL,
  `dm_seqno` varchar(5) DEFAULT NULL,
  `dupped_file` varchar(50) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `hash_value` varchar(32) DEFAULT NULL,
  `image_accuracy` double DEFAULT NULL,
  `image_document_type_ocr` varchar(255) DEFAULT NULL,
  `image_processing_content` varchar(50) DEFAULT NULL,
  `image_processing_result_code` varchar(255) DEFAULT NULL,
  `img_id` varchar(255) DEFAULT NULL,
  `img_type` varchar(255) DEFAULT NULL,
  `is_dup` tinyint(1) DEFAULT 0,
  `is_input_required` tinyint(1) DEFAULT 1,
  `is_qa` tinyint(1) DEFAULT 0,
  `qa_reason` text DEFAULT NULL,
  `qa_status` tinyint(1) DEFAULT 0,
  `raw_data` text DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  `rqs_req_id` varchar(255) DEFAULT NULL,
  `img_dcm_no` varchar(255) DEFAULT NULL,
  `img_dcmfl_no` varchar(255) DEFAULT NULL,
  `origin_file_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=532 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (528,'2024-07-03 13:44:37.092449','2024-07-03 15:00:48.310814','202453599','5','','3024041809330002.jpg','b67a30f8bad8e66c527c87813baf6cc1',NULL,'ETCS',NULL,'NORMAL','3024041809330002','MDDG',0,1,0,NULL,0,'2023/11/2013:13 (FAX) P 005/006 \nClinicalChart\n차트번호:59081,김지은 \n피보험자휴대전화 010-3055-0273 증번호 \n조합기효조합명칭 \n수진자김지은 주민등록번호 760323-2781612 나이 47세 성별 여 \n주소 대구광역시동구동화천로77길30 317/1004(지묘동,LH뉴웰시티) \n내원일:2023년11월17일14:49:33조할:증:진료시간:14:51:28 \n담당의:정수원진찰구분: 초진 \n<<C.C>> \nNeckpain\n11/16교통사고조수석,\n단독사고,우회전하다벽율부딪혀수상함\n사고후경추및우촉승모근통발생\nTd+\nno.neurologicsign\nx-ray\nno.def.tx.line\nR/0sprain\n<<Diagnosis>> \n(S134)경추의염좌및긴장\n(K219)식도역류NOS\n<<Examine>> \nC-spine4view\nC-spine4view\n<<Treatment>> \n표흥열치료(상층열동시)1 1 심충열치료[1일당] 1 1 1 \n간설파전류치료[ICT]1 1 1 아페락정(원외) 2 2 3 \n동구에페리손정(원의)2 2 3 라베스토정10mg(원외) 2 2 3 \n초진차트1 1 1 \n보간히장절의 \n1쭉더참본정형외과의원 \n',1,'4o1thBwshR',NULL,NULL,'3024041809330002.tif'),(529,'2024-07-03 13:44:39.168366','2024-07-03 13:44:39.168366','202453599','5','','3024041809221200.jpg','2f848bb7ce29057ce27121a721cee79e',NULL,'ETCS',NULL,'NORMAL','3024041809221200','ETCS',0,1,0,NULL,0,'',2,'4o1thBwshR',NULL,NULL,'3024041809221200.jpg'),(530,'2024-07-03 13:44:41.353685','2024-07-03 13:44:41.353685','202453599','5','','3024041809330003.jpg','4973eb46062b88a5e42c704ef69d6622',NULL,'ETCS',NULL,'NORMAL','3024041809330003','ETCS',0,1,0,NULL,0,'2023/11/2013:13 (FAX) P 004/006 \n소회에관한시항\n조회대상기관종합신용정보집중기관,생명·손해보험협회,보험요을산출기관,국토교통부 \n종합신용정보집중기관:보험사고·보험사기조사및보험금지급·심사,교통사고처리내역발\n급간소화서비스\n조회목적생명·손해보험협회:보험금지급보험금·심사(보험금청구서류접수대행서비스등) \n보험요을산출기관-국토교통부:보험사고·보험사기조사및보험금지급·심사,법령에의한업\n무수행등\n수집·이용등의일로부터거래종료후5년까지\n조회동의의효력기간(단거래종료후5년이경과한후에는보험금지급금융사고조사,분쟁해결,민원처리,법령 \n상의무이행을위한경우에한하여보유·이용하며,별도보관함)\n1조회항목 \n주민등록번호,외국인등록번호,여권번호,운전면허번호\n9고유식별정보\n위고유식별정보조회에동의하십니까?ㅁ동의하지않음 12동의함 \n피보험자의질병·상해에관한정보(진료기록,상병명동)교통법규위반정보교통사고조\nA민감정보 사기록(당사의요청에따라보험요울산출기관이경찰청으로부터제공받은정보)\n위민감정보조회에동의하십니까?ㅁ동의하지않음 5동의함 \nle개인(신용)정보\nL흔일반개인정보성명면허의효력에관한정보 \nㄴ9신용거래정보보험계약정보(상품종류,기간,보험가입금액등),보험금정보(보험금지급사유,지급금액등) \n위개인신용정보조회에동의하십니까? 亡동의함ㅁ동의하지않음 \n피보험자: 김지은\n유하나손해보험2023년 /7 월 20 일 법정대리인: (서명) \n보험수익자:깊지은 서명는 \n※만14세미만의경우법정대리인(친권자또는후견인)이작성후서명을하시고,만14세이상의경우미성년본인직접동의 \n또는법정대리인(친권자또는후견인)대리동의후서명하시기바랍니다.\n고객콜센터1566-3000www.hanaansure.co.kr발행일시: 2022.09.0715:55 발행자: 이규민[페이지:4/5] \n',3,'4o1thBwshR',NULL,NULL,'3024041809330003.tif'),(531,'2024-07-03 16:19:52.298530','2024-07-03 16:19:52.298530','202486318','1','','3024070316194800.jpg','',NULL,'ETCS',NULL,'FTP_ERROR','3024070316194800','ETCS',0,0,1,NULL,0,'',1,'4o1trNA8m9',NULL,NULL,'3024070316194800.jpg');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-03 17:11:51
