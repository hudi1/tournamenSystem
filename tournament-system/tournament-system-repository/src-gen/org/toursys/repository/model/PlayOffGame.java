package org.toursys.repository.model;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class PlayOffGame implements Serializable {
  
  private static final long serialVersionUID = 1L;
  public static final int ORDER_BY_ID = 1;
  public static final int ORDER_BY_HOME_PARTICIPANT = 4;
  public static final int ORDER_BY_AWAY_PARTICIPANT = 3;
  public static final int ORDER_BY_GROUP = 2;
	
  public PlayOffGame() {
  }
  
  public PlayOffGame(Groups group, Integer position) {
    this.group = group;
    this.position = position;
  }
  
  private Integer id;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public PlayOffGame _setId(Integer id) {
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
  
  public PlayOffGame _setHomeParticipant(Participant homeParticipant) {
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
  
  public PlayOffGame _setAwayParticipant(Participant awayParticipant) {
    this.awayParticipant = awayParticipant;
    return this;
  }
  
  private Groups group;
  
  public Groups getGroup() {
    return group;
  }
  
  public void setGroup(Groups group) {
    this.group = group;
  }
  
  public PlayOffGame _setGroup(Groups group) {
    this.group = group;
    return this;
  }
  
  private Integer position;
  
  public Integer getPosition() {
    return position;
  }
  
  public void setPosition(Integer position) {
    this.position = position;
  }
  
  public PlayOffGame _setPosition(Integer position) {
    this.position = position;
    return this;
  }
  
  private String results;
  
  public String getResults() {
    return results;
  }
  
  public void setResults(String results) {
    this.results = results;
  }
  
  public PlayOffGame _setResults(String results) {
    this.results = results;
    return this;
  }
  
  private PlayOffGameWinner winner;
  
  public PlayOffGameWinner getWinner() {
    return winner;
  }
  
  public void setWinner(PlayOffGameWinner winner) {
    this.winner = winner;
  }
  
  public PlayOffGame _setWinner(PlayOffGameWinner winner) {
    this.winner = winner;
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
    PlayOffGame other = (PlayOffGame) obj;
    if (id == null || !id.equals(other.id))
      return false;
    return true;
  }  
  
  public enum Association {
    homeParticipant, awayParticipant, group
  }
  
  private Set<String> initAssociations = new HashSet<String>();
  
  public void setInit(Association... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (Association association : associations)
      initAssociations.add(association.name());
  }
  
  public PlayOffGame  _setInit(Association... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(Association... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (Association association : associations)
      initAssociations.remove(association.name());
  }
  
  public PlayOffGame _clearInit(Association... associations) {
    clearInit(associations);
    return this;
  }
  
  public void setInit(String... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (String association : associations)
      initAssociations.add(association);
  }
  
  public PlayOffGame _setInit(String... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(String... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (String association : associations)
      initAssociations.remove(association);
  }
  
  public PlayOffGame _clearInit(String... associations) {
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
    results, winner, homeParticipant, awayParticipant
  }
  
  private Set<String> nullValues = new HashSet<String>();
  
  public void setNull(Attribute... attributes) {
    if (attributes == null)
      throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
      nullValues.add(attribute.name());
  }
  
  public PlayOffGame _setNull(Attribute... attributes) {
    setNull(attributes);
    return this;
  }
  
  public void clearNull(Attribute... attributes) {
    if (attributes == null)
      throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
      nullValues.remove(attribute.name());
  }
  
  public PlayOffGame _clearNull(Attribute... attributes) {
    clearNull(attributes);
    return this;
  }
  
  public void setNull(String... attributes) {
    if (attributes == null)
      throw new IllegalArgumentException();
    for (String attribute : attributes)
      nullValues.add(attribute);
  }
  
  public PlayOffGame _setNull(String... attributes) {
    setNull(attributes);
    return this;
  }
  
  public void clearNull(String... attributes) {
    if (attributes == null)
      throw new IllegalArgumentException();
    for (String attribute : attributes)
      nullValues.remove(attribute);
  }
  
  public PlayOffGame _clearNull(String... attributes) {
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
    return "PlayOffGame [position=" + position + ", id=" + id + ", results=" + results + ", winner=" + winner + "]";
  }
  
  public String toStringFull() {
    return "PlayOffGame [id=" + id + ", homeParticipant=" + homeParticipant + ", awayParticipant=" + awayParticipant + ", group=" + group + ", position=" + position + ", results=" + results + ", winner=" + winner + "]";
  }
}
