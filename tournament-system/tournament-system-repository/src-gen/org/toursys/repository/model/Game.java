package org.toursys.repository.model;
	
import org.toursys.repository.model.Results;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class Game implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final int ORDER_BY_ID = 1;
	public static final int ORDER_BY_HOME_PARTICIPANT = 2;
	public static final int ORDER_BY_AWAY_PARTICIPANT = 3;
	
	public Game() {
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Game _setId(Integer id) {
		this.id = id;
		return this;
	}

	private Participant homeParticipant;

	public Participant getHomeParticipant() {
		return homeParticipant;
	}

	public void setHomeParticipant(Participant homeParticipant) {
		this.homeParticipant = homeParticipant;
	}

	public Game _setHomeParticipant(Participant homeParticipant) {
		this.homeParticipant = homeParticipant;
		return this;
	}

	private Participant awayParticipant;

	public Participant getAwayParticipant() {
		return awayParticipant;
	}

	public void setAwayParticipant(Participant awayParticipant) {
		this.awayParticipant = awayParticipant;
	}

	public Game _setAwayParticipant(Participant awayParticipant) {
		this.awayParticipant = awayParticipant;
		return this;
	}

	private GameStatus status;

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public Game _setStatus(GameStatus status) {
		this.status = status;
		return this;
	}

	private Results result;

	public Results getResult() {
		return result;
	}

	public void setResult(Results result) {
		this.result = result;
	}

	public Game _setResult(Results result) {
		this.result = result;
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
		Game other = (Game) obj;
		if (id == null || !id.equals(other.id))
		return false;
		return true;
	}	

	public enum Association {
		homeParticipant, awayParticipant
	}

	private Set<String> initAssociations = new HashSet<String>();

	public void setInit(Association... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (Association association : associations)
			initAssociations.add(association.name());
	}
	
	public Game	_setInit(Association... associations) {
		setInit(associations);
		return this;
	}

	public void clearInit(Association... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (Association association : associations)
			initAssociations.remove(association.name());
	}

	public Game _clearInit(Association... associations) {
		clearInit(associations);
		return this;
	}

	public void setInit(String... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (String association : associations)
			initAssociations.add(association);
	}

	public Game _setInit(String... associations) {
		setInit(associations);
		return this;
	}

	public void clearInit(String... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (String association : associations)
			initAssociations.remove(association);
	}

	public Game _clearInit(String... associations) {
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
		result, status, homeParticipant, awayParticipant
	}

	private Set<String> nullValues = new HashSet<String>();

	public void setNull(Attribute... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (Attribute attribute : attributes)
			nullValues.add(attribute.name());
	}

	public Game _setNull(Attribute... attributes) {
		setNull(attributes);
		return this;
	}

	public void clearNull(Attribute... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (Attribute attribute : attributes)
			nullValues.remove(attribute.name());
	}

	public Game _clearNull(Attribute... attributes) {
		clearNull(attributes);
		return this;
	}

	public void setNull(String... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (String attribute : attributes)
			nullValues.add(attribute);
	}

	public Game _setNull(String... attributes) {
		setNull(attributes);
		return this;
	}

	public void clearNull(String... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (String attribute : attributes)
			nullValues.remove(attribute);
	}

	public Game _clearNull(String... attributes) {
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
		return "Game [id=" + id + ", result=" + result + ", status=" + status + "]";
	}

	public String toStringFull() {
		return "Game [id=" + id + ", homeParticipant=" + homeParticipant + ", awayParticipant=" + awayParticipant + ", status=" + status + ", result=" + result + "]";
	}
}
