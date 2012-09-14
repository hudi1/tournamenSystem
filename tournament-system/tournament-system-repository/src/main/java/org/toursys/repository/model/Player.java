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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((club == null) ? 0 : club.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (int) (playerId ^ (playerId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (club == null) {
            if (other.club != null)
                return false;
        } else if (!club.equals(other.club))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (playerId != other.playerId)
            return false;
        return true;
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

    @Override
    public String toString() {
        return "Player:" + playerId + " " + surname + " " + name;
    }
}
