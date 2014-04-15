package org.toursys.processor.sorter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Tournament;

import com.thoughtworks.xstream.XStream;

public class SkTournamentSorterTest {

    private TournamentSorter sorter;
    private List<Participant> participants = new ArrayList<Participant>();
    private XStream xstream = new XStream();

    @Before
    public void setup() {
        sorter = new SkTournamentSorter(new Tournament()._setWinPoints(2));
    }

    @Test
    public void testInnerSortCriterium() throws Exception {
        readData("innerSortData.xml");

        sorter.sort(participants);

        for (Participant participant : participants) {
            switch (participant.getId()) {
            case 10:
                checkParticipant(participant, 5, "8:8", 0, 9);
                break;
            case 11:
                checkParticipant(participant, 2, "5:4", null, 10);
                break;
            case 12:
                checkParticipant(participant, 4, "5:4", null, 10);
                break;
            case 13:
                checkParticipant(participant, 1, "5:4", null, 10);
                break;
            case 14:
                checkParticipant(participant, 3, "5:4", null, 10);
                break;
            case 15:
                checkParticipant(participant, 8, "4:5", null, 8);
                break;
            case 16:
                checkParticipant(participant, 10, "4:5", null, 8);
                break;
            case 17:
                checkParticipant(participant, 7, "4:5", null, 8);
                break;
            case 18:
                checkParticipant(participant, 9, "4:5", null, 8);
                break;
            case 19:
                checkParticipant(participant, 6, "8:8", 0, 9);
                break;
            }
        }
    }

    private void checkParticipant(Participant participant, Integer rank, String score, Integer equalRank, Integer points) {
        Assert.assertEquals(participant.getRank(), rank);
        Assert.assertEquals(participant.getScore().toString(), score);
        Assert.assertEquals(participant.getEqualRank(), equalRank);
        Assert.assertEquals(participant.getPoints(), points);
    }

    private void readData(String file) throws Exception {
        String xml = FileUtils.readFileToString(new File("src/test/resources/xml/" + file), "UTF-8");
        participants = (List<Participant>) xstream.fromXML(xml);
    }
}
