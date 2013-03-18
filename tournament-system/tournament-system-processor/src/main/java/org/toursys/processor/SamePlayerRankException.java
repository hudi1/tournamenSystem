package org.toursys.processor;

import org.toursys.repository.model.PlayerResult;

public class SamePlayerRankException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private PlayerResult player1;
    private PlayerResult player2;

    public SamePlayerRankException(PlayerResult player1, PlayerResult player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public PlayerResult getPlayer1() {
        return player1;
    }

    public void setPlayer1(PlayerResult player1) {
        this.player1 = player1;
    }

    public PlayerResult getPlayer2() {
        return player2;
    }

    public void setPlayer2(PlayerResult player2) {
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
