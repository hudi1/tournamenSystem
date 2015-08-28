package org.toursys.processor;

import org.toursys.processor.util.MessaceCodes;

public class PlayerUpdateTournamentException extends TournamentException {

    private static final long serialVersionUID = 1L;

    private final String CODE = MessaceCodes.PLAYER;

    public PlayerUpdateTournamentException() {
        super();
    }

    public PlayerUpdateTournamentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerUpdateTournamentException(String message) {
        super(message);
    }

    public PlayerUpdateTournamentException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getCode() {
        return CODE;
    }

}
