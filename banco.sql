-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: banco
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `conta`
--

DROP TABLE IF EXISTS `conta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `conta` (
  `id` int(11) NOT NULL,
  `criacao` datetime DEFAULT NULL,
  `saldo` double DEFAULT NULL,
  `usuario` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conta`
--

LOCK TABLES `conta` WRITE;
/*!40000 ALTER TABLE `conta` DISABLE KEYS */;
INSERT INTO `conta` VALUES (1,'2018-10-27 15:00:00',0,'111.111.111-11');
/*!40000 ALTER TABLE `conta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `id_gen`
--

DROP TABLE IF EXISTS `id_gen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `id_gen` (
  `id_name` varchar(255) NOT NULL,
  `id_val` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `id_gen`
--

LOCK TABLES `id_gen` WRITE;
/*!40000 ALTER TABLE `id_gen` DISABLE KEYS */;
INSERT INTO `id_gen` VALUES ('ContaAtualId',1),('TransacaoAtualId',0);
/*!40000 ALTER TABLE `id_gen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_transacoes`
--

DROP TABLE IF EXISTS `tipos_transacoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tipos_transacoes` (
  `tipo` varchar(255) NOT NULL,
  `camposUtilizados` int(11) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_transacoes`
--

LOCK TABLES `tipos_transacoes` WRITE;
/*!40000 ALTER TABLE `tipos_transacoes` DISABLE KEYS */;
INSERT INTO `tipos_transacoes` VALUES ('Depósito',2,'Colocar dinheiro em uma conta'),('Saque',1,'Retirar dinheiro de uma conta'),('Transferência',3,'Transferir de uma conta para outra');
/*!40000 ALTER TABLE `tipos_transacoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_usuarios`
--

DROP TABLE IF EXISTS `tipos_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tipos_usuarios` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_usuarios`
--

LOCK TABLES `tipos_usuarios` WRITE;
/*!40000 ALTER TABLE `tipos_usuarios` DISABLE KEYS */;
INSERT INTO `tipos_usuarios` VALUES (1,'Gerente'),(2,'Cliente');
/*!40000 ALTER TABLE `tipos_usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transacao`
--

DROP TABLE IF EXISTS `transacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `transacao` (
  `id` int(11) NOT NULL,
  `data` datetime DEFAULT NULL,
  `valor` double DEFAULT NULL,
  `conta_destino_id` int(11) DEFAULT NULL,
  `conta_origem_id` int(11) DEFAULT NULL,
  `tipo_transacao_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkut4s9v98tg538xk99tqhncvj` (`conta_destino_id`),
  KEY `FKh9v7wctkl2o918xt7houdbgqh` (`conta_origem_id`),
  KEY `FKgpif0n4yfms987b7s85cdu1bb` (`tipo_transacao_id`),
  CONSTRAINT `FKgpif0n4yfms987b7s85cdu1bb` FOREIGN KEY (`tipo_transacao_id`) REFERENCES `tipos_transacoes` (`tipo`),
  CONSTRAINT `FKh9v7wctkl2o918xt7houdbgqh` FOREIGN KEY (`conta_origem_id`) REFERENCES `conta` (`id`),
  CONSTRAINT `FKkut4s9v98tg538xk99tqhncvj` FOREIGN KEY (`conta_destino_id`) REFERENCES `conta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transacao`
--

LOCK TABLES `transacao` WRITE;
/*!40000 ALTER TABLE `transacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `transacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usuario` (
  `cpf` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `endereco` varchar(255) DEFAULT NULL,
  `nascimento` datetime DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `conta_id` int(11) DEFAULT NULL,
  `tipo_usuario_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`cpf`),
  KEY `FKm9ew1jp3jewapkiridletuerr` (`conta_id`),
  KEY `FKih1sf09txece0px9gs5ivevxg` (`tipo_usuario_id`),
  CONSTRAINT `FKih1sf09txece0px9gs5ivevxg` FOREIGN KEY (`tipo_usuario_id`) REFERENCES `tipos_usuarios` (`id`),
  CONSTRAINT `FKm9ew1jp3jewapkiridletuerr` FOREIGN KEY (`conta_id`) REFERENCES `conta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('111.111.111-11','gabriel@gmail.com','Belo Horizonte','1995-03-23 15:00:00','Gabriel','e99a18c428cb38d5f260853678922e03',1,1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-28 13:10:02
