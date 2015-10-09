package org.toursys.repository.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WChRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Integer> tournamentPoints1 = new ArrayList<Integer>();
    private List<Integer> tournamentPoints2 = new ArrayList<Integer>();
    private Integer slovakChampionship1points;
    private Integer slovakChampionship2points;
    private Integer total;
    private String name;

    public List<Integer> getTournamentPoints1() {
        return tournamentPoints1;
    }

    public List<Integer> getTournamentPoints2() {
        return tournamentPoints2;
    }

    public Integer getSlovakChampionship1points() {
        return slovakChampionship1points;
    }

    public void setSlovakChampionship1points(Integer slovakChampionship1points) {
        this.slovakChampionship1points = slovakChampionship1points;
    }

    public Integer getSlovakChampionship2points() {
        return slovakChampionship2points;
    }

    public void setSlovakChampionship2points(Integer slovakChampionship2points) {
        this.slovakChampionship2points = slovakChampionship2points;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
