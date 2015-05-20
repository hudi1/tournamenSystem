package org.toursys.repository.dao.helper;

import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsPlayOffType;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.TournamentSortType;
import org.toursys.repository.model.User;
import org.toursys.repository.model.UserRole;

public class TournamentFactory {

    public static final String TOURNAMENT_NAME = "Kosice Open";
    public static final String SEASONE_NAME = "2012";
    public static final String GROUP_NAME = "1";

    public static final String PLAYER_NAME = "Tomáš";
    public static final String PLAYER_CLUB = "THC Blue Dragon";
    public static final String PLAYER_SURNAME = "Hudec1";

    public static final String USER_NAME = "Tomáš";
    public static final String USER_SURNAME = "Hudec";
    public static final String USER_USERNAME = "111hudi1test1";
    public static final String USER_PASSWORD = "travian";
    public static final int USER_PLATNOST = 0;
    public static final String USER_EMAIL = "111hudi1star@gmail.com";

    public static User createUser() {
        return new User(USER_EMAIL, USER_USERNAME, USER_PASSWORD, UserRole.USER, USER_PLATNOST);
    }

    public static Tournament createTournament() {
        return new Tournament(TOURNAMENT_NAME, null, 6, 5, 2, 16, 8, 0, TournamentSortType.SK, false);
    }

    public static Season createSeason() {
        return new Season(SEASONE_NAME, null);
    }

    public static Player createPlayer() {
        return new Player(PLAYER_NAME, PLAYER_SURNAME, null, null);
    }

    public static Groups createGroup() {
        return new Groups(GROUP_NAME, 5, GroupsType.BASIC, 1, null, true, true, true, GroupsPlayOffType.LOWER);
    }
}
