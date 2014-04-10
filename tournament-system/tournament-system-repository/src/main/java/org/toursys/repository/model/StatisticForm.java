package org.toursys.repository.model;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class StatisticForm {

    private User user;
    private Tournament tournament;
    private Season season;
    private Player player;
    private Groups group;

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

    @Override
    public String toString() {
        return "StatisticForm [user=" + user + ", tournament=" + tournament + ", season=" + season + ", player="
                + player + ", group=" + group + "]";
    }

}
