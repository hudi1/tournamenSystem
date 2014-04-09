package org.toursys.repository.model;

public class GameImpl extends Game {

    private static final long serialVersionUID = 1L;

    private Integer hockey;
    private Integer round;
    private String test;
    private Integer homeScore;

    public GameImpl() {
    }

    public GameImpl(Game game) {
        super.setAwayParticipant(game.getAwayParticipant());
        super.setHomeParticipant(game.getHomeParticipant());
        super.setResult(game.getResult());
        super.setId(game.getId());
    }

    public Integer getHockey() {
        return hockey;
    }

    public void setHockey(Integer hockey) {
        this.hockey = hockey;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

}
