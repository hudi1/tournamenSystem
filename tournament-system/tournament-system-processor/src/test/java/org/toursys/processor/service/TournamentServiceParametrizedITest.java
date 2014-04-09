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
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Results;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;

@RunWith(value = Parameterized.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest-business.xml" })
public class TournamentServiceParametrizedITest {

    @Autowired
    TournamentService tournamentService;

    @Autowired
    FinalStandingService finalStandingService;

    @Autowired
    GameService gameService;

    @Autowired
    GroupService groupService;

    @Autowired
    ParticipantService playerResultService;

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayOffGameService playOffGameService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    UserService userService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    NameGenerator nameGenerator;

    private Tournament tournament;
    private User user;
    private static final int MAX_PLAYER_COUNT = 20;
    private static final int MAX_GROUP_COUNT = 4;

    private int playerCount;
    private int hockeyCount;
    private int groupCount;

    private TestContextManager testContextManager;

    private Random random = new Random();

    public TournamentServiceParametrizedITest(int playerCount, int hockeyCount, int groupCount) {
        this.playerCount = playerCount;
        this.hockeyCount = hockeyCount;
        this.groupCount = groupCount;
    }

    @Parameters
    public static List<Object[]> data() {
        final List<Object[]> parametry = new ArrayList<Object[]>();
        for (int i = 4; i <= MAX_PLAYER_COUNT; i++) {
            for (int j = 2; j <= i / 2; j++) {
                for (int k = 1; k <= MAX_GROUP_COUNT; k++) {
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
        user = userService.createUser(user);

        Season season = TournamentFactory.createSeason();
        season.setUser(user);
        season = seasonService.createSeason(season);

        tournament = TournamentFactory.createTournament();
        tournament.setSeason(season);
        tournamentService.createTournament(season, tournament);
    }

    @After
    public void deleteUser() {
        userService.deleteUser(user);
    }

    @Test
    public void createTournamentGamesTest() {

        System.out.println("Start Players: " + playerCount + " HockeyCount: " + hockeyCount + " Groups: " + groupCount);

        for (int i = 0; i < groupCount; i++) {
            List<Participant> playerResults = new ArrayList<Participant>();
            Groups group = TournamentFactory.createGroup();
            group.setName(Integer.toString(i + 1));
            for (int j = 0; j < playerCount; j++) {
                Player player = new Player(nameGenerator.getName(), nameGenerator.getName(), null, user);
                player = playerService.createPlayer(player);
                playerResults.add(participantService.createBasicParticipant(tournament, player, group));
            }
            gameService.processGames(playerResults);

            for (Participant playerResult : playerResults) {
                Assert.assertFalse(playerResult.getGames().isEmpty());
                Assert.assertEquals(playerResult.getGames().size(), playerCount - 1);
            }

            group.setNumberOfHockey(hockeyCount);

            List<GameImpl> schedule = scheduleService.getSchedule(tournament, group, playerResults).getSchedule();

            Set<Participant> players = new HashSet<Participant>();
            int round = 1;
            for (GameImpl gameImpl : schedule) {
                if (gameImpl.getRound() == round) {
                    checkConstainsInRound(gameImpl.getAwayParticipant(), players);
                    checkConstainsInRound(gameImpl.getHomeParticipant(), players);
                } else {
                    players.clear();
                    round++;
                    checkConstainsInRound(gameImpl.getAwayParticipant(), players);
                    checkConstainsInRound(gameImpl.getHomeParticipant(), players);
                }
            }

            for (GameImpl game : schedule) {
                game.setResult(new Results(getRandomScore() + ":" + getRandomScore()));
            }

            Participant previousParticipant = playerResults.get(0);
            for (Participant playerResult : playerResults) {
                Assert.assertTrue(playerResult.getPoints() >= previousParticipant.getPoints());
                previousParticipant = playerResult;
            }
        } // TODO ak pocet min hracov v skupine je vacsi ako pocet hracov v A // skupine tak vyhodi vyjimku

        groupService.createFinalGroup(tournament);
        finalStandingService.processFinalStandings(tournament);

        List<Groups> finalGroups = groupService.getFinalGroups(new Groups()._setTournament(tournament));

        Assert.assertFalse(finalGroups.isEmpty());

        for (Groups finalGroup : finalGroups) {
            List<Participant> playerResult = participantService
                    .getParticipants(new Participant()._setGroup(finalGroup));
            Assert.assertFalse(playerResult.isEmpty());
            groupService.copyResult(tournament);

            if (finalGroup.getName().equals("A")) {
                Assert.assertTrue(playerResult.size() <= (groupCount * tournament.getFinalPromoting()));

                if (Math.min(playerCount, tournament.getFinalPromoting()) % 2 == 0) {
                    finalGroup.setNumberOfHockey(playerResult.size() / 2);
                } else {
                    finalGroup.setNumberOfHockey(playerResult.size() / groupCount);
                }

                List<GameImpl> finalSchedule = scheduleService.getSchedule(tournament, finalGroup, playerResult)
                        .getSchedule();

                // TODO zatial mi finalove skupiny funguju len s parnym poctom // ked sa prenasaju vysledky
                // Set<Participant>
                Set<Participant> finalPlayers = new HashSet<Participant>();
                int finalRound = 1;
                for (GameImpl gameImpl : finalSchedule) {
                    if (gameImpl.getRound() == finalRound) {
                        checkConstainsInRound(gameImpl.getAwayParticipant(), finalPlayers);
                        checkConstainsInRound(gameImpl.getHomeParticipant(), finalPlayers);
                    } else {
                        Assert.assertEquals(finalPlayers.size(), finalGroup.getNumberOfHockey() * 2);
                        finalPlayers.clear();
                        finalRound++;
                        checkConstainsInRound(gameImpl.getAwayParticipant(), finalPlayers);
                        checkConstainsInRound(gameImpl.getHomeParticipant(), finalPlayers);
                    }
                }
            } else {
                Assert.assertTrue(playerResult.size() <= (groupCount * tournament.getLowerPromoting()));
            }

            // playOff

            finalGroup.setPlayThirdPlace(random.nextBoolean());
            playOffGameService.processPlayOffGames(tournament);
            List<PlayOffGame> playOffGames = playOffGameService
                    .getPlayOffGames(new PlayOffGame()._setGroup(finalGroup));

            int gamesCount = 0;

            switch (finalGroup.getPlayOffType()) {
            case FINAL:
                gamesCount = tournament.getPlayOffFinal();
                break;
            case LOWER:
                gamesCount = tournament.getPlayOffLower();
                break;
            default:
                break;
            }
            Assert.assertEquals(gamesCount, playOffGames.size());

            // tournamentService.getPlayOffGames(tournament, finalGroup);

        }

    }

    private void checkConstainsInRound(Participant playerResult, Set<Participant> players) {
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
