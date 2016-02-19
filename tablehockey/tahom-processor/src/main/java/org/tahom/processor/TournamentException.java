package org.tahom.processor;

public abstract class TournamentException extends RuntimeException {

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

    /**
     * Returns the code of the exception as passed on to the frontend.
     * 
     * @return
     */
    abstract public String getCode();

}
