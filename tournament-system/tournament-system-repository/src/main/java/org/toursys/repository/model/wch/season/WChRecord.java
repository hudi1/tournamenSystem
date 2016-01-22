package org.toursys.repository.model.wch.season;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.toursys.repository.model.wch.WChSeasonRecord;
import org.toursys.repository.model.wch.WChTournamentDetailsRecord;

public class WChRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, WChSeasonRecord> wchSeasonRecords;
    private Integer totalPoints;
    private String playerName;

    public WChRecord() {
        wchSeasonRecords = new HashMap<String, WChSeasonRecord>();
    }

    public Map<String, WChSeasonRecord> getWchSeasonRecords() {
        return wchSeasonRecords;
    }

    public List<WChSeasonRecord> getSeasonRecordValues() {
        return new ArrayList<WChSeasonRecord>(wchSeasonRecords.values());
    }

    public List<WChTournamentDetailsRecord> getTournamentDetails() {
        List<WChTournamentDetailsRecord> records = new LinkedList<WChTournamentDetailsRecord>();
        for (Map.Entry<String, WChSeasonRecord> entry : wchSeasonRecords.entrySet()) {
            for (WChTournamentDetailsRecord detail : entry.getValue().getTournamentDetails()) {
                records.add(detail);
            }
            records.add(entry.getValue().getNationalChampionship());
        }
        return records;
    }

    public void setWchSeasonRecords(Map<String, WChSeasonRecord> wchSeasonRecords) {
        this.wchSeasonRecords = wchSeasonRecords;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
