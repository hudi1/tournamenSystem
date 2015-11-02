package it.participant;

import java.util.List;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.toursys.processor.service.participant.ParticipantService;
import org.toursys.repository.model.Groups;
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
		int count = participantService.deletePlayerParticipant(new Participant()._setId(1), new Tournament()._setId(1));
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("updateParticipantTest-verify.xml")
	public void updateParticipantTest() {
		Participant participant = participantService.getParticipandByGroup(new Groups()._setId(1)).get(0);
		int count = participantService.updateParticipant(participant._setEqualRank(1));
		Assert.assertNotSame(0, count);
	}

	@Test
	@Verify("getParticipantTest-verify.xml")
	public void getRegistratedParticipantTest() {
		List<Participant> participants = participantService.getParticipandByGroup(new Groups()._setId(1));
		Assert.assertSame(1, participants.size());
	}
}
