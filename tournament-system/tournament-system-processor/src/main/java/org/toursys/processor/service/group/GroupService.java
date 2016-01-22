package org.toursys.processor.service.group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.sqlproc.engine.impl.SqlStandardControl;
import org.toursys.processor.comparators.WorldRankingComparator;
import org.toursys.processor.service.game.GameService;
import org.toursys.processor.service.participant.ParticipantService;
import org.toursys.processor.service.playOffGame.PlayOffGameService;
import org.toursys.processor.service.standing.FinalStandingService;
import org.toursys.processor.util.GroupsName;
import org.toursys.processor.util.SnakeList;
import org.toursys.repository.dao.GameDao;
import org.toursys.repository.dao.GroupsExtDao;
import org.toursys.repository.dao.ParticipantExtDao;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Groups.Association;
import org.toursys.repository.model.GroupsPlayOffType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Tournament;

public class GroupService {

	@Inject
	private ParticipantService participantService;

	@Inject
	private GameService gameService;

	@Inject
	private GroupsExtDao groupsDao;

	@Inject
	private GameDao gameDao;

	@Inject
	private ParticipantExtDao participantDao;

	@Inject
	private GroupModel groupModel;

	@Inject
	private FinalStandingService finalStandingService;

	@Inject
	private PlayOffGameService playOffGameService;

	@Transactional
	public Groups createGroup(Groups group) {
		return groupsDao.insert(group);
	}

	@Transactional
	public int updateGroup(Groups group) {
		return groupsDao.update(group);
	}

	@Transactional
	public int deleteGroup(Groups group) {
		return groupsDao.delete(group);
	}

	@Transactional(readOnly = true)
	public Groups getGroup(Groups group) {
		return groupsDao.get(group);
	}

	@Transactional(readOnly = true)
	public List<Groups> getGroups(Groups group) {
		return groupsDao.list(group);
	}

	@Transactional(readOnly = true)
	public List<Groups> getBasicGroups(Tournament tournament) {
		Groups group = groupModel.createBasicGroup(tournament);
		return groupsDao.list(group);
	}

	@Transactional(readOnly = true)
	public List<Groups> getFinalGroups(Tournament tournament) {
		Groups group = groupModel.createFinalGroup(tournament);
		return groupsDao.list(group);
	}

	@Transactional(readOnly = true)
	public Groups getGroupByTournament(Tournament tournament) {
		Groups group = groupModel.createGroup(tournament);
		List<Groups> groups = groupsDao.list(group);
		if (groups.isEmpty()) {
			return null;
		}
		return groups.get(0);
	}

	@Transactional
	public void processFinalGroup(Tournament tournament) {
		deleteFinalGroup(tournament);
		createFinalGroup(tournament);
		finalStandingService.processFinalStandings(tournament);
		playOffGameService.processPlayOffGames(tournament);
	}

	private void deleteFinalGroup(Tournament tournament) {
		groupsDao.deleteFinalGroups(tournament);
	}

	private void createFinalGroup(Tournament tournament) {
		List<Groups> basicGroups = getBasicGroups(tournament);

		boolean insertGroup = true;
		Map<String, Groups> groupByName = new HashMap<String, Groups>();

		Groups finalGroup = null;
		String groupName = null;
		GroupsName groupsName = new GroupsName();

		for (Groups group : basicGroups) {
			List<Participant> participants = participantService.getParticipandByGroup(group);

			groupName = groupsName.getFirst();
			int promotingFinal = Math.min(participants.size(), tournament.getFinalPromoting());
			finalGroup = createFullGroup(tournament, groupByName, groupName, insertGroup, 0, promotingFinal,
			        participants, basicGroups.size(), GroupsPlayOffType.FINAL);

			int promotingLower = Math.min(participants.size(), tournament.getLowerPromoting());
			int prefix = promotingFinal;

			while (prefix < participants.size()) {
				String nextGroups = groupsName.getNext(groupName);
				groupName = nextGroups;
				finalGroup = createFullGroup(tournament, groupByName, groupName, insertGroup, prefix, promotingLower,
				        participants, basicGroups.size(), GroupsPlayOffType.LOWER);

				prefix = prefix + promotingLower;
			}
			insertGroup = false;
		}

		// minPlayer
		if (finalGroup != null && finalGroup.getParticipants().size() < tournament.getMinPlayersInGroup()
		        && groupName != null && !groupsName.getFirst().equals(groupName)) {
			for (Participant participant : finalGroup.getParticipants()) {
				String previousGroupName = groupsName.getPrevious(groupName);
				participant.setGroup(groupByName.get(previousGroupName));
				Groups previosFinalGroup = getGroup(new Groups()._setTournament(tournament)._setName(previousGroupName)
				        ._setInit(Association.participants));
				gameService.createGames(previosFinalGroup.getParticipants(), participant);
			}

			for (Participant participant : finalGroup.getParticipants()) {
				participantDao.update(participant);
			}
			groupsDao.delete(finalGroup);
		}

	}

