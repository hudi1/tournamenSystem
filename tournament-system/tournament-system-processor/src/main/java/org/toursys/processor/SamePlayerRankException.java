package org.toursys.processor;

import java.util.ArrayList;
import java.util.List;

import org.toursys.repository.model.Participant;

public class SamePlayerRankException extends TournamentException {

    private static final long serialVersionUID = 1L;
    private List<Participant> players;

    public SamePlayerRankException() {
        players = new ArrayList<Participant>();
    }

    public SamePlayerRankException(List<Participant> players) {
        this.players = new ArrayList<Participant>(players);
    }

    public SamePlayerRankException(String message) {
        super(message);
    }

    public SamePlayerRankException(Throwable cause) {
        super(cause);
    }

    public SamePlayerRankException(String message, Throwable cause) {
        super(message, cause);
    }

    public List<Participant> getPlayers() {
        return players;
    }

}
