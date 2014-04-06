package org.toursys.processor.sorter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Tournament;

public class SkTournamentSorterTest {

    private TournamentSorter sorter;
    private List<Participant> participants = new ArrayList<Participant>();

    @Before
    public void setup() {
        sorter = new SkTournamentSorter(new Tournament()._setWinPoints(3));
    }

    @Test
    public void testPointCriterium() throws Exception {
        // TODO read data from file
    }
}
