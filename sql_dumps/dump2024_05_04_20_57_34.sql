-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_wastemanagement
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `companies` (
  `companyid` bigint NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL,
  `customerid` bigint DEFAULT NULL,
  PRIMARY KEY (`companyid`),
  UNIQUE KEY `UK_6hna7oxts8k53dpcjli5gjbh5` (`customerid`),
  CONSTRAINT `FKd505gnxumq0inlqqp40m6ga7h` FOREIGN KEY (`customerid`) REFERENCES `customers` (`customerid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companies`
--

LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
INSERT INTO `companies` VALUES (1,'ABC Corp',NULL),(2,'ABC Corp',3),(3,'DIM Corp',5),(4,'DIM Corp',6),(5,'DIM Corp',7),(6,'DIM Corp',1),(7,'IZA Corp',9),(8,'Dela Corp',10);
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_product`
--

DROP TABLE IF EXISTS `customer_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_product` (
  `productid` bigint NOT NULL,
  `customerid` bigint NOT NULL,
  PRIMARY KEY (`customerid`,`productid`),
  KEY `FKi7e9g8fi4i8o8e0clbcxhiiqq` (`productid`),
  CONSTRAINT `FKi7e9g8fi4i8o8e0clbcxhiiqq` FOREIGN KEY (`productid`) REFERENCES `products` (`productid`),
  CONSTRAINT `FKmpxidr5st54bbhrdpb05rejwk` FOREIGN KEY (`customerid`) REFERENCES `customers` (`customerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_product`
--

LOCK TABLES `customer_product` WRITE;
/*!40000 ALTER TABLE `customer_product` DISABLE KEYS */;
INSERT INTO `customer_product` VALUES (1,2),(1,3),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(2,8),(2,10),(3,1),(3,6),(3,9);
/*!40000 ALTER TABLE `customer_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `customerid` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `customer_type` enum('COMPANY','INDIVIDUAL') DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `municipalityid` bigint DEFAULT NULL,
  PRIMARY KEY (`customerid`),
  KEY `FK3v44gpkibcyg0pldfayylyi1u` (`municipalityid`),
  CONSTRAINT `FK3v44gpkibcyg0pldfayylyi1u` FOREIGN KEY (`municipalityid`) REFERENCES `municipalities` (`municipalityid`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'123 Main St','COMPANY','john.doe@example.com','Dim','Iza','123-7890',1),(8,'123 Main St','INDIVIDUAL','john.doe@example.com','John','Doe','123-456-7890',1),(9,'123 Main St','COMPANY','aria.iza@example.com','Aria','Izairi','123-456-7890',1),(10,'123 Main St','COMPANY','john.doe@example.com','Mandela','Wat','123-456-7890',1),(11,'123 Main St','INDIVIDUAL','john.doe@example.com','Mandela','Wat','123-456-7890',1),(12,'Chegrane, str. 300','INDIVIDUAL','izairi.q@gmail.com','Qëndrim','Izairi','076288808',2),(13,'Chegrane, str. 300','INDIVIDUAL','izairi.q@gmail.com','Qëndrim','Izairi','076288808',1),(14,'Chegrane, str. 300','INDIVIDUAL','izairi.q@gmail.com','Qëndrim','Izairi','076288808',1),(15,'Derenyvka','INDIVIDUAL','izairi.q@gmail.com','Bohdana','Stelmashchuk','0988287762',2),(16,'Derenyvka','INDIVIDUAL','izairi.q@gmail.com','Bohdana','Stelmashchuk','0988287762',1),(17,'Derenyvka','INDIVIDUAL','izairi.q@gmail.com','Bohdana','Stelmashchuk','0988287762',2),(18,'123 Main St','INDIVIDUAL','john.doe@example.com','John','Doe','123-456-7890',1),(19,'123 Main St','INDIVIDUAL','john.doe@example.com','John','Doe','123-456-7890',1),(20,'Derenyvka','INDIVIDUAL','izairi.q@gmail.com','Bohdana','Stelmashchuk','0988287762',2),(21,'Chegrane, str. 300','INDIVIDUAL','izairi.q@gmail.com','Qëndrim','Izairi','076288808',2),(22,'Chegrane, str. 300','INDIVIDUAL','izairi.q@gmail.com','Qëndrim','Izairi','076288808',1),(23,'Chegrane, str. 300','INDIVIDUAL','izairi.q@gmail.com','Qëndrim','Izairi','076288808',2),(24,'Chegrane, str. 300','INDIVIDUAL','izairi.q@gmail.com','Qëndrim','Izairi','076288808',1),(25,'Chegrane, str. 300','INDIVIDUAL','izairi.q@gmail.com','Qëndrim','Izairi','076288808',2),(26,'Chegrane, str. 300','INDIVIDUAL','izairi.q@gmail.com','Qëndrim','Izairi','076288808',1);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `employeeid` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`employeeid`),
  UNIQUE KEY `UK3gqbimdf7fckjbwt1kcud141m` (`username`),
  UNIQUE KEY `UKj9xgmd0ya5jmus09o0b8pqrpb` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'admin@wm.com',NULL,NULL,'$2a$10$PIr.fEdnVjXIAkwFQDZE/uHrGsj2ZT2dt/I9MsBvoVD3vzAGIAZ9i','admin');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_items`
--

DROP TABLE IF EXISTS `invoice_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_items` (
  `invoice_itemid` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `invoiceid` bigint DEFAULT NULL,
  `productid` bigint DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`invoice_itemid`),
  KEY `FK9gfitgka8rx1v3vl8raau75hv` (`invoiceid`),
  KEY `FK3asaxpep5l01vlp6r3erwa906` (`productid`),
  CONSTRAINT `FK3asaxpep5l01vlp6r3erwa906` FOREIGN KEY (`productid`) REFERENCES `products` (`productid`),
  CONSTRAINT `FK9gfitgka8rx1v3vl8raau75hv` FOREIGN KEY (`invoiceid`) REFERENCES `invoices` (`invoiceid`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_items`
