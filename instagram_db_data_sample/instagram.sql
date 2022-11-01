-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: instagram
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `post_id` int NOT NULL,
  `owner_id` int NOT NULL,
  `comment` varchar(500) NOT NULL,
  `reply_id` int DEFAULT NULL,
  `is_deleted` tinyint NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`owner_id`),
  KEY `reply_id_idx` (`reply_id`),
  KEY `fk_post_id_post_idx` (`post_id`),
  CONSTRAINT `fk_post_id_post` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `fk_reply_id_user_id` FOREIGN KEY (`reply_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `fk_user_id_reply_id` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (52,113,12,'deleted at2022-10-31T11:02:31.641372119',NULL,1,'2022-10-31 07:22:09'),(53,113,14,'deleted at2022-10-31T11:02:31.641506771',52,1,'2022-10-31 07:27:53'),(54,137,14,'deleted at2022-10-31T19:19:13.274337100',NULL,1,'2022-10-31 09:25:28'),(55,140,14,'deleted at2022-10-31T19:20:17.747642700',NULL,1,'2022-10-31 16:20:22'),(56,140,13,'deleted at2022-10-31T19:20:17.747642700',NULL,1,'2022-10-31 16:21:46'),(57,140,13,'deleted at2022-10-31T19:20:17.747642700',55,1,'2022-10-31 16:31:06'),(58,149,23,'deleted at2022-11-01T15:26:14.776677100',NULL,1,'2022-11-01 12:58:34'),(59,149,13,'deleted at2022-11-01T15:26:14.782555100',58,1,'2022-11-01 13:01:42'),(60,149,23,'deleted at2022-11-01T15:26:14.782555100',59,1,'2022-11-01 13:06:10'),(61,149,23,'deleted at2022-11-01T15:26:14.782555100',NULL,1,'2022-11-01 13:08:49');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `following`
--

DROP TABLE IF EXISTS `following`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `following` (
  `user_id` int NOT NULL,
  `follower_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`follower_id`),
  UNIQUE KEY `user_follower_unique` (`user_id`,`follower_id`),
  KEY `id_idx` (`follower_id`),
  CONSTRAINT `following_user_fk` FOREIGN KEY (`follower_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_following_user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `following`
--

LOCK TABLES `following` WRITE;
/*!40000 ALTER TABLE `following` DISABLE KEYS */;
INSERT INTO `following` VALUES (16,15);
/*!40000 ALTER TABLE `following` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashtags`
--

DROP TABLE IF EXISTS `hashtags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hashtags` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tag_name_UNIQUE` (`tag_name`),
  KEY `tag_name` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtags`
--

LOCK TABLES `hashtags` WRITE;
/*!40000 ALTER TABLE `hashtags` DISABLE KEYS */;
INSERT INTO `hashtags` VALUES (26,' #wdalirabotaiwow '),(22,'#amazing'),(24,'#moje'),(28,'#moje da... '),(21,'#story'),(27,'#test'),(23,'#testaa'),(20,'#wow'),(25,'amazing comment '),(29,'amazing comment 2 ');
/*!40000 ALTER TABLE `hashtags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashtags_posts`
--

DROP TABLE IF EXISTS `hashtags_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hashtags_posts` (
  `tag_id` int NOT NULL,
  `post_hashtag_id` int NOT NULL,
  PRIMARY KEY (`post_hashtag_id`,`tag_id`),
  UNIQUE KEY `fk_hashtag_post_post_unique` (`post_hashtag_id`,`tag_id`),
  KEY `post_id_idx` (`post_hashtag_id`),
  KEY `tag_id_idx` (`tag_id`),
  CONSTRAINT `fk_id_post` FOREIGN KEY (`post_hashtag_id`) REFERENCES `post` (`id`),
  CONSTRAINT `fk_tag_id_post` FOREIGN KEY (`tag_id`) REFERENCES `hashtags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtags_posts`
--

LOCK TABLES `hashtags_posts` WRITE;
/*!40000 ALTER TABLE `hashtags_posts` DISABLE KEYS */;
INSERT INTO `hashtags_posts` VALUES (20,113),(22,113),(20,114),(21,115),(20,122),(20,123),(20,124),(20,125),(20,126),(20,127),(20,130),(20,131),(20,132),(20,133),(21,134),(21,135),(20,136),(20,137),(23,137),(20,138),(20,139),(20,140),(24,140),(20,141),(20,142),(20,143),(25,149),(26,149),(27,149),(28,149),(29,149);
/*!40000 ALTER TABLE `hashtags_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like_comments`
--

DROP TABLE IF EXISTS `like_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `like_comments` (
  `user_id` int NOT NULL,
  `comment_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`comment_id`),
  UNIQUE KEY `user_id_like_id_unique` (`user_id`,`comment_id`),
  KEY `like_id_idx` (`comment_id`),
  KEY `user_id_idx` (`user_id`) /*!80000 INVISIBLE */,
  CONSTRAINT `fk_comment_user` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `fk_user_comment` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like_comments`
--

LOCK TABLES `like_comments` WRITE;
/*!40000 ALTER TABLE `like_comments` DISABLE KEYS */;
INSERT INTO `like_comments` VALUES (14,54),(13,56),(13,57);
/*!40000 ALTER TABLE `like_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like_post`
--

DROP TABLE IF EXISTS `like_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `like_post` (
  `user_id` int NOT NULL,
  `post_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`post_id`),
  UNIQUE KEY `post_id_user_id_unique` (`user_id`,`post_id`),
  UNIQUE KEY `user_post_unique` (`user_id`,`post_id`),
  KEY `post_id_idx` (`post_id`) /*!80000 INVISIBLE */,
  CONSTRAINT `fk_post_like_user` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `fk_user_like_post` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like_post`
--

LOCK TABLES `like_post` WRITE;
/*!40000 ALTER TABLE `like_post` DISABLE KEYS */;
INSERT INTO `like_post` VALUES (14,114),(14,140),(16,114),(23,150);
/*!40000 ALTER TABLE `like_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` VALUES (5,'fail'),(10,'Kazanluk'),(13,'nowhere'),(12,'Pazardjik'),(4,'Plovdiv'),(6,'probaly fail'),(3,'Sofia'),(9,'Varna');
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_tag`
--

DROP TABLE IF EXISTS `person_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person_tag` (
  `user_tag_id` int NOT NULL,
  `post_persontag_id` int NOT NULL,
  PRIMARY KEY (`user_tag_id`,`post_persontag_id`),
  KEY `user_id_idx` (`user_tag_id`),
  KEY `post_id_idx` (`post_persontag_id`),
  CONSTRAINT `post_id_fk` FOREIGN KEY (`post_persontag_id`) REFERENCES `post` (`id`),
  CONSTRAINT `user_id_fk` FOREIGN KEY (`user_tag_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_tag`
--

LOCK TABLES `person_tag` WRITE;
/*!40000 ALTER TABLE `person_tag` DISABLE KEYS */;
INSERT INTO `person_tag` VALUES (12,113),(12,114),(12,115),(12,122),(12,123),(12,124),(12,125),(12,126),(12,127),(12,131),(12,132),(12,133),(12,134),(12,135);
/*!40000 ALTER TABLE `person_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `caption` varchar(2200) DEFAULT NULL,
  `location_id` int DEFAULT NULL,
  `expiration_time` datetime DEFAULT NULL,
  `is_deleted` tinyint NOT NULL,
  `created_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `post_user_fk_idx` (`user_id`),
  KEY `post_location_idx` (`location_id`),
  CONSTRAINT `post_location_fk` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `post_user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (113,12,'del-2022-10-31T11:02:31.467907',3,NULL,1,'2022-10-28 10:11:48'),(114,14,'amazing #wow @user1 test',3,NULL,0,'2022-10-31 09:08:36'),(115,12,'del-2022-10-31T11:02:31.467907',4,'2022-11-01 09:20:09',1,'2022-10-31 09:20:09'),(116,14,'fail',5,NULL,0,'2022-10-31 09:33:51'),(117,14,'fail',5,NULL,0,'2022-10-31 09:35:15'),(118,14,'fail',5,NULL,0,'2022-10-31 09:37:43'),(119,14,'probaly fail',6,NULL,0,'2022-10-31 09:39:39'),(122,16,'amazing #wow @user1 test',3,NULL,0,'2022-10-31 10:26:13'),(123,16,'amazing #wow @user1 test',3,NULL,0,'2022-10-31 10:26:16'),(124,16,'amazing #wow @user1 test',3,NULL,0,'2022-10-31 10:26:17'),(125,16,'amazing #wow @user1 test',3,NULL,0,'2022-10-31 10:26:18'),(126,16,'amazing #wow @user1 test',3,NULL,0,'2022-10-31 10:26:19'),(127,16,'amazing #wow @user1 test',3,NULL,0,'2022-10-31 10:26:20'),(130,16,'#wow @hello amazing',9,NULL,0,'2022-10-31 10:28:34'),(131,16,'amazing #wow @user1 test',3,NULL,0,'2022-10-31 10:39:13'),(132,16,'amazing #wow @user1 test',10,NULL,0,'2022-10-31 10:41:05'),(133,16,'amazing #wow @user1 test',10,NULL,0,'2022-10-31 10:42:26'),(134,16,'#story @user1 ',4,'2022-11-01 10:42:52',0,'2022-10-31 10:42:52'),(135,15,'#story @user1 ',4,'2022-11-01 10:45:51',0,'2022-10-31 10:45:51'),(136,12,'amazing #wow @user1 test',10,NULL,0,'2022-10-31 11:03:01'),(137,14,'amazing #wow @user1 test',10,NULL,0,'2022-10-31 11:24:27'),(138,13,'amazing #wow @user1 ',12,NULL,0,'2022-10-31 18:05:59'),(139,13,'amazing #wow @user1 ',12,NULL,0,'2022-10-31 18:17:50'),(140,14,'del-2022-10-31T19:20:17.745640',12,NULL,0,'2022-10-31 18:18:52'),(141,13,'amazing #wow @user1 ',12,NULL,0,'2022-10-31 18:25:41'),(142,13,'amazing #wow @user1 ',12,NULL,0,'2022-10-31 18:26:01'),(143,13,'amazing #wow @user1 ',12,NULL,0,'2022-10-31 18:28:06'),(149,23,'del- 2022-11-01 15:26:14',12,NULL,0,'2022-11-01 14:55:58'),(150,23,'yea #baby',13,'2022-11-02 15:31:10',0,'2022-11-01 15:31:10');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_content`
--

DROP TABLE IF EXISTS `post_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_content` (
  `id` int NOT NULL AUTO_INCREMENT,
  `media` varchar(300) NOT NULL,
  `post_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `media_post_unique` (`media`,`post_id`),
  KEY `post_id_idx` (`post_id`),
  CONSTRAINT `post_id_maiak` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_content`
--

LOCK TABLES `post_content` WRITE;
/*!40000 ALTER TABLE `post_content` DISABLE KEYS */;
INSERT INTO `post_content` VALUES (109,'src/main/resources/static/post/128978784521414.png',114),(108,'src/main/resources/static/post/163826942544212.png',113),(111,'src/main/resources/static/post/594665139669216.png',122),(112,'src/main/resources/static/post/594965500164216.png',123),(113,'src/main/resources/static/post/595080169671716.png',124),(114,'src/main/resources/static/post/595180690460716.png',125),(115,'src/main/resources/static/post/595279772233816.png',126),(116,'src/main/resources/static/post/595385892498516.png',127),(117,'src/main/resources/static/post/608750489246116.png',130),(118,'src/main/resources/static/post/672647846298916.png',131),(119,'src/main/resources/static/post/683807168801816.png',132),(120,'src/main/resources/static/post/691893301154116.png',133),(123,'src/main/resources/static/post/815414865017412.png',136),(124,'src/main/resources/static/post/943997986195114.png',137),(110,'src/main/resources/static/story/198242617833112.png',115),(121,'src/main/resources/static/story/694572353171216.png',134),(122,'src/main/resources/static/story/712481231168215.png',135),(125,'src\\main\\resources\\static\\post\\439078855950013.jpg',143),(126,'src\\main\\resources\\static\\story\\1667420776869923.jpg',150),(127,'src\\main\\resources\\static\\story\\1671066588570023.jpg',150),(128,'src\\main\\resources\\static\\story\\1671066697669923.jpg',150),(129,'src\\main\\resources\\static\\story\\1671066810390023.jpg',150),(131,'src\\main\\resources\\static\\story\\1673506395490023.jpg',150),(132,'src\\main\\resources\\static\\story\\1673506521440023.jpg',150),(133,'src\\main\\resources\\static\\story\\1673506648430023.jpg',150),(134,'src\\main\\resources\\static\\story\\1673506801290023.jpg',150),(135,'src\\main\\resources\\static\\story\\1677709101070023.jpg',150),(136,'src\\main\\resources\\static\\story\\1677709234229923.jpg',150),(137,'src\\main\\resources\\static\\story\\1677709356910023.jpg',150),(138,'src\\main\\resources\\static\\story\\1677709461830023.jpg',150);
/*!40000 ALTER TABLE `post_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `private_message`
--

DROP TABLE IF EXISTS `private_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `private_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sender_id` int NOT NULL,
  `receiver_id` int NOT NULL,
  `text` varchar(500) NOT NULL,
  `send_date` datetime NOT NULL,
  `is_seen` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sender_id_idx` (`sender_id`),
  KEY `receiver_id_idx` (`receiver_id`),
  CONSTRAINT `receiver_id` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`),
  CONSTRAINT `sender_id` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `private_message`
--

LOCK TABLES `private_message` WRITE;
/*!40000 ALTER TABLE `private_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `private_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `activity_status` tinyint DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `username` varchar(30) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `bio` varchar(200) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `phone_num` varchar(45) DEFAULT NULL,
  `gender` varchar(15) DEFAULT NULL,
  `profile_picture` varchar(200) DEFAULT NULL,
  `is_verified` tinyint NOT NULL,
  `password` varchar(100) NOT NULL,
  `is_banned` tinyint NOT NULL,
  `is_deleted` tinyint NOT NULL,
  `created_at` datetime NOT NULL,
  `is_deactivated` tinyint DEFAULT NULL,
  `verification_code` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`) /*!80000 INVISIBLE */,
  KEY `username` (`username`),
  KEY `email` (`email`),
  KEY `verification_code` (`verification_code`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (12,0,'del-2022-10-31T11:02:31.467907','del-2022-10-31T11:02:31.467907','','','del-2022-10-31T11:02:31.467907','1900-01-01','del-2022-10-31T11:02:31.467907','del-2022-10-31','del-2022-10-31T11:02:31.467907',0,'$2a$10$/Pinxm3HbrINY2W3sEw63.nCB8y6a7NrwSUAbe8vij1XAJsxj.OQi',0,1,'2022-10-28 09:41:27',1,'0XT4JQJyJsd5y'),(13,0,'user1@use12.com','user12','User','One','Hello This IS user 1','2000-06-06',NULL,NULL,'src/main/resources/static/pfp/988196439791-13-.png',1,'$2a$10$5EsQLQkTLlZ8S1ay3d8k3OESZ83iBQ3pCRgQ50BLBwrw73X2xuIzO',0,0,'2022-10-28 09:42:39',0,'2yDaXU8NVmD4GrU9jHDY1nywiazBhhI4f55cvsE8yuZdZmXLrezv8VhnvJmEKRan'),(14,0,'user1@use121.com','user123','User','One','Hello This IS user 1','2000-06-06',NULL,NULL,'src/main/resources/static/pfp/856068321764-14-.png',1,'$2a$10$oH1PoHoU87QqWogDeATlSeaQAJ1zdSyiStBov7gM7op1NX3jy9SPe',0,0,'2022-10-31 09:01:23',0,'Htcl0qNHAXINUMLSTgocjcaTtAMdK04Sy0RH3NrPxs7LNA4zAgWFyDGphuAeMB3V'),(15,0,'user3@use121.com','user124','User','One','Hello This IS user 1','2000-06-06',NULL,NULL,'src/main/resources/static/pfp/5412364907938-15-.png',1,'$2a$10$K8Q9s1HCLpfZ3wdNBAFKlOJDPayavVuuoQ8fpR5mzcKHGPyPCA.KS',0,0,'2022-10-31 10:17:19',0,'1PkczQbSlrZ6DjpT64Ub4V9twbGxc8ISByNRJol1kBmxyjTbIxML08i860BlWi9h'),(16,0,'user4@use121.com','user126','User','One','Hello This IS user 1','2000-06-06','3468759620','male','src/main/resources/static/pfp/5780235595823-16-.png',1,'$2a$10$v/q3uhE/dskI5/jtWnmfTOaBbc355TZsnQdF6riagQHkfyj.Ksttq',1,0,'2022-10-31 10:23:27',0,'pOEu3Li1a91WuNlcPTydcXSJ6NLp4whYGicd4BDvmururpv88pt01lhYMWfkEIK5'),(17,0,'user5@use121.com','user127','User','One','Hello This IS user 1','2000-06-06','3468759620','male','src/main/resources/static/pfp/5815856295670-17-.png',1,'$2a$10$rVA35Hdndg9H3NE5ndXLxuRme4EkXWN1XVnwBkISXpy/7K08mGcBq',0,0,'2022-10-31 10:24:02',0,'m5grqfsxkWMDxQp9nJlEjohr352mJxlQDPNxLvk3Hl1dEKAGTguWKz6CLqZ6IR9m'),(18,0,'vasildincev@gmail.com','user1271','User','One','Hello This IS user 1','2000-06-06','3468759620','male','src/main/resources/static/pfp/6545632742225-18-.png',1,'$2a$10$VhzCoEtHhOPp2JJxlPyybOKVPK/R21tLYv3x3Hr8suNnMbnCnpEgy',0,0,'2022-10-31 10:36:12',0,'tZrulBcz4tTOJmZBSMY8xnj7yHINUNTTIZwD3tSOpIECN0OSDwvLkN80O5llq7v5'),(19,0,'vasildinchev@gmail.com','user12712','User','One','Hello This IS user 1','2000-06-06','3468759620','male','src/main/resources/static/pfp/8930964304769-19-.png',1,'$2a$10$NIceAOQ1sMzl8B5WZUW0xusixs0mmjY396Azl8WGTp/W6JrDgXnuW',0,0,'2022-10-31 11:15:58',0,'OIyGU1wPXMcBTKyWwZlUFtIoFUNL623l8Bch7U6TT6zTPSQLibilTSCpYhswILdq'),(20,0,'vasildinchevs@gmail.com','user127123','User','One','Hello This IS user 1','2000-06-06','3468759620','male','src/main/resources/static/pfp/8961338084413-20-.png',0,'$2a$10$FeUdmiQsmheMpwEAz6t6z.p5vcbnO9PVbE1XWGE9EJ1XGPxlBe.4m',0,0,'2022-10-31 11:16:28',0,'3kDrSlW0U0zAYerzTYQk5rypi2SGCcL7ibuzso4wCYRwdwpieDUrZORL7MPjhcHg'),(21,0,'vasildinchevss@gmail.com','user1271238','User','One','Hello This IS user 1','2000-06-06','3468759620','male','src/main/resources/static/pfp/9311457266793-21-.png',0,'$2a$10$nAIwEoVwYuAk/BsIkPv64ucJKD0NAVz9Of9g9Z3xzADhACfZS9bfe',0,0,'2022-10-31 11:22:18',0,'wYtWtqi1sOuH1IzAveY9eORnJUIbc18fOBETsJjnSUuDDc1rQiwUmQtRvH2F7aJj'),(22,0,'vasildinchevss3@gmail.com','user12712383','User','One','Hello This IS user 1','2000-06-06','3468759620','male','src/main/resources/static/pfp/9322229735738-22-.png',0,'$2a$10$2dO0BwIPPUt60m5rHfO5pOgDRphsyZuPvzx.ICBNRFAGFQIC0KRRu',0,0,'2022-10-31 11:22:29',0,'PJV3Y3gBSGLIaoG0vxWBKCX3IzYlVU8FAOx6f0gypbNFbNhhudCMnUX2TV3Aa2uU'),(23,0,'user1@use1.com','user15','User','One','Hello This IS user 1','2000-06-06','3468759620','m','src\\main\\resources\\static\\pfp\\14382399817900-23-.jpg',1,'$2a$10$JtpF8srpkRV6ISPNSClE.u3/EW0WO0DT/K16kohNLGTNIu69HGe5K',0,0,'2022-11-01 14:52:58',0,'eKZCiE0hZdq0JcJxYDIGuzxzLOSFsOjpPyFlVuchcxNqqhj4fxRiJmLKNpJiBZRZ');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-01 16:55:15
