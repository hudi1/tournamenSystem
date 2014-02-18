package org.toursys.repository.model;

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
  
  public Game(Participant homeParticipant, Participant awayParticipant) {
    this.homeParticipant = homeParticipant;
    this.awayParticipant = awayParticipant;
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
  
  private Integer homeScore;
  
  public Integer getHomeScore() {
    return homeScore;
  }
  
  public void setHomeScore(Integer homeScore) {
    this.homeScore = homeScore;
  }
  
  public Game _setHomeScore(Integer homeScore) {
    this.homeScore = homeScore;
    return this;
  }
  
  private Integer awayScore;
  
  public Integer getAwayScore() {
    return awayScore;
  }
  
  public void setAwayScore(Integer awayScore) {
    this.awayScore = awayScore;
  }
  
  public Game _setAwayScore(Integer awayScore) {
    this.awayScore = awayScore;
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
  
  public Game  _setInit(Association... associations) {
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
  
  @Override
  public String toString() {
    return "Game [id=" + id + ", homeScore=" + homeScore + ", awayScore=" + awayScore + "]";
  }
  
  public String toStringFull() {
    return "Game [id=" + id + ", homeParticipant=" + homeParticipant + ", awayParticipant=" + awayParticipant + ", homeScore=" + homeScore + ", awayScore=" + awayScore + "]";
  }
}
