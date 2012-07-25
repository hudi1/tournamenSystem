package org.toursys.repository.model;

import java.io.Serializable;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class Season implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private long seasonId;

    private String name;

    public Season() {
    }

    public long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(long seasonId) {
        this.seasonId = seasonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Season clone() {
        Season season = new Season();
        season.setSeasonId(getSeasonId());
        season.setName(getName());
        return season;
    }

}