	private Groups createFullGroup(Tournament tournament, Map<String, Groups> groupByName, String groupName,
	        boolean insertGroup, int prefix, int promoting, List<Participant> participants, int basicGroupsSize,
	        GroupsPlayOffType playOffType) {
		Groups finalGroup = null;

		if (insertGroup) {
			int hockeyCount = getHockeyCount(promoting, basicGroupsSize);
			finalGroup = groupModel.createFullFinalGroup(tournament, groupName, hockeyCount, playOffType);
			finalGroup = groupsDao.insert(finalGroup);
			groupByName.put(groupName, finalGroup);
		} else {
			finalGroup = groupByName.get(groupName);
		}

		for (int i = prefix; i < Math.min(prefix + promoting, participants.size()); i++) {
			Participant participant = participantService.createParticipant(participants.get(i).getPlayer(), finalGroup);
			gameService.createGames(finalGroup.getParticipants(), participant);
			finalGroup.getParticipants().add(participant);
		}
		return finalGroup;
	}

	private int getHockeyCount(int promotingFinal, int basicGroupsSize) {
		int hockeyCount;
		if (promotingFinal % 2 == 0) {
			hockeyCount = (promotingFinal * basicGroupsSize) / 2;
		} else {
			hockeyCount = promotingFinal;
		}
		return hockeyCount;
	}

	public void copyResult(Tournament tournament) {
		List<Groups> basicGroupss = getBasicGroups(tournament);
		List<Groups> finalGroupss = getFinalGroups(tournament);

		for (Groups finalGroups : finalGroupss) {
			if (finalGroups.getCopyResult()) {
				List<Participant> finalParticipants = participantDao.list(new Participant()._setGroup(finalGroups));
				for (Groups basicGroups : basicGroupss) {
					List<Participant> participants = participantDao.list(new Participant()._setGroup(basicGroups));
					for (Participant finalParticipant : finalParticipants) {
						for (Participant participant : participants) {
							if (participant.getPlayer().equals(finalParticipant.getPlayer())) {
								List<Game> finalGames = gameService.getFullGames(new Game()
								        ._setHomeParticipant(finalParticipant));
								List<Game> basicGames = gameService.getFullGames(new Game()
								        ._setHomeParticipant(participant));
								for (Game finalGame : finalGames) {
									for (Game basicGame : basicGames) {
										if (finalGame.getAwayParticipant().getPlayer().getId()
										        .equals(basicGame.getAwayParticipant().getPlayer().getId())) {
											finalGame.setResult(basicGame.getResult());
											gameDao.update(finalGame);
											break;
										}
									}
								}
							}
						}
					}
					List<Participant> savedParticipants = participantDao.list(new Participant()._setGroup(finalGroups));
					participantService.sortParticipantsByRank(savedParticipants, tournament);
				}
			}
		}
	}

	public List<Groups> getSortedByNameFinalGroups(Tournament tournament) {
		Groups group = groupModel.createFinalGroup(tournament);
		SqlStandardControl control = new SqlStandardControl();
		control.setAscOrder(Groups.ORDER_BY_NAME);
		return groupsDao.list(group);
	}

	public void createGroupsWithSnakeSystem(Tournament tournament, List<Participant> tournamentParticipants,
	        String groupNameMax) {
		List<Groups> groupss = getGroups(new Groups()._setTournament(tournament));
		for (Groups groups : groupss) {
			groupsDao.delete(groups);
		}

		Integer maxGroupName = Integer.parseInt(groupNameMax);
		SnakeList snakeList = new SnakeList(maxGroupName);
		Map<Integer, List<Participant>> participantByGroup = new HashMap<Integer, List<Participant>>();

		for (int i = 0; i < maxGroupName; i++) {
			participantByGroup.put(i + 1, new ArrayList<Participant>());
		}

		Collections.sort(tournamentParticipants, new WorldRankingComparator());

		for (int i = 0; i < tournamentParticipants.size(); i++) {
			Integer actualGroupName = snakeList.get(i % (maxGroupName * 2));
			Participant participant = participantService.createBasicParticipant(tournament,
			        tournamentParticipants.get(i).getPlayer(), actualGroupName.toString());
			gameService.createGames(participantByGroup.get(actualGroupName), participant);
			participantByGroup.get(actualGroupName).add(participant);
		}

	}
}