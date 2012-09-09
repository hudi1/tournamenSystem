package org.toursys.repository.dao;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.dao.helper.TournamentFactory;
import org.toursys.repository.form.GameForm;
import org.toursys.repository.form.PlayerResultForm;
import org.toursys.repository.form.TableForm;
import org.toursys.repository.form.TournamentForm;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Result;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Table;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.service.TournamentAggregationDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:repositoryContextTest.xml" })
@Transactional()
@TransactionConfiguration(defaultRollback = true)
public class TournamentAggregationITest {

    private Season season;
    private Player player;
    private Tournament tournament;
    private Table table;
    private PlayerResult playerResult;
    private Game game;
    private Result result;

    private TournamentForm tournamentForm;
    private TableForm tableForm;
    private PlayerResultForm playerResultForm;
    private GameForm gameForm;

    private int playerSize;
    private int seasonSize;

    @Autowired
    private TournamentAggregationDao tournamentAggregationDao;

    @Before
    public void setUp() throws Exception {
        season = TournamentFactory.createSeason();
        player = TournamentFactory.createPlayer();
        tournament = TournamentFactory.createTournament();
        table = TournamentFactory.createTable();
        playerResult = TournamentFactory.createPlayerResult();
        game = TournamentFactory.createGame();
        result = TournamentFactory.createResult();

        tournamentForm = TournamentFactory.createTournamentForm();
        playerResultForm = TournamentFactory.createPlayerResultForm();
        tableForm = TournamentFactory.createTableForm();
        gameForm = TournamentFactory.createGameForm();

        playerSize = tournamentAggregationDao.getAllPlayer().size();
        seasonSize = tournamentAggregationDao.getAllSeason().size();
    }

    @Test
    public void tournamentTest() {
        tournamentAggregationDao.createPlayer(player);
        tournamentAggregationDao.createSeason(season);
        tournamentAggregationDao.createTournament(tournament);
        tournamentAggregationDao.createTable(table);
        tournamentAggregationDao.createPlayerResult(playerResult);
        tournamentAggregationDao.createResult(result);
        tournamentAggregationDao.createGame(game);

        tournamentAggregationDao.updatePlayer(player);
        tournamentAggregationDao.updateSeason(season);
        tournamentAggregationDao.updateTournament(tournament);
        tournamentAggregationDao.updateTable(table);
        tournamentAggregationDao.updatePlayerResult(playerResult);
        tournamentAggregationDao.updateResult(result);
        tournamentAggregationDao.updateGame(game);

        Assert.assertTrue(tournamentAggregationDao.getAllPlayer().size() > playerSize);
        Assert.assertTrue(tournamentAggregationDao.getAllSeason().size() > seasonSize);

        Assert.assertTrue(tournamentAggregationDao.findTournament(tournamentForm).size() > 0);
        Assert.assertTrue(tournamentAggregationDao.findTable(tableForm).size() > 0);
        Assert.assertTrue(tournamentAggregationDao.findPlayerResult(playerResultForm).size() > 0);
        Assert.assertTrue(tournamentAggregationDao.findGame(gameForm).size() > 0);

        tournamentAggregationDao.deleteGame(game);
        tournamentAggregationDao.deletePlayerResult(playerResult);
        tournamentAggregationDao.deletePlayer(player);
        tournamentAggregationDao.deleteResult(result);
        tournamentAggregationDao.deleteTable(table);
        tournamentAggregationDao.deleteTournament(tournament);
        tournamentAggregationDao.deleteSeason(season);

        Assert.assertTrue(tournamentAggregationDao.getAllPlayer().size() == playerSize);
        Assert.assertTrue(tournamentAggregationDao.getAllSeason().size() == seasonSize);

        Assert.assertTrue(tournamentAggregationDao.findTournament(tournamentForm).size() == 0);
        Assert.assertTrue(tournamentAggregationDao.findTable(tableForm).size() == 0);
        Assert.assertTrue(tournamentAggregationDao.findPlayerResult(playerResultForm).size() == 0);
        Assert.assertTrue(tournamentAggregationDao.findGame(gameForm).size() == 0);
    }

    @Test
    public void tournamentTest1() {
        Tournament t = new Tournament();
        t.setTournamentId(5);
        tournamentAggregationDao.findPlayerResult(new PlayerResultForm(t));
    }

}
