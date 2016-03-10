package org.tahom.processor.service.playOffGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.sqlproc.engine.impl.SqlStandardControl;
import org.tahom.processor.BadOptionsTournamentException;
import org.tahom.processor.service.group.GroupService;
import org.tahom.processor.service.participant.ParticipantService;
import org.tahom.processor.service.playOffGame.dto.PlayOffGameDto;
import org.tahom.processor.service.playOffGame.dto.PlayOffGroupDto;
import org.tahom.processor.service.playOffGame.dto.PlayOffPageDto;
import org.tahom.processor.util.PositionCounter;
import org.tahom.repository.dao.PlayOffGameDao;
import org.tahom.repository.model.GameStatus;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.GroupsPlayOffType;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.PlayOffGame;
import org.tahom.repository.model.PlayOffGame.Association;
import org.tahom.repository.model.Tournament;

public class PlayOffGameService {

	@Inject
	private ParticipantService participantService;

	@Inject
	private GroupService groupService;

	@Inject
	private PlayOffGameDao playOffGameDao;

	@Inject
	private PlayOffGameModel playOffGameModel;

	@Transactional
	public PlayOffGame createPlayOffGame(PlayOffGame playOffGame) {
		return playOffGameDao.insert(playOffGame);
	}

	@Transactional(readOnly = true)
	public PlayOffGame getPlayOffGame(PlayOffGame playOffGame) {
		return playOffGameDao.get(playOffGame);
	}

	@Transactional
	public int updatePlayOffGame(PlayOffGame playOffGame) {
		playOffGame.setNull(PlayOffGame.Attribute.homeParticipant, PlayOffGame.Attribute.awayParticipant,
		        PlayOffGame.Attribute.result, PlayOffGame.Attribute.status);
		return playOffGameDao.update(playOffGame);
	}

	@Transactional
	public int deletePlayOffGame(PlayOffGame playOffGame) {
		return playOffGameDao.delete(playOffGame);
	}

	@Transactional(readOnly = true)
	public List<PlayOffGame> getPlayOffGames(PlayOffGame playOffGame) {
		SqlStandardControl control = new SqlStandardControl();
		control.setAscOrder(PlayOffGame.ORDER_BY_POSITION);

		playOffGame.setInit(PlayOffGame.Association.awayParticipant.name(),
		        PlayOffGame.Association.homeParticipant.name());
		return playOffGameDao.list(playOffGame, control);
	}

	@Transactional(readOnly = true)
	public List<PlayOffGameDto> getPlayOffGamesByGroup(Groups group, Tournament tournament) {
		PlayOffGame playOffGame = new PlayOffGame();
		playOffGame.setGroup(group);
		SqlStandardControl control = new SqlStandardControl();
		control.setAscOrder(PlayOffGame.ORDER_BY_POSITION);

		playOffGame.setInit(PlayOffGame.Association.awayParticipant.name(),
		        PlayOffGame.Association.homeParticipant.name());
		List<PlayOffGame> playOffGames = playOffGameDao.list(playOffGame, control);
		return playOffGameModel.createPlayOffGamesDto(playOffGames, group, tournament);
	}

	@Transactional(readOnly = true)
	public List<PlayOffGame> getFullPlayOffGames(Groups group) {
		PlayOffGame playOffGame = new PlayOffGame();
		playOffGame.setGroup(group);
		playOffGame.setInit(Association.awayParticipant, Association.homeParticipant);
		return playOffGameDao.list(playOffGame);
	}

	@Transactional
	public int updatePlayOffGameResult(PlayOffGameDto playOffGameDto) {
		playOffGameModel.assignWinner(playOffGameDto);
		PlayOffGame playOffGame = playOffGameModel.createPlayOffGameFromDto(playOffGameDto);
		return updatePlayOffGame(playOffGame);
	}

	@Transactional
	public void processPlayOffGames(Tournament tournament) {

		List<Groups> finalGroups = groupService.getFinalGroups(tournament);
		int groupIndex = 0;

		for (Groups group : finalGroups) {
			List<Participant> players = new ArrayList<Participant>();
			if (GroupsPlayOffType.CROSS.equals(group.getPlayOffType())) {
				groupIndex++;
				List<Participant> tempPart = participantService.getSortedParticipantByGroup(group);
				if (groupIndex % 2 == 0) {
					Collections.reverse(tempPart);
				}
				players.addAll(tempPart);
				if (finalGroups.size() != groupIndex) {
					continue;
				}
			} else {
				players = participantService.getSortedParticipantByGroup(group);
			}
			int playOffPlayerCount = getPlayOffPlayerCount(group, tournament);

			while (players.size() < playOffPlayerCount) {
				players.add(new Participant());
			}

			LinkedList<Participant> playOffPlayer = new LinkedList<Participant>(players.subList(0, playOffPlayerCount));
			createPlayOffGames(playOffPlayer, group, playOffPlayerCount);
		}
	}

