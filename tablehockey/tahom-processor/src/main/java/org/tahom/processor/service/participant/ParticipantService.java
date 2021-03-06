package org.tahom.processor.service.participant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.sqlproc.engine.impl.SqlStandardControl;
import org.tahom.processor.BadOptionsTournamentException;
import org.tahom.processor.service.group.GroupModel;
import org.tahom.processor.service.group.GroupService;
import org.tahom.processor.sorter.SkTournamentSorter;
import org.tahom.processor.sorter.TournamentSorter;
import org.tahom.repository.dao.ParticipantExtDao;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Participant.Association;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.impl.Score;

public class ParticipantService {

	@Inject
	private GroupService groupService;

	@Inject
	private ParticipantExtDao participantDao;

	@Inject
	private GroupModel groupModel;

	@Transactional
	public int updateParticipant(Participant participant) {
		return participantDao.update(participant);
	}

	@Transactional
	public Participant createParticipant(Player player, Groups group) {
		return participantDao.insert(new Participant(0, group, player, new Score(0, 0)));
	}

	@Transactional
	public int deletePlayerParticipant(Participant participant, Tournament tournament) {
		int count = 0;
		List<Participant> participants = participantDao.list(new Participant()._setPlayer(participant.getPlayer())
		        ._setInit_(Participant.Association.group));

		for (Participant deletedParticipant : participants) {
			if (deletedParticipant.getGroup().getTournament().equals(tournament)) {
				count += participantDao.delete(deletedParticipant);
				Groups group = deletedParticipant.getGroup();
				participants = participantDao.list(new Participant()._setGroup(group));
				if (participants.isEmpty()) {
					groupService.deleteGroup(group);
				}
			}
		}

		return count;
	}

	@Transactional(readOnly = true)
	public List<Participant> getRegistratedParticipant(Tournament tournament) {
		return participantDao.listTournamentParticipants(tournament);
	}

	@Transactional
	public Participant createBasicParticipant(Tournament tournament, Player player, String groupName) {
		if (groupName != null) {
			Groups group = groupModel.createBasicGroup(tournament);
			group.setName(groupName);
			List<Groups> savedGroups = groupService.getGroups(group);
			if (savedGroups.isEmpty()) {
				groupModel.initDefaultGroup(group);
				groupService.createGroup(group);
			} else {
				group = savedGroups.get(0);
			}
			return createParticipant(player, group);
		} else {
			return new Participant(0, null, player, new Score(0, 0));
		}
	}

	@Transactional
	public void sortParticipantsByRank(List<Participant> participants, Tournament tournament) {

		// TODO CZ
		TournamentSorter sorter = new SkTournamentSorter(tournament);
		sorter.sort(participants);

		for (Participant participant : participants) {
			participantDao.update(participant._setNull_(Participant.Attribute.equalRank));
		}

	}

	@Transactional(readOnly = true)
	public LinkedList<List<Participant>> getAdvancedPlayersByGroup(Groups group, Tournament tournament,
	        List<Participant> finalParticipants) {

		LinkedList<List<Participant>> playerByGroup = new LinkedList<List<Participant>>();

		List<Groups> basicGroups = groupService.getBasicGroups(tournament);

		int startIndex = getStartIndex(tournament, group);
		int promotingCount = getPromoting(tournament, group);

		for (Groups basicGroup : basicGroups) {
			List<Participant> participants = new ArrayList<Participant>();
			playerByGroup.add(participants);
			List<Participant> players = getParticipantByGroup(basicGroup);

			for (Participant participant : players.subList(Math.min(startIndex, players.size()),
			        Math.min(players.size(), promotingCount + startIndex))) {
				for (Participant finalParticipant : finalParticipants) {
					if (participant.getPlayer().equals(finalParticipant.getPlayer())) {
						participants.add(finalParticipant);
						break;
					}
				}
			}
		}
		return playerByGroup;
	}

	private int getPromoting(Tournament tournament, Groups group) {
		int promotingCount = 0;

		switch (group.getPlayOffType()) {
		case FINAL:
			promotingCount += tournament.getFinalPromoting();
			break;
		case LOWER:
			promotingCount += tournament.getLowerPromoting();
			break;
		case CROSS:
			if (tournament.getFinalPromoting() != tournament.getLowerPromoting()) {
				throw new BadOptionsTournamentException();
			}
			promotingCount += tournament.getLowerPromoting();
			break;
		}

		return promotingCount;
	}

	private int getStartIndex(Tournament tournament, Groups group) {
		int startIndex = 0;

		List<Groups> finalGroups = groupService.getSortedByNameFinalGroups(tournament);
		for (Groups finalGroup : finalGroups) {
			if (finalGroup.equals(group)) {
				break;
			}
			startIndex += getPromoting(tournament, finalGroup);
		}

		return startIndex;
	}

	public Set<Participant> getGoldGoalParticipants(List<Participant> participants) {
		Set<Participant> participantsSet = new HashSet<Participant>();

		for (Participant participant : participants) {
			if (participant.getEqualRank() != null) {
				participantsSet.add(participant);
			}
		}
		return participantsSet;
	}

	public List<Participant> getSortedParticipantByGroup(Groups group) {
		SqlStandardControl control = new SqlStandardControl();
		control.setAscOrder(Participant.ORDER_BY_RANK);

		return getParticipantByGroup(group, control);
	}

	public List<Participant> getParticipantByGroup(Groups group) {
		SqlStandardControl control = new SqlStandardControl();
		control.setAscOrder(Participant.ORDER_BY_ID);

		return getParticipantByGroup(group, control);
	}

	private List<Participant> getParticipantByGroup(Groups group, SqlStandardControl control) {

		Participant participant = new Participant();
		participant.setGroup(group);
		participant.setInit_(Association.player, Association.games);

		List<Participant> participants = participantDao.list(participant, control);
		return participants;
	}

	public List<Participant> getFullParticipants(Participant participant) {
		participant.setInit_(Association.player, Association.games, Association.group, Association.playOffGames);
		List<Participant> participants = participantDao.list(participant);
		return participants;
	}

	public List<Participant> getParticipants(Participant participant) {
		List<Participant> participants = participantDao.list(participant);
		return participants;
	}

}