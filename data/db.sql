-- MySQL dump 10.13  Distrib 5.5.29, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: romeshop_development
-- ------------------------------------------------------
-- Server version	5.5.29-0ubuntu0.12.04.1

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
-- Table structure for table `DATABASECHANGELOG`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOG` (
  `ID` varchar(63) NOT NULL,
  `AUTHOR` varchar(63) NOT NULL,
  `FILENAME` varchar(200) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`,`AUTHOR`,`FILENAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOG`
--

LOCK TABLES `DATABASECHANGELOG` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOG` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOG` VALUES ('1','adi','id/ac/bsi/adi/ta/ecommerce/db/db-changelog-0.0.1.xml','2013-07-01 12:00:05',1,'EXECUTED','3:d41d8cd98f00b204e9800998ecf8427e','Empty','',NULL,'2.0.5');
/*!40000 ALTER TABLE `DATABASECHANGELOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOGLOCK`
--

DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int(11) NOT NULL,
  `LOCKED` tinyint(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOGLOCK`
--

LOCK TABLES `DATABASECHANGELOGLOCK` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOGLOCK` VALUES (1,0,NULL,NULL);
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_category_product`
--

DROP TABLE IF EXISTS `mst_category_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mst_category_product` (
  `id` varchar(36) NOT NULL,
  `category_code` varchar(3) NOT NULL,
  `description` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_code` (`category_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_category_product`
--

LOCK TABLES `mst_category_product` WRITE;
/*!40000 ALTER TABLE `mst_category_product` DISABLE KEYS */;
INSERT INTO `mst_category_product` VALUES ('ff8081813f99f12c013f99f832020000','JAK','Jaket'),('ff8081813f99f12c013f99f866360001','POL','Polo'),('ff8081813f99f12c013f99f87b160002','ROM','Rompi');
/*!40000 ALTER TABLE `mst_category_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_city`
--

DROP TABLE IF EXISTS `mst_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mst_city` (
  `id` varchar(36) NOT NULL,
  `city_code` varchar(3) NOT NULL,
  `city_name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `city_code` (`city_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_city`
--

LOCK TABLES `mst_city` WRITE;
/*!40000 ALTER TABLE `mst_city` DISABLE KEYS */;
INSERT INTO `mst_city` VALUES ('ff8081813f99f12c013f99fbd7ef0008','JKT','Jakarta'),('ff8081813f99f12c013f99fbf0a10009','SBY','Surabaya'),('ff8081813f99f12c013f99fc3352000a','CGK','Cengkareng'),('ff8081813f99f12c013f99fc6f84000b','BEK','Bekasi');
/*!40000 ALTER TABLE `mst_city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_member`
--

DROP TABLE IF EXISTS `mst_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mst_member` (
  `id` varchar(36) NOT NULL,
  `address` varchar(50) NOT NULL,
  `email` varchar(25) NOT NULL,
  `firstname` varchar(10) NOT NULL,
  `lastname` varchar(15) DEFAULT NULL,
  `mobile` varchar(15) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `province` varchar(20) NOT NULL,
  `registration_date` datetime NOT NULL,
  `zip_code` varchar(5) DEFAULT NULL,
  `id_city` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK113DE46B43E932B4` (`id_city`),
  CONSTRAINT `FK113DE46B43E932B4` FOREIGN KEY (`id_city`) REFERENCES `mst_city` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_member`
--

LOCK TABLES `mst_member` WRITE;
/*!40000 ALTER TABLE `mst_member` DISABLE KEYS */;
INSERT INTO `mst_member` VALUES ('ff8081813fa18f16013fa19335aa0006','Jl. xxx 12/1','lars@gmail.com','lars','ulrich','940394090940','','Jakarta Timur','2013-07-03 05:49:06','60123','ff8081813f99f12c013f99fbd7ef0008');
/*!40000 ALTER TABLE `mst_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_picture_product`
--

DROP TABLE IF EXISTS `mst_picture_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mst_picture_product` (
  `id_product` varchar(36) NOT NULL,
  `pictures` varchar(50) DEFAULT NULL,
  KEY `FK9CA613FD5C002540` (`id_product`),
  CONSTRAINT `FK9CA613FD5C002540` FOREIGN KEY (`id_product`) REFERENCES `mst_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_picture_product`
--

LOCK TABLES `mst_picture_product` WRITE;
/*!40000 ALTER TABLE `mst_picture_product` DISABLE KEYS */;
INSERT INTO `mst_picture_product` VALUES ('ff8081813f9ab148013f9ab416d70000','img/product/curvasud_juve_jersey.jpg'),('ff8081813fa08756013fa09a78d50000','img/product/curvasud_arsenal_jersey.jpg'),('ff8081813fa08756013fa09aef980001','img/product/curvasud_polo1.jpg');
/*!40000 ALTER TABLE `mst_picture_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_product`
--

DROP TABLE IF EXISTS `mst_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mst_product` (
  `id` varchar(36) NOT NULL,
  `price` decimal(19,2) NOT NULL,
  `product_code` varchar(8) NOT NULL,
  `product_info` longtext NOT NULL,
  `product_name` varchar(25) NOT NULL,
  `id_category` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_code` (`product_code`),
  KEY `FKCB7DAD3EACD25A39` (`id_category`),
  CONSTRAINT `FKCB7DAD3EACD25A39` FOREIGN KEY (`id_category`) REFERENCES `mst_category_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_product`
--

LOCK TABLES `mst_product` WRITE;
/*!40000 ALTER TABLE `mst_product` DISABLE KEYS */;
INSERT INTO `mst_product` VALUES ('ff8081813f9ab148013f9ab416d70000',90000.00,'CSOS0004','Jaket Roma Gambar Badak','Jaket Roma 12','ff8081813f99f12c013f99f832020000'),('ff8081813fa08756013fa09a78d50000',90000.00,'CSOS0005','bla bla bla bla bla bla bla bla bla bla bla','Kaos Polo Roma Hitam','ff8081813f99f12c013f99f866360001'),('ff8081813fa08756013fa09aef980001',90000.00,'CSOS0006','web v dcd re sldj eljslj sklj','Kaos Polo Roma Putih','ff8081813f99f12c013f99f866360001');
/*!40000 ALTER TABLE `mst_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_running_number`
--

DROP TABLE IF EXISTS `mst_running_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mst_running_number` (
  `id` varchar(36) NOT NULL,
  `bussiness_date` date NOT NULL,
  `designation_type` varchar(10) NOT NULL,
  `last_number` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `designation_type` (`designation_type`,`bussiness_date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_running_number`
--

LOCK TABLES `mst_running_number` WRITE;
/*!40000 ALTER TABLE `mst_running_number` DISABLE KEYS */;
INSERT INTO `mst_running_number` VALUES ('ff8081813f9aabe8013f9aac8c3a0000','2013-07-01','PRODUCT',6);
/*!40000 ALTER TABLE `mst_running_number` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_shipping_rate`
--

DROP TABLE IF EXISTS `mst_shipping_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mst_shipping_rate` (
  `id` varchar(36) NOT NULL,
  `express` decimal(19,2) NOT NULL,
  `reguler` decimal(19,2) NOT NULL,
  `id_city` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK55F6ADE043E932B4` (`id_city`),
  CONSTRAINT `FK55F6ADE043E932B4` FOREIGN KEY (`id_city`) REFERENCES `mst_city` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_shipping_rate`
--

LOCK TABLES `mst_shipping_rate` WRITE;
/*!40000 ALTER TABLE `mst_shipping_rate` DISABLE KEYS */;
INSERT INTO `mst_shipping_rate` VALUES ('ff8081813f99f12c013f99fcd963000c',20000.00,14000.00,'ff8081813f99f12c013f99fbf0a10009'),('ff8081813f99f12c013f99fd0352000d',15000.00,9000.00,'ff8081813f99f12c013f99fbd7ef0008'),('ff8081813f99f12c013f99fd2787000e',18000.00,10000.00,'ff8081813f99f12c013f99fc3352000a'),('ff8081813f99f12c013f99fd883c000f',15000.00,9000.00,'ff8081813f99f12c013f99fc6f84000b');
/*!40000 ALTER TABLE `mst_shipping_rate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_supplier`
--

DROP TABLE IF EXISTS `mst_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mst_supplier` (
  `id` varchar(36) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `code` varchar(3) NOT NULL,
  `email` varchar(25) DEFAULT NULL,
  `name` varchar(25) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_supplier`
--

LOCK TABLES `mst_supplier` WRITE;
/*!40000 ALTER TABLE `mst_supplier` DISABLE KEYS */;
INSERT INTO `mst_supplier` VALUES ('ff8081813f99f12c013f99f9bbaf0003','','MTK','','Motekar Konveksi',''),('ff8081813f99f12c013f99f9f3020004','','OCL','','Ovum Cloth','');
/*!40000 ALTER TABLE `mst_supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sec_permission`
--

DROP TABLE IF EXISTS `sec_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sec_permission` (
  `id` varchar(36) NOT NULL,
  `label` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `label` (`label`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_permission`
--

LOCK TABLES `sec_permission` WRITE;
/*!40000 ALTER TABLE `sec_permission` DISABLE KEYS */;
INSERT INTO `sec_permission` VALUES ('5aa4bf28-e371-11e2-9a58-f04da2694372','ADMIN'),('5aa4d900-e371-11e2-9a58-f04da2694372','USER');
/*!40000 ALTER TABLE `sec_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sec_role`
--

DROP TABLE IF EXISTS `sec_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sec_role` (
  `id` varchar(36) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_role`
--

LOCK TABLES `sec_role` WRITE;
/*!40000 ALTER TABLE `sec_role` DISABLE KEYS */;
INSERT INTO `sec_role` VALUES ('68c8c687-e369-11e2-9a58-f04da2694372','Akses Super User','ADMIN'),('68c8f6e0-e369-11e2-9a58-f04da2694372','Akses Untuk Member Curvasud','MEMBER');
/*!40000 ALTER TABLE `sec_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sec_role_permission`
--

DROP TABLE IF EXISTS `sec_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sec_role_permission` (
  `id_role` varchar(36) NOT NULL,
  `id_permission` varchar(36) NOT NULL,
  PRIMARY KEY (`id_role`,`id_permission`),
  KEY `FK952B254AE9FC5DDE` (`id_permission`),
  KEY `FK952B254AA8B6832C` (`id_role`),
  CONSTRAINT `FK952B254AA8B6832C` FOREIGN KEY (`id_role`) REFERENCES `sec_role` (`id`),
  CONSTRAINT `FK952B254AE9FC5DDE` FOREIGN KEY (`id_permission`) REFERENCES `sec_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_role_permission`
--

LOCK TABLES `sec_role_permission` WRITE;
/*!40000 ALTER TABLE `sec_role_permission` DISABLE KEYS */;
INSERT INTO `sec_role_permission` VALUES ('68c8c687-e369-11e2-9a58-f04da2694372','5aa4bf28-e371-11e2-9a58-f04da2694372'),('68c8f6e0-e369-11e2-9a58-f04da2694372','5aa4d900-e371-11e2-9a58-f04da2694372');
/*!40000 ALTER TABLE `sec_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sec_user`
--

DROP TABLE IF EXISTS `sec_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sec_user` (
  `id` varchar(36) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `password` varchar(36) NOT NULL,
  `status` varchar(6) NOT NULL,
  `username` varchar(20) NOT NULL,
  `id_member` varchar(36) DEFAULT NULL,
  `id_role` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `FK375DF2F910109592` (`id_member`),
  KEY `FK375DF2F9A8B6832C` (`id_role`),
  CONSTRAINT `FK375DF2F910109592` FOREIGN KEY (`id_member`) REFERENCES `mst_member` (`id`),
  CONSTRAINT `FK375DF2F9A8B6832C` FOREIGN KEY (`id_role`) REFERENCES `sec_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_user`
--

LOCK TABLES `sec_user` WRITE;
/*!40000 ALTER TABLE `sec_user` DISABLE KEYS */;
INSERT INTO `sec_user` VALUES ('e1e0a400-e372-11e2-9a58-f04da2694372',1,'86888061b399e74e30eeead8c7aab922','ACTIVE','admin',NULL,'68c8c687-e369-11e2-9a58-f04da2694372'),('ff8081813fa18f16013fa19335ab0007',1,'21a0b208f7b40d1ffc408e4196d2e397','ACTIVE','larscok','ff8081813fa18f16013fa19335aa0006','68c8f6e0-e369-11e2-9a58-f04da2694372');
/*!40000 ALTER TABLE `sec_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trx_booking`
--

DROP TABLE IF EXISTS `trx_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trx_booking` (
  `id` varchar(36) NOT NULL,
  `booking_code` varchar(10) NOT NULL,
  `shipping_address` varchar(100) NOT NULL,
  `shipping_cost` decimal(19,2) NOT NULL,
  `shipping_phone` varchar(15) NOT NULL,
  `transaction_date` datetime NOT NULL,
  `id_member` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `booking_code` (`booking_code`),
  KEY `FK50D416D410109592` (`id_member`),
  CONSTRAINT `FK50D416D410109592` FOREIGN KEY (`id_member`) REFERENCES `mst_member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_booking`
--

LOCK TABLES `trx_booking` WRITE;
/*!40000 ALTER TABLE `trx_booking` DISABLE KEYS */;
/*!40000 ALTER TABLE `trx_booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trx_booking_detail`
--

DROP TABLE IF EXISTS `trx_booking_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trx_booking_detail` (
  `id` varchar(36) NOT NULL,
  `amount` decimal(19,2) NOT NULL,
  `qty` int(11) NOT NULL,
  `total_amount` decimal(19,2) NOT NULL,
  `id_booking` varchar(36) NOT NULL,
  `id_product` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_booking` (`id_booking`,`id_product`),
  KEY `FK8ADA681C9625F36C` (`id_booking`),
  KEY `FK8ADA681C5C002540` (`id_product`),
  CONSTRAINT `FK8ADA681C5C002540` FOREIGN KEY (`id_product`) REFERENCES `mst_product` (`id`),
  CONSTRAINT `FK8ADA681C9625F36C` FOREIGN KEY (`id_booking`) REFERENCES `trx_booking` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_booking_detail`
--

LOCK TABLES `trx_booking_detail` WRITE;
/*!40000 ALTER TABLE `trx_booking_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `trx_booking_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trx_change_of_stock`
--

DROP TABLE IF EXISTS `trx_change_of_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trx_change_of_stock` (
  `id` varchar(36) NOT NULL,
  `date_of_mutation` date DEFAULT NULL,
  `final_stock` int(11) NOT NULL,
  `initial_stock` int(11) NOT NULL,
  `stock_credit` int(11) NOT NULL,
  `stock_debet` int(11) NOT NULL,
  `id_product` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `date_of_mutation` (`date_of_mutation`,`id_product`),
  KEY `FK844311D85C002540` (`id_product`),
  CONSTRAINT `FK844311D85C002540` FOREIGN KEY (`id_product`) REFERENCES `mst_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_change_of_stock`
--

LOCK TABLES `trx_change_of_stock` WRITE;
/*!40000 ALTER TABLE `trx_change_of_stock` DISABLE KEYS */;
/*!40000 ALTER TABLE `trx_change_of_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trx_invoice`
--

DROP TABLE IF EXISTS `trx_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trx_invoice` (
  `id` varchar(36) NOT NULL,
  `invoice_number` varchar(10) NOT NULL,
  `transaction_date` datetime NOT NULL,
  `id_booking` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC1CF51A89625F36C` (`id_booking`),
  CONSTRAINT `FKC1CF51A89625F36C` FOREIGN KEY (`id_booking`) REFERENCES `trx_booking` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_invoice`
--

LOCK TABLES `trx_invoice` WRITE;
/*!40000 ALTER TABLE `trx_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `trx_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trx_payment`
--

DROP TABLE IF EXISTS `trx_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trx_payment` (
  `id` varchar(36) NOT NULL,
  `account_name` varchar(35) NOT NULL,
  `approve_date` datetime NOT NULL,
  `approved` tinyint(1) NOT NULL,
  `payment_code` varchar(10) NOT NULL,
  `payment_date` date NOT NULL,
  `transaction_date` datetime NOT NULL,
  `transfer_amount` decimal(19,2) NOT NULL,
  `id_booking` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1E1540819625F36C` (`id_booking`),
  CONSTRAINT `FK1E1540819625F36C` FOREIGN KEY (`id_booking`) REFERENCES `trx_booking` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_payment`
--

LOCK TABLES `trx_payment` WRITE;
/*!40000 ALTER TABLE `trx_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `trx_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trx_purchase`
--

DROP TABLE IF EXISTS `trx_purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trx_purchase` (
  `id` varchar(36) NOT NULL,
  `purchase_date` datetime NOT NULL,
  `purchase_number` varchar(20) NOT NULL,
  `id_supplier` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBA11C5E6D564D476` (`id_supplier`),
  CONSTRAINT `FKBA11C5E6D564D476` FOREIGN KEY (`id_supplier`) REFERENCES `mst_supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_purchase`
--

LOCK TABLES `trx_purchase` WRITE;
/*!40000 ALTER TABLE `trx_purchase` DISABLE KEYS */;
INSERT INTO `trx_purchase` VALUES ('ff8081813f9e9954013f9e9a54030000','2013-07-02 15:58:01','PCM439048','ff8081813f99f12c013f99f9bbaf0003'),('ff8081813f9ea1a8013f9ea3c3d70000','2013-07-02 16:08:20','PMB5909','ff8081813f99f12c013f99f9bbaf0003');
/*!40000 ALTER TABLE `trx_purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trx_purchase_detail`
--

DROP TABLE IF EXISTS `trx_purchase_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trx_purchase_detail` (
  `id` varchar(36) NOT NULL,
  `price` decimal(19,2) NOT NULL,
  `qty` int(11) NOT NULL,
  `total_price` decimal(19,2) NOT NULL,
  `id_product` varchar(36) NOT NULL,
  `id_purchase` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_purchase` (`id_purchase`,`id_product`),
  KEY `FKEE7F00CA5C002540` (`id_product`),
  KEY `FKEE7F00CAF5E7E88` (`id_purchase`),
  CONSTRAINT `FKEE7F00CA5C002540` FOREIGN KEY (`id_product`) REFERENCES `mst_product` (`id`),
  CONSTRAINT `FKEE7F00CAF5E7E88` FOREIGN KEY (`id_purchase`) REFERENCES `trx_purchase` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_purchase_detail`
--

LOCK TABLES `trx_purchase_detail` WRITE;
/*!40000 ALTER TABLE `trx_purchase_detail` DISABLE KEYS */;
INSERT INTO `trx_purchase_detail` VALUES ('ff8081813f9ea1a8013f9ea3c3e70001',3434.00,43,147662.00,'ff8081813f9ab148013f9ab416d70000','ff8081813f9ea1a8013f9ea3c3d70000');
/*!40000 ALTER TABLE `trx_purchase_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-07-03  9:06:51
