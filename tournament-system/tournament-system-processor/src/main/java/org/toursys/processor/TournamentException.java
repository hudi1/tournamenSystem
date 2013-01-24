package org.toursys.processor;

public class TournamentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TournamentException() {
        super();
    }

    public TournamentException(String message, Throwable cause) {
        super(message, cause);
    }

    public TournamentException(String message) {
        super(message);
    }

    public TournamentException(Throwable cause) {
        super(cause);
    }

}
