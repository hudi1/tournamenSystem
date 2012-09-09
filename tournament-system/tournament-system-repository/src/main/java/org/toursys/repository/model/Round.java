package org.toursys.repository.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Round {

    private List<Game> games;

    public Round() {
        this.games = new ArrayList<Game>();
    }

    public Iterator<Game> getIteratorKola() {
        return games.iterator();
    }

    public List<Game> getGames() {
        if (games == null) {
            games = new ArrayList<Game>();
        }
        return games;
    }
}
