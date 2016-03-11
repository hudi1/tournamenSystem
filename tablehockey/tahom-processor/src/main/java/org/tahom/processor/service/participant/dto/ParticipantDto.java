package org.tahom.processor.service.participant.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.repository.model.Score;

public class ParticipantDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String shortName;
	private Score score;
	private Integer points;
	private Integer rank;
	private List<GameDto> games;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public List<GameDto> getGames() {
		if (games == null) {
			games = new LinkedList<GameDto>();
		}
		return games;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
