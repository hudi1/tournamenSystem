-- phpMyAdmin SQL Dump
-- version 3.3.10deb1
-- http://www.phpmyadmin.net
--
-- Počítač: localhost
-- Vygenerováno: Sobota 14. července 2012, 18:31
-- Verze MySQL: 5.1.63
-- Verze PHP: 5.3.5-1ubuntu7.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databáze: `tournament_system`
--

-- --------------------------------------------------------

--
-- Struktura tabulky `GAME`
--

CREATE TABLE IF NOT EXISTS `GAME` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `player_result_id` int(11) NOT NULL,
  `opponent_id` int(11) NOT NULL,
  `result_id` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `result_id` (`result_id`),
  KEY `player_result_id` (`player_result_id`),
  KEY `opponent_id` (`opponent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Vypisuji data pro tabulku `GAME`
--


-- --------------------------------------------------------

--
-- Struktura tabulky `PLAYER`
--

CREATE TABLE IF NOT EXISTS `PLAYER` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `SURNAME` varchar(255) NOT NULL,
  `CLUB` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Vypisuji data pro tabulku `PLAYER`
--


-- --------------------------------------------------------

--
-- Struktura tabulky `PLAYER_RESULT`
--

CREATE TABLE IF NOT EXISTS `PLAYER_RESULT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `POINTS` int(11) DEFAULT NULL,
  `RANK` int(11) DEFAULT NULL,
  `TOURNAMENT_TABLE_ID` int(11) NOT NULL,
  `PLAYER_ID` int(11) NOT NULL,
  `SCORE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `TOURNAMENT_TABLE_ID` (`TOURNAMENT_TABLE_ID`),
  KEY `PLAYER_ID` (`PLAYER_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Vypisuji data pro tabulku `PLAYER_RESULT`
--


-- --------------------------------------------------------

--
-- Struktura tabulky `RESULT`
--

CREATE TABLE IF NOT EXISTS `RESULT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LEFT_SIDE` int(11) NOT NULL,
  `RIGHT_SIDE` int(11) NOT NULL,
  `OVERTIME` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Vypisuji data pro tabulku `RESULT`
--


-- --------------------------------------------------------

--
-- Struktura tabulky `SEASON`
--

CREATE TABLE IF NOT EXISTS `SEASON` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Vypisuji data pro tabulku `SEASON`
--


-- --------------------------------------------------------

--
-- Struktura tabulky `TOURNAMENT`
--

CREATE TABLE IF NOT EXISTS `TOURNAMENT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `SEASON_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `SEASON_ID` (`SEASON_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Vypisuji data pro tabulku `TOURNAMENT`
--


-- --------------------------------------------------------

--
-- Struktura tabulky `TOURNAMENT_TABLE`
--

CREATE TABLE IF NOT EXISTS `TOURNAMENT_TABLE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `INDEX_OF_FIRST_HOCKEY` int(11) DEFAULT NULL,
  `NUMBER_OF_HOCKEY` int(11) DEFAULT NULL,
  `TABLE_TYPE` char(1) NOT NULL,
  `TOURNAMENT_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `TOURNAMENT_ID` (`TOURNAMENT_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Vypisuji data pro tabulku `TOURNAMENT_TABLE`
--


--
-- Omezení pro exportované tabulky
--

--
-- Omezení pro tabulku `GAME`
--
ALTER TABLE `GAME`
  ADD CONSTRAINT `GAME_ibfk_3` FOREIGN KEY (`result_id`) REFERENCES `RESULT` (`ID`),
  ADD CONSTRAINT `GAME_ibfk_1` FOREIGN KEY (`player_result_id`) REFERENCES `PLAYER_RESULT` (`ID`),
  ADD CONSTRAINT `GAME_ibfk_2` FOREIGN KEY (`opponent_id`) REFERENCES `PLAYER_RESULT` (`ID`);

--
-- Omezení pro tabulku `PLAYER_RESULT`
--
ALTER TABLE `PLAYER_RESULT`
  ADD CONSTRAINT `PLAYER_RESULT_ibfk_2` FOREIGN KEY (`PLAYER_ID`) REFERENCES `PLAYER` (`ID`),
  ADD CONSTRAINT `PLAYER_RESULT_ibfk_1` FOREIGN KEY (`TOURNAMENT_TABLE_ID`) REFERENCES `TOURNAMENT` (`ID`);

--
-- Omezení pro tabulku `TOURNAMENT`
--
ALTER TABLE `TOURNAMENT`
  ADD CONSTRAINT `TOURNAMENT_ibfk_1` FOREIGN KEY (`SEASON_ID`) REFERENCES `SEASON` (`ID`);

--
-- Omezení pro tabulku `TOURNAMENT_TABLE`
--
ALTER TABLE `TOURNAMENT_TABLE`
  ADD CONSTRAINT `TOURNAMENT_TABLE_ibfk_1` FOREIGN KEY (`TOURNAMENT_ID`) REFERENCES `TOURNAMENT` (`ID`);
