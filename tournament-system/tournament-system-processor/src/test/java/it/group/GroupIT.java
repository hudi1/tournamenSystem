package it.group;

import java.util.List;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.toursys.processor.service.group.GroupService;
import org.toursys.processor.service.participant.ParticipantService;
import org.toursys.processor.service.tournament.TournamentService;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsPlayOffType;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Tournament;

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

    @Test
    @Verify("createGroupTest-verify.xml")
    public void createGroupTest() {
        Tournament tournament = tournamentService.getTournament(new Tournament()._setId(1));
        Groups group = groupService.createGroup(new Groups()._setCopyResult(true)._setIndexOfFirstHockey(1)
                ._setName("groupName")._setNumberOfHockey(1)._setPlayOff(true)._setPlayOffType(GroupsPlayOffType.FINAL)
                ._setPlayThirdPlace(true)._setType(GroupsType.FINAL)._setTournament(tournament));
        Assert.assertNotNull(group.getId());
    }

    @Test
    @Verify("getGroupTest-verify.xml")
    public void getGroupTest() {
        Groups Group = groupService.getGroup(new Groups()._setId(1));
        Assert.assertNotNull(Group);
    }

    @Test
    @Verify("deleteGroupTest-verify.xml")
    public void deleteGroupTest() {
        int count = groupService.deleteGroup(new Groups()._setId(1));
        Assert.assertNotSame(0, count);
    }

    @Test
    @Verify("updateGroupTest-verify.xml")
    public void updateGroupTest() {
        Groups group = groupService.getGroup(new Groups()._setId(1));
        int count = groupService.updateGroup(group._setCopyResult(false)._setIndexOfFirstHockey(1)
                ._setName("groupNameEdit")._setNumberOfHockey(1)._setPlayOff(false)
                ._setPlayOffType(GroupsPlayOffType.LOWER)._setPlayThirdPlace(false)._setType(GroupsType.BASIC));
        Assert.assertNotSame(0, count);
    }

    @Test
    @Verify("getGroupTest-verify.xml")
    public void getGroupsTest() {
        List<Groups> groups = groupService.getGroups(new Groups());
        Assert.assertSame(2, groups.size());
    }

    @Test
    @Verify("getGroupTest-verify.xml")
    public void getBasicGroupsTest() {
        List<Groups> groups = groupService.getBasicGroups(new Tournament()._setId(1));
        Assert.assertSame(1, groups.size());
    }

    @Test
    @Verify("getGroupTest-verify.xml")
    public void getFinalGroupsTest() {
        List<Groups> groups = groupService.getFinalGroups(new Tournament()._setId(1));
        Assert.assertSame(1, groups.size());
    }

    @Test
    @Verify("createGroupsTest-verify.xml")
    // TODO
    public void createGroups() {
        Tournament tournament = new Tournament()._setId(1);
        List<Participant> tournamentParticipants = participantService.getRegistratedParticipant(tournament);

        groupService.createGroups(new Tournament()._setId(1), tournamentParticipants, "4");
    }

}
