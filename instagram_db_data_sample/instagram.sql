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
  KEY `user_id_idx` (`owner_id`),
  KEY `reply_id_idx` (`reply_id`),
  KEY `fk_post_id_post_idx` (`post_id`),
  CONSTRAINT `fk_post_id_post` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `fk_reply_id_user_id` FOREIGN KEY (`reply_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `fk_user_id_reply_id` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,3,1,'wow #beautiful',NULL,0,'0000-00-00 00:00:00'),(2,3,2,'thank you',1,0,'0000-00-00 00:00:00'),(6,3,2,'test123',NULL,0,'0000-00-00 00:00:00'),(7,4,1,'opa',1,0,'0000-00-00 00:00:00'),(10,4,2,'wow',7,0,'0000-00-00 00:00:00'),(11,4,1,'ebasi',10,0,'0000-00-00 00:00:00'),(15,3,2,'aaa',NULL,0,'0000-00-00 00:00:00'),(16,3,2,'sisi@abv.bg',NULL,0,'0000-00-00 00:00:00'),(17,3,2,'sisi@abv.bg',NULL,0,'0000-00-00 00:00:00'),(18,3,2,'sisi@abv.bg',NULL,0,'0000-00-00 00:00:00'),(19,3,2,'sisi@abv.bg',NULL,0,'0000-00-00 00:00:00'),(20,62,8,'deleted at2022-10-24T20:08:36.024237900',NULL,1,'2022-10-24 19:02:10'),(21,62,8,'test',NULL,0,'2022-10-24 18:27:53'),(22,62,8,'deleted at2022-10-24T19:44:39.609198200',NULL,1,'2022-10-24 19:00:48'),(23,62,8,'deleted at2022-10-24T20:08:36.027241500',20,1,'2022-10-24 20:01:16'),(24,63,8,'deleted at2022-10-24T20:31:25.796625700',NULL,1,'2022-10-24 20:29:41'),(25,63,8,'deleted at2022-10-24T20:31:25.796625700',NULL,1,'2022-10-24 20:29:42'),(26,63,8,'deleted at2022-10-24T20:31:25.796625700',NULL,1,'2022-10-24 20:29:43'),(27,63,8,'deleted at2022-10-24T20:31:25.796625700',NULL,1,'2022-10-24 20:29:44'),(28,63,8,'deleted at2022-10-24T20:31:25.796625700',NULL,1,'2022-10-24 20:29:44'),(29,63,8,'deleted at2022-10-24T20:31:25.796625700',NULL,1,'2022-10-24 20:29:45'),(30,63,8,'deleted at2022-10-24T20:31:25.796625700',28,1,'2022-10-24 20:30:01'),(32,63,8,'#daliraboti ',NULL,0,'2022-10-24 23:47:15'),(33,63,9,'#daliraboti ',NULL,0,'2022-10-24 23:47:48'),(34,63,9,'#daliraboti ',NULL,0,'2022-10-24 23:49:04'),(35,62,9,'#dalirabotiaaa ',21,0,'2022-10-24 23:49:55'),(37,63,9,'#daliraboti ',NULL,0,'2022-10-25 15:15:54'),(38,63,9,'#daliraboti ',NULL,0,'2022-10-25 15:15:56'),(39,63,8,'#daliraboti ',NULL,0,'2022-10-25 15:17:08'),(40,63,8,'#daliraboti ',NULL,0,'2022-10-25 15:17:09'),(41,63,8,'#daliraboti ',NULL,0,'2022-10-25 15:17:13'),(42,64,8,'#daliraboti ',NULL,0,'2022-10-25 15:21:25'),(43,64,9,'#dalirabotiaaa ',42,0,'2022-10-25 15:22:12'),(44,64,9,'#dalirabotiaaa ',42,0,'2022-10-25 15:22:52'),(45,64,9,'#dalirabotiaaa ',42,0,'2022-10-25 15:22:53'),(46,64,9,'#dalirabotiaaa ',42,0,'2022-10-25 15:22:54'),(47,64,9,'#daliraboti ',NULL,0,'2022-10-25 15:44:55'),(48,64,9,'#daliraboti ',NULL,0,'2022-10-25 15:44:56'),(49,66,8,'#daliraboti ',NULL,0,'2022-10-25 15:48:23'),(50,66,9,'#dalirabotiaaa ',49,0,'2022-10-25 15:48:58');
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
  UNIQUE KEY `tag_name_UNIQUE` (`tag_name`),
  KEY `tag_name` (`tag_name`)
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
INSERT INTO `hashtags_posts` VALUES (3,1),(14,2),(14,3),(15,2),(15,3),(16,2),(16,3),(53,8),(54,8),(55,8),(56,8),(56,9),(57,8),(57,9),(58,8),(58,9),(58,10),(59,8),(59,9),(59,10),(60,8),(60,9),(60,10),(61,8),(61,11),(62,8),(62,11),(62,14),(62,15),(62,17),(62,18),(63,8),(63,11),(63,12),(63,18),(64,8),(64,11),(64,12),(64,18),(65,8),(65,11),(66,8),(66,11),(66,12),(66,18),(67,8),(67,11),(68,8),(68,11),(69,8),(69,11),(70,8),(70,11),(71,8),(71,11),(72,8),(72,11),(73,8),(73,11),(74,8),(74,11),(75,8),(75,11),(76,8),(76,11),(77,8),(77,11),(78,8),(78,11),(79,8),(79,11),(80,8),(80,11),(81,8),(81,11),(82,8),(82,11),(83,8),(83,11),(84,8),(84,11),(85,8),(85,11),(86,8),(86,11),(87,8),(87,11),(88,8),(88,11),(89,8),(89,11),(90,8),(90,11),(91,8),(91,11),(92,8),(92,11),(93,8),(93,11),(94,8),(94,11),(95,8),(95,11),(96,8),(96,11),(97,8),(97,11),(98,8),(98,11),(99,8),(99,11),(100,8),(100,11),(101,8),(101,11),(102,8),(102,11),(103,8),(103,11),(104,8),(104,11),(105,8),(105,11),(106,8),(106,11),(107,8),(107,11),(108,8),(108,11),(109,8),(109,11),(110,8),(110,11);
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
  KEY `location_id_idx` (`id`) /*!80000 INVISIBLE */,
  KEY `name` (`name`),
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
INSERT INTO `person_tag` VALUES (1,3),(1,4),(1,60),(1,61),(1,62),(1,63),(1,64),(1,65),(1,66),(1,67),(1,68),(1,69),(1,70),(1,71),(1,72),(1,73),(1,74),(1,75),(1,76),(1,77),(1,78),(1,79),(1,80),(1,81),(1,82),(1,83),(1,84),(1,85),(1,86),(1,87),(1,88),(1,89),(1,90),(1,91),(1,92),(1,93),(1,94),(1,95),(1,96),(1,97),(1,98),(1,99),(1,100),(1,101),(1,102),(1,103),(1,104),(1,105),(1,106),(1,107),(1,108),(1,109),(1,110),(2,60),(4,15),(4,16);
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
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (3,1,'hi',1,'2022-10-21 16:44:21',0,'2022-10-21 16:44:21'),(4,2,'hello',NULL,'2022-10-21 16:44:21',0,'2022-10-21 16:44:21'),(5,1,'wow',NULL,'2022-10-21 16:44:21',0,'2022-10-21 16:44:21'),(6,1,'woww',NULL,'2022-10-22 16:43:49',0,'2022-10-21 16:43:49'),(7,1,'woww',NULL,'2022-10-22 16:44:21',0,'2022-10-21 16:44:21'),(8,1,'woww',NULL,'2022-10-21 16:44:21',0,'2022-10-21 16:44:21'),(14,4,'#wowkor #yesvbe @go6ogeq ',NULL,'2022-10-23 18:04:53',0,'2022-10-22 18:04:53'),(15,4,'deleted at2022-10-22T18:42:45.145775',NULL,'2022-10-23 18:05:11',1,'2022-10-22 18:05:11'),(16,4,'deleted at2022-10-22T18:24:05.800117800',NULL,'2022-10-23 18:23:11',1,'2022-10-22 18:23:11'),(17,4,'@test #test',NULL,NULL,0,'2022-10-23 15:47:38'),(18,4,'@test #test',NULL,NULL,0,'2022-10-23 16:06:21'),(19,4,'@test #test',NULL,NULL,0,'2022-10-23 17:28:36'),(20,4,'@test #test',NULL,NULL,0,'2022-10-23 17:31:03'),(21,4,'@test #test',NULL,NULL,0,'2022-10-23 17:35:20'),(22,4,'@test #test',NULL,NULL,0,'2022-10-23 17:35:51'),(23,4,'@test #test',NULL,NULL,0,'2022-10-23 17:45:07'),(24,4,'@test #test',NULL,NULL,0,'2022-10-23 17:46:35'),(25,4,'@test #test',NULL,NULL,0,'2022-10-23 17:51:13'),(26,4,'@test #test',NULL,NULL,0,'2022-10-23 17:56:22'),(27,4,'@test #test',NULL,NULL,0,'2022-10-23 18:32:24'),(28,4,'@test #test',NULL,NULL,0,'2022-10-23 18:43:56'),(30,4,'@test #test',NULL,NULL,0,'2022-10-23 18:53:57'),(31,4,'@test #test',NULL,NULL,0,'2022-10-23 18:59:16'),(32,4,'@test #test',NULL,NULL,0,'2022-10-23 19:01:44'),(33,4,'@test #test',NULL,NULL,0,'2022-10-23 19:04:35'),(36,4,'@test #test',NULL,NULL,0,'2022-10-23 19:07:23'),(37,4,'@test #test',NULL,NULL,0,'2022-10-23 19:11:20'),(39,4,'@test #test',NULL,NULL,0,'2022-10-23 19:11:54'),(42,4,'@test #test',NULL,NULL,0,'2022-10-23 19:24:28'),(44,4,'@test #test',NULL,NULL,0,'2022-10-23 19:30:01'),(45,4,'@test #test',NULL,NULL,0,'2022-10-23 19:32:01'),(46,4,'@test #test',NULL,NULL,0,'2022-10-23 19:32:15'),(49,4,'@test #test',NULL,NULL,0,'2022-10-23 21:42:52'),(50,4,'@test #test',NULL,NULL,0,'2022-10-23 21:44:28'),(53,4,'@test #test #test #test',NULL,NULL,0,'2022-10-24 17:34:51'),(54,4,'@test #test #test #test #kur',NULL,NULL,0,'2022-10-24 17:35:35'),(55,4,'@test #test #test #test #kur',NULL,NULL,0,'2022-10-24 17:36:19'),(56,4,'@test #test #test #test #kur #gwat',NULL,NULL,0,'2022-10-24 17:36:32'),(57,4,'@test #test #test #test #kur #gwat',NULL,NULL,0,'2022-10-24 17:38:41'),(58,4,'@test #test #test #test #kur #gwat ',NULL,NULL,0,'2022-10-24 17:39:12'),(59,4,'@test #test #test #test #kur #gwat @pe',NULL,NULL,0,'2022-10-24 17:39:56'),(60,4,'@test #test #test #test #kur #gwat @pe6o @pe6o @pe6o @pe6o @go6o ',NULL,NULL,0,'2022-10-24 17:40:32'),(61,4,'#test #kor @pe6o asfkbasfkb ',NULL,NULL,0,'2022-10-24 17:45:39'),(62,4,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-24 17:45:50'),(63,8,'deleted at2022-10-24T20:31:25.795625',1,NULL,0,'2022-10-24 20:26:24'),(64,8,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 15:21:20'),(65,8,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 15:47:52'),(66,8,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 15:48:16'),(67,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 16:13:22'),(68,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 16:13:23'),(69,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 16:13:48'),(70,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 16:13:49'),(71,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 16:13:50'),(72,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 17:24:39'),(73,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 17:25:26'),(74,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 17:27:11'),(75,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 17:27:32'),(76,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 17:28:23'),(77,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 17:28:24'),(78,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 17:28:49'),(79,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 17:29:30'),(80,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 17:29:59'),(81,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 17:39:58'),(82,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:06:04'),(83,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:06:06'),(84,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:06:07'),(85,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:06:08'),(86,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:06:08'),(87,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:06:09'),(88,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:22'),(89,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:23'),(90,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:23'),(91,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:24'),(92,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:24'),(93,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:24'),(94,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:25'),(95,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:27'),(96,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:27'),(97,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:28'),(98,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:28'),(99,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:29'),(100,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:29'),(101,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:29'),(102,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:30'),(103,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:30'),(104,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:30'),(105,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:31'),(106,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:31'),(107,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:32'),(108,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:32'),(109,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:32'),(110,9,'#test #kor @pe6o asfkbasfkb ',1,NULL,0,'2022-10-25 20:34:33');
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
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_content`
--

LOCK TABLES `post_content` WRITE;
/*!40000 ALTER TABLE `post_content` DISABLE KEYS */;
INSERT INTO `post_content` VALUES (1,'google.bg',3),(2,'hi',3),(42,'src\\main\\resources\\static\\post\\142497586864009.jpg',79),(43,'src\\main\\resources\\static\\post\\142497632682009.jpg',79),(44,'src\\main\\resources\\static\\post\\142497646825009.jpg',79),(45,'src\\main\\resources\\static\\post\\142702241636009.jpg',80),(46,'src\\main\\resources\\static\\post\\142702278770009.jpg',80),(47,'src\\main\\resources\\static\\post\\142702287467009.jpg',80),(48,'src\\main\\resources\\static\\post\\148690893218009.jpg',81),(49,'src\\main\\resources\\static\\post\\148690925647009.jpg',81),(50,'src\\main\\resources\\static\\post\\236350663047009.jpg',82),(51,'src\\main\\resources\\static\\post\\236350693210009.jpg',82),(52,'src\\main\\resources\\static\\post\\236370662091009.jpg',83),(53,'src\\main\\resources\\static\\post\\236370671604009.jpg',83),(54,'src\\main\\resources\\static\\post\\236380122627009.jpg',84),(55,'src\\main\\resources\\static\\post\\236380130971009.jpg',84),(56,'src\\main\\resources\\static\\post\\236386422391009.jpg',85),(57,'src\\main\\resources\\static\\post\\236386432287009.jpg',85),(58,'src\\main\\resources\\static\\post\\236392250434009.jpg',86),(59,'src\\main\\resources\\static\\post\\236392258726009.jpg',86),(60,'src\\main\\resources\\static\\post\\236397143992009.jpg',87),(61,'src\\main\\resources\\static\\post\\236397153036009.jpg',87),(62,'src\\main\\resources\\static\\post\\253331986005009.jpg',88),(63,'src\\main\\resources\\static\\post\\253332016554009.jpg',88),(64,'src\\main\\resources\\static\\post\\253338943681009.jpg',89),(65,'src\\main\\resources\\static\\post\\253338953420009.jpg',89),(66,'src\\main\\resources\\static\\post\\253342746465009.jpg',90),(67,'src\\main\\resources\\static\\post\\253342756246009.jpg',90),(68,'src\\main\\resources\\static\\post\\253346260843009.jpg',91),(69,'src\\main\\resources\\static\\post\\253346278519009.jpg',91),(70,'src\\main\\resources\\static\\post\\253349875363009.jpg',92),(71,'src\\main\\resources\\static\\post\\253349884730009.jpg',92),(72,'src\\main\\resources\\static\\post\\253353077184009.jpg',93),(73,'src\\main\\resources\\static\\post\\253353086991009.jpg',93),(74,'src\\main\\resources\\static\\post\\253356232477009.jpg',94),(75,'src\\main\\resources\\static\\post\\253356242056009.jpg',94),(76,'src\\main\\resources\\static\\post\\253375068892009.jpg',95),(77,'src\\main\\resources\\static\\post\\253375078202009.jpg',95),(78,'src\\main\\resources\\static\\post\\253382312220009.jpg',96),(79,'src\\main\\resources\\static\\post\\253382323533009.jpg',96),(80,'src\\main\\resources\\static\\post\\253386418893009.jpg',97),(81,'src\\main\\resources\\static\\post\\253386428154009.jpg',97),(82,'src\\main\\resources\\static\\post\\253390200698009.jpg',98),(83,'src\\main\\resources\\static\\post\\253390212791009.jpg',98),(84,'src\\main\\resources\\static\\post\\253394207934009.jpg',99),(85,'src\\main\\resources\\static\\post\\253394218622009.jpg',99),(86,'src\\main\\resources\\static\\post\\253397959734009.jpg',100),(87,'src\\main\\resources\\static\\post\\253397973620009.jpg',100),(88,'src\\main\\resources\\static\\post\\253401616648009.jpg',101),(89,'src\\main\\resources\\static\\post\\253401625613009.jpg',101),(90,'src\\main\\resources\\static\\post\\253405873187009.jpg',102),(91,'src\\main\\resources\\static\\post\\253405882502009.jpg',102),(92,'src\\main\\resources\\static\\post\\253409708939009.jpg',103),(93,'src\\main\\resources\\static\\post\\253409718156009.jpg',103),(94,'src\\main\\resources\\static\\post\\253413209140009.jpg',104),(95,'src\\main\\resources\\static\\post\\253413218075009.jpg',104),(96,'src\\main\\resources\\static\\post\\253417010512009.jpg',105),(97,'src\\main\\resources\\static\\post\\253417019968009.jpg',105),(98,'src\\main\\resources\\static\\post\\253420654342009.jpg',106),(99,'src\\main\\resources\\static\\post\\253420663215009.jpg',106),(100,'src\\main\\resources\\static\\post\\253423978837009.jpg',107),(101,'src\\main\\resources\\static\\post\\253423988485009.jpg',107),(102,'src\\main\\resources\\static\\post\\253427958357009.jpg',108),(103,'src\\main\\resources\\static\\post\\253427968973009.jpg',108),(104,'src\\main\\resources\\static\\post\\253433478737009.jpg',109),(105,'src\\main\\resources\\static\\post\\253433492132009.jpg',109),(106,'src\\main\\resources\\static\\post\\253441186230009.jpg',110),(107,'src\\main\\resources\\static\\post\\253441194870009.jpg',110),(32,'uploads\\1066419690004.jpg',49),(34,'uploads\\194032597567004.jpg',53),(35,'uploads\\194466645459004.jpg',54),(36,'uploads\\194906075945004.jpg',55),(37,'uploads\\195041059030004.jpg',56),(38,'uploads\\196327429469004.jpg',57),(39,'uploads\\196635957495004.jpg',58),(40,'uploads\\197074655179004.jpg',59),(41,'uploads\\197437082547004.jpg',60),(33,'uploads\\2029433623004.jpg',50),(3,'uploads\\241119256881004.jpg',30),(4,'uploads\\241119294823004.jpg',30),(5,'uploads\\241119300299004.jpg',30),(6,'uploads\\244307098516004.jpg',31),(7,'uploads\\244307140418004.jpg',31),(8,'uploads\\244307147543004.jpg',31),(9,'uploads\\245792726790004.jpg',32),(10,'uploads\\245792767520004.jpg',32),(11,'uploads\\245792776227004.jpg',32),(12,'uploads\\247500665499004.jpg',33),(13,'uploads\\247500706739004.jpg',33),(14,'uploads\\247500714821004.jpg',33),(15,'uploads\\251547303150004.jpg',37),(16,'uploads\\251547344229004.jpg',37),(17,'uploads\\251547351055004.jpg',37),(18,'uploads\\251893753164004.jpg',39),(19,'uploads\\251893761682004.jpg',39),(20,'uploads\\251893767754004.jpg',39),(21,'uploads\\251893773703004.jpg',39),(22,'uploads\\251893778478004.jpg',39),(23,'uploads\\251893785074004.jpg',39),(24,'uploads\\251893793793004.png',39),(25,'uploads\\251893804039004.jpg',39),(26,'uploads\\251893811261004.jpg',39),(27,'uploads\\251893817676004.png',39),(28,'uploads\\259426639773004.jpg',42),(29,'uploads\\262756033894004.jpg',44),(30,'uploads\\263960630697004.jpg',45),(31,'uploads\\264101703942004.jpg',46);
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
  `is_deactivated` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`) /*!80000 INVISIBLE */,
  KEY `username` (`username`),
  KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,0,'bksdv@ubef.com','pe6o',NULL,NULL,'adf','1999-01-01','07','m','',1,'123',0,0,'2022-10-21 23:33:24',0,0),(2,0,'busdfb@bsukdfb.com','go6o',NULL,NULL,NULL,'1998-02-02','02','m','',1,'test',0,0,'2022-10-21 23:33:26',0,0),(3,0,'wefw4r24r@efw4.com','hillow',NULL,NULL,'wew','2022-10-19','3855843','m',NULL,1,'123456',0,0,'2022-10-21 23:32:26',0,0),(4,0,'user@user.com','user1234',NULL,NULL,NULL,NULL,NULL,NULL,'default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$IXZrDlovnaGmJpf4yX2ZyucDVaRpJF79vMR162ZpiYlNGEI6VqO8u',0,0,'2022-10-22 18:04:13',0,0),(5,0,'user@1user.com','user11234',NULL,NULL,NULL,NULL,NULL,NULL,'default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$Er4ApYjsLEGG3MB.IepqNuv7gyxYYWVHy1X9ZzA1ayxVAyqDWvkGy',0,0,'2022-10-23 17:58:10',0,0),(6,0,'user@11user.com','user111234',NULL,NULL,NULL,NULL,NULL,NULL,'default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$qK1T/aLO5HQYH352lm57G.vp83Lvvc33nrAY.Iv0YtJ94W4zlMRH.',0,0,'2022-10-23 17:59:00',0,0),(7,0,'user@111user.com','user1111234',NULL,NULL,NULL,NULL,NULL,NULL,'default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$UXDGBbRN/D8537Zo.JvSben1ykC6iVJ6JcNDWicLy5ZwXQu6RqtZe',1,0,'2022-10-23 18:00:27',0,0),(8,0,'inguofdsl@gm.coms','gifgiasigd7erqw',NULL,NULL,NULL,NULL,NULL,NULL,'default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$0gg4Aq9B9lcylYBWTwlmeeDLjDkxf0jD4qahcNfCb/Eihm7ZvaAPa',0,0,'2022-10-24 18:02:16',0,0),(9,0,'inguofdsl@gm.com1s','gifgiasigd7erqw1',NULL,NULL,NULL,NULL,NULL,NULL,'default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$kjWldusVVeyTDVv1c9KeBOiperuSl20WyVU0EFiERQ/ZaR5lJz4U.',0,0,'2022-10-24 23:47:03',0,0),(10,0,'inguofdsl@admin.instagram.com','admin',NULL,NULL,NULL,NULL,NULL,NULL,'src\\main\\resources\\static\\default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$gS2wJW7IxRMkRr/w2UPe..8fBKzO3OwiDgKIidLKKOP/oglV2olxm',0,0,'2022-10-25 17:44:20',0,0),(11,0,'inguofdsl@admin1.instagram.com','admina',NULL,NULL,NULL,NULL,NULL,NULL,'src\\main\\resources\\static\\default_profile_picture\\default_profile_picture.jpg',0,'$2a$10$q11/ghA0I54DzdFQTzzZlO629dLNzla0.jEwWlU63y/YGE8e.jNPe',0,0,'2022-10-25 17:59:48',0,0);
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

-- Dump completed on 2022-10-26 15:36:45
