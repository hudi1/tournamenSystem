package org.tahom.processor.service.group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.sqlproc.engine.impl.SqlStandardControl;
import org.tahom.processor.comparators.WorldRankingComparator;
import org.tahom.processor.service.finalStanding.FinalStandingService;
import org.tahom.processor.service.game.GameService;
import org.tahom.processor.service.group.dto.GroupPageDto;
import org.tahom.processor.service.group.dto.GroupPageOverviewDto;
import org.tahom.processor.service.group.dto.GroupsOverviewDto;
import org.tahom.processor.service.participant.ParticipantModel;
import org.tahom.processor.service.participant.ParticipantService;
import org.tahom.processor.service.playOffGame.PlayOffGameService;
import org.tahom.processor.service.schedule.ScheduleService;
import org.tahom.processor.util.GroupsName;
import org.tahom.processor.util.SnakeList;
import org.tahom.repository.dao.GameDao;
import org.tahom.repository.dao.GroupsExtDao;
import org.tahom.repository.dao.ParticipantExtDao;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Groups.Association;
import org.tahom.repository.model.GroupsPlayOffType;
import org.tahom.repository.model.GroupsType;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.impl.Result;

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

	@Inject
	private ScheduleService scheduleService;

	@Inject
	private ParticipantModel participantModel;

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

	@Transactional
	public GroupPageOverviewDto getGroupPageOverviewDto(Tournament tournament) {
		GroupPageOverviewDto groupPageOverviewDto = new GroupPageOverviewDto();
		groupPageOverviewDto.getGroups().addAll(
		        groupModel.getGroupsOverviewDto(getGroups(new Groups()._setTournament(tournament))));
		for (GroupsOverviewDto groupsOverviewDto : groupPageOverviewDto.getGroups()) {
			List<Participant> participants = participantService.getSortedParticipantByGroup(new Groups()
			        ._setId(groupsOverviewDto.getId()));
			groupsOverviewDto.getParticipants().addAll(participantModel.createParticipantsDto(participants));
		}
		return groupPageOverviewDto;
	}

	@Transactional
	public GroupPageDto getGroupPageDto(Groups group, Tournament tournament) {
		GroupPageDto groupPageDto = new GroupPageDto();
		groupPageDto.setGroup(group);
		groupPageDto.getGroups().addAll(getGroups(new Groups()._setTournament(tournament)));
		if (group != null) {
			List<Participant> participants = participantService.getSortedParticipantByGroup(group);
			Set<Participant> goldGoalParticipants = participantService.getGoldGoalParticipants(participants);

			groupPageDto.getParticipants().addAll(participantModel.createParticipantsDto(participants));
			groupPageDto.getGoldGoalParticipants().addAll(participantModel.createParticipantsDto(goldGoalParticipants));
			groupPageDto.getSchedule().addAll(scheduleService.getSchedule(tournament, group).getSchedule());
		}

		return groupPageDto;
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
			List<Participant> participants = participantService.getSortedParticipantByGroup(group);

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
				        ._setInit_(Association.participants));
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
		if (promotingFinal % 2 == 0 && basicGroupsSize % 2 == 0) {
			hockeyCount = (promotingFinal * basicGroupsSize) / 2;
		} else if (promotingFinal % 2 == 0 && basicGroupsSize % 2 == 1) {
			hockeyCount = promotingFinal * (basicGroupsSize / 2);
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
											for (Result result : finalGame.getResult().getResults()) {
												result.setContumacy(true);
											}
											gameDao.update(finalGame);
											break;
										}
									}
								}
							}
						}
					}

					List<Participant> savedParticipants = participantService.getParticipantByGroup(finalGroups);
					participantService.sortParticipantsByRank(savedParticipants, tournament);
					if (GroupsType.FINAL.equals(finalGroups.getType())) {
						playOffGameService.updatePlayOffGames(tournament, finalGroups);
						finalStandingService.updateNotPromotingFinalStandings(tournament, finalGroups);
					}
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