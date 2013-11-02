-- MySQL dump 10.13  Distrib 5.5.32, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: romeshop_development
-- ------------------------------------------------------
-- Server version	5.5.32-0ubuntu0.12.04.1

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
INSERT INTO `DATABASECHANGELOG` VALUES ('1','adi','id/ac/bsi/adi/ta/ecommerce/db/db-changelog-0.0.1.xml','2013-09-14 14:48:19',1,'EXECUTED','3:d41d8cd98f00b204e9800998ecf8427e','Empty','',NULL,'2.0.5');
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
  `category_code` varchar(3) NOT NULL,
  `description` varchar(100) NOT NULL,
  PRIMARY KEY (`category_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_category_product`
--

LOCK TABLES `mst_category_product` WRITE;
/*!40000 ALTER TABLE `mst_category_product` DISABLE KEYS */;
INSERT INTO `mst_category_product` VALUES ('JAK','JAKET'),('KAO','KAOS'),('POL','POLO'),('SWT','SWEATER'),('TOP','TOPI');
/*!40000 ALTER TABLE `mst_category_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_city`
--

DROP TABLE IF EXISTS `mst_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mst_city` (
  `city_code` varchar(3) NOT NULL,
  `city_name` varchar(25) NOT NULL,
  PRIMARY KEY (`city_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_city`
--

LOCK TABLES `mst_city` WRITE;
/*!40000 ALTER TABLE `mst_city` DISABLE KEYS */;
INSERT INTO `mst_city` VALUES ('BDG','BANDUNG'),('JAK','JAKARTA'),('MDN','MEDAN'),('SBY','SURABAYA'),('SMG','SEMARANG'),('SOL','SOLO');
/*!40000 ALTER TABLE `mst_city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_member`
--

DROP TABLE IF EXISTS `mst_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mst_member` (
  `member_code` varchar(10) NOT NULL,
  `address` varchar(50) NOT NULL,
  `email` varchar(25) NOT NULL,
  `firstname` varchar(10) NOT NULL,
  `lastname` varchar(15) DEFAULT NULL,
  `mobile` varchar(15) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `province` varchar(20) NOT NULL,
  `registration_date` datetime NOT NULL,
  `zip_code` varchar(5) DEFAULT NULL,
  `id_city` varchar(3) NOT NULL,
  PRIMARY KEY (`member_code`),
  KEY `FK113DE46B43E932B4` (`id_city`),
  CONSTRAINT `FK113DE46B43E932B4` FOREIGN KEY (`id_city`) REFERENCES `mst_city` (`city_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_member`
--

LOCK TABLES `mst_member` WRITE;
/*!40000 ALTER TABLE `mst_member` DISABLE KEYS */;
INSERT INTO `mst_member` VALUES ('MB13000001','JL. Pangkalan Jati 2 No. 22','sulistiono.adi@gmail.com','ADI','SULISTIONO','85210831766','','Jakarta Timur','2013-07-20 08:20:09','13620','SBY'),('MB13000003','JL. Bekasi Timur 12','j.rengga@gmail.com','JIMMY','RENGGA','32345445342324','','Bekasi','2013-07-20 10:55:08','12312','JAK'),('MB13000029','Tanah Abang 123','awey@gmail.com','AWEY','BUSWAY','234839489738','83847','Jakarta Timur','2013-09-15 12:54:27','12312','JAK'),('MB13000030','pangkalan jati opat','dadang@gmail.com','DADANG','ISWANTO','234789798786','43423434','24ejlkjlkdfjls','2013-09-15 13:45:26','12321','SBY');
/*!40000 ALTER TABLE `mst_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_picture_product`
--

DROP TABLE IF EXISTS `mst_picture_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mst_picture_product` (
  `id_product` varchar(8) NOT NULL,
  `pictures` varchar(50) DEFAULT NULL,
  KEY `FK9CA613FD5C002540` (`id_product`),
  CONSTRAINT `FK9CA613FD5C002540` FOREIGN KEY (`id_product`) REFERENCES `mst_product` (`product_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_picture_product`
--

LOCK TABLES `mst_picture_product` WRITE;
/*!40000 ALTER TABLE `mst_picture_product` DISABLE KEYS */;
INSERT INTO `mst_picture_product` VALUES ('CSOS0001','img/product/jaket_nike_roma.jpg'),('CSOS0002','img/product/curvasud_roma_cloth.jpg'),('CSOS0009','img/product/juve_jersey_away_2014.jpg');
/*!40000 ALTER TABLE `mst_picture_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_product`
--

DROP TABLE IF EXISTS `mst_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mst_product` (
  `product_code` varchar(8) NOT NULL,
  `price` decimal(19,2) NOT NULL,
  `product_info` longtext NOT NULL,
  `product_name` varchar(25) NOT NULL,
  `weight` decimal(19,2) NOT NULL,
  `id_category` varchar(3) NOT NULL,
  PRIMARY KEY (`product_code`),
  KEY `FKCB7DAD3EACD25A39` (`id_category`),
  CONSTRAINT `FKCB7DAD3EACD25A39` FOREIGN KEY (`id_category`) REFERENCES `mst_category_product` (`category_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_product`
--

LOCK TABLES `mst_product` WRITE;
/*!40000 ALTER TABLE `mst_product` DISABLE KEYS */;
INSERT INTO `mst_product` VALUES ('CSOS0001',230000.00,'Jaket Nike Roma Warna Merah Marun','Jaket Nike Roma',1.55,'JAK'),('CSOS0002',120000.00,'Jersey Baru AS Roma','Jersey Roma',0.70,'KAO'),('CSOS0003',200000.00,'Jaket Parasut AS Roma Warna Hitam','Jaket Parasut Roma',0.00,'JAK'),('CSOS0004',80000.00,'Kaos Polo AS Roma Warna Merah Hitam','Polo Roma 11',1.00,'POL'),('CSOS0005',80000.00,'Kaos Polo Roma Warna Putih','Polo Roma Pth',0.00,'POL'),('CSOS0006',135000.00,'Jersey Arsenal','Jersey Arsenal',1.00,'KAO'),('CSOS0007',165000.00,'Jersey Juventus Home','Jersey Juventus',0.00,'KAO'),('CSOS0008',130000.00,'Jersey Manchester United Warna Putih','Jersey MU Pth',0.00,'KAO'),('CSOS0009',190000.00,'Jersey Juve Away (Wasit)','Jersey Juve',1.20,'KAO');
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
INSERT INTO `mst_running_number` VALUES ('ff808181411bcc3b01411bcd50230000','2013-09-14','MEMBER',30),('ff808181411bcc3b01411bd2d13b0003','2013-09-14','PRODUCT',17),('ff80818141223be10141224eeac90000','2013-09-15','BOOKING',3),('ff808181412275c001412276c9c10000','2013-09-15','PAYMENT',3),('ff8081814192941e014192bd25590000','2013-10-07','BOOKING',1);
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
  `id_city` varchar(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK55F6ADE043E932B4` (`id_city`),
  CONSTRAINT `FK55F6ADE043E932B4` FOREIGN KEY (`id_city`) REFERENCES `mst_city` (`city_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_shipping_rate`
--

LOCK TABLES `mst_shipping_rate` WRITE;
/*!40000 ALTER TABLE `mst_shipping_rate` DISABLE KEYS */;
INSERT INTO `mst_shipping_rate` VALUES ('ff8081813ff99204013ff997ee7a0009',12000.00,9000.00,'JAK'),('ff8081813ff99204013ff998196e000a',20000.00,12000.00,'SBY'),('ff8081813ff99204013ff9985a78000b',30000.00,15000.00,'MDN'),('ff8081813ff99204013ff99885d9000c',18000.00,12000.00,'SMG'),('ff8081813ff99204013ff998a9f6000d',18000.00,12000.00,'SOL'),('ff808181411bcc3b01411bd649790005',12000.00,8000.00,'BDG');
/*!40000 ALTER TABLE `mst_shipping_rate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_supplier`
--

DROP TABLE IF EXISTS `mst_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mst_supplier` (
  `code` varchar(3) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `email` varchar(25) DEFAULT NULL,
  `name` varchar(25) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_supplier`
--

LOCK TABLES `mst_supplier` WRITE;
/*!40000 ALTER TABLE `mst_supplier` DISABLE KEYS */;
INSERT INTO `mst_supplier` VALUES ('MTK','','','MOTEKAR KONVEKSI',''),('OVC','','','OVUM CLOTHING','');
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
  `id_member` varchar(10) DEFAULT NULL,
  `id_role` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `FK375DF2F910109592` (`id_member`),
  KEY `FK375DF2F9A8B6832C` (`id_role`),
  CONSTRAINT `FK375DF2F910109592` FOREIGN KEY (`id_member`) REFERENCES `mst_member` (`member_code`),
  CONSTRAINT `FK375DF2F9A8B6832C` FOREIGN KEY (`id_role`) REFERENCES `sec_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_user`
--

LOCK TABLES `sec_user` WRITE;
/*!40000 ALTER TABLE `sec_user` DISABLE KEYS */;
INSERT INTO `sec_user` VALUES ('e1e0a400-e372-11e2-9a58-f04da2694372',1,'86888061b399e74e30eeead8c7aab922','ACTIVE','admin',NULL,'68c8c687-e369-11e2-9a58-f04da2694372'),('ff8081813ff99204013ff9a99bed001b',1,'1b2771fcd66b958f89520628082643bd','ACTIVE','adi123','MB13000001','68c8f6e0-e369-11e2-9a58-f04da2694372'),('ff8081813ffa3557013ffa377f1c0001',1,'d470220a3af0402da69e8320bd01e616','ACTIVE','jimmy123','MB13000003','68c8f6e0-e369-11e2-9a58-f04da2694372'),('ff80818141202e380141202f38f60001',1,'af004a8de32f256fdc1c0e5d392c2aed','ACTIVE','aweybb','MB13000029','68c8f6e0-e369-11e2-9a58-f04da2694372'),('ff808181412036a50141203cfca00000',1,'15ba2c0a3aa85e1c1b1354a6842520ce','ACTIVE','dadang','MB13000030','68c8f6e0-e369-11e2-9a58-f04da2694372');
/*!40000 ALTER TABLE `sec_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trx_booking`
--

DROP TABLE IF EXISTS `trx_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trx_booking` (
  `booking_code` varchar(10) NOT NULL,
  `shipping_address` varchar(100) NOT NULL,
  `shipping_cost` decimal(19,2) NOT NULL,
  `shipping_name` varchar(50) NOT NULL,
  `shipping_phone` varchar(15) NOT NULL,
  `transaction_date` datetime NOT NULL,
  `id_member` varchar(10) NOT NULL,
  PRIMARY KEY (`booking_code`),
  UNIQUE KEY `booking_code` (`booking_code`),
  KEY `FK50D416D410109592` (`id_member`),
  CONSTRAINT `FK50D416D410109592` FOREIGN KEY (`id_member`) REFERENCES `mst_member` (`member_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_booking`
--

LOCK TABLES `trx_booking` WRITE;
/*!40000 ALTER TABLE `trx_booking` DISABLE KEYS */;
INSERT INTO `trx_booking` VALUES ('PO13090002','PANGKALAN JATI OPAT 24EJLKJLKDFJLS SURABAYA 12321',1050000.00,'DADANG  ISWANTO','234789798786','2013-09-15 22:53:17','MB13000030'),('PO13090003','PANGKALAN JATI OPAT 24EJLKJLKDFJLS SURABAYA 12321',18600.00,'DADANG  ISWANTO','234789798786','2013-09-18 22:15:13','MB13000030'),('PO13100001','PANGKALAN JATI OPAT 24EJLKJLKDFJLS SURABAYA 12321',409200.00,'DADANG  ISWANTO','234789798786','2013-10-07 18:46:11','MB13000030');
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
  `id_booking` varchar(10) NOT NULL,
  `id_product` varchar(8) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_booking` (`id_booking`,`id_product`),
  KEY `FK8ADA681C9625F36C` (`id_booking`),
  KEY `FK8ADA681C5C002540` (`id_product`),
  CONSTRAINT `FK8ADA681C5C002540` FOREIGN KEY (`id_product`) REFERENCES `mst_product` (`product_code`),
  CONSTRAINT `FK8ADA681C9625F36C` FOREIGN KEY (`id_booking`) REFERENCES `trx_booking` (`booking_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_booking_detail`
--

LOCK TABLES `trx_booking_detail` WRITE;
/*!40000 ALTER TABLE `trx_booking_detail` DISABLE KEYS */;
INSERT INTO `trx_booking_detail` VALUES ('ff808181412252b70141225378dc0000',230000.00,50,11500000.00,'PO13090002','CSOS0001'),('ff808181412252b70141225378df0001',135000.00,10,1350000.00,'PO13090002','CSOS0006'),('ff8081814131a305014131a3b3460000',230000.00,1,230000.00,'PO13090003','CSOS0001'),('ff8081814192941e014192bd25ee0001',230000.00,22,5060000.00,'PO13100001','CSOS0001');
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
  `id_product` varchar(8) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `date_of_mutation` (`date_of_mutation`,`id_product`),
  KEY `FK844311D85C002540` (`id_product`),
  CONSTRAINT `FK844311D85C002540` FOREIGN KEY (`id_product`) REFERENCES `mst_product` (`product_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_change_of_stock`
--

LOCK TABLES `trx_change_of_stock` WRITE;
/*!40000 ALTER TABLE `trx_change_of_stock` DISABLE KEYS */;
INSERT INTO `trx_change_of_stock` VALUES ('ff808181412275c00141227b22450001','2013-10-04',53,0,50,103,'CSOS0001'),('ff808181412275c00141227b22650002','2013-10-04',-10,0,10,0,'CSOS0006'),('ff808181412b0c5301412b104bbc0004','2013-10-04',2,0,0,2,'CSOS0002'),('ff80818141fe3c660141fe3dc3620003','2013-10-01',122,0,0,122,'CSOS0003'),('ff80818141fe3c660141fe3dc38c0004','2013-10-01',10,0,0,10,'CSOS0007');
/*!40000 ALTER TABLE `trx_change_of_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trx_invoice`
--

DROP TABLE IF EXISTS `trx_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trx_invoice` (
  `invoice_number` varchar(10) NOT NULL,
  `transaction_date` datetime NOT NULL,
  `id_booking` varchar(10) NOT NULL,
  PRIMARY KEY (`invoice_number`),
  UNIQUE KEY `invoice_number` (`invoice_number`),
  KEY `FKC1CF51A89625F36C` (`id_booking`),
  CONSTRAINT `FKC1CF51A89625F36C` FOREIGN KEY (`id_booking`) REFERENCES `trx_booking` (`booking_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_invoice`
--

LOCK TABLES `trx_invoice` WRITE;
/*!40000 ALTER TABLE `trx_invoice` DISABLE KEYS */;
INSERT INTO `trx_invoice` VALUES ('IN13090002','2013-09-15 23:36:37','PO13090002');
/*!40000 ALTER TABLE `trx_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trx_payment`
--

DROP TABLE IF EXISTS `trx_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trx_payment` (
  `payment_code` varchar(10) NOT NULL,
  `account_name` varchar(35) NOT NULL,
  `approve_date` datetime DEFAULT NULL,
  `approved` tinyint(1) NOT NULL,
  `payment_date` date NOT NULL,
  `transaction_date` datetime NOT NULL,
  `transfer_amount` decimal(19,2) NOT NULL,
  `id_booking` varchar(10) NOT NULL,
  PRIMARY KEY (`payment_code`),
  KEY `FK1E1540819625F36C` (`id_booking`),
  CONSTRAINT `FK1E1540819625F36C` FOREIGN KEY (`id_booking`) REFERENCES `trx_booking` (`booking_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_payment`
--

LOCK TABLES `trx_payment` WRITE;
/*!40000 ALTER TABLE `trx_payment` DISABLE KEYS */;
INSERT INTO `trx_payment` VALUES ('PA13090001','DADANG','2013-09-15 23:36:36',1,'2013-09-15','2013-09-15 23:31:52',13900000.00,'PO13090002'),('PA13090003','dadang',NULL,0,'2013-09-18','2013-09-18 22:39:52',248000.00,'PO13090003');
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
  `id_supplier` varchar(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBA11C5E6D564D476` (`id_supplier`),
  CONSTRAINT `FKBA11C5E6D564D476` FOREIGN KEY (`id_supplier`) REFERENCES `mst_supplier` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_purchase`
--

LOCK TABLES `trx_purchase` WRITE;
/*!40000 ALTER TABLE `trx_purchase` DISABLE KEYS */;
INSERT INTO `trx_purchase` VALUES ('ff808181412b0c5301412b0ee7fb0000','2013-09-17 15:34:59','123','MTK'),('ff808181412b0c5301412b104b490002','2013-09-17 15:36:30','123','OVC'),('ff80818141326c1c0141326dda590000','2013-09-19 01:56:02','PURC000094','MTK'),('ff80818141fe3c660141fe3dc3000000','2013-10-28 15:46:02','123456','OVC');
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
  `id_product` varchar(8) NOT NULL,
  `id_purchase` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_purchase` (`id_purchase`,`id_product`),
  KEY `FKEE7F00CA5C002540` (`id_product`),
  KEY `FKEE7F00CAF5E7E88` (`id_purchase`),
  CONSTRAINT `FKEE7F00CA5C002540` FOREIGN KEY (`id_product`) REFERENCES `mst_product` (`product_code`),
  CONSTRAINT `FKEE7F00CAF5E7E88` FOREIGN KEY (`id_purchase`) REFERENCES `trx_purchase` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_purchase_detail`
--

LOCK TABLES `trx_purchase_detail` WRITE;
/*!40000 ALTER TABLE `trx_purchase_detail` DISABLE KEYS */;
INSERT INTO `trx_purchase_detail` VALUES ('ff808181412b0c5301412b0ee8080001',33000.00,3,99000.00,'CSOS0001','ff808181412b0c5301412b0ee7fb0000'),('ff808181412b0c5301412b104b4b0003',23423423.00,2,46846846.00,'CSOS0002','ff808181412b0c5301412b104b490002'),('ff80818141326c1c0141326dda640001',89000.00,100,8900000.00,'CSOS0001','ff80818141326c1c0141326dda590000'),('ff80818141fe3c660141fe3dc3120001',100000.00,122,12200000.00,'CSOS0003','ff80818141fe3c660141fe3dc3000000'),('ff80818141fe3c660141fe3dc3140002',80000.00,10,800000.00,'CSOS0007','ff80818141fe3c660141fe3dc3000000');
/*!40000 ALTER TABLE `trx_purchase_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trx_testimoni_product`
--

DROP TABLE IF EXISTS `trx_testimoni_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trx_testimoni_product` (
  `id` varchar(36) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `testimoni_date` datetime NOT NULL,
  `id_member` varchar(10) NOT NULL,
  `id_product` varchar(8) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK623ED5DF10109592` (`id_member`),
  KEY `FK623ED5DF5C002540` (`id_product`),
  CONSTRAINT `FK623ED5DF10109592` FOREIGN KEY (`id_member`) REFERENCES `mst_member` (`member_code`),
  CONSTRAINT `FK623ED5DF5C002540` FOREIGN KEY (`id_product`) REFERENCES `mst_product` (`product_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_testimoni_product`
--

LOCK TABLES `trx_testimoni_product` WRITE;
/*!40000 ALTER TABLE `trx_testimoni_product` DISABLE KEYS */;
INSERT INTO `trx_testimoni_product` VALUES ('ff808181413214e301413216b7da0000','barangnya bagus coy....','2013-09-19 00:20:51','MB13000030','CSOS0001'),('ff808181413232790141323af2fd0000','ok ok ok','2013-09-19 01:00:26','MB13000030','CSOS0001'),('ff8081814132327901413247a7b70001','test redirect','2013-09-19 01:14:18','MB13000030','CSOS0001'),('ff808181413232790141324a3dfb0002','test sukses','2013-09-19 01:17:08','MB13000030','CSOS0001'),('ff808181413232790141324be77c0003','hore.. hore...','2013-09-19 01:18:57','MB13000030','CSOS0001'),('ff808181413232790141324c1d710004','opo broo...','2013-09-19 01:19:11','MB13000030','CSOS0001'),('ff808181413254530141325698960000','opo iki','2013-09-19 01:30:37','MB13000029','CSOS0003');
/*!40000 ALTER TABLE `trx_testimoni_product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-11-01 18:31:57
