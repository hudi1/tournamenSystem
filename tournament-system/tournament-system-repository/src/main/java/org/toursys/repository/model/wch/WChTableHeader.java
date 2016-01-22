package org.toursys.repository.model.wch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WChTableHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> wchTournamentHeaders;
    private List<String> wchSeasonHeaders;

    public List<String> getWchTournamentHeaders() {
        if (wchTournamentHeaders == null) {
            wchTournamentHeaders = new ArrayList<String>();
        }
        return wchTournamentHeaders;
    }

    public List<String> getWchSeasonHeaders() {
        if (wchSeasonHeaders == null) {
            wchSeasonHeaders = new ArrayList<String>();
        }
        return wchSeasonHeaders;
    }

}
