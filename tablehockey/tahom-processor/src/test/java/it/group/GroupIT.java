package it.group;

import java.util.List;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.tahom.processor.service.group.GroupService;
import org.tahom.processor.service.participant.ParticipantService;
import org.tahom.processor.service.tournament.TournamentService;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.GroupsPlayOffType;
import org.tahom.repository.model.GroupsType;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Tournament;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("../clear-all.xml"), @Setup() })
public class GroupIT {

	@Autowired
	private GroupService groupService;

	@Autowired
	private TournamentService tournamentService;

	@Autowired
	private ParticipantService participantService;

	private Tournament tournament;
	private Groups group;

	@Before
	public void setup() {
		tournament = new Tournament()._setId(1);
		group = new Groups()._setId(1);
	}

	@Test
	@Verify("createGroupTest-verify.xml")
	public void createGroupTest() {
		Groups group = groupService.createGroup(new Groups()._setCopyResult(true)._setIndexOfFirstHockey(1)
		        ._setName("groupName")._setNumberOfHockey(1)._setPlayOff(true)._setPlayOffType(GroupsPlayOffType.FINAL)
		        ._setPlayThirdPlace(true)._setType(GroupsType.FINAL)._setTournament(tournament));
		Assert.assertNotNull(group.getId());
	}

	@Test
	@Verify("getGroupTest-verify.xml")
	public void getGroupTest() {
		Groups Group = groupService.getGroup(group);
		Assert.assertNotNull(Group);
	}

	@Test
	@Verify("deleteGroupTest-verify.xml")
	public void deleteGroupTest() {
		int count = groupService.deleteGroup(group);
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("updateGroupTest-verify.xml")
	public void updateGroupTest() {
		int count = groupService.updateGroup(group._setTournament(tournament)._setCopyResult(false)
		        ._setIndexOfFirstHockey(1)._setName("groupNameEdit")._setNumberOfHockey(1)._setPlayOff(false)
		        ._setPlayOffType(GroupsPlayOffType.LOWER)._setPlayThirdPlace(false)._setType(GroupsType.BASIC));
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("getGroupTest-verify.xml")
	public void getGroupsTest() {
		List<Groups> groups = groupService.getGroups(new Groups());
		Assert.assertSame(5, groups.size());
	}

	@Test
	@Verify("getGroupTest-verify.xml")
	public void getBasicGroupsTest() {
		List<Groups> groups = groupService.getBasicGroups(tournament);
		Assert.assertSame(2, groups.size());
	}

	@Test
	@Verify("getGroupTest-verify.xml")
	public void getFinalGroupsTest() {
		List<Groups> groups = groupService.getFinalGroups(tournament);
		Assert.assertSame(2, groups.size());
	}

	@Test
	@Verify("getGroupTest-verify.xml")
	public void getGroupByTournamentNotNullTest() {
		Groups group = groupService.getGroupByTournament(tournament);
		Assert.assertNotNull(group.getId());
	}

	@Test
	@Verify("getGroupTest-verify.xml")
	public void getGroupByTournamentNullTest() {
		Groups group = groupService.getGroupByTournament(tournament._setId(0));
		Assert.assertNull(group);
	}

	@Test
	@Verify("createGroupsWithSnakeSystemTest-verify.xml")
	public void createGroupsWithSnakeSystemTest() {
		List<Participant> tournamentParticipants = participantService.getRegistratedParticipant(tournament);
		groupService.createGroupsWithSnakeSystem(tournament, tournamentParticipants, "4");
	}

	@Test
	@Verify("processFinalGroupTest-verify.xml")
	public void processFinalGroupTest() {
		groupService.processFinalGroup(tournament._setFinalPromoting(2)._setLowerPromoting(2)._setMinPlayersInGroup(1)
		        ._setPlayOffFinal(2)._setPlayOffLower(2));
	}

	@Test
	@Verify("processFinalGroupMinPlayerTest-verify.xml")
	public void processFinalGroupMinPlayerTest() {
		groupService.processFinalGroup(tournament._setFinalPromoting(3)._setLowerPromoting(2)._setMinPlayersInGroup(3)
		        ._setPlayOffFinal(2)._setPlayOffLower(2));
	}

	@Test
	@Verify("copyResultTest-verify.xml")
	public void copyResultTest() {
		groupService.processFinalGroup(tournament._setFinalPromoting(2)._setLowerPromoting(2)._setMinPlayersInGroup(1)
		        ._setPlayOffFinal(2)._setPlayOffLower(2)._setWinPoints(2));
		groupService.copyResult(tournament);
	}

}
