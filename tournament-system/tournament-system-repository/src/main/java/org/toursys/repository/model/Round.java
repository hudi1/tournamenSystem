package org.toursys.repository.model;

import java.util.ArrayList;
import java.util.List;

public class Round {

    private List<Game> games;

    public Round() {
        this.games = new ArrayList<Game>();
    }

    public List<Game> getGames() {
        if (games == null) {
            games = new ArrayList<Game>();
        }
        return games;
    }
}
