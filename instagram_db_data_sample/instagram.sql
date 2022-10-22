-- MySQL dump 10.13  Distrib 8.0.31, for Linux (x86_64)
--
-- Host: localhost    Database: instagram
-- ------------------------------------------------------
-- Server version	8.0.31

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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,3,1,'wow #beautiful',NULL,0,'0000-00-00 00:00:00'),(2,3,2,'thank you',1,0,'0000-00-00 00:00:00'),(6,3,2,'test123',NULL,0,'0000-00-00 00:00:00'),(7,4,1,'opa',1,0,'0000-00-00 00:00:00'),(10,4,2,'wow',7,0,'0000-00-00 00:00:00'),(11,4,1,'ebasi',10,0,'0000-00-00 00:00:00'),(15,3,2,'aaa',NULL,0,'0000-00-00 00:00:00'),(16,3,2,'sisi@abv.bg',NULL,0,'0000-00-00 00:00:00'),(17,3,2,'sisi@abv.bg',NULL,0,'0000-00-00 00:00:00'),(18,3,2,'sisi@abv.bg',NULL,0,'0000-00-00 00:00:00'),(19,3,2,'sisi@abv.bg',NULL,0,'0000-00-00 00:00:00');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtags`
--

LOCK TABLES `hashtags` WRITE;
/*!40000 ALTER TABLE `hashtags` DISABLE KEYS */;
INSERT INTO `hashtags` VALUES (2,'#kur'),(1,'food');
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
INSERT INTO `hashtags_posts` VALUES (3,1),(14,2),(15,2);
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
INSERT INTO `person_tag` VALUES (1,3),(1,4),(1,14),(1,15);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (3,1,'hi',1,'2022-10-21 16:44:21',0,'2022-10-21 16:44:21'),(4,2,'hello',NULL,'2022-10-21 16:44:21',0,'2022-10-21 16:44:21'),(5,1,'wow',NULL,'2022-10-21 16:44:21',0,'2022-10-21 16:44:21'),(6,1,'woww',NULL,'2022-10-22 16:43:49',0,'2022-10-21 16:43:49'),(7,1,'woww',NULL,'2022-10-22 16:44:21',0,'2022-10-21 16:44:21'),(8,1,'woww',NULL,'2022-10-21 16:44:21',0,'2022-10-21 16:44:21'),(14,4,'#kur @khbkhbjkbh @pe6o ',NULL,'2022-10-23 22:04:36',0,'2022-10-22 22:04:36'),(15,4,'#kur @khbkhbjkbh @pe6o ',NULL,NULL,0,'2022-10-22 22:24:39');
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
  `media` varchar(100) NOT NULL,
  `post_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `media_post_unique` (`media`,`post_id`),
  KEY `post_id_idx` (`post_id`),
  CONSTRAINT `post_id_maiak` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_content`
--

LOCK TABLES `post_content` WRITE;
/*!40000 ALTER TABLE `post_content` DISABLE KEYS */;
INSERT INTO `post_content` VALUES (1,'google.bg',3),(2,'hi',3);
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
  UNIQUE KEY `email_UNIQUE` (`email`) /*!80000 INVISIBLE */
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,0,'bksdv@ubef.com','pe6o',NULL,NULL,'adf','1999-01-01','07','m','',1,'123',0,0,'2022-10-21 23:33:24',0),(2,0,'busdfb@bsukdfb.com','go6o',NULL,NULL,NULL,'1998-02-02','02','m','',1,'test',0,0,'2022-10-21 23:33:26',0),(3,0,'wefw4r24r@efw4.com','hillow',NULL,NULL,'wew','2022-10-19','3855843','m',NULL,1,'123456',0,0,'2022-10-21 23:32:26',0),(4,0,'email@email.com','user1234',NULL,NULL,NULL,NULL,NULL,NULL,'default_profile_picture/default_profile_picture.jpg',0,'$2a$10$DUWpSsq4lNDDIZPXa8x/..bc/QfegJs9H3Ff/hlPkDsq5hpfi6Xja',0,0,'2022-10-22 21:38:20',0);
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

-- Dump completed on 2022-10-22 22:42:11
