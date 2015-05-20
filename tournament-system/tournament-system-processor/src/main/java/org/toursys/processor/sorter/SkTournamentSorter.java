package org.toursys.processor.sorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.toursys.processor.comparators.SkInnerParticipantComparator;
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
    // (2) Prednosť má vždy vyššie uvedené kritérium, ktoré možno použiť.
    // (3) Pri vzájomnej rovnosti bodov najmenej troch hráčov sa výsledky ich vzájomných zápasov
    // zapíšu do osobitnej tabuľky a poradie sa určí podľa kritérií uvedených v bode 1. // Len na prvej urovni
    @Override
    public void sort(List<Participant> participants) {
        sort(participants, 0);
    }

    public void sort(List<Participant> participants, int level) {
        sortParticipants(participants, level);
        setRanks(participants);
    }

    private void sortParticipants(List<Participant> participants, int level) {
        logger.debug("Start Sort participants: " + level + " " + Arrays.toString(participants.toArray()));

        if (participants.isEmpty()) {
            return;
        }

        if (level == 0) {
            calculateParticipants(participants);
            Collections.sort(participants, new SkParticipantComparator());
        }

        List<Participant> temporatyParticipant = new ArrayList<Participant>();
        temporatyParticipant.add(participants.get(0));

        for (int i = 0; i < participants.size() - 1; i++) {
            logger.debug("xxxxxxxxxxxx" + Arrays.toString(temporatyParticipant.toArray()));

            if (participants.get(i).getPoints() == participants.get(i + 1).getPoints()) {
                temporatyParticipant.add(participants.get(i + 1));
            }

            int position = i;
            if (participants.get(i).getPoints() != participants.get(i + 1).getPoints()
                    || (i == participants.size() - 2)) {
                if (i == participants.size() - 2) {
                    position++;
                }
                if (temporatyParticipant.size() > 2) {

                    calculateInnerParticipantsPoint(temporatyParticipant);
                    if (level < 1) {
                        Collections.sort(temporatyParticipant, new SkParticipantComparator());
                        sort(temporatyParticipant, level + 1);
                    } else {
                        Collections.sort(temporatyParticipant, new SkInnerParticipantComparator());
                    }

                    Collections.reverse(temporatyParticipant);
                    for (Participant participant : temporatyParticipant) {
                        participants.set(position, participant);
                        position--;
                    }

                    calculateParticipants(temporatyParticipant);
                }
                temporatyParticipant.clear();
                temporatyParticipant.add(participants.get(i + 1));
            }
        }

        logger.debug("End Sort participants: " + Arrays.toString(participants.toArray()));
    }

    private void setRanks(List<Participant> participants) {
        for (int i = 0; i < participants.size(); i++) {
            participants.get(i).setRank(i + 1);
        }
    }
}