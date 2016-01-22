package org.toursys.processor.service.standing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.BadOptionsTournamentException;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.processor.service.group.GroupService;
import org.toursys.processor.service.participant.ParticipantService;
import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.dao.FinalStandingDao;
import org.toursys.repository.dao.PlayOffGameDao;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsPlayOffType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffGame.Association;
import org.toursys.repository.model.PlayOffGameStatus;
import org.toursys.repository.model.Tournament;

public class FinalStandingService {

	@Inject
	private GroupService groupService;

	@Inject
	private ParticipantService participantService;

	@Inject
	private FinalStandingDao finalStandingDao;

	@Inject
	private PlayOffGameDao playOffGameDao;

	@Inject
	private FinalStandingModel finalStandingModel;

	@Transactional(readOnly = true)
	public List<FinalStanding> getFinalStandings(Tournament tournament) {
		FinalStanding finalStanding = new FinalStanding()._setTournament(tournament);
		finalStanding.setInit(FinalStanding.Association.player.name());
		return finalStandingDao.list(finalStanding);
	}

	@Transactional
	public void processFinalStandings(Tournament tournament) {
		int playerCount = participantService.getRegistratedParticipant(tournament).size();
		List<FinalStanding> finalStandings = getFinalStandings(tournament);
		for (FinalStanding finalStanding : finalStandings) {
			finalStandingDao.delete(finalStanding);
		}
		for (int i = 1; i < playerCount + 1; i++) {
			FinalStanding finalStanding = new FinalStanding();
			finalStanding.setFinalRank(i);
			finalStanding.setTournament(tournament);
			finalStandingDao.insert(finalStanding);
		}
	}

	@Transactional
	public void updateNotPromotingFinalStandings(List<Participant> participants, Groups group, Tournament tournament) {

		int previousParticipantsCount = getPreviousParticipantsCount(group, tournament);
		int playOffCountPlayer = getPlayOffCountPlayer(group, tournament);

		for (int i = participants.size(); i > playOffCountPlayer; i--) {
			FinalStanding finalStanding = finalStandingDao.get(new FinalStanding()._setFinalRank(
			        i + previousParticipantsCount)._setTournament(tournament));
			if (finalStanding != null
			        && (finalStanding.getPlayer() == null || !finalStanding.getPlayer().equals(
			                participants.get(i - 1).getPlayer()))) {
				finalStanding.setPlayer(participants.get(i - 1).getPlayer());
				finalStandingDao.update(finalStanding);
			}
		}
	}

	private int getPreviousParticipantsCount(Groups actualGroup, Tournament tournament) {
		int previousParticipantsCount = 0;

		List<Groups> finalGroups = groupService.getFinalGroups(tournament);
		for (Groups groups : finalGroups) {
			if (actualGroup.getName().compareTo(groups.getName()) == -1) {
				previousParticipantsCount += participantService.getParticipandByGroup(groups).size();
			}
		}

		return previousParticipantsCount;
	}

	private int getPlayOffCountPlayer(Groups group, Tournament tournament) {
		int playOffCountPlayer = 0;
		switch (group.getPlayOffType()) {
		case FINAL:
			playOffCountPlayer = tournament.getPlayOffFinal();
			break;
		case LOWER:
			playOffCountPlayer = tournament.getPlayOffLower();
			break;
		case CROSS:
			if (tournament.getPlayOffFinal() != tournament.getPlayOffLower()) {
				throw new BadOptionsTournamentException();
			}
			playOffCountPlayer += tournament.getPlayOffFinal();
			break;
		default:
			break;
		}
		return playOffCountPlayer;
	}

	@Transactional
	public void updatePromotingFinalStandings(Tournament tournament) {
		int startGroupSufix = 0;
		// (Ked je play off o 16 hracov ale su tam len 4 tak treba odcitat inac v dalsej skupiny nenajde final standing)
		int startGroupMinusSufix = 0;

		List<Groups> finalGroups = groupService.getFinalGroups(tournament);
		for (Groups group : finalGroups) {

			List<PlayOffGame> playOffGames = getFullPlayOffGames(group);
			if (GroupsPlayOffType.CROSS.equals(group.getPlayOffType())) {
				updateFinalStandings(tournament, playOffGames, 0);
			} else {

				int startGroupMinusSufixTemp = 0;

				Map<Integer, List<Participant>> losersPerRound = new HashMap<Integer, List<Participant>>();
				for (int i = 0; i < playOffGames.size() - 4; i++) {
					Integer round = TournamentUtil.getRound(playOffGames.size(), playOffGames.get(i).getPosition());
					Participant participant = null;
					if (PlayOffGameStatus.WIN.equals(playOffGames.get(i).getStatus())) {
						participant = playOffGames.get(i).getAwayParticipant();
					} else if (PlayOffGameStatus.LOSE.equals(playOffGames.get(i).getStatus())) {
						participant = playOffGames.get(i).getHomeParticipant();
					}

					if (participant != null) {
						if (!losersPerRound.containsKey(round)) {
							losersPerRound.put(round, new ArrayList<Participant>());
						}
						losersPerRound.get(round).add(participant);
					}

					if (round == 1) {
						if (playOffGames.get(i).getHomeParticipant() == null) {
							startGroupMinusSufixTemp++;
						}
						if (playOffGames.get(i).getAwayParticipant() == null) {
							startGroupMinusSufixTemp++;
						}
					}
				}

				int maxRound = TournamentUtil.binlog(playOffGames.size());
				for (int i = 1; i < maxRound; i++) {
					if (losersPerRound.get(i) != null) {
						updateRoundFinalStandings(losersPerRound.get(i), i, playOffGames.size(), tournament,
						        startGroupSufix - startGroupMinusSufix);
					}
				}

				int position = 4 + startGroupSufix - startGroupMinusSufix;
				for (int i = playOffGames.size() - 1; i > playOffGames.size() - 3; i--) {
					FinalStanding firstFinalStanding = finalStandingDao.get(new FinalStanding()._setFinalRank(
					        position - 1)._setTournament(tournament));
					FinalStanding secondFinalStanding = finalStandingDao.get(new FinalStanding()
					        ._setFinalRank(position)._setTournament(tournament));

					if (playOffGames.get(i).getStatus() != null
					        && !PlayOffGameStatus.DRAW.equals(playOffGames.get(i).getStatus())) {
						if (playOffGames.get(i).getStatus().equals(PlayOffGameStatus.WIN)) {
							if (playOffGames.get(i).getHomeParticipant() != null) {
								firstFinalStanding.setPlayer(playOffGames.get(i).getHomeParticipant().getPlayer());
							} else {
								firstFinalStanding.setPlayer(null);
							}
							if (playOffGames.get(i).getAwayParticipant() != null) {
								secondFinalStanding.setPlayer(playOffGames.get(i).getAwayParticipant().getPlayer());
							} else {
								secondFinalStanding.setPlayer(null);
							}
						} else if (playOffGames.get(i).getStatus().equals(PlayOffGameStatus.LOSE)) {
							if (playOffGames.get(i).getAwayParticipant() != null) {
								firstFinalStanding.setPlayer(playOffGames.get(i).getAwayParticipant().getPlayer());
							} else {
								firstFinalStanding.setPlayer(null);
							}
							if (playOffGames.get(i).getHomeParticipant() != null) {
								secondFinalStanding.setPlayer(playOffGames.get(i).getHomeParticipant().getPlayer());
							} else {
								secondFinalStanding.setPlayer(null);
							}
						}
					} else {
						firstFinalStanding.setPlayer(null);
						secondFinalStanding.setPlayer(null);
					}
					finalStandingDao.update(firstFinalStanding);
					finalStandingDao.update(secondFinalStanding);
					position -= 2;
				}

				startGroupSufix += participantService.getParticipandByGroup(group).size();
				startGroupMinusSufix += startGroupMinusSufixTemp;
			}
		}
	}

	private void updateFinalStandings(Tournament tournament, List<PlayOffGame> playOffGames, int start) {
		for (int i = 0; i < playOffGames.size(); i++) {
			FinalStanding finalStanding1 = finalStandingDao.get(finalStandingModel.createFinalStanding(tournament, i));
			FinalStanding finalStanding2 = finalStandingDao.get(finalStandingModel.createFinalStanding(tournament,
			        i + 1));

			if (PlayOffGameStatus.WIN.equals(playOffGames.get(i).getStatus())) {
				if (playOffGames.get(i).getHomeParticipant() != null) {
					finalStanding1.setPlayer(playOffGames.get(i).getHomeParticipant().getPlayer());
				} else {
					finalStanding1.setPlayer(null);
				}
				if (playOffGames.get(i).getAwayParticipant() != null) {
					finalStanding2.setPlayer(playOffGames.get(i).getAwayParticipant().getPlayer());
				} else {
					finalStanding2.setPlayer(null);
				}
			} else if (PlayOffGameStatus.LOSE.equals(playOffGames.get(i).getStatus())) {
				if (playOffGames.get(i).getHomeParticipant() != null) {
					finalStanding1.setPlayer(playOffGames.get(i).getHomeParticipant().getPlayer());
				} else {
					finalStanding1.setPlayer(null);
				}
				if (playOffGames.get(i).getAwayParticipant() != null) {
					finalStanding2.setPlayer(playOffGames.get(i).getAwayParticipant().getPlayer());
				} else {
					finalStanding2.setPlayer(null);
				}
			} else {
				finalStanding1.setPlayer(null);
				finalStanding2.setPlayer(null);
			}
			finalStandingDao.update(finalStanding1);
			finalStandingDao.update(finalStanding2);
		}
	}

	private void updateRoundFinalStandings(List<Participant> participants, int round, int playerPlayOffCount,
	        Tournament tournament, int playerGroupCountSuffix) {

		Collections.sort(participants, new RankComparator());
		int maxRound = TournamentUtil.binlog(playerPlayOffCount);
		int actualRank = (int) Math.pow(2, maxRound - round) + 1 + playerGroupCountSuffix;
		for (Participant participant : participants) {
			FinalStanding finalStanding = finalStandingDao.get(new FinalStanding()._setFinalRank(actualRank)
			        ._setTournament(tournament));
			if ((finalStanding != null && participant != null)
			        && (finalStanding.getPlayer() == null || !finalStanding.getPlayer().equals(participant.getPlayer()))) {
				finalStanding.setPlayer(participant.getPlayer());
				finalStandingDao.update(finalStanding);
			}
			actualRank++;
		}
		participants.clear();
	}

	private List<PlayOffGame> getFullPlayOffGames(Groups group) {
		PlayOffGame playOffGame = new PlayOffGame();
		playOffGame.setGroup(group);
		playOffGame.setInit(Association.awayParticipant, Association.homeParticipant);
		return playOffGameDao.list(playOffGame);
	}
}