package org.tahom.processor.service.statistic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.tahom.processor.service.finalStanding.FinalStandingService;
import org.tahom.processor.service.participant.ParticipantService;
import org.tahom.processor.service.playOffGame.PlayOffGameService;
import org.tahom.processor.service.statistic.dto.PlayerStatisticDto;
import org.tahom.processor.service.statistic.dto.PlayerStatisticInfo;
import org.tahom.processor.service.tournament.TournamentService;
import org.tahom.repository.model.FinalStanding;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.GroupsType;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.PlayOffGame;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.Result;
import org.tahom.repository.model.Score;
import org.tahom.repository.model.Tournament;

public class StatisticService {

	@Inject
	private ParticipantService participantService;

	@Inject
	private TournamentService tournamentService;

	@Inject
	private FinalStandingService finalStandingService;

	@Inject
	private PlayOffGameService playOffGameService;

	public PlayerStatisticDto getPlayerStatistic(Player player) {
		PlayerStatisticDto dto = new PlayerStatisticDto();

		if (player != null) {
			dto.setPlayerInfos(getPlayerInfos(player));
		}
		return dto;
	}

	private Map<String, PlayerStatisticInfo> getPlayerInfos(Player player) {
		Map<String, PlayerStatisticInfo> infos = new HashMap<String, PlayerStatisticInfo>();
		List<Participant> participants = participantService.getParticipant(new Participant()._setPlayer(player));

		for (Participant participant : participants) {
			Integer matches = 0;
			Integer wins = 0;
			Integer draws = 0;
			Integer loses = 0;
			Integer homeScore = 0;
			Integer awayScore = 0;
			Integer points = 0;

			Tournament tournament = tournamentService.getTournament(participant.getGroup().getTournament());
			FinalStanding finalStanding = finalStandingService.getFinalStanding(new FinalStanding()._setPlayer(player)
			        ._setTournament(tournament));
			if (!tournament.getOpen()) {
				continue;
			}

			PlayerStatisticInfo info;
			if (infos.get(tournament.getId().toString()) == null) {
				info = new PlayerStatisticInfo();
				infos.put(tournament.getId().toString(), info);
			} else {
				info = infos.get(tournament.getId().toString());

				matches = info.getMatchesCount();
				wins = info.getWinnersCount();
				draws = info.getDrawsCount();
				loses = info.getLosesCount();
				homeScore = info.getScore().getLeftSide();
				awayScore = info.getScore().getRightSide();
				points = info.getPoints();
			}

			for (Game game : participant.getGames()) {
				if (game.getResult() == null) {
					continue;
				}

				for (Result result : game.getResult().getResults()) {
					if (GroupsType.FINAL.equals(participant.getGroup().getType()) && result.isContumacy()) {
						continue;
					}

					matches++;
					homeScore += result.getLeftSide();
					awayScore += result.getRightSide();
					if (result.getLeftSide() > result.getRightSide()) {
						wins++;
						points += tournament.getWinPoints();
					} else if (result.getLeftSide() < result.getRightSide()) {
						loses++;
					} else {
						points += 1;
						draws++;
					}
				}
			}
			for (PlayOffGame game : playOffGameService.getPlayOffGames(new PlayOffGame()
			        ._setHomeParticipant(participant))) {
				if (game.getResult() == null) {
					continue;
				}

				for (Result result : game.getResult().getResults()) {
					matches++;
					homeScore += result.getLeftSide();
					awayScore += result.getRightSide();
					if (result.getLeftSide() > result.getRightSide()) {
						wins++;
						points += tournament.getWinPoints();
					} else if (result.getLeftSide() < result.getRightSide()) {
						loses++;
					} else {
						points += 1;
						draws++;
					}
				}
			}

			for (PlayOffGame game : playOffGameService.getPlayOffGames(new PlayOffGame()
			        ._setAwayParticipant(participant))) {
				if (game.getResult() == null) {
					continue;
				}

				for (Result result : game.getResult().getResults()) {
					matches++;
					homeScore += result.getRightSide();
					awayScore += result.getLeftSide();
					if (result.getRightSide() > result.getLeftSide()) {
						wins++;
						points += tournament.getWinPoints();
					} else if (result.getRightSide() < result.getLeftSide()) {
						loses++;
					} else {
						points += 1;
						draws++;
					}
				}
			}

			info.setTournamentName(tournament.getName());
			info.setDrawsCount(draws);
			info.setMatchesCount(matches);
			info.setScore(new Score(homeScore, awayScore));
			info.setWinnersCount(wins);
			info.setLosesCount(loses);
			info.setPoints(points);
			if (finalStanding != null) {
				info.setFinalRank(finalStanding.getFinalRank());
			}
			info.setRatioPlus(Math.round((double) homeScore / (double) matches * 100.0) / 100.0 + "");
			info.setRatioMinus(Math.round((double) awayScore / (double) matches * 100.0) / 100.0 + "");
			info.setPercentage(Math.round((((double) wins * 100) / (double) matches) * 100.0) / 100.0 + "");
		}

		return infos;
	}
}