	@Transactional
	public void updatePlayOffGames(Tournament tournament, Groups group) {

		List<Participant> players = participantService.getSortedParticipantByGroup(group);

		int playOffPlayerCount = getPlayOffPlayerCount(group, tournament);

		while (players.size() < playOffPlayerCount) {
			players.add(new Participant()._setTemp(true));
		}

		LinkedList<Participant> playOffPlayer = new LinkedList<Participant>(players.subList(0, playOffPlayerCount));

		List<PlayOffGame> finalgames = getPlayOffGames(new PlayOffGame()._setGroup(group));
		if (!finalgames.isEmpty()) {
			updatePlayOffGames(playOffPlayer, finalgames, group, playOffPlayerCount);
		}
	}

	@Transactional
	public void updateNextRoundPlayOffGames(Tournament tournament) {

		List<Groups> finalGroups = groupService.getFinalGroups(tournament);
		for (Groups group : finalGroups) {

			if (GroupsPlayOffType.CROSS.equals(group.getPlayOffType())) {
				continue;
			}

			int playerPlayOffCount = getPlayOffPlayerCount(group, tournament);
			List<PlayOffGame> playOffGames = getPlayOffGames(new PlayOffGame()._setGroup(group));

			// u finale a o tretie miesto uz nie je co updatovat
			int gameCount = playOffGames.size() - 2;
			for (int i = 0; i < gameCount; i++) {
				updatePlayOffGame(playOffGames, i + 1, playerPlayOffCount);
			}
		}
	}

	@Transactional(readOnly = true)
	public PlayOffPageDto getPlayOffPageDto(Tournament tournament) {
		PlayOffPageDto playOffPageDto = new PlayOffPageDto();

		List<Groups> groups = groupService.getFinalGroups(tournament);
		if (groups.isEmpty()) {
			groups = groupService.getBasicGroups(tournament);
		}

		for (Groups group : groups) {
			PlayOffGroupDto playOffGroupDto = playOffGameModel.createPlayOffGroupDto(group);
			playOffPageDto.getPlayOffGroups().add(playOffGroupDto);
			playOffGroupDto.getPlayOffGames().addAll(getPlayOffGamesByGroup(group, tournament));
		}

		return playOffPageDto;
	}

	private int getPlayOffPlayerCount(Groups group, Tournament tournament) {
		int playOffPlayerCount = 0;

		switch (group.getPlayOffType()) {
		case FINAL:
			playOffPlayerCount += tournament.getPlayOffFinal();
			break;
		case LOWER:
			playOffPlayerCount += tournament.getPlayOffLower();
			break;
		case CROSS:
			if (tournament.getPlayOffFinal() != tournament.getPlayOffLower()) {
				throw new BadOptionsTournamentException();
			}
			playOffPlayerCount += tournament.getPlayOffFinal();
			break;
		default:
			break;
		}
		return playOffPlayerCount;
	}

	private void createPlayOffGames(LinkedList<Participant> playOffPlayer, Groups group, int playOffPlayerCount) {
		List<PlayOffGame> playOffGames = new LinkedList<PlayOffGame>();
		int playerCount = playOffPlayer.size();
		PositionCounter pc = new PositionCounter(playerCount);
		int position = 0;
		for (int i = 0; i < playOffPlayerCount; i++) {

			PlayOffGame playOffGame;
			if (!playOffPlayer.isEmpty()) {
				playOffGame = playOffGameModel.createPlayOffGame(playOffPlayer.removeFirst(),
				        playOffPlayer.removeLast(), group, pc.getPosition(i));
				position = playerCount / 2;
			} else {
				playOffGame = playOffGameModel.createPlayOffGame(null, null, group, ++position);
			}
			createPlayOffGame(playOffGame);
			playOffGames.add(playOffGame);
		}
	}

