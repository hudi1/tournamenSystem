SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `final_standing`;
DROP TABLE IF EXISTS `game`;
DROP TABLE IF EXISTS `groups`;
DROP TABLE IF EXISTS `player`;
DROP TABLE IF EXISTS `participant`;
DROP TABLE IF EXISTS `play_off_game`;
DROP TABLE IF EXISTS `season`;
DROP TABLE IF EXISTS `tournament`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `wch_qualification`;
DROP TABLE IF EXISTS `wch_tournament`;
SET FOREIGN_KEY_CHECKS=1;

-- --------------------------------------------------------

--
-- Table structure for table `final_standing`
--

CREATE TABLE IF NOT EXISTS `final_standing` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PLAYER_ID` int(11) DEFAULT NULL,
  `FINAL_RANK` int(11) NOT NULL,
  `TOURNAMENT_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `FINAL_RANK` (`FINAL_RANK`,`TOURNAMENT_ID`),
  KEY `TOURNAMENT_ID` (`TOURNAMENT_ID`),
  KEY `PLAYER_ID` (`PLAYER_ID`),
  KEY `FINAL_RANK_2` (`FINAL_RANK`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `game`
--

CREATE TABLE IF NOT EXISTS `game` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HOME_PARTICIPANT_ID` int(11) DEFAULT NULL,
  `AWAY_PARTICIPANT_ID` int(11) DEFAULT NULL,
  `STATUS` enum('WIN','LOSE','DRAW') DEFAULT NULL,  
  `RESULT` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `HOME_PARTICIPANT_ID` (`HOME_PARTICIPANT_ID`),
  KEY `AWAY_PARTICIPANT_ID` (`AWAY_PARTICIPANT_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `NUMBER_OF_HOCKEY` int(4) NOT NULL DEFAULT '1',
  `TYPE` enum('B','F','P') NOT NULL DEFAULT 'B',
  `INDEX_OF_FIRST_HOCKEY` int(4) NOT NULL DEFAULT '1',
  `TOURNAMENT_ID` int(11) NOT NULL,
  `COPY_RESULT` tinyint(1) NOT NULL DEFAULT '0',
  `PLAY_THIRD_PLACE` tinyint(1) NOT NULL DEFAULT '0',
  `PLAY_OFF` tinyint(1) NOT NULL DEFAULT '1',
  `PLAY_OFF_TYPE` enum('F','L','C') NOT NULL DEFAULT 'L',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME` (`NAME`,`TOURNAMENT_ID`),
  KEY `TOURNAMENT_ID` (`TOURNAMENT_ID`),
  KEY `NAME_2` (`NAME`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `participant`
--

CREATE TABLE IF NOT EXISTS `participant` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `POINTS` int(4) NOT NULL DEFAULT '0',
  `RANK` int(4) DEFAULT NULL,
  `GROUP_ID` int(11) NOT NULL,
  `PLAYER_ID` int(11) NOT NULL,
  `SCORE` varchar(10) NOT NULL DEFAULT '0:0',
  `EQUAL_RANK` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `PLAYER_ID` (`PLAYER_ID`),
  KEY `GROUP_ID` (`GROUP_ID`),
  KEY `RANK` (`RANK`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE IF NOT EXISTS `player` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `SURNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `CLUB` varchar(255) DEFAULT NULL,
  `WORLD_RANKING` int(11) DEFAULT NULL,
  `USER_ID` int(11) NOT NULL,
  `ITHF_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME` (`NAME`,`SURNAME`,`USER_ID`),
  KEY `USER_ID` (`USER_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `play_off_game`
--

CREATE TABLE IF NOT EXISTS `play_off_game` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HOME_PARTICIPANT_ID` int(11) DEFAULT NULL,
  `AWAY_PARTICIPANT_ID` int(11) DEFAULT NULL,
  `STATUS` enum('WIN','LOSE','DRAW') DEFAULT NULL,
  `RESULT` varchar(50) DEFAULT NULL,
  `GROUP_ID` int(11) NOT NULL,
  `POSITION` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `HOME_PARTICIPANT_ID` (`HOME_PARTICIPANT_ID`),
  KEY `AWAY_PARTICIPANT_ID` (`AWAY_PARTICIPANT_ID`),
  KEY `GROUP_ID` (`GROUP_ID`),
  KEY `POSITION` (`POSITION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `season`
--

CREATE TABLE IF NOT EXISTS `season` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `USER_ID` (`USER_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `tournament`
--

CREATE TABLE IF NOT EXISTS `tournament` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `SEASON_ID` int(11) NOT NULL,
  `FINAL_PROMOTING` int(4) NOT NULL DEFAULT '6',
  `LOWER_PROMOTING` int(4) NOT NULL DEFAULT '5',
  `WIN_POINTS` int(1) NOT NULL DEFAULT '2',
  `PLAY_OFF_FINAL` int(4) NOT NULL DEFAULT '16',
  `PLAY_OFF_LOWER` int(4) NOT NULL DEFAULT '8',
  `MIN_PLAYERS_IN_GROUP` int(11) NOT NULL DEFAULT '4',
  `SORT_TYPE` enum('SK','CZ') NOT NULL DEFAULT 'SK',
  `OPEN` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `SEASON_ID` (`SEASON_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  `SURNAME` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) NOT NULL,
  `USER_NAME` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `ROLE` enum('ADMIN','USER') NOT NULL DEFAULT 'USER',
  `VALIDITY` int(11) NOT NULL DEFAULT '0',
  `OPEN` tinyint(1) NOT NULL DEFAULT '0',  
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USER_NAME` (`USER_NAME`),
  UNIQUE KEY `EMAIL` (`EMAIL`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `wch_qualification`
--

CREATE TABLE IF NOT EXISTS `wch_qualification` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ITHF_ID` int(11) NOT NULL,
  `LAST_UPDATE` date NOT NULL,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ITHF_ID` (`ITHF_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `wch_tournament`
--

CREATE TABLE IF NOT EXISTS `wch_tournament` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `WCH_QUALIFICATION_ID` int(11) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `SERIES` varchar(255) DEFAULT NULL,
  `DATE` date NOT NULL,
  `POINTS` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `WCH_QUALIFICATION_ID` (`WCH_QUALIFICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `final_standing`
--
ALTER TABLE `final_standing`
  ADD CONSTRAINT `FINAL_STANDING_ibfk_1` FOREIGN KEY (`TOURNAMENT_ID`) REFERENCES `tournament` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FINAL_STANDING_ibfk_2` FOREIGN KEY (`PLAYER_ID`) REFERENCES `player` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `game`
--
ALTER TABLE `game`
  ADD CONSTRAINT `GAME_ibfk_1` FOREIGN KEY (`HOME_PARTICIPANT_ID`) REFERENCES `participant` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `GAME_ibfk_2` FOREIGN KEY (`AWAY_PARTICIPANT_ID`) REFERENCES `participant` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `groups`
--
ALTER TABLE `groups`
  ADD CONSTRAINT `GROUPS_ibfk_1` FOREIGN KEY (`TOURNAMENT_ID`) REFERENCES `tournament` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `participant`
--
ALTER TABLE `participant`
  ADD CONSTRAINT `PARTICIPANT_ibfk_1` FOREIGN KEY (`GROUP_ID`) REFERENCES `groups` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `PARTICIPANT_ibfk_2` FOREIGN KEY (`PLAYER_ID`) REFERENCES `player` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `player`
--
ALTER TABLE `player`
  ADD CONSTRAINT `PLAYER_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `play_off_game`
--
ALTER TABLE `play_off_game`
  ADD CONSTRAINT `play_off_game_ibfk_1` FOREIGN KEY (`HOME_PARTICIPANT_ID`) REFERENCES `participant` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `play_off_game_ibfk_2` FOREIGN KEY (`AWAY_PARTICIPANT_ID`) REFERENCES `participant` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `play_off_game_ibfk_3` FOREIGN KEY (`GROUP_ID`) REFERENCES `groups` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `season`
--
ALTER TABLE `season`
  ADD CONSTRAINT `SEASON_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tournament`
--
ALTER TABLE `tournament`
  ADD CONSTRAINT `TOURNAMENT_ibfk_1` FOREIGN KEY (`SEASON_ID`) REFERENCES `season` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `wch_tournament`
--
ALTER TABLE `wch_tournament`
  ADD CONSTRAINT `WCH_TOURNAMENT_ibfk_1` FOREIGN KEY (`WCH_QUALIFICATION_ID`) REFERENCES `wch_qualification` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;