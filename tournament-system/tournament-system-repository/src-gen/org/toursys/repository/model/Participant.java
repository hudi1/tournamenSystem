package org.toursys.repository.model;
	
import java.util.List;
import org.toursys.repository.model.Score;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class Participant implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final int ORDER_BY_ID = 1;
	public static final int ORDER_BY_GROUP = 3;
	public static final int ORDER_BY_PLAYER = 2;
	
	public Participant() {
	}
		
	public Participant(Integer points, Groups group, Player player, Score score) {
		this.points = points;
		this.group = group;
		this.player = player;
		this.score = score;
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Participant _setId(Integer id) {
		this.id = id;
		return this;
	}

	private Integer points;

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Participant _setPoints(Integer points) {
		this.points = points;
		return this;
	}

	private Integer rank;

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Participant _setRank(Integer rank) {
		this.rank = rank;
		return this;
	}

	private Groups group;

	public Groups getGroup() {
		return group;
	}

	public void setGroup(Groups group) {
		this.group = group;
	}

	public Participant _setGroup(Groups group) {
		this.group = group;
		return this;
	}

	private Player player;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Participant _setPlayer(Player player) {
		this.player = player;
		return this;
	}

	private Score score;

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public Participant _setScore(Score score) {
		this.score = score;
		return this;
	}

	private Integer equalRank;

	public Integer getEqualRank() {
		return equalRank;
	}

	public void setEqualRank(Integer equalRank) {
		this.equalRank = equalRank;
	}

	public Participant _setEqualRank(Integer equalRank) {
		this.equalRank = equalRank;
		return this;
	}

	private boolean temp;

	public boolean getTemp() {
		return temp;
	}

	public void setTemp(boolean temp) {
		this.temp = temp;
	}

	public Participant _setTemp(boolean temp) {
		this.temp = temp;
		return this;
	}

	private List<Game> games = new ArrayList<Game>();

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public Participant _setGames(List<Game> games) {
		this.games = games;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participant other = (Participant) obj;
		if (id == null || !id.equals(other.id))
		return false;
		return true;
	}	

	public enum Association {
		games, player, group
	}

	private Set<String> initAssociations = new HashSet<String>();

	public void setInit(Association... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (Association association : associations)
			initAssociations.add(association.name());
	}
	
	public Participant	_setInit(Association... associations) {
		setInit(associations);
		return this;
	}

	public void clearInit(Association... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (Association association : associations)
			initAssociations.remove(association.name());
	}

	public Participant _clearInit(Association... associations) {
		clearInit(associations);
		return this;
	}

	public void setInit(String... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (String association : associations)
			initAssociations.add(association);
	}

	public Participant _setInit(String... associations) {
		setInit(associations);
		return this;
	}

	public void clearInit(String... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (String association : associations)
			initAssociations.remove(association);
	}

	public Participant _clearInit(String... associations) {
		clearInit(associations);
		return this;
	}

	public Boolean toInit(String association) {
		if (association == null)
			throw new IllegalArgumentException();
		return initAssociations.contains(association);
	}

	public void clearAllInit() {
		initAssociations = new HashSet<String>();
	}

	public enum Attribute {
		rank, temp, equalRank
	}

	private Set<String> nullValues = new HashSet<String>();

	public void setNull(Attribute... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (Attribute attribute : attributes)
			nullValues.add(attribute.name());
	}

	public Participant _setNull(Attribute... attributes) {
		setNull(attributes);
		return this;
	}

	public void clearNull(Attribute... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (Attribute attribute : attributes)
			nullValues.remove(attribute.name());
	}

	public Participant _clearNull(Attribute... attributes) {
		clearNull(attributes);
		return this;
	}

	public void setNull(String... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (String attribute : attributes)
			nullValues.add(attribute);
	}

	public Participant _setNull(String... attributes) {
		setNull(attributes);
		return this;
	}

	public void clearNull(String... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (String attribute : attributes)
			nullValues.remove(attribute);
	}

	public Participant _clearNull(String... attributes) {
		clearNull(attributes);
		return this;
	}

	public Boolean isNull(String attrName) {
		if (attrName == null)
			throw new IllegalArgumentException();
		return nullValues.contains(attrName);
	}

	public Boolean isNull(Attribute attribute) {
		if (attribute == null)
			throw new IllegalArgumentException();
		return nullValues.contains(attribute.name());
	}

	public Boolean isDef(String attrName) {
		if (attrName == null)
			throw new IllegalArgumentException();
		if (nullValues.contains(attrName))
			return true;
		try {
			Object result = MethodUtils.invokeMethod(this, "get" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1, attrName.length()), null);
			return (result != null) ? true : false;
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		try {
			Object result = MethodUtils.invokeMethod(this, "is" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1, attrName.length()), null);
			return (result != null) ? true : false;
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public void clearAllNull() {
		nullValues = new HashSet<String>();
	}

	@Override
	public String toString() {
		return "Participant [id=" + id + ", rank=" + rank + ", score=" + score + ", temp=" + temp + ", equalRank=" + equalRank + ", points=" + points + "]";
	}

	public String toStringFull() {
		return "Participant [id=" + id + ", points=" + points + ", rank=" + rank + ", group=" + group + ", player=" + player + ", score=" + score + ", equalRank=" + equalRank + ", temp=" + temp + ", games=" + games + "]";
	}
}
