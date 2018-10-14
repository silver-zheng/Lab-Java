CREATE DATABASE  IF NOT EXISTS `hospital_data` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `hospital_data`;
-- MySQL dump 10.13  Distrib 5.7.20, for Win64 (x86_64)
--
-- Host: localhost    Database: hospital_data
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `doctor_info`
--

DROP TABLE IF EXISTS `doctor_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctor_info` (
  `doc_num` char(6) NOT NULL,
  `office_num` char(6) NOT NULL,
  `doc_name` char(10) NOT NULL,
  `doc_pinyin` char(4) NOT NULL,
  `login_cmd` char(8) NOT NULL,
  `expert` tinyint(1) NOT NULL,
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`doc_num`),
  KEY `医生编号` (`doc_num`),
  KEY `科室编号` (`office_num`),
  CONSTRAINT `doc_office_num` FOREIGN KEY (`office_num`) REFERENCES `office_info` (`office_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='科室医生';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_info`
--

LOCK TABLES `doctor_info` WRITE;
/*!40000 ALTER TABLE `doctor_info` DISABLE KEYS */;
INSERT INTO `doctor_info` VALUES ('000001','000001','裴阳','py','pei',0,'2018-05-02 20:22:59'),('000002','000001','杨鑫','yx','yang',1,NULL),('000003','000001','黄赛','hs','huang',1,'2018-04-24 11:30:49'),('000004','000002','陈胜强','csq','chen',1,'2018-04-18 20:48:39'),('000005','000003','刘永强','lyq','liu',1,'2018-04-18 20:52:01'),('000006','000004','朱奇','zq','zhu',0,'2018-04-24 11:31:19'),('000007','000004','杨永信','yyx','yang',1,'2018-04-29 16:52:49'),('000008','000005','龙港星','lgx','long',0,'2018-04-23 19:16:02'),('000009','000005','陈筱昀','cxy','chen',1,NULL),('000010','000006','蔡彗','ch','cai',1,'2018-05-03 09:51:28'),('000011','000006','蔡韬','ct','cai',1,NULL),('000012','000006','黄雨柔','hyr','huang',0,NULL);
/*!40000 ALTER TABLE `doctor_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `office_info`
--

DROP TABLE IF EXISTS `office_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `office_info` (
  `office_num` char(6) NOT NULL,
  `office_name` char(10) NOT NULL,
  `office_pinyin` char(8) NOT NULL,
  PRIMARY KEY (`office_num`),
  KEY `科室编号` (`office_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='科室信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `office_info`
--

LOCK TABLES `office_info` WRITE;
/*!40000 ALTER TABLE `office_info` DISABLE KEYS */;
INSERT INTO `office_info` VALUES ('000001','普通外科','ptwk'),('000002','普通内科','ptnk'),('000003','儿科','ek'),('000004','耳鼻喉科','ebhk'),('000005','放射科','fsk'),('000006','风湿科','fsk');
/*!40000 ALTER TABLE `office_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_info`
--

DROP TABLE IF EXISTS `patient_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_info` (
  `pat_num` char(6) NOT NULL,
  `pat_name` char(10) NOT NULL,
  `login_cmd` char(8) NOT NULL,
  `prestore_amount` decimal(10,2) NOT NULL,
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`pat_num`),
  KEY `病人编号` (`pat_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='病人信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_info`
--

LOCK TABLES `patient_info` WRITE;
/*!40000 ALTER TABLE `patient_info` DISABLE KEYS */;
INSERT INTO `patient_info` VALUES ('000001','durant','kevin',3.00,'2018-05-03 09:32:19'),('000002','james','lebron',2.30,'2018-04-23 20:11:30'),('000003','harden','james',1.30,'2018-04-22 23:16:03'),('000004','curry','steph',29.00,'2018-05-02 21:08:19'),('000005','westbrook','russell',0.00,'2018-04-22 20:39:06'),('000006','george','paul',2.41,'2018-04-26 14:51:42'),('000007','anthony','carmelo',1.00,'2018-04-29 16:47:52'),('000008','wall','john',7.00,'2018-05-03 09:34:12'),('000009','irving','kyrie',2.11,'2018-04-23 19:28:50');
/*!40000 ALTER TABLE `patient_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `register_info`
--

DROP TABLE IF EXISTS `register_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `register_info` (
  `register_num` char(6) NOT NULL,
  `registration_num` char(6) NOT NULL,
  `doc_num` char(6) NOT NULL,
  `pat_num` char(6) NOT NULL,
  `pat_count` int(11) NOT NULL,
  `unreg` tinyint(1) NOT NULL,
  `reg_cost` decimal(8,2) NOT NULL,
  `reg_datetime` datetime NOT NULL,
  PRIMARY KEY (`register_num`),
  KEY `挂号编号` (`register_num`),
  KEY `号种编号` (`registration_num`),
  KEY `医生编号` (`doc_num`),
  KEY `病人编号` (`pat_num`),
  KEY `病人数目` (`pat_count`),
  CONSTRAINT `regi_doc_num` FOREIGN KEY (`doc_num`) REFERENCES `doctor_info` (`doc_num`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `regi_pat_num` FOREIGN KEY (`pat_num`) REFERENCES `patient_info` (`pat_num`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `regi_reg_num` FOREIGN KEY (`registration_num`) REFERENCES `registration_info` (`reg_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='挂号信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `register_info`
--

LOCK TABLES `register_info` WRITE;
/*!40000 ALTER TABLE `register_info` DISABLE KEYS */;
INSERT INTO `register_info` VALUES ('1','000007','000007','000003',1,1,10.00,'2018-04-18 00:20:18'),('10','000016','000010','000001',1,0,4.00,'2018-04-24 10:16:05'),('11','000014','000010','000001',2,1,5.00,'2018-04-24 10:16:20'),('12','000014','000010','000001',2,0,5.00,'2018-04-24 10:16:35'),('13','000014','000010','000001',2,0,5.00,'2018-04-24 10:16:47'),('14','000014','000010','000001',2,1,5.00,'2018-04-24 10:18:32'),('15','000001','000001','000001',0,1,2.00,'2018-04-26 15:03:52'),('16','000006','000007','000007',3,0,4.00,'2018-04-29 16:48:01'),('17','000006','000007','000007',3,0,4.00,'2018-04-29 16:48:15'),('18','000008','000007','000007',1,0,4.00,'2018-04-29 16:48:27'),('19','000006','000007','000007',3,1,4.00,'2018-04-29 16:49:30'),('2','000004','000005','000001',1,1,3.50,'2018-04-18 18:59:37'),('20','000006','000007','000007',3,0,4.00,'2018-04-29 16:51:36'),('21','000009','000009','000001',1,1,1.00,'2018-05-02 18:59:39'),('22','000006','000007','000001',1,0,4.00,'2018-05-02 20:52:32'),('23','000002','000002','000001',1,0,3.50,'2018-05-02 21:00:59'),('24','000004','000005','000001',2,0,3.50,'2018-05-02 21:02:15'),('25','000009','000009','000004',1,0,1.00,'2018-05-02 21:08:54'),('26','000004','000005','000001',2,0,3.50,'2018-05-02 21:08:58'),('27','000013','000012','000001',3,0,5.00,'2018-05-03 08:53:11'),('28','000013','000012','000001',3,0,5.00,'2018-05-03 08:53:14'),('29','000013','000012','000001',3,0,5.00,'2018-05-03 08:53:16'),('3','000003','000004','000001',1,1,3.00,'2018-04-18 19:06:47'),('30','000003','000004','000001',2,0,3.00,'2018-05-03 09:09:53'),('31','000006','000007','000001',1,0,4.00,'2018-05-03 09:21:42'),('32','000002','000002','000001',1,0,3.50,'2018-05-03 09:34:44'),('33','000003','000004','000008',2,0,3.00,'2018-05-03 09:34:47'),('4','000014','000010','000001',1,1,5.00,'2018-04-18 19:10:36'),('5','000014','000010','000001',1,0,5.00,'2018-04-18 19:10:47'),('6','000016','000010','000001',1,0,4.00,'2018-04-22 22:58:05'),('7','000014','000011','000001',1,0,5.00,'2018-04-22 22:59:32'),('8','000014','000010','000001',1,1,5.00,'2018-04-22 23:00:34'),('9','000014','000010','000001',1,1,5.00,'2018-04-22 23:00:46');
/*!40000 ALTER TABLE `register_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration_info`
--

DROP TABLE IF EXISTS `registration_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registration_info` (
  `reg_num` char(6) NOT NULL,
  `reg_name` char(12) NOT NULL,
  `reg_pinyin` char(4) NOT NULL,
  `office_num` char(6) NOT NULL,
  `expert` tinyint(1) NOT NULL,
  `max_patient` int(11) NOT NULL,
  `reg_cost` decimal(8,2) NOT NULL,
  PRIMARY KEY (`reg_num`),
  KEY `号种编号` (`reg_num`),
  KEY `科室编号` (`office_num`),
  CONSTRAINT `reg_office_num` FOREIGN KEY (`office_num`) REFERENCES `office_info` (`office_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='号种信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration_info`
--

LOCK TABLES `registration_info` WRITE;
/*!40000 ALTER TABLE `registration_info` DISABLE KEYS */;
INSERT INTO `registration_info` VALUES ('000001','皮外伤','pws','000001',0,3,2.00),('000002','疱疹','pz','000001',1,3,3.50),('000003','感冒','gm','000002',0,3,3.00),('000004','腹泻','fx','000003',1,3,3.50),('000005','发烧','fs','000003',1,3,3.50),('000006','中耳炎','zey','000004',1,3,4.00),('000007','鼻炎','by','000004',1,3,4.00),('000008','咽喉炎','yhy','000004',1,3,4.00),('000009','X光','xg','000005',1,3,1.00),('000010','CT扫描','ctsm','000005',0,3,1.00),('000011','核磁共振','hcgz','000005',0,3,1.00),('000012','关节炎','gjy','000006',0,3,5.00),('000013','肩周炎','jzy','000006',0,3,5.00),('000014','风湿','fs','000006',1,3,5.00),('000015','类风湿','lfs','000006',1,3,5.00),('000016','风湿','fs','000006',0,3,4.00);
/*!40000 ALTER TABLE `registration_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-10 13:09:52
