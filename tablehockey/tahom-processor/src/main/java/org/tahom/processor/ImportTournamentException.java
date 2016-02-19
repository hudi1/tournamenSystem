package org.tahom.processor;

import org.tahom.processor.util.MessaceCodes;

public class ImportTournamentException extends TournamentException {

    private static final long serialVersionUID = 1L;

    private final String CODE = MessaceCodes.IMPORT;

    public ImportTournamentException() {
        super();
    }

    public ImportTournamentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImportTournamentException(String message) {
        super(message);
    }

    public ImportTournamentException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getCode() {
        return CODE;
    }

}
