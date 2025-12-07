-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hostiteľ: 127.0.0.1
-- Čas generovania: Sun 07.Dec 2025, 17:41
-- Verzia serveru: 10.4.32-MariaDB
-- Verzia PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáza: `evidencia_klientov`
--
CREATE DATABASE IF NOT EXISTS `evidencia_klientov` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `evidencia_klientov`;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `klienti`
--

CREATE TABLE IF NOT EXISTS `klienti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `krstne_meno` varchar(50) DEFAULT NULL,
  `priezvisko` varchar(50) DEFAULT NULL,
  `datum_narodenia` date DEFAULT NULL,
  `telefonne_cislo` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `adresa` varchar(255) DEFAULT NULL,
  `datum_registracie` date DEFAULT curdate(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
