package org.toursys.repository.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.model.Season;
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

        tournamentAggregationDao.getSeason(new Season()._setName("xxx"));

        return;
        /*
         * // insert User user = TournamentFactory.createUser(); tournamentAggregationDao.createUser(user);
         * Assert.assertNotNull(user);
         * 
         * Player player = TournamentFactory.createPlayer(); player.setUser(user);
         * tournamentAggregationDao.createPlayer(player); Assert.assertNotNull(player);
         * 
         * Season season = TournamentFactory.createSeason(); season.setUser(user);
         * tournamentAggregationDao.createSeason(season); Assert.assertNotNull(season);
         * 
         * Tournament tournament = TournamentFactory.createTournament();
         * tournamentAggregationDao.createTournament(tournament._setSeason(season)); Assert.assertNotNull(tournament);
         * 
         * Groups group = TournamentFactory.createGroup(); group.setTournament(tournament);
         * tournamentAggregationDao.createGroup(group); Assert.assertNotNull(group);
         * 
         * Participant participant = tournamentAggregationDao.createParticipant(player, group);
         * Assert.assertNotNull(participant);
         * 
         * Game game = tournamentAggregationDao.createGame(participant, participant); Assert.assertNotNull(game);
         * 
         * PlayOffGame playOffGame = tournamentAggregationDao.createPlayOffGame(participant, participant, group, 1);
         * Assert.assertNotNull(playOffGame);
         * 
         * // update User updatedUser = new User(); updatedUser.setId(user.getId());
         * updatedUser.setName(TournamentFactory.USER_NAME + UPDATED);
         * updatedUser.setSurname(TournamentFactory.USER_SURNAME + UPDATED);
         * updatedUser.setEmail(TournamentFactory.USER_EMAIL + UPDATED);
         * updatedUser.setUserName(TournamentFactory.USER_USERNAME + UPDATED);
         * updatedUser.setPassword(TournamentFactory.USER_PASSWORD + UPDATED);
         * updatedUser.setPlatnost(TournamentFactory.USER_PLATNOST); tournamentAggregationDao.updateUser(updatedUser);
         * Assert.assertNotNull(updatedUser);
         * 
         * Player updatedPlayer = new Player(); updatedPlayer.setId(player.getId());
         * updatedPlayer.setName(TournamentFactory.PLAYER_NAME + UPDATED);
         * updatedPlayer.setSurname(TournamentFactory.PLAYER_SURNAME + UPDATED); updatedPlayer.setUser(updatedUser);
         * tournamentAggregationDao.updatePlayer(updatedPlayer); Assert.assertNotNull(updatedPlayer);
         * 
         * Season updatedSeason = new Season(); updatedSeason.setId(season.getId()); updatedSeason.setUser(updatedUser);
         * updatedSeason.setName(TournamentFactory.SEASONE_NAME + UPDATED);
         * tournamentAggregationDao.updateSeason(updatedSeason); Assert.assertNotNull(updatedSeason);
         * 
         * Tournament updatedTournament = TournamentFactory.createTournament();
         * updatedTournament.setId(tournament.getId()); updatedTournament.setSeason(updatedSeason);
         * updatedTournament.setName(TournamentFactory.TOURNAMENT_NAME + UPDATED);
         * tournamentAggregationDao.updateTournament(updatedTournament); Assert.assertNotNull(updatedTournament);
         * 
         * Groups updatedGroup = TournamentFactory.createGroup(); updatedGroup.setId(group.getId());
         * updatedGroup.setTournament(updatedTournament); updatedGroup.setType(GroupsType.FINAL);
         * updatedGroup.setName(TournamentFactory.GROUP_NAME + UPDATED);
         * tournamentAggregationDao.updateGroup(updatedGroup); Assert.assertNotNull(updatedGroup);
         * 
         * Participant updatedParticipant = new Participant(); updatedParticipant.setId(participant.getId());
         * updatedParticipant.setPlayer(updatedPlayer); updatedParticipant.setGroup(updatedGroup);
         * updatedParticipant.setPoints(10); updatedParticipant.setScore(new Score(1, 1));
         * tournamentAggregationDao.updateParticipant(updatedParticipant); Assert.assertNotNull(updatedParticipant);
         * 
         * Game updatedGame = new Game(); updatedGame.setId(game.getId());
         * updatedGame.setAwayParticipant(updatedParticipant); updatedGame.setHomeParticipant(updatedParticipant);
         * updatedGame.setAwayScore(5); updatedGame.setHomeScore(6); tournamentAggregationDao.updateGame(updatedGame);
         * Assert.assertNotNull(updatedGame);
         * 
         * PlayOffGame updatedPlayOffGame = new PlayOffGame(); updatedPlayOffGame.setId(playOffGame.getId());
         * updatedPlayOffGame.setAwayParticipant(updatedParticipant);
         * updatedPlayOffGame.setHomeParticipant(updatedParticipant); updatedPlayOffGame.setPosition(2);
         * updatedPlayOffGame.setGroup(updatedGroup); tournamentAggregationDao.updatePlayOffGame(updatedPlayOffGame);
         * Assert.assertNotNull(updatedPlayOffGame);
         * 
         * // get User u = new User(); u.setId(updatedUser.getId()); u = tournamentAggregationDao.getUser(u);
         * Assert.assertNotNull(u); Assert.assertEquals(TournamentFactory.USER_NAME + UPDATED, u.getName());
         * Assert.assertEquals(TournamentFactory.USER_SURNAME + UPDATED, u.getSurname());
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
         * Participant pr = new Participant(); pr.setId(updatedParticipant.getId()); pr.setPoints(10); pr =
         * tournamentAggregationDao.getParticipant(pr); Assert.assertNotNull(pr); Assert.assertEquals((Integer) 10,
         * pr.getPoints()); Assert.assertEquals(new Score(1, 1), pr.getScore());
         * 
         * Game gm = new Game(); gm.setId(updatedGame.getId()); gm = tournamentAggregationDao.getGame(updatedGame);
         * Assert.assertNotNull(gm); Assert.assertEquals((Integer) 6, gm.getHomeScore()); Assert.assertEquals((Integer)
         * 5, gm.getAwayScore());
         * 
         * PlayOffGame pogm = new PlayOffGame(); pogm.setId(updatedPlayOffGame.getId()); pogm =
         * tournamentAggregationDao.getPlayOffGame(updatedPlayOffGame); Assert.assertNotNull(pogm);
         * Assert.assertEquals((Integer) 2, pogm.getPosition());
         * 
         * // test of my own sql
         * 
         * List<Player> notRegistratedPlayer = tournamentAggregationDao.getNotRegisteredPlayers(tournament);
         * Assert.assertNotNull(notRegistratedPlayer);
         * 
         * List<Participant> registratedParticipant = tournamentAggregationDao.getRegisteredParticipant(tournament);
         * Assert.assertNotNull(registratedParticipant);
         */
    }
}
