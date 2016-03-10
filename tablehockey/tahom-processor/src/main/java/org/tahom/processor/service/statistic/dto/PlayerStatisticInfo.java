package org.tahom.processor.service.statistic.dto;

import java.io.Serializable;

import org.tahom.repository.model.Score;

public class PlayerStatisticInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tournamentName;
	private Integer finalRank;
	private Integer matchesCount;
	private Integer winnersCount;
	private Integer losesCount;
	private Integer drawsCount;
	private Score score;
	private Integer points;
	private String percentage;
	private String ratioPlus;
	private String ratioMinus;

	public String getTournamentName() {
		return tournamentName;
	}

	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}

	public Integer getFinalRank() {
		return finalRank;
	}

	public void setFinalRank(Integer finalRank) {
		this.finalRank = finalRank;
	}

	public Integer getMatchesCount() {
		return matchesCount;
	}

	public void setMatchesCount(Integer matchesCount) {
		this.matchesCount = matchesCount;
	}

	public Integer getWinnersCount() {
		return winnersCount;
	}

	public void setWinnersCount(Integer winnersCount) {
		this.winnersCount = winnersCount;
	}

	public Integer getLosesCount() {
		return losesCount;
	}

	public void setLosesCount(Integer losesCount) {
		this.losesCount = losesCount;
	}

	public Integer getDrawsCount() {
		return drawsCount;
	}

	public void setDrawsCount(Integer drawsCount) {
		this.drawsCount = drawsCount;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getRatioPlus() {
		return ratioPlus;
	}

	public void setRatioPlus(String ratioPlus) {
		this.ratioPlus = ratioPlus;
	}

	public String getRatioMinus() {
		return ratioMinus;
	}

	public void setRatioMinus(String ratioMinus) {
		this.ratioMinus = ratioMinus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}