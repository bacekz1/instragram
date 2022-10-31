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
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `reply_owner_comment_unique` (`reply_id`,`owner_id`,`post_id`),
  KEY `user_id_idx` (`owner_id`),
  KEY `reply_id_idx` (`reply_id`),
  KEY `fk_post_id_post_idx` (`post_id`),
  CONSTRAINT `fk_post_id_post` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `fk_reply_id_user_id` FOREIGN KEY (`reply_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `fk_user_id_reply_id` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,3,1,'wow #beautiful',NULL,0,'0000-00-00 00:00:00'),(2,3,2,'thank you',1,0,'0000-00-00 00:00:00'),(6,3,2,'test123',NULL,0,'0000-00-00 00:00:00'),(7,4,1,'opa',1,0,'0000-00-00 00:00:00'),(10,4,2,'wow',7,0,'0000-00-00 00:00:00'),(11,4,1,'ebasi',10,0,'0000-00-00 00:00:00'),(15,3,2,'aaa',NULL,0,'0000-00-00 00:00:00'),(16,3,2,'sisi@abv.bg',NULL,0,'0000-00-00 00:00:00'),(17,3,2,'sisi@abv.bg',NULL,0,'0000-00-00 00:00:00'),(18,3,2,'sisi@abv.bg',NULL,0,'0000-00-00 00:00:00'),(19,3,2,'sisi@abv.bg',NULL,0,'0000-00-00 00:00:00'),(20,62,8,'deleted at2022-10-24T20:08:36.024237900',NULL,1,'2022-10-24 19:02:10'),(21,62,8,'test',NULL,0,'2022-10-24 18:27:53'),(22,62,8,'deleted at2022-10-24T19:44:39.609198200',NULL,1,'2022-10-24 19:00:48'),(23,62,8,'deleted at2022-10-24T20:08:36.027241500',20,1,'2022-10-24 20:01:16'),(24,63,8,'deleted at2022-10-24T20:31:25.796625700',NULL,1,'2022-10-24 20:29:41'),(25,63,8,'deleted at2022-10-24T20:31:25.796625700',NULL,1,'2022-10-24 20:29:42'),(26,63,8,'deleted at2022-10-24T20:31:25.796625700',NULL,1,'2022-10-24 20:29:43'),(27,63,8,'deleted at2022-10-24T20:31:25.796625700',NULL,1,'2022-10-24 20:29:44'),(28,63,8,'deleted at2022-10-24T20:31:25.796625700',NULL,1,'2022-10-24 20:29:44'),(29,63,8,'deleted at2022-10-24T20:31:25.796625700',NULL,1,'2022-10-24 20:29:45'),(30,63,8,'deleted at2022-10-24T20:31:25.796625700',28,1,'2022-10-24 20:30:01'),(32,63,8,'#daliraboti ',NULL,0,'2022-10-24 23:47:15'),(33,63,9,'#daliraboti ',NULL,0,'2022-10-24 23:47:48'),(34,63,9,'#daliraboti ',NULL,0,'2022-10-24 23:49:04'),(35,62,9,'#dalirabotiaaa ',21,0,'2022-10-24 23:49:55');
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
INSERT INTO `following` VALUES (2,1),(1,2);
/*!40000 ALTER TABLE `following` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashtag_following`
--

DROP TABLE IF EXISTS `hashtag_following`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hashtag_following` (
  `user_id` int NOT NULL,
  `hashtag_id` int NOT NULL,
  UNIQUE KEY `fk_hashtag_following_unique` (`user_id`,`hashtag_id`),
  KEY `fk_hashtag_following_1_idx` (`user_id`),
  KEY `fk_hashtag_following_2_idx` (`hashtag_id`),
  CONSTRAINT `fk_hashtag_following_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_hashtag_following_2` FOREIGN KEY (`hashtag_id`) REFERENCES `hashtags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtag_following`
--

LOCK TABLES `hashtag_following` WRITE;
/*!40000 ALTER TABLE `hashtag_following` DISABLE KEYS */;
INSERT INTO `hashtag_following` VALUES (1,1),(2,1);
/*!40000 ALTER TABLE `hashtag_following` ENABLE KEYS */;
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
  UNIQUE KEY `tag_name_UNIQUE` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtags`
--

LOCK TABLES `hashtags` WRITE;
/*!40000 ALTER TABLE `hashtags` DISABLE KEYS */;
INSERT INTO `hashtags` VALUES (17,' #wdalirabotaiwow '),(13,' #wdaliraboti '),(16,' #wdalirabotiwow '),(15,' daliraboti '),(12,'#daliraboti'),(18,'#dalirabotiaaa'),(10,'#gwat'),(11,'#kor'),(9,'#kur'),(8,'#test'),(2,'#wowkor'),(3,'#yesvbe'),(1,'food'),(14,'test');
/*!40000 ALTER TABLE `hashtags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashtags_posts`
--

DROP TABLE IF EXISTS `hashtags_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hashtags_posts` (
  `post_hashtag_id` int NOT NULL,
  `tag_id` int NOT NULL,
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
INSERT INTO `hashtags_posts` VALUES (3,1),(14,2),(14,3),(15,2),(15,3),(16,2),(16,3),(53,8),(54,8),(55,8),(56,8),(56,9),(57,8),(57,9),(58,8),(58,9),(58,10),(59,8),(59,9),(59,10),(60,8),(60,9),(60,10),(61,8),(61,11),(62,8),(62,11),(62,14),(62,15),(62,17),(62,18),(63,8),(63,11),(63,12),(63,18);
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
INSERT INTO `like_comments` VALUES (1,2);
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
INSERT INTO `like_post` VALUES (2,4);
/*!40000 ALTER TABLE `like_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `location_id_idx` (`id`),
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `post` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` VALUES (1,'Sofia');
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
  UNIQUE KEY `post_id_unique` (`user_tag_id`,`post_persontag_id`),
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
INSERT INTO `person_tag` VALUES (1,3),(1,4),(1,60),(1,61),(1,62),(1,63),(2,60),(4,15),(4,16);
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
  KEY `post_user_fk` (`user_id`),
  KEY `location_id_idx` (`location_id`),
  CONSTRAINT `post_user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (3,1,'hi',1,'2022-10-21 16:44:21',0,'2022-10-21 16:44:21'),(4,2,'hello',NULL,'2022-10-21 16:44:21',0,'2022-10-21 16:44:21'),(5,1,'wow',NULL,'2022-10-21 16:44:21',0,'2022-10-21 16:44:21'),(6,1,'woww',NULL,'2022-10-22 16:43:49',0,'2022-10-21 16:43:49'),(7,1,'woww',NULL,'2022-10-22 16:44:21',0,'2022-10-21 16:44:21'),(8,1,'woww',NULL,'2022-10-21 16:44:21',0,'2022-10-21 16:44:21'),(14,4,'#wowkor #yesvbe @go6ogeq ',NULL,'2022-10-23 18:04:53',0,'2022-10-22 18:04:53'),(15,4,'deleted at2022-10-22T18:42:45.145775',NULL,'2022-10-23 18:05:11',1,'2022-10-22 18:05:11'),(16,4,'deleted at2022-10-22T18:24:05.800117800',NULL,'2022-10-23 18:23:11',1,'2022-10-22 18:23:11'),(17,4,'@test #test',NULL,NULL,0,'2022-10-23 15:47:38'),(18,4,'@test #test',NULL,NULL,0,'2022-10-23 16:06:21'),(19,4,'@test #test',NULL,NULL,0,'2022-10-23 17:28:36'),(20,4,'@test #test',NULL,NULL,0,'2022-10-23 17:31:03'),(21,4,'@test #test',NULL,NULL,0,'2022-10-23 17:35:20'),(22,4,'@test #test',NULL,NULL,0,'2022-10-23 17:35:51'),(23,4,'@test #test',NULL,NULL,0,'2022-10-23 17:45:07'),(24,4,'@test #test',NULL,NULL,0,'2022-10-23 17:46:35'),(25,4,'@test #test',NULL,NULL,0,'2022-10-23 17:51:13'),(26,4,'@test #test',NULL,NULL,0,'2022-10-23 17:56:22'),(27,4,'@test #test',NULL,NULL,0,'2022-10-23 18:32:24'),(28,4,'@test #test',NULL,NULL,0,'2022-10-23 18:43:56'),(30,4,'@test #test',NULL,NULL,0,'2022-10-23 18:53:57'),(31,4,'@test #test',NULL,NULL,0,'2022-10-23 18:59:16'),(32,4,'@test #test',NULL,NULL,0,'2022-10-23 19:01:44'),(33,4,'@test #test',NULL,NULL,0,'2022-10-23 19:04:35'),(36,4,'@test #test',NULL,NULL,0,'2022-10-23 19:07:23'),(37,4,'@test #test',NULL,NULL,0,'2022-10-23 19:11:20'),(39,4,'@test #test',NULL,NULL,0,'2022-10-23 19:11:54'),(42,4,'@test #test',NULL,NULL,0,'2022-10-23 19:24:28'),(44,4,'@test #test',NULL,NULL,0,'2022-10-23 19:30:01'),(45,4,'@test #test',NULL,NULL,0,'2022-10-23 19:32:01'),(46,4,'@test #test',NULL,NULL,0,'2022-10-23 19:32:15'),(49,4,'@test #test',NULL,NULL,0,'2022-10-23 21:42:52'),(50,4,'@test #test',NULL,NULL,0,'2022-10-23 21:44:28'),(53,4,'@test #test #test #test',NULL,NULL,0,'2022-10-24 17:34:51'),(54,4,'@test #test #test #test #kur',NULL,NULL,0,'2022-10-24 17:35:35'),(55,4,'@test #test #test #test #kur',NULL,NULL,0,'2022-10-24 17:36:19'),(56,4,'@test #test #test #test #kur #gwat',NULL,NULL,0,'2022-10-24 17:36:32'),(57,4,'@test #test #test #test #kur #gwat',NULL,NULL,0,'2022-10-24 17:38:41'),(58,4,'@test #test #test #test #kur #gwat ',NULL,NULL,0,'2022-10-24 17:39:12'),(59,4,'@test #test #test #test #kur #gwat @pe',NULL,NULL,0,'2022-10-24 17:39:56'),(60,4,'@test #test #test #test #kur #gwat @pe6o @pe6o @pe6o @pe6o @go6o ',NULL,NULL,0,'2022-10-24 17:40:32'),(61,4,'#test #kor @pe6o asfkbasfkb ',NULL,NULL,0,'2022-10-24 17:45:39'),(62,4,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-24 17:45:50'),(63,8,'deleted at2022-10-24T20:31:25.795625',1,NULL,0,'2022-10-24 20:26:24');
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
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_content`
--

LOCK TABLES `post_content` WRITE;
/*!40000 ALTER TABLE `post_content` DISABLE KEYS */;
INSERT INTO `post_content` VALUES (1,'google.bg',3),(2,'hi',3),(32,'uploads\\1066419690004.jpg',49),(34,'uploads\\194032597567004.jpg',53),(35,'uploads\\194466645459004.jpg',54),(36,'uploads\\194906075945004.jpg',55),(37,'uploads\\195041059030004.jpg',56),(38,'uploads\\196327429469004.jpg',57),(39,'uploads\\196635957495004.jpg',58),(40,'uploads\\197074655179004.jpg',59),(41,'uploads\\197437082547004.jpg',60),(33,'uploads\\2029433623004.jpg',50),(3,'uploads\\241119256881004.jpg',30),(4,'uploads\\241119294823004.jpg',30),(5,'uploads\\241119300299004.jpg',30),(6,'uploads\\244307098516004.jpg',31),(7,'uploads\\244307140418004.jpg',31),(8,'uploads\\244307147543004.jpg',31),(9,'uploads\\245792726790004.jpg',32),(10,'uploads\\245792767520004.jpg',32),(11,'uploads\\245792776227004.jpg',32),(12,'uploads\\247500665499004.jpg',33),(13,'uploads\\247500706739004.jpg',33),(14,'uploads\\247500714821004.jpg',33),(15,'uploads\\251547303150004.jpg',37),(16,'uploads\\251547344229004.jpg',37),(17,'uploads\\251547351055004.jpg',37),(18,'uploads\\251893753164004.jpg',39),(19,'uploads\\251893761682004.jpg',39),(20,'uploads\\251893767754004.jpg',39),(21,'uploads\\251893773703004.jpg',39),(22,'uploads\\251893778478004.jpg',39),(23,'uploads\\251893785074004.jpg',39),(24,'uploads\\251893793793004.png',39),(25,'uploads\\251893804039004.jpg',39),(26,'uploads\\251893811261004.jpg',39),(27,'uploads\\251893817676004.png',39),(28,'uploads\\259426639773004.jpg',42),(29,'uploads\\262756033894004.jpg',44),(30,'uploads\\263960630697004.jpg',45),(31,'uploads\\264101703942004.jpg',46);
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
INSERT INTO `private_message` VALUES (1,1,2,'hello','2021-02-03 00:00:00',0),(2,2,1,'um hi','2021-03-09 00:00:00',0);
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
  `is_private` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`) /*!80000 INVISIBLE */,
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,0,'bksdv@ubef.com','pe6o',NULL,NULL,'adf','1999-01-01','07','m','',1,'123',0,0,'2022-10-21 23:33:24',0),(2,0,'busdfb@bsukdfb.com','go6o',NULL,NULL,NULL,'1998-02-02','02','m','',1,'test',0,0,'2022-10-21 23:33:26',0),(3,0,'wefw4r24r@efw4.com','hillow',NULL,NULL,'wew','2022-10-19','3855843','m',NULL,1,'123456',0,0,'2022-10-21 23:32:26',0),(4,0,'user@user.com','user1234',NULL,NULL,NULL,NULL,NULL,NULL,'default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$IXZrDlovnaGmJpf4yX2ZyucDVaRpJF79vMR162ZpiYlNGEI6VqO8u',0,0,'2022-10-22 18:04:13',0),(5,0,'user@1user.com','user11234',NULL,NULL,NULL,NULL,NULL,NULL,'default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$Er4ApYjsLEGG3MB.IepqNuv7gyxYYWVHy1X9ZzA1ayxVAyqDWvkGy',0,0,'2022-10-23 17:58:10',0),(6,0,'user@11user.com','user111234',NULL,NULL,NULL,NULL,NULL,NULL,'default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$qK1T/aLO5HQYH352lm57G.vp83Lvvc33nrAY.Iv0YtJ94W4zlMRH.',0,0,'2022-10-23 17:59:00',0),(7,0,'user@111user.com','user1111234',NULL,NULL,NULL,NULL,NULL,NULL,'default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$UXDGBbRN/D8537Zo.JvSben1ykC6iVJ6JcNDWicLy5ZwXQu6RqtZe',0,0,'2022-10-23 18:00:27',0),(8,0,'inguofdsl@gm.coms','gifgiasigd7erqw',NULL,NULL,NULL,NULL,NULL,NULL,'default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$0gg4Aq9B9lcylYBWTwlmeeDLjDkxf0jD4qahcNfCb/Eihm7ZvaAPa',0,0,'2022-10-24 18:02:16',0),(9,0,'inguofdsl@gm.com1s','gifgiasigd7erqw1',NULL,NULL,NULL,NULL,NULL,NULL,'default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$kjWldusVVeyTDVv1c9KeBOiperuSl20WyVU0EFiERQ/ZaR5lJz4U.',0,0,'2022-10-24 23:47:03',0);
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

-- Dump completed on 2022-10-24 23:51:21