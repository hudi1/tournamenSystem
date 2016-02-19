package org.tahom.processor;

import org.tahom.processor.util.MessaceCodes;

public class PdfTournamentException extends TournamentException {

    private static final long serialVersionUID = 1L;

    private final String CODE = MessaceCodes.PDF;

    public PdfTournamentException() {
        super();
    }

    public PdfTournamentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdfTournamentException(String message) {
        super(message);
    }

    public PdfTournamentException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getCode() {
        return CODE;
    }

}
