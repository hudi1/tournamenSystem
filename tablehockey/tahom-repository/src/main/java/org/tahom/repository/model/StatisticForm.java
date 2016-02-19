package org.tahom.repository.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.sqlproc.engine.annotation.Pojo;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.Season;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.User;

@Pojo
public class StatisticForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private User user;
    private Tournament tournament;
    private Season season;
    private Player player;
    private Groups group;

    private List<Player> players = new ArrayList<Player>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StatisticForm _setUser(User user) {
        this.user = user;
        return this;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public StatisticForm _setTournament(Tournament tournament) {
        this.tournament = tournament;
        return this;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public StatisticForm _setSeason(Season season) {
        this.season = season;
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public StatisticForm _setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    public StatisticForm _setGroup(Groups group) {
        this.group = group;
        return this;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public StatisticForm _setPlayers(List<Player> players) {
        this.players = players;
        return this;
    }

    @Override
    public String toString() {
        return "StatisticForm [user=" + user + ", tournament=" + tournament + ", season=" + season + ", player="
                + player + ", group=" + group + "]";
    }

}
