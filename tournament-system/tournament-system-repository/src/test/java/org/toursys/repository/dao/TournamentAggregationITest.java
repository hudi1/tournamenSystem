package org.toursys.repository.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.service.TournamentAggregationDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:repositoryContextTest.xml" })
@Transactional()
@TransactionConfiguration(defaultRollback = true)
public class TournamentAggregationITest {

    private static final String UPDATED = " Updated";

    @Autowired
    private TournamentAggregationDao tournamentAggregationDao;

    @Test
    public void tournamentTest() {

        // insert
        /*
         * Player player = TournamentFactory.createPlayer(); tournamentAggregationDao.createPlayer(player);
         * Assert.assertNotNull(player);
         * 
         * Season season = TournamentFactory.createSeason(); tournamentAggregationDao.createSeason(season);
         * Assert.assertNotNull(season);
         * 
         * Tournament tournament = TournamentFactory.createTournament();
         * tournamentAggregationDao.createTournament(season, tournament); Assert.assertNotNull(tournament);
         * 
         * Groups group = TournamentFactory.createGroup(); group.setTournament(tournament);
         * tournamentAggregationDao.createGroup(group); Assert.assertNotNull(group);
         * 
         * PlayerResult playerResult = tournamentAggregationDao.createPlayerResult(player, group);
         * Assert.assertNotNull(playerResult);
         * 
         * Game game = tournamentAggregationDao.createGame(playerResult, playerResult); Assert.assertNotNull(game);
         * 
         * // update
         * 
         * Player updatedPlayer = new Player(); updatedPlayer.setId(player.getId());
         * updatedPlayer.setName(TournamentFactory.PLAYER_NAME + UPDATED);
         * updatedPlayer.setSurname(TournamentFactory.PLAYER_SURNAME + UPDATED);
         * tournamentAggregationDao.updatePlayer(updatedPlayer); Assert.assertNotNull(updatedPlayer);
         * 
         * Season updatedSeason = new Season(); updatedSeason.setId(season.getId());
         * updatedSeason.setName(TournamentFactory.SEASONE_NAME + UPDATED);
         * tournamentAggregationDao.updateSeason(updatedSeason); Assert.assertNotNull(updatedSeason);
         * 
         * Tournament updatedTournament = new Tournament(); updatedTournament.setId(tournament.getId());
         * updatedTournament.setSeason(updatedSeason); updatedTournament.setName(TournamentFactory.TOURNAMENT_NAME +
         * UPDATED); tournamentAggregationDao.updateTournament(updatedTournament);
         * Assert.assertNotNull(updatedTournament);
         * 
         * Groups updatedGroup = new Groups(); updatedGroup.setId(group.getId());
         * updatedGroup.setTournament(updatedTournament); updatedGroup.setGroupType(GroupType.F.value());
         * updatedGroup.setName(TournamentFactory.GROUP_NAME + UPDATED);
         * tournamentAggregationDao.updateGroup(updatedGroup); Assert.assertNotNull(updatedGroup);
         * 
         * PlayerResult updatedPlayerResult = new PlayerResult(); updatedPlayerResult.setId(playerResult.getId());
         * updatedPlayerResult.setPlayer(updatedPlayer); updatedPlayerResult.setGroup(updatedGroup);
         * updatedPlayerResult.setPoints(10); updatedPlayerResult.setScore(new Score(1, 1));
         * tournamentAggregationDao.updatePlayerResult(updatedPlayerResult); Assert.assertNotNull(updatedPlayerResult);
         * 
         * Game updatedGame = new Game(); updatedGame.setId(game.getId());
         * updatedGame.setAwayPlayerResult(updatedPlayerResult); updatedGame.setHomePlayerResult(updatedPlayerResult);
         * updatedGame.setAwayScore(5); updatedGame.setHomeScore(6); tournamentAggregationDao.updateGame(updatedGame);
         * Assert.assertNotNull(updatedGame);
         * 
         * // get
         * 
         * Player p = new Player(); p.setId(updatedPlayer.getId()); p = tournamentAggregationDao.getPlayer(p);
         * Assert.assertNotNull(p); Assert.assertEquals(TournamentFactory.PLAYER_NAME + UPDATED, p.getName());
         * Assert.assertEquals(TournamentFactory.PLAYER_SURNAME + UPDATED, p.getSurname());
         * 
         * Season s = new Season(); s.setId(updatedSeason.getId()); s = tournamentAggregationDao.getSeason(s);
         * Assert.assertNotNull(s); Assert.assertEquals(TournamentFactory.SEASONE_NAME + UPDATED, s.getName());
         * 
         * Tournament t = new Tournament(); t.setId(updatedTournament.getId()); t =
         * tournamentAggregationDao.getTournament(t); Assert.assertNotNull(t);
         * Assert.assertEquals(TournamentFactory.TOURNAMENT_NAME + UPDATED, t.getName());
         * 
         * Groups g = new Groups(); g.setId(updatedGroup.getId()); g = tournamentAggregationDao.getGroup(updatedGroup);
         * Assert.assertNotNull(g); Assert.assertEquals(TournamentFactory.GROUP_NAME + UPDATED, g.getName());
         * 
         * PlayerResult pr = new PlayerResult(); pr.setId(updatedPlayerResult.getId()); pr.setPoints(10); pr =
         * tournamentAggregationDao.getPlayerResult(pr); Assert.assertNotNull(pr); Assert.assertEquals((Integer) 10,
         * pr.getPoints()); Assert.assertEquals(new Score(1, 1), pr.getScore());
         * 
         * Game gm = new Game(); gm.setId(updatedGame.getId()); gm = tournamentAggregationDao.getGame(updatedGame);
         * Assert.assertNotNull(gm); Assert.assertEquals((Integer) 6, gm.getHomeScore()); Assert.assertEquals((Integer)
         * 5, gm.getAwayScore());
         * 
         * // test of my own sql
         * 
         * List<Player> notRegistratedPlayer = tournamentAggregationDao.getNotRegistratedPlayers(tournament);
         * Assert.assertNotNull(notRegistratedPlayer);
         * 
         * List<PlayerResult> registratedPlayerResult = tournamentAggregationDao.getRegistratedPlayerResult(tournament);
         * Assert.assertNotNull(registratedPlayerResult);
         */
    }

    @Ignore
    @Test
    public void tournamentPlayerTest() {
        // insert
        // Player player = TournamentFactory.createPlayer();
        // tournamentAggregationDao.createPlayer(player);
        // Assert.assertNotNull(player);

        // Season season = TournamentFactory.createSeason();
        // tournamentAggregationDao.createSeason(season);
        // Assert.assertNotNull(season);

        // Tournament tournament = TournamentFactory.createTournament();
        // tournamentAggregationDao.createTournament(season, tournament);
        // Assert.assertNotNull(tournament);

        // Groups group = TournamentFactory.createGroup();
        // tournamentAggregationDao.createGroup(group);
        // Assert.assertNotNull(group);

        // PlayerResult playerResult = tournamentAggregationDao.createPlayerResult(player, group);
        // Assert.assertNotNull(playerResult);

    }
}
