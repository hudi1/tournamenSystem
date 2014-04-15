package org.toursys.processor.sorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.toursys.processor.comparators.SkParticipantComparator;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Tournament;

public class SkTournamentSorter extends TournamentSorter {

    public SkTournamentSorter(Tournament tournament) {
        this.tournament = tournament;
    }

    // (1) Pre určovanie poradia hráčov v skupine sú rozhodujúce nasledovné kritéria:
    // a) body,
    // b) vzájomný zápas, resp. body za vzájomné zápasy,
    // c) rozdiel skóre zo vzájomných zápasov,
    // d) rozdiel celkového skóre,
    // e) počet strelených gólov celkom,
    // f) počet vyhraných zápasov,
    // g) hra o „zlatý gól“.
    @Override
    public void sort(List<Participant> participants) {
        sortParticipants(participants, false);
        setRanks(participants);
    }

    private void sortParticipants(List<Participant> participants, boolean innerSort) {
        logger.debug("Start Sort participants: " + Arrays.toString(participants.toArray()));

        if (participants.isEmpty()) {
            return;
        }

        if (!innerSort) {
            logger.debug("Basic sort");
            calculateParticipants(participants);
            Collections.sort(participants, new SkParticipantComparator());
        } else {
            logger.debug("Inner sort");
            calculateInnerParticipantsPoint(participants);
            Collections.sort(participants, new SkParticipantComparator());
            calculateParticipants(participants);
        }

        doInnerSort(participants);
    }

    private void doInnerSort(List<Participant> participants) {
        logger.debug("Inner sort: " + Arrays.toString(participants.toArray()));
        List<Participant> temporatyParticipant = new ArrayList<Participant>();
        temporatyParticipant.add(participants.get(0));

        for (int i = 0; i < participants.size() - 1; i++) {
            if (participants.get(i).getPoints() == participants.get(i + 1).getPoints()) {
                temporatyParticipant.add(participants.get(i + 1));
            }

            if (participants.get(i).getPoints() != participants.get(i + 1).getPoints()
                    || (i == participants.size() - 2)) {
                if (temporatyParticipant.size() > 2) {
                    if (temporatyParticipant.size() != participants.size()) {
                        sortParticipants(temporatyParticipant, true);
                    }
                }
                temporatyParticipant.clear();
                temporatyParticipant.add(participants.get(i + 1));
            }
        }
    }

    private void setRanks(List<Participant> participants) {
        for (int i = 0; i < participants.size(); i++) {
            participants.get(i).setRank(i + 1);
        }
    }
}