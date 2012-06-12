package org.toursys.repository.model;

import java.io.Serializable;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class Player implements Serializable {

	private static final long serialVersionUID = 1L;

	private long idPlayer;

	private String name;

	private String surname;

	private String club;

	public Player() {
	}

	public long getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(long idPlayer) {
		this.idPlayer = idPlayer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

}
