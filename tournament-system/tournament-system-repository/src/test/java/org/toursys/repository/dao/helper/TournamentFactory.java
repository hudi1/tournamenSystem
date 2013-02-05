package org.toursys.repository.dao.helper;

import org.toursys.repository.model.GroupType;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;

public class TournamentFactory {

    public static final String TOURNAMENT_NAME = "Kosice Open";
    public static final String SEASONE_NAME = "2012";
    public static final String GROUP_NAME = "1";

    public static final String PLAYER_NAME = "Tomáš";
    public static final String PLAYER_CLUB = "THC Blue Dragon";
    public static final String PLAYER_SURNAME = "Hudec";

    public static Tournament createTournament() {
        Tournament tournament = new Tournament();
        tournament.setName(TOURNAMENT_NAME);
        return tournament;
    }

    public static Season createSeason() {
        Season season = new Season(SEASONE_NAME, null); // TODO
        return season;
    }

    public static Groups createGroup() {
        Groups group = new Groups();
        group.setGroupType(GroupType.B.value());
        group.setName(GROUP_NAME);
        return group;
    }

    public static Player createPlayer() {
        Player player = new Player(PLAYER_NAME, PLAYER_SURNAME);
        player.setClub(PLAYER_CLUB);
        return player;
    }

}