	private void updatePlayOffGames(LinkedList<Participant> playOffPlayer, List<PlayOffGame> finalgames, Groups group,
	        int playOffPlayerCount) {
		int playerCount = playOffPlayer.size();
		PositionCounter pc = new PositionCounter(playerCount);
		int position = 0;

		for (int i = 0; i < playOffPlayerCount; i++) {

			if (!playOffPlayer.isEmpty()) {
				PlayOffGame playOffGame = getPlayOffGameByPosition(finalgames, pc.getPosition(i));
				playOffGame.setHomeParticipant(playOffPlayer.removeFirst());
				if (playOffGame.getHomeParticipant().isTemp()) {
					playOffGame.setHomeParticipant(null);
				}
				playOffGame.setAwayParticipant(playOffPlayer.removeLast());
				if (playOffGame.getAwayParticipant().isTemp()) {
					playOffGame.setAwayParticipant(null);
				}
				position = playerCount / 2;
				updatePlayOffGame(playOffGame);
			} else {
				PlayOffGame playOffGame = getPlayOffGameByPosition(finalgames, ++position);
				playOffGame.setHomeParticipant(null);
				playOffGame.setAwayParticipant(null);
				playOffGame.setResult(null);
				playOffGame.setStatus(null);
				updatePlayOffGame(playOffGame);
			}
		}
	}

	private void updatePlayOffGame(List<PlayOffGame> playOffGames, int position, int playerPlayOffCount) {

		PlayOffGame playOffGame = playOffGames.get(position - 1);
		int nextPosition = nextGame(playerPlayOffCount, position);
		PlayOffGame tempPlayOffGame = getPlayOffGameByPosition(playOffGames, nextPosition);

		PlayOffGame tempThirdPlayOffGame = null;
		// hra o tretie miesto bude ako posledna hra
		boolean isThirdPlace = (nextPosition == playOffGames.size() - 1);
		if (isThirdPlace) {
			tempThirdPlayOffGame = getPlayOffGameByPosition(playOffGames, nextPosition + 1);
		}

		if (playOffGame.getStatus() != null) {
			if (playOffGame.getStatus().equals(GameStatus.WIN)) {
				if (position % 2 == 1) {
					tempPlayOffGame._setHomeParticipant((playOffGame.getHomeParticipant()));
					if (isThirdPlace) {
						tempThirdPlayOffGame._setHomeParticipant((playOffGame.getAwayParticipant()));
					}
				} else {
					tempPlayOffGame._setAwayParticipant((playOffGame.getHomeParticipant()));
					if (isThirdPlace) {
						tempThirdPlayOffGame._setAwayParticipant((playOffGame.getAwayParticipant()));
					}
				}
			} else if (playOffGame.getStatus().equals(GameStatus.LOSE)) {
				if (position % 2 == 1) {
					tempPlayOffGame._setHomeParticipant((playOffGame.getAwayParticipant()));
					if (isThirdPlace) {
						tempThirdPlayOffGame._setHomeParticipant((playOffGame.getHomeParticipant()));
					}
				} else {
					tempPlayOffGame._setAwayParticipant((playOffGame.getAwayParticipant()));
					if (isThirdPlace) {
						tempThirdPlayOffGame._setAwayParticipant((playOffGame.getHomeParticipant()));
					}
				}
			} else {
				tempPlayOffGame.setStatus(null);
			}
		} else {
			if (position % 2 == 1) {
				tempPlayOffGame._setHomeParticipant((null));
				if (isThirdPlace) {
					tempThirdPlayOffGame._setHomeParticipant((null));
				}
			} else {
				tempPlayOffGame._setAwayParticipant((null));
				if (isThirdPlace) {
					tempThirdPlayOffGame._setAwayParticipant((null));
				}
			}
		}

		updatePlayOffGame(tempPlayOffGame);
		if (tempThirdPlayOffGame != null) {
			updatePlayOffGame(tempThirdPlayOffGame);
		}
	}

	private PlayOffGame getPlayOffGameByPosition(List<PlayOffGame> playOffGames, int position) {

		for (PlayOffGame playOffGame : playOffGames) {
			if (playOffGame.getPosition().equals(position)) {
				return playOffGame;
			}
		}
		return null;
	}

	/**
	 * @param playerCount
	 *            number of players in playOff (4,8,16...)
	 * @param currentGame
	 *            starting with 1
	 * @return
	 */
	public int nextGame(int playerCount, int currentGame) {
		return (playerCount / 2) + (int) Math.ceil((double) currentGame / 2);
	}

}