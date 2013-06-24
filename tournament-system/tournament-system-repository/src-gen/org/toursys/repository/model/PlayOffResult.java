package org.toursys.repository.model;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class PlayOffResult implements Serializable {
  
  private static final long serialVersionUID = 1L;
  public static final int ORDER_BY_ID = 1;
  public static final int ORDER_BY_PLAY_OFF_GAME = 2;
	
  public PlayOffResult() {
  }
  
  public PlayOffResult(Boolean overtime, PlayOffGame playOffGame) {
    this.overtime = overtime;
    this.playOffGame = playOffGame;
  }
  
  private Integer id;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public PlayOffResult _setId(Integer id) {
    this.id = id;
    return this;
  }
  
  private Integer homeScore;
  
  public Integer getHomeScore() {
    return homeScore;
  }
  
  public void setHomeScore(Integer homeScore) {
    this.homeScore = homeScore;
  }
  
  public PlayOffResult _setHomeScore(Integer homeScore) {
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
  
  public PlayOffResult _setAwayScore(Integer awayScore) {
    this.awayScore = awayScore;
    return this;
  }
  
  private Boolean overtime;
  
  public Boolean getOvertime() {
    return overtime;
  }
  
  public void setOvertime(Boolean overtime) {
    this.overtime = overtime;
  }
  
  public PlayOffResult _setOvertime(Boolean overtime) {
    this.overtime = overtime;
    return this;
  }
  
  private PlayOffGame playOffGame;
  
  public PlayOffGame getPlayOffGame() {
    return playOffGame;
  }
  
  public void setPlayOffGame(PlayOffGame playOffGame) {
    this.playOffGame = playOffGame;
  }
  
  public PlayOffResult _setPlayOffGame(PlayOffGame playOffGame) {
    this.playOffGame = playOffGame;
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
    PlayOffResult other = (PlayOffResult) obj;
    if (id == null || !id.equals(other.id))
      return false;
    return true;
  }  
  
  public enum Association {
    playOffGame
  }
  
  private Set<String> initAssociations = new HashSet<String>();
  
  public void setInit(Association... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (Association association : associations)
      initAssociations.add(association.name());
  }
  
  public void clearInit(Association... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (Association association : associations)
      initAssociations.remove(association.name());
  }
  
  public void setInit(String... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (String association : associations)
      initAssociations.add(association);
  }
  
  public void clearInit(String... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (String association : associations)
      initAssociations.remove(association);
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
    return "PlayOffResult [id=" + id + ", homeScore=" + homeScore + ", overtime=" + overtime + ", awayScore=" + awayScore + "]";
  }
  
  public String toStringFull() {
    return "PlayOffResult [id=" + id + ", homeScore=" + homeScore + ", playOffGame=" + playOffGame + ", overtime=" + overtime + ", awayScore=" + awayScore + "]";
  }
}
