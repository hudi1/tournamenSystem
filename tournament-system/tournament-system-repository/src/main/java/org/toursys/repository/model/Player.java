package org.toursys.repository.model;

import java.io.Serializable;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class Player implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private long playerId;

    private String name;

    private String surname;

    private String club;

    public Player() {
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    @Override
    public Player clone() {
        Player player = new Player();
        player.setClub(getClub());
        player.setName(getName());
        player.setPlayerId(getPlayerId());
        player.setSurname(getSurname());
        return player;
    }

}
