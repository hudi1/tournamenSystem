package it.group;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.toursys.processor.service.group.GroupService;
import org.toursys.processor.service.tournament.TournamentService;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsPlayOffType;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Tournament;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("../clear-all.xml"), @Setup() })
public class GroupIT {

    @Autowired
    private GroupService groupService;

    @Autowired
    private TournamentService tournamentService;

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

}
