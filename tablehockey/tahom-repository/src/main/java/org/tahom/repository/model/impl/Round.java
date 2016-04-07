package org.tahom.repository.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.tahom.repository.model.Game;

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
