package org.toursys.processor.sorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.toursys.processor.comparators.RankComparator;
import org.toursys.processor.comparators.SkParticipantComparator;
import org.toursys.repository.model.Game;
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
    // Pri vzájomnej rovnosti bodov najmenej troch hráčov sa výsledky ich vzájomných zápasov
    // zapíšu do osobitnej tabuľky a poradie sa určí podľa kritérií uvedených v bode 1.
    @Override
    public void sort(List<Participant> participants) {

        if (participants.isEmpty()) {
            return;
        }

        for (Participant participant : participants) {
            calculateParticipant(participant);
        }

        Collections.sort(participants, new SkParticipantComparator());

        for (int i = 0; i < participants.size(); i++) {
            participants.get(i).setRank(i + 1);
        }

        List<Participant> temporatyParticipant = new ArrayList<Participant>();

        temporatyParticipant.add(cloneParticipant(participants.get(0)));
        int actualRank = 0;
        for (int i = 0; i < participants.size() - 1; i++) {
            if (participants.get(i).getPoints() == participants.get(i + 1).getPoints()) {
                temporatyParticipant.add(cloneParticipant(participants.get(i + 1)));
            }

            if (participants.get(i).getPoints() != participants.get(i + 1).getPoints()
                    || (i == participants.size() - 2)) {
                if (temporatyParticipant.size() > 2) {

                    Collections.sort(temporatyParticipant, new SkParticipantComparator(true, false));

                    for (Participant participant : temporatyParticipant) {
                        boolean delGame = true;
                        List<Game> g1 = new ArrayList<Game>(participant.getGames());
                        for (Game game : g1) {
                            for (Participant participant2 : temporatyParticipant) {
                                if (participant2.equals(game.getAwayParticipant())) {
                                    delGame = false;
                                    break;
                                }
                            }
                            if (delGame) {
                                participant.getGames().remove(game);
                            }
                            delGame = true;
                        }
                    }
                    for (Participant participant : temporatyParticipant) {
                        calculateParticipant(participant);
                    }

                    Collections.sort(temporatyParticipant, new SkParticipantComparator(true, true));

                    for (Participant participant1 : participants) {
                        for (int j = 0; j < temporatyParticipant.size(); j++) {
                            if (participant1.equals(temporatyParticipant.get(j))) {
                                participant1.setRank(j + 1 + actualRank);
                                participant1.setEqualRank(temporatyParticipant.get(j).getEqualRank());
                            }
                        }
                    }
                }
                temporatyParticipant.clear();
                temporatyParticipant.add(cloneParticipant(participants.get(i + 1)));
                actualRank = i + 1;
            }
        }
        Collections.sort(participants, new RankComparator());
    }
}