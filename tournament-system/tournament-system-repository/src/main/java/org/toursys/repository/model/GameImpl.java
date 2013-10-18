package org.toursys.repository.model;

public class GameImpl extends Game {

    private static final long serialVersionUID = 1L;

    private Integer hockey;
    private Integer round;

    public GameImpl() {
    }

    public GameImpl(Game game) {
        super.setAwayParticipant(game.getAwayParticipant());
        super.setAwayScore(game.getAwayScore());
        super.setHomeParticipant(game.getHomeParticipant());
        super.setHomeScore(game.getHomeScore());
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

}
