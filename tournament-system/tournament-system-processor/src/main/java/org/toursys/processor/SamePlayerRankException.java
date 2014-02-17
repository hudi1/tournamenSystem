package org.toursys.processor;

import org.toursys.repository.model.Participant;

public class SamePlayerRankException extends TournamentException {

    private static final long serialVersionUID = 1L;
    private Participant player1;
    private Participant player2;

    public SamePlayerRankException(Participant player1, Participant player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public Participant getPlayer1() {
        return player1;
    }

    public void setPlayer1(Participant player1) {
        this.player1 = player1;
    }

    public Participant getPlayer2() {
        return player2;
    }

    public void setPlayer2(Participant player2) {
        this.player2 = player2;
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
}
