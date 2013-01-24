-- Database: `tournament_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `GAME`
--

CREATE TABLE IF NOT EXISTS `GAME` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HOME_PLAYER_RESULT_ID` int(11) NOT NULL,
  `AWAY_PLAYER_RESULT_ID` int(11) NOT NULL,
  `HOME_SCORE` int(4) DEFAULT NULL,
  `AWAY_SCORE` int(4) DEFAULT NULL,
  `OVERTIME` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `HOME_PLAYER_RESULT_ID` (`HOME_PLAYER_RESULT_ID`),
  KEY `AWAY_PLAYER_RESULT_ID` (`AWAY_PLAYER_RESULT_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Table structure for table `GROUPS`
--

CREATE TABLE IF NOT EXISTS `GROUPS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `NUMBER_OF_HOCKEY` int(4) NOT NULL DEFAULT '1',
  `GROUP_TYPE` enum('B','F','P') NOT NULL DEFAULT 'B',
  `INDEX_OF_FIRST_HOCKEY` int(4) NOT NULL DEFAULT '1',
  `TOURNAMENT_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME` (`NAME`,`TOURNAMENT_ID`),
  KEY `TOURNAMENT_ID` (`TOURNAMENT_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Table structure for table `PLAYER`
--

CREATE TABLE IF NOT EXISTS `PLAYER` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `SURNAME` varchar(255) NOT NULL,
  `CLUB` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Table structure for table `PLAYER_RESULT`
--

CREATE TABLE IF NOT EXISTS `PLAYER_RESULT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `POINTS` int(4) NOT NULL DEFAULT '0',
  `RANK` int(4) DEFAULT NULL,
  `GROUP_ID` int(11) NOT NULL,
  `PLAYER_ID` int(11) NOT NULL,
  `SCORE` varchar(10) NOT NULL DEFAULT '0:0',
  PRIMARY KEY (`ID`),
  KEY `PLAYER_ID` (`PLAYER_ID`),
  KEY `GROUP_ID` (`GROUP_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Table structure for table `PLAY_OFF_GAME`
--

CREATE TABLE IF NOT EXISTS `PLAY_OFF_GAME` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HOME_PLAYER_ID` int(11) NOT NULL,
  `AWAY_PLAYER_ID` int(11) NOT NULL,
  `HOME_SCORE` int(4) DEFAULT NULL,
  `AWAY_SCORE` int(4) DEFAULT NULL,
  `OVERTIME` tinyint(1) NOT NULL,
  `TOURNAMENT_ID` int(11) NOT NULL,
  `POSITION` int(4) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `HOME_PLAYER_ID` (`HOME_PLAYER_ID`),
  KEY `AWAY_PLAYER_ID` (`AWAY_PLAYER_ID`),
  KEY `TOURNAMENT_ID` (`TOURNAMENT_ID`),
  KEY `POSITION` (`POSITION`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Table structure for table `SEASON`
--

CREATE TABLE IF NOT EXISTS `SEASON` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Table structure for table `TOURNAMENT`
--

CREATE TABLE IF NOT EXISTS `TOURNAMENT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `SEASON_ID` int(11) NOT NULL,
  `FINAL_PROMOTING` int(4) NOT NULL DEFAULT '6',
  `LOWER_PROMOTING` int(4) NOT NULL DEFAULT '5',
  `WIN_POINTS` int(1) NOT NULL DEFAULT '2',
  `PLAY_OFF_A` int(4) NOT NULL DEFAULT '16',
  `PLAY_OFF_LOWER` int(4) NOT NULL DEFAULT '8',
  PRIMARY KEY (`ID`),
  KEY `SEASON_ID` (`SEASON_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `GAME`
--
ALTER TABLE `GAME`
  ADD CONSTRAINT `GAME_ibfk_1` FOREIGN KEY (`HOME_PLAYER_RESULT_ID`) REFERENCES `PLAYER_RESULT` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `GAME_ibfk_2` FOREIGN KEY (`AWAY_PLAYER_RESULT_ID`) REFERENCES `PLAYER_RESULT` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `GROUPS`
--
ALTER TABLE `GROUPS`
  ADD CONSTRAINT `GROUPS_ibfk_1` FOREIGN KEY (`TOURNAMENT_ID`) REFERENCES `TOURNAMENT` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `PLAYER_RESULT`
--
ALTER TABLE `PLAYER_RESULT`
  ADD CONSTRAINT `PLAYER_RESULT_ibfk_1` FOREIGN KEY (`GROUP_ID`) REFERENCES `GROUPS` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `PLAYER_RESULT_ibfk_2` FOREIGN KEY (`PLAYER_ID`) REFERENCES `PLAYER` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `PLAY_OFF_GAME`
--
ALTER TABLE `PLAY_OFF_GAME`
  ADD CONSTRAINT `PLAY_OFF_GAME_ibfk_3` FOREIGN KEY (`TOURNAMENT_ID`) REFERENCES `TOURNAMENT` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `PLAY_OFF_GAME_ibfk_1` FOREIGN KEY (`HOME_PLAYER_ID`) REFERENCES `PLAYER` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `PLAY_OFF_GAME_ibfk_2` FOREIGN KEY (`AWAY_PLAYER_ID`) REFERENCES `PLAYER_RESULT` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `TOURNAMENT`
--
ALTER TABLE `TOURNAMENT`
  ADD CONSTRAINT `TOURNAMENT_ibfk_1` FOREIGN KEY (`SEASON_ID`) REFERENCES `SEASON` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;