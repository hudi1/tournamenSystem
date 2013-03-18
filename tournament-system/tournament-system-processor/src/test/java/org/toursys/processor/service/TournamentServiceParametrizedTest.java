package org.toursys.processor.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.toursys.processor.util.NameGenerator;
import org.toursys.repository.dao.helper.TournamentFactory;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;

@RunWith(value = Parameterized.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest-business.xml" })
public class TournamentServiceParametrizedTest {

    @Autowired
    TournamentService tournamentService;

    @Autowired
    NameGenerator nameGenerator;

    private Tournament tournament;
    private User user;
    private static final int MAX_PLAYER_COUNT = 26;
    private static final int MAX_GROUP_COUNT = 4;

    private int playerCount;
    private int hockeyCount;
    private int groupCount;

    private TestContextManager testContextManager;

    private Random random = new Random();

    public TournamentServiceParametrizedTest(int playerCount, int hockeyCount, int groupCount) {
        this.playerCount = playerCount;
        this.hockeyCount = hockeyCount;
        this.groupCount = groupCount;
    }

    @Parameters
    public static List<Object[]> data() {
        final List<Object[]> parametry = new ArrayList<Object[]>();
        for (int i = 4; i <= 4; i++) {
            for (int j = 2; j <= i / 2; j++) {
                for (int k = 1; k <= 1; k++) {
                    parametry.add(new Object[] { i, j, k });
                }
            }
        }
        return parametry;
    }

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

    @After
    public void deleteUser() {
        tournamentService.deleteUser(user);
    }

    @Test
    public void createTournamentGamesTest() {
        System.out.println("Start Players: " + playerCount + " HockeyCount: " + hockeyCount + " Groups: " + groupCount);

        for (int i = 0; i < groupCount; i++) {
            List<PlayerResult> playerResults = new ArrayList<PlayerResult>();
            Groups group = TournamentFactory.createGroup();
            group.setName(Integer.toString(i + 1));
            for (int j = 0; j < playerCount; j++) {
                Player player = new Player(nameGenerator.getName(), nameGenerator.getName(), user);
                player = tournamentService.createPlayer(player);
                playerResults.add(tournamentService.createBasicPlayerResult(tournament, player, group));
            }
            tournamentService.createGames(playerResults);

            for (PlayerResult playerResult : playerResults) {
                Assert.assertFalse(playerResult.getGames().isEmpty());
                Assert.assertEquals(playerResult.getGames().size(), playerCount - 1);
            }

            group.setNumberOfHockey(hockeyCount);

            List<GameImpl> schedule = tournamentService.getSchedule(group, tournament, playerResults);
            int gamesCount = playerCount * 2;

            for (GameImpl gameImpl : schedule) {
                System.out.println(gameImpl.toStringFull());
            }
            Assert.assertEquals(schedule.size(), gamesCount);

            Set<PlayerResult> players = new HashSet<PlayerResult>();
            int round = 1;
            for (GameImpl gameImpl : schedule) {
                if (gameImpl.getRound() == round) {
                    checkConstainsInRound(gameImpl.getAwayPlayerResult(), players);
                    checkConstainsInRound(gameImpl.getHomePlayerResult(), players);
                } else {
                    // Assert.assertEquals(players.size(), hockeyCount * 2);
                    players.clear();
                    round++;
                    checkConstainsInRound(gameImpl.getAwayPlayerResult(), players);
                    checkConstainsInRound(gameImpl.getHomePlayerResult(), players);
                }
            }

            int roundCount = (playerCount % 2 == 0) ? playerCount : playerCount + 1;
            Assert.assertEquals(round, roundCount);

            for (GameImpl game : schedule) {
                game.setHomeScore(getRandomScore());
                game.setHomeScore(getRandomScore());
            }

            PlayerResult previousPlayerResult = playerResults.get(0);
            for (PlayerResult playerResult : playerResults) {
                Assert.assertTrue(playerResult.getPoints() >= previousPlayerResult.getPoints());
                previousPlayerResult = playerResult;
            }
        }
        // TODO ak pocet min hracov v skupine je vacsi ako pocet hracov v A skupine tak vyhodi vyjimku
        tournamentService.createFinalGroup(tournament);

        List<Groups> finalGroups = tournamentService.getFinalGroups(new Groups()._setTournament(tournament));

        Assert.assertFalse(finalGroups.isEmpty());

        for (Groups finalGroup : finalGroups) {
            List<PlayerResult> playerResult = tournamentService.getPlayerResultInGroup(new PlayerResult()
                    ._setGroup(finalGroup));
            Assert.assertFalse(playerResult.isEmpty());
            tournamentService.copyResult(tournament);

            if (finalGroup.getName().equals("A")) {
                Assert.assertTrue(playerResult.size() <= (groupCount * tournament.getFinalPromoting()));

                if (Math.min(playerCount, tournament.getFinalPromoting()) % 2 == 0) {
                    finalGroup.setNumberOfHockey(playerResult.size() / 2);
                } else {
                    finalGroup.setNumberOfHockey(playerResult.size() / groupCount);
                }

                List<GameImpl> finalSchedule = tournamentService.getSchedule(finalGroup, tournament, playerResult);

                // TODO zatial mi finalove skupiny funguju len s parnym poctom ked sa prenasaju vysledky
                Set<PlayerResult> finalPlayers = new HashSet<PlayerResult>();
                int finalRound = 1;
                for (GameImpl gameImpl : finalSchedule) {
                    if (gameImpl.getRound() == finalRound) {
                        checkConstainsInRound(gameImpl.getAwayPlayerResult(), finalPlayers);
                        checkConstainsInRound(gameImpl.getHomePlayerResult(), finalPlayers);
                    } else {
                        Assert.assertEquals(finalPlayers.size(), finalGroup.getNumberOfHockey() * 2);
                        finalPlayers.clear();
                        finalRound++;
                        checkConstainsInRound(gameImpl.getAwayPlayerResult(), finalPlayers);
                        checkConstainsInRound(gameImpl.getHomePlayerResult(), finalPlayers);
                    }
                }
            } else {
                Assert.assertTrue(playerResult.size() <= (groupCount * tournament.getLowerPromoting()));
                tournamentService.getSchedule(finalGroup, tournament, playerResult);
            }

            // playOff
            finalGroup.setPlayThirdPlace(random.nextBoolean());
            List<PlayOffGame> playOffGames = tournamentService.getPlayOffGames(tournament, finalGroup);

            if (finalGroup.getName().equals("A")) {
                int gamesCount = tournament.getPlayOffA();
                if (!finalGroup.getPlayThirdPlace()) {
                    gamesCount--;
                }
                Assert.assertEquals(gamesCount, playOffGames.size());
            } else {
                int gamesCount = tournament.getPlayOffLower();
                if (!finalGroup.getPlayThirdPlace()) {
                    gamesCount--;
                }
                Assert.assertEquals(gamesCount, playOffGames.size());
            }
        }
    }

    private void checkConstainsInRound(PlayerResult playerResult, Set<PlayerResult> players) {
        if (playerResult != null) {
            boolean contains = !players.add(playerResult);
            if (contains) {
                Assert.fail("Player: " + playerResult + " is already in this round. Players: " + playerCount
                        + " HockeyCount: " + hockeyCount + "Groups: " + groupCount);
            }
        }
    }

    private int getRandomScore() {
        return random.nextInt(15);
    }
}
