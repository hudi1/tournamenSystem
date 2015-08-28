package org.toursys.processor.service;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.service.group.GroupService;
import org.toursys.processor.service.participant.ParticipantService;
import org.toursys.processor.service.player.PlayerService;
import org.toursys.processor.service.season.SeasonService;
import org.toursys.processor.service.tournament.TournamentService;
import org.toursys.processor.service.user.UserService;
import org.toursys.processor.util.NameGenerator;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest-business.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TournamentServiceTest {

    @Autowired
    TournamentService tournamentService;

    @Autowired
    UserService userService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    PlayerService playerService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    GroupService groupService;

    @Autowired
    NameGenerator nameGenerator;

    private Tournament tournament;
    private User user;

    private TestContextManager testContextManager;
    /*
     * @Before public void vytvorTurnaj() throws Exception { testContextManager = new TestContextManager(getClass());
     * testContextManager.prepareTestInstance(this);
     * 
     * user = TournamentFactory.createUser(); user = userService.createUser(user);
     * 
     * Season season = TournamentFactory.createSeason(); season.setUser(user); season =
     * seasonService.createSeason(season);
     * 
     * tournament = TournamentFactory.createTournament(); tournament.setSeason(season);
     * tournamentService.createTournament(season, tournament); }
     * 
     * @Test public void createBasicParticipantTest() { Groups group = TournamentFactory.createGroup(); Player player =
     * TournamentFactory.createPlayer(); player.setUser(user); player = playerService.createPlayer(player);
     * participantService.createBasicParticipant(tournament, player, group);
     * 
     * Assert.assertNotNull(groupService.getBasicGroups(group));
     * Assert.assertNotNull(participantService.getParticipant(new Participant()._setPlayer(player))); }
     */

}
