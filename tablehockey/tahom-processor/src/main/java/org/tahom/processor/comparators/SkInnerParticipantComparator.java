package org.tahom.processor.comparators;

import java.util.Comparator;

import org.tahom.repository.model.Game;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.impl.Result;

public class SkInnerParticipantComparator implements Comparator<Participant> {

	public SkInnerParticipantComparator() {
	}

	public int compare(Participant participant1, Participant participant2) {

		if (participant1.getPoints() > participant2.getPoints()) {
			return -1;
		}
		if (participant1.getPoints() < participant2.getPoints()) {
			return 1;
		}

		if ((participant1.getScore().getLeftSide() - participant1.getScore().getRightSide()) > (participant2.getScore()
		        .getLeftSide() - participant2.getScore().getRightSide())) {
			return -1;
		}
		if ((participant1.getScore().getLeftSide() - participant1.getScore().getRightSide()) < (participant2.getScore()
		        .getLeftSide() - participant2.getScore().getRightSide())) {
			return 1;
		}

		if (participant1.getScore().getLeftSide() > participant2.getScore().getLeftSide()) {
			return -1;
		}
		if (participant1.getScore().getLeftSide() < participant2.getScore().getLeftSide()) {
			return 1;
		}
		int homeWinnerCount1 = 0;
		int homeWinnerCount2 = 0;

		for (Game game : participant1.getGames()) {
			if (game.getResult() == null) {
				continue;
			}
			for (Result result : game.getResult().getResults()) {
				if (result.getLeftSide() > result.getRightSide()) {
					homeWinnerCount1++;
				}
			}
		}
		for (Game game : participant2.getGames()) {
			if (game.getResult() == null) {
				continue;
			}
			for (Result result : game.getResult().getResults()) {
				if (result.getLeftSide() > result.getRightSide()) {
					homeWinnerCount2++;
				}
			}
		}
		if (homeWinnerCount1 > homeWinnerCount2) {
			return -1;
		} else if (homeWinnerCount1 < homeWinnerCount2) {
			return 1;
		}

		if (participant1.getEqualRank() == null && participant1.getPoints() > 0) {
			participant1.setEqualRank(0);
		}
		if (participant2.getEqualRank() == null && participant2.getPoints() > 0) {
			participant2.setEqualRank(0);
		}
		return 0;
	}

}