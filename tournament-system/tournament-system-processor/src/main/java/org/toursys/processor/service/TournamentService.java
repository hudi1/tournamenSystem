package org.toursys.processor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.toursys.repository.form.PlayerForm;
import org.toursys.repository.form.PlayerResultForm;
import org.toursys.repository.form.TableForm;
import org.toursys.repository.form.TournamentForm;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Table;
import org.toursys.repository.model.TableType;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.service.TournamentAggregationDao;

public class TournamentService {

    private TournamentAggregationDao tournamentAggregationDao;

    public void createPlayer(Player player) {
        tournamentAggregationDao.createPlayer(player);
    }

    public void updatePlayer(Player player) {
        tournamentAggregationDao.updatePlayer(player);
    }

    public void deletePlayer(Player player) {
        tournamentAggregationDao.deletePlayer(player);
    }

    public List<Player> getAllPlayer() {
        return tournamentAggregationDao.getAllPlayer();
    }

    public List<Player> getNotRegistrationPlayer(PlayerForm playerForm) {
        return tournamentAggregationDao.getNotRegistrationPlayer(playerForm);
    }

    public List<Season> getAllSeason() {
        return tournamentAggregationDao.getAllSeason();
    }

    public void updateSeason(Season season) {
        tournamentAggregationDao.updateSeason(season);
    }

    public void createSeason(Season season) {
        tournamentAggregationDao.createSeason(season);
    }

    public void deleteSeason(Season season) {
        List<Tournament> tournaments = findTournament(new TournamentForm(season));
        for (Tournament tournament : tournaments) {
            deleteTournament(tournament);
        }
        tournamentAggregationDao.deleteSeason(season);
    }

    public List<Tournament> findTournament(TournamentForm tournamentForm) {
        return tournamentAggregationDao.findTournament(tournamentForm);
    }

    public void updateTournament(Tournament tournament) {
        tournamentAggregationDao.updateTournament(tournament);
    }

    public void createTournament(Tournament tournament) {
        tournamentAggregationDao.createTournament(tournament);
    }

    public void deleteTournament(Tournament tournament) {
        tournamentAggregationDao.deleteTournament(tournament);
    }

    public void createPlayerResult(PlayerResult playerResult) {
        tournamentAggregationDao.createPlayerResult(playerResult);
    }

    public void deletePlayerResult(PlayerResult playerResult) {
        tournamentAggregationDao.deletePlayerResult(playerResult);
    }

    public List<PlayerResult> findPlayerResult(PlayerResultForm playerResultForm) {
        return tournamentAggregationDao.findPlayerResult(playerResultForm);
    }

    @Required
    public void setTournamentAggregationDao(TournamentAggregationDao tournamentAggregationDao) {
        this.tournamentAggregationDao = tournamentAggregationDao;
    }

    public void createPlayerResult(Tournament tournament, Player player, Table table, TableType tableType) {
        Table currentTable = null;
        if (table.getName() != null) {
            List<Table> tables = tournamentAggregationDao.findTable(new TableForm(table.getName(), tournament));
            if (tables.isEmpty()) {
                table.setTournamentId(tournament.getTournamentId());
                table.setTableType(tableType);
                tournamentAggregationDao.createTable(table);
            } else {
                currentTable = tables.get(0);
            }

            if (currentTable == null) {
                currentTable = tournamentAggregationDao.findTable(new TableForm(table.getName(), tournament)).get(0);
            }

            PlayerResult playerResult = new PlayerResult();
            playerResult.setPlayer(player);
            playerResult.setPoints(0);
            playerResult.setRank(0);
            playerResult.setScore("0:0");
            playerResult.setTournamentTable(currentTable);
            tournamentAggregationDao.createPlayerResult(playerResult);

        }
    }
}
