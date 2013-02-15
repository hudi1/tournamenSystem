package org.toursys.processor.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.util.NameGenerator;
import org.toursys.repository.dao.helper.TournamentFactory;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest-business.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TournamentServiceTest {

    @Autowired
    TournamentService tournamentService;

    @Autowired
    NameGenerator nameGenerator;

    private Tournament tournament;
    private User user;

    private TestContextManager testContextManager;

    @Before
    public void vytvorTurnaj() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        user = TournamentFactory.createUser();
        user = tournamentService.createUser(user);

        Season season = TournamentFactory.createSeason();
        season.setUser(user);
        season = tournamentService.createSeason(season);

        tournament = TournamentFactory.createTournament();
        tournament.setSeason(season);
        tournamentService.createTournament(season, tournament);
    }

    @Test
    public void createBasicPlayerResultTest() {
        Groups group = TournamentFactory.createGroup();
        Player player = TournamentFactory.createPlayer();
        player.setUser(user);
        player = tournamentService.createPlayer(player);
        tournamentService.createBasicPlayerResult(tournament, player, group);

        Assert.assertNotNull(tournamentService.getBasicGroups(group));
        Assert.assertNotNull(tournamentService.getPlayerResultInGroup(new PlayerResult()._setPlayer(player)));
    }

}
