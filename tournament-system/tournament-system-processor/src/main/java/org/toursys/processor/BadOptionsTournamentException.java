package org.toursys.processor;

import org.toursys.processor.util.MessaceCodes;

public class BadOptionsTournamentException extends TournamentException {

    private static final long serialVersionUID = 1L;

    private final String CODE = MessaceCodes.OPTIONS;

    public BadOptionsTournamentException() {
        super();
    }

    public BadOptionsTournamentException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadOptionsTournamentException(String message) {
        super(message);
    }

    public BadOptionsTournamentException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getCode() {
        return CODE;
    }

}
