package org.toursys.repository.dao.helper;

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
import org.toursys.repository.model.TableType;
import org.toursys.repository.model.Tournament;

public class TournamentFactory {

	private static final long id = 1l;

	public static Tournament createTournament() {
		Tournament tournament = new Tournament();
		tournament.setName("Kosice Open");
		tournament.setSeasonId(id);
		tournament.setTournamentId(id);
		return tournament;
	}

	public static Season createSeason() {
		Season season = new Season();
		season.setName("2012");
		season.setSeasonId(id);
		return season;
	}

	public static Table createTable() {
		Table table = new Table();
		table.setIndexOfFirstHockey(1);
		table.setName("1");
		table.setNumberOfHockey(5);
		table.setTableType(TableType.B);
		table.setTournamentId(id);
		table.setTableId(id);
		return table;
	}

	public static Player createPlayer() {
		Player player = new Player();
		player.setClub("THC BLUE DRAGON");
		player.setName("Tomas");
		player.setSurname("Hudec");
		player.setPlayerId(id);
		return player;
	}

	public static PlayerResult createPlayerResult() {
		PlayerResult playerResult = new PlayerResult();
		playerResult.setPlayerId(id);
		playerResult.setPlayerResultId(id);
		playerResult.setPoints(0);
		playerResult.setRank(1);
		playerResult.setTournamentTableId(id);
		playerResult.setScore("0:0");
		return playerResult;
	}

	public static Result createResult() {
		Result result = new Result();
		result.setLeftSide(1);
		result.setRightSide(0);
		result.setOvertime(false);
		result.setResultId(id);
		return result;
	}

	public static Game createGame() {
		Game game = new Game();
		game.setGameId(id);
		game.setOpponentId(id);
		game.setPlayerResultId(id);
		game.setResult(createResult());
		return game;
	}

	public static TournamentForm createTournamentForm() {
		return new TournamentForm(createSeason());
	}

	public static PlayerResultForm createPlayerResultForm() {
		return new PlayerResultForm(createTable());

	}

	public static TableForm createTableForm() {
		return new TableForm(createTournament());
	}

	public static GameForm createGameForm() {
		return new GameForm(createPlayerResult(), createPlayerResult());
	}
}
