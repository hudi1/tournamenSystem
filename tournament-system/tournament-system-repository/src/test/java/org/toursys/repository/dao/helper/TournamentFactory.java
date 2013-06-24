package org.toursys.repository.dao.helper;

import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;

public class TournamentFactory {

	public static final String TOURNAMENT_NAME = "Kosice Open";
	public static final String SEASONE_NAME = "2012";
	public static final String GROUP_NAME = "1";

	public static final String PLAYER_NAME = "Tomáš";
	public static final String PLAYER_CLUB = "THC Blue Dragon";
	public static final String PLAYER_SURNAME = "Hudec";

	public static final String USER_NAME = "Tomáš";
	public static final String USER_SURNAME = "Hudec";
	public static final String USER_USERNAME = "11hudi1test1";
	public static final String USER_PASSWORD = "travian";
	public static final int USER_PLATNOST = 0;
	public static final String USER_EMAIL = "11hudi1star@gmail.com";

	public static User createUser() {
		return new User(USER_NAME, USER_SURNAME, USER_EMAIL, USER_USERNAME,
				USER_PASSWORD, USER_PLATNOST);
	}

	public static Tournament createTournament() {
		return new Tournament(TOURNAMENT_NAME, null, 6, 5, 2, 16, 8, 0);
	}

	public static Season createSeason() {
		return new Season(SEASONE_NAME, null);
	}

	public static Player createPlayer() {
		return new Player(PLAYER_NAME, PLAYER_SURNAME, null);
	}

	public static Groups createGroup() {
		return new Groups(GROUP_NAME, 5, GroupsType.BASIC, 1, null, true,
				true);
	}

}
