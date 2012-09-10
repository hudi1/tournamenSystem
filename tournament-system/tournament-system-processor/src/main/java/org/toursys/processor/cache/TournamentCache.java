package org.toursys.processor.cache;

import java.util.List;
import java.util.Map;

import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Table;

public class TournamentCache {

    private static TournamentCache instance = new TournamentCache();

    private Map<String, List<Table>> tables;
    private Map<Table, List<PlayerResult>> players;

    private TournamentCache() {
    }

    public static synchronized TournamentCache getInstance() {
        return instance;
    }

    public Map<String, List<Table>> getTables() {
        return tables;
    }

    public void setTables(Map<String, List<Table>> tables) {
        this.tables = tables;
    }

    public Map<Table, List<PlayerResult>> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Table, List<PlayerResult>> players) {
        this.players = players;
    }

}