--

LOCK TABLES `invoice_items` WRITE;
/*!40000 ALTER TABLE `invoice_items` DISABLE KEYS */;
INSERT INTO `invoice_items` VALUES (1,1,350,NULL,1,350),(2,1,29.99,NULL,2,29.99),(3,1,700,14,3,700),(4,1,350,14,1,350),(5,1,29.99,14,2,29.99),(6,1,350,15,1,350),(7,1,29.99,15,2,29.99),(8,1,700,15,3,700),(9,1,350,16,1,350),(10,1,29.99,16,2,29.99),(11,1,700,16,3,700),(12,1,350,17,1,350),(13,1,29.99,17,2,29.99),(14,1,700,17,3,700),(15,1,350,18,1,350),(16,1,29.99,18,2,29.99),(17,1,350,19,1,350),(18,1,29.99,19,2,29.99),(19,1,350,20,1,350),(20,1,29.99,20,2,29.99),(21,1,350,21,1,350),(22,1,29.99,21,2,29.99),(23,1,350,22,1,350),(24,1,29.99,22,2,29.99),(25,1,350,23,1,350),(26,1,29.99,23,2,29.99),(27,1,350,24,1,350),(28,1,350,25,1,350),(29,1,29.99,25,2,29.99),(30,1,350,26,1,350),(31,1,350,27,1,350),(32,1,29.99,27,2,29.99),(34,1,350,29,1,350),(35,1,350,30,1,350),(36,1,29.99,30,2,29.99),(37,1,350,31,1,350),(38,1,700,31,3,700),(39,1,29.99,32,2,29.99),(40,1,700,32,3,700),(41,1,350,33,1,350),(42,1,700,33,3,700),(43,1,29.99,34,2,29.99),(44,1,700,34,3,700),(45,1,350,35,1,350),(46,1,29.99,35,2,29.99),(47,1,350,36,1,350),(48,1,29.99,36,2,29.99),(49,1,350,37,1,350),(50,3,89.97,37,2,29.99),(51,1,350,38,1,350),(52,1,29.99,38,2,29.99);
/*!40000 ALTER TABLE `invoice_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoices` (
  `invoiceid` bigint NOT NULL AUTO_INCREMENT,
  `invoice_date` datetime(6) DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `customerid` bigint DEFAULT NULL,
  `employeeid` bigint DEFAULT NULL,
  `month` varchar(255) DEFAULT NULL,
  `municipalityid` bigint DEFAULT NULL,
  `is_paid` bit(1) DEFAULT NULL,
  PRIMARY KEY (`invoiceid`),
  KEY `FKnud02aeumeene9e1uxj1ee5bb` (`customerid`),
  KEY `FKbc7sc73ecpa30nyx3376vpc4n` (`employeeid`),
  KEY `FKf5erne9tuoqeqdwqj8dq9krou` (`municipalityid`),
  CONSTRAINT `FKbc7sc73ecpa30nyx3376vpc4n` FOREIGN KEY (`employeeid`) REFERENCES `employees` (`employeeid`),
  CONSTRAINT `FKf5erne9tuoqeqdwqj8dq9krou` FOREIGN KEY (`municipalityid`) REFERENCES `municipalities` (`municipalityid`),
  CONSTRAINT `FKnud02aeumeene9e1uxj1ee5bb` FOREIGN KEY (`customerid`) REFERENCES `customers` (`customerid`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES (1,NULL,1400,1,NULL,NULL,NULL,_binary '\0'),(2,NULL,1400,1,NULL,NULL,NULL,_binary '\0'),(3,NULL,1400,1,1,'2024-02-17 01:00:00.000000',1,_binary '\0'),(4,'2024-02-18 02:03:02.329000',1400,1,1,'2024-02-17 01:00:00.000000',1,_binary ''),(5,'2024-02-19 00:38:47.161000',59.98,8,1,'2024-02-17 01:00:00.000000',1,_binary ''),(6,'2024-02-19 22:47:11.819000',1400,9,1,'2024-02-17 01:00:00.000000',1,_binary '\0'),(7,'2024-02-19 22:53:15.416000',1400,9,1,'2024-02-19 01:00:00.000000',1,_binary '\0'),(8,'2024-02-19 22:53:28.847000',1400,9,1,'2024-02-11 01:00:00.000000',1,_binary '\0'),(9,'2024-02-19 22:54:56.338000',1400,9,1,'2024-02-11 01:00:00.000000',1,_binary '\0'),(10,'2024-02-19 22:57:24.664000',700,9,1,'2024-01-11 01:00:00.000000',1,_binary '\0'),(11,'2024-02-19 23:25:21.567000',1779.99,9,1,'2024-01-11 01:00:00.000000',1,_binary '\0'),(12,'2024-02-20 12:18:19.927000',1079.99,9,1,'2024-01-10 01:00:00.000000',1,_binary '\0'),(13,'2024-02-20 13:11:47.579000',1079.99,9,1,'2024-01-10 01:00:00.000000',1,_binary '\0'),(14,'2024-02-20 13:24:09.252000',1079.99,9,1,'2024-01-13 01:00:00.000000',1,_binary '\0'),(15,'2024-02-20 16:33:53.594000',1079.99,11,1,'2024-02-17 01:00:00.000000',2,_binary '\0'),(16,'2024-02-21 14:14:46.594000',1079.99,11,1,'2024-02-14 01:00:00.000000',2,_binary '\0'),(17,'2024-02-21 19:38:49.795000',1079.99,11,1,'2024-01',2,_binary '\0'),(18,'2024-02-24 19:35:05.449000',729.99,11,1,NULL,2,_binary '\0'),(19,'2024-02-24 19:36:42.568000',379.99,11,1,NULL,2,_binary '\0'),(20,'2024-02-24 19:37:18.462000',379.99,11,1,NULL,2,_binary '\0'),(21,'2024-02-24 19:37:45.867000',379.99,11,1,NULL,2,_binary '\0'),(22,'2024-02-25 00:21:35.246000',379.99,11,1,NULL,2,_binary '\0'),(23,'2024-02-25 00:21:35.304000',379.99,11,1,NULL,2,_binary '\0'),(24,'2024-02-25 00:22:31.714000',350,11,1,NULL,2,_binary '\0'),(25,'2024-02-25 00:22:31.726000',379.99,11,1,NULL,2,_binary '\0'),(26,'2024-02-25 00:55:27.768000',350,11,1,'2023-12',2,_binary '\0'),(27,'2024-02-25 00:55:27.820000',379.99,11,1,'2024-01',2,_binary '\0'),(29,'2024-02-25 01:07:37.963000',350,11,1,'2023-10',2,_binary '\0'),(30,'2024-02-25 01:07:37.979000',379.99,11,1,'2024-06',2,_binary '\0'),(31,'2024-02-25 14:53:28.789000',1050,10,1,NULL,2,_binary '\0'),(32,'2024-02-25 14:53:28.851000',729.99,11,1,NULL,1,_binary '\0'),(33,'2024-03-04 23:52:28.190000',1050,10,1,NULL,2,_binary '\0'),(34,'2024-03-04 23:52:28.245000',729.99,11,1,NULL,1,_binary '\0'),(35,'2024-03-04 23:54:19.007000',379.99,11,1,'2021-01',2,_binary '\0'),(36,'2024-03-04 23:54:19.020000',379.99,11,1,'2020-02',2,_binary '\0'),(37,'2024-03-04 23:55:14.734000',439.97,11,1,'2020-01',2,_binary '\0'),(38,'2024-03-04 23:55:14.747000',379.99,11,1,'2019-02',2,_binary '\0');
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `municipalities`
--

DROP TABLE IF EXISTS `municipalities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `municipalities` (
  `municipalityid` bigint NOT NULL AUTO_INCREMENT,
  `municipality_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`municipalityid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `municipalities`
--

LOCK TABLES `municipalities` WRITE;
/*!40000 ALTER TABLE `municipalities` DISABLE KEYS */;
INSERT INTO `municipalities` VALUES (1,'Çegran'),(2,'Gostivar');
/*!40000 ALTER TABLE `municipalities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `productid` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `municipalityid` bigint DEFAULT NULL,
  PRIMARY KEY (`productid`),
  KEY `FKedahd93prkmc4cm20vkvuisaa` (`municipalityid`),
  CONSTRAINT `FKedahd93prkmc4cm20vkvuisaa` FOREIGN KEY (`municipalityid`) REFERENCES `municipalities` (`municipalityid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Kosh plehrash prej 120 litrave',350,'120L',1),(2,'This is a sample product description.',29.99,'Sample Product',1),(3,'Kosh plehrash prej 240 litrave',700,'240L',1);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` enum('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_USER'),(2,'ROLE_MODERATOR'),(3,'ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FK9tdu6x1oj5wsxfpvunqv5gccy` FOREIGN KEY (`user_id`) REFERENCES `employees` (`employeeid`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1),(1,2),(1,3);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-04 20:57:35
