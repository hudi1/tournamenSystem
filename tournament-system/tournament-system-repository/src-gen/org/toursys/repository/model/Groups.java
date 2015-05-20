package org.toursys.repository.model;
	
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class Groups implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final int ORDER_BY_ID = 1;
	public static final int ORDER_BY_NAME = 4;
	public static final int ORDER_BY_TOURNAMENT = 3;
	
	public Groups() {
	}
		
	public Groups(String name, Integer numberOfHockey, GroupsType type, Integer indexOfFirstHockey, Tournament tournament, Boolean copyResult, Boolean playThirdPlace, Boolean playOff, GroupsPlayOffType playOffType) {
		this.name = name;
		this.numberOfHockey = numberOfHockey;
		this.type = type;
		this.indexOfFirstHockey = indexOfFirstHockey;
		this.tournament = tournament;
		this.copyResult = copyResult;
		this.playThirdPlace = playThirdPlace;
		this.playOff = playOff;
		this.playOffType = playOffType;
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Groups _setId(Integer id) {
		this.id = id;
		return this;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Groups _setName(String name) {
		this.name = name;
		return this;
	}

	private Integer numberOfHockey;

	public Integer getNumberOfHockey() {
		return numberOfHockey;
	}

	public void setNumberOfHockey(Integer numberOfHockey) {
		this.numberOfHockey = numberOfHockey;
	}

	public Groups _setNumberOfHockey(Integer numberOfHockey) {
		this.numberOfHockey = numberOfHockey;
		return this;
	}

	private GroupsType type;

	public GroupsType getType() {
		return type;
	}

	public void setType(GroupsType type) {
		this.type = type;
	}

	public Groups _setType(GroupsType type) {
		this.type = type;
		return this;
	}

	private Integer indexOfFirstHockey;

	public Integer getIndexOfFirstHockey() {
		return indexOfFirstHockey;
	}

	public void setIndexOfFirstHockey(Integer indexOfFirstHockey) {
		this.indexOfFirstHockey = indexOfFirstHockey;
	}

	public Groups _setIndexOfFirstHockey(Integer indexOfFirstHockey) {
		this.indexOfFirstHockey = indexOfFirstHockey;
		return this;
	}

	private Tournament tournament;

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public Groups _setTournament(Tournament tournament) {
		this.tournament = tournament;
		return this;
	}

	private Boolean copyResult;

	public Boolean getCopyResult() {
		return copyResult;
	}

	public void setCopyResult(Boolean copyResult) {
		this.copyResult = copyResult;
	}

	public Groups _setCopyResult(Boolean copyResult) {
		this.copyResult = copyResult;
		return this;
	}

	private Boolean playThirdPlace;

	public Boolean getPlayThirdPlace() {
		return playThirdPlace;
	}

	public void setPlayThirdPlace(Boolean playThirdPlace) {
		this.playThirdPlace = playThirdPlace;
	}

	public Groups _setPlayThirdPlace(Boolean playThirdPlace) {
		this.playThirdPlace = playThirdPlace;
		return this;
	}

	private Boolean playOff;

	public Boolean getPlayOff() {
		return playOff;
	}

	public void setPlayOff(Boolean playOff) {
		this.playOff = playOff;
	}

	public Groups _setPlayOff(Boolean playOff) {
		this.playOff = playOff;
		return this;
	}

	private GroupsPlayOffType playOffType;

	public GroupsPlayOffType getPlayOffType() {
		return playOffType;
	}

	public void setPlayOffType(GroupsPlayOffType playOffType) {
		this.playOffType = playOffType;
	}

	public Groups _setPlayOffType(GroupsPlayOffType playOffType) {
		this.playOffType = playOffType;
		return this;
	}

	private List<Participant> participants = new ArrayList<Participant>();

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public Groups _setParticipants(List<Participant> participants) {
		this.participants = participants;
		return this;
	}

	private List<PlayOffGame> playOffGames = new ArrayList<PlayOffGame>();

	public List<PlayOffGame> getPlayOffGames() {
		return playOffGames;
	}

	public void setPlayOffGames(List<PlayOffGame> playOffGames) {
		this.playOffGames = playOffGames;
	}

	public Groups _setPlayOffGames(List<PlayOffGame> playOffGames) {
		this.playOffGames = playOffGames;
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
		Groups other = (Groups) obj;
		if (id == null || !id.equals(other.id))
		return false;
		return true;
	}	

	public enum Association {
		playOffGames, tournament, participants
	}

	private Set<String> initAssociations = new HashSet<String>();

	public void setInit(Association... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (Association association : associations)
			initAssociations.add(association.name());
	}
	
	public Groups	_setInit(Association... associations) {
		setInit(associations);
		return this;
	}

	public void clearInit(Association... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (Association association : associations)
			initAssociations.remove(association.name());
	}

	public Groups _clearInit(Association... associations) {
		clearInit(associations);
		return this;
	}

	public void setInit(String... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (String association : associations)
			initAssociations.add(association);
	}

	public Groups _setInit(String... associations) {
		setInit(associations);
		return this;
	}

	public void clearInit(String... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (String association : associations)
			initAssociations.remove(association);
	}

	public Groups _clearInit(String... associations) {
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
	}

	private Set<String> nullValues = new HashSet<String>();

	public void setNull(Attribute... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (Attribute attribute : attributes)
			nullValues.add(attribute.name());
	}

	public Groups _setNull(Attribute... attributes) {
		setNull(attributes);
		return this;
	}

	public void clearNull(Attribute... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (Attribute attribute : attributes)
			nullValues.remove(attribute.name());
	}

	public Groups _clearNull(Attribute... attributes) {
		clearNull(attributes);
		return this;
	}

	public void setNull(String... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (String attribute : attributes)
			nullValues.add(attribute);
	}

	public Groups _setNull(String... attributes) {
		setNull(attributes);
		return this;
	}

	public void clearNull(String... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (String attribute : attributes)
			nullValues.remove(attribute);
	}

	public Groups _clearNull(String... attributes) {
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
		return "Groups [id=" + id + ", playThirdPlace=" + playThirdPlace + ", indexOfFirstHockey=" + indexOfFirstHockey + ", copyResult=" + copyResult + ", name=" + name + ", numberOfHockey=" + numberOfHockey + ", type=" + type + ", playOffType=" + playOffType + ", playOff=" + playOff + "]";
	}

	public String toStringFull() {
		return "Groups [id=" + id + ", name=" + name + ", numberOfHockey=" + numberOfHockey + ", type=" + type + ", indexOfFirstHockey=" + indexOfFirstHockey + ", tournament=" + tournament + ", copyResult=" + copyResult + ", playThirdPlace=" + playThirdPlace + ", playOff=" + playOff + ", playOffType=" + playOffType + ", participants=" + participants + ", playOffGames=" + playOffGames + "]";
	}
}
