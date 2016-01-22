package it.participant;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.toursys.processor.BadOptionsTournamentException;
import org.toursys.processor.service.participant.ParticipantService;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsPlayOffType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Tournament;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("../clear-all.xml"), @Setup() })
public class ParticipantIT {

	@Autowired
	private ParticipantService participantService;

	@Test
	@Verify("createParticipantTest-verify.xml")
	public void createParticipantTest() {
		Participant participant = participantService.createParticipant(new Player()._setId(2), new Groups()._setId(1));
		Assert.assertNotNull(participant.getId());
	}

	@Test
	@Verify("deleteParticipantTest-verify.xml")
	public void deleteParticipantTest() {
		int count = participantService.deletePlayerParticipant(
		        new Participant()._setId(1)._setPlayer(new Player()._setId(1)), new Tournament()._setId(1));
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("deleteLastParticipantTest-verify.xml")
	public void deleteLastParticipantTest() {
		int count = participantService.deletePlayerParticipant(
		        new Participant()._setId(17)._setPlayer(new Player()._setId(1)), new Tournament()._setId(2));
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("updateParticipantTest-verify.xml")
	public void updateParticipantTest() {
		int count = participantService.updateParticipant(new Participant()._setEqualRank(1)._setId(1));
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("getParticipantTest-verify.xml")
	public void getParticipandByGroup() {
		List<Participant> participants = participantService.getParticipandByGroup(new Groups()._setId(1));
		Assert.assertSame(4, participants.size());
	}

	@Test
	@Verify("getParticipantTest-verify.xml")
	public void getRegistratedParticipant() {
		List<Participant> participants = participantService.getRegistratedParticipant(new Tournament()._setId(1));
		Assert.assertSame(8, participants.size());
	}

	@Test
	@Verify("createBasicParticipantTest-verify.xml")
	public void createBasicParticipant() {
		Participant participant = participantService.createBasicParticipant(new Tournament()._setId(1),
		        new Player()._setId(1), "3");
		Assert.assertNotNull(participant.getId());
	}

	@Test
	@Verify("createBasicParticipantWithExistingGroupTest-verify.xml")
	public void createBasicParticipantWithExistingGroup() {
		Participant participant = participantService.createBasicParticipant(new Tournament()._setId(1),
		        new Player()._setId(1), "2");
		Assert.assertNotNull(participant.getId());
	}

	@Test
	@Verify("getParticipantTest-verify.xml")
	public void createVirtualParticipantTest() {
		Participant participant = participantService.createBasicParticipant(new Tournament()._setId(1),
		        new Player()._setId(2), null);
		Assert.assertNull(participant.getId());
	}

	@Test
	@Verify("getParticipantTest-verify.xml")
	public void getAdvancedPlayersByGroupFinal() {
		LinkedList<List<Participant>> participantsByGroup = participantService.getAdvancedPlayersByGroup(new Groups()
		        ._setId(3)._setPlayOffType(GroupsPlayOffType.FINAL), new Tournament()._setId(1)._setFinalPromoting(2),
		        participantService.getParticipandByGroup(new Groups()._setId(3)));
		Assert.assertSame(2, participantsByGroup.size());
		Assert.assertSame(2, participantsByGroup.get(0).size());
		Assert.assertSame(2, participantsByGroup.get(1).size());
	}

	@Test
	@Verify("getParticipantTest-verify.xml")
	public void getAdvancedPlayersByGroupLower() {
		LinkedList<List<Participant>> participantsByGroup = participantService.getAdvancedPlayersByGroup(new Groups()
		        ._setId(4)._setPlayOffType(GroupsPlayOffType.LOWER), new Tournament()._setId(1)._setLowerPromoting(2)
		        ._setFinalPromoting(2), participantService.getParticipandByGroup(new Groups()._setId(4)));
		Assert.assertSame(2, participantsByGroup.size());
		Assert.assertSame(2, participantsByGroup.get(0).size());
		Assert.assertSame(2, participantsByGroup.get(1).size());
	}

	@Test
	@Verify("getParticipantTest-verify.xml")
	public void getAdvancedPlayersByGroupCrossException() {
		try {
			participantService.getAdvancedPlayersByGroup(new Groups()._setId(4)
			        ._setPlayOffType(GroupsPlayOffType.CROSS), new Tournament()._setId(1)._setLowerPromoting(2)
			        ._setFinalPromoting(1), participantService.getParticipandByGroup(new Groups()._setId(4)));
			Assert.fail();
		} catch (BadOptionsTournamentException e) {
			Assert.assertSame("bad.options.exception", e.getCode());
		}
	}

	@Test
	@Verify("getParticipantTest-verify.xml")
	public void getAdvancedPlayersByGroupCross() {
		LinkedList<List<Participant>> participantsByGroup = participantService.getAdvancedPlayersByGroup(new Groups()
		        ._setId(4)._setPlayOffType(GroupsPlayOffType.CROSS), new Tournament()._setId(1)._setLowerPromoting(2)
		        ._setFinalPromoting(2), participantService.getParticipandByGroup(new Groups()._setId(4)));
		Assert.assertSame(2, participantsByGroup.size());
		Assert.assertSame(2, participantsByGroup.get(0).size());
		Assert.assertSame(2, participantsByGroup.get(1).size());
	}

	@Test
	@Verify("getParticipantTest-verify.xml")
	public void sortParticipantsByRank() {
		List<Participant> participants = participantService.getParticipandByGroup(new Groups()._setId(1));
		participantService.sortParticipantsByRank(participants, new Tournament()._setWinPoints(2));

		Assert.assertSame(4, participants.size());
		Assert.assertEquals((Integer) 1, participants.get(0).getId());
		Assert.assertEquals((Integer) 2, participants.get(1).getId());
		Assert.assertEquals((Integer) 3, participants.get(2).getId());
		Assert.assertEquals((Integer) 4, participants.get(3).getId());
	}

	@Test
	@Verify("getParticipantTest-verify.xml")
	public void getGoldGoalParticipants() {
		List<Participant> participants = participantService.getParticipandByGroup(new Groups()._setId(4));
		Set<Participant> goldenParticipants = participantService.getGoldGoalParticipants(participants);

		Assert.assertSame(1, goldenParticipants.size());
	}
}
