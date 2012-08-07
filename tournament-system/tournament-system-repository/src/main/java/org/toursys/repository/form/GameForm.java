package org.toursys.repository.form;

import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.PlayerResult;

@Pojo
public class GameForm {

    private PlayerResult playerResult;

    private PlayerResult opponent;

    public PlayerResult getPlayerResult() {
        return playerResult;
    }

    public void setPlayerResult(PlayerResult playerResult) {
        this.playerResult = playerResult;
    }

    public PlayerResult getOpponent() {
        return opponent;
    }

    public void setOpponent(PlayerResult opponent) {
        this.opponent = opponent;
    }

    public GameForm(PlayerResult playerResult, PlayerResult opponent) {
        this.playerResult = playerResult;
        this.opponent = opponent;
    }

}
