package org.toursys.processor.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest-business.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TournamentServiceTest {

    /*
     * @Autowired TournamentService tournamentService;
     * 
     * @Autowired NameGenerator nameGenerator;
     * 
     * private Tournament tournament; private User user;
     * 
     * private TestContextManager testContextManager;
     * 
     * @Before public void vytvorTurnaj() throws Exception { testContextManager = new TestContextManager(getClass());
     * testContextManager.prepareTestInstance(this);
     * 
     * user = TournamentFactory.createUser(); user = tournamentService.createUser(user);
     * 
     * Season season = TournamentFactory.createSeason(); season.setUser(user); season =
     * tournamentService.createSeason(season);
     * 
     * tournament = TournamentFactory.createTournament(); tournament.setSeason(season);
     * tournamentService.createTournament(season, tournament); }
     * 
     * @Test public void createBasicPlayerResultTest() { Groups group = TournamentFactory.createGroup(); Player player =
     * TournamentFactory.createPlayer(); player.setUser(user); player = tournamentService.createPlayer(player);
     * tournamentService.createBasicPlayerResult(tournament, player, group);
     * 
     * Assert.assertNotNull(tournamentService.getBasicGroups(group));
     * Assert.assertNotNull(tournamentService.getPlayerResults(new PlayerResult()._setPlayer(player))); }
     */

}
