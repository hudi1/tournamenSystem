package org.toursys.repository.model;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class Game implements Serializable {
  
  private static final long serialVersionUID = 1L;
  public static final int ORDER_BY_ID = 1;
  public static final int ORDER_BY_HOME_PLAYER_RESULT = 2;
  public static final int ORDER_BY_AWAY_PLAYER_RESULT = 3;
	
  public Game() {
  }
  
  public Game(PlayerResult homePlayerResult, PlayerResult awayPlayerResult) {
    this.homePlayerResult = homePlayerResult;
    this.awayPlayerResult = awayPlayerResult;
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
  
  private PlayerResult homePlayerResult;
  
  public PlayerResult getHomePlayerResult() {
    return homePlayerResult;
  }
  
  public void setHomePlayerResult(PlayerResult homePlayerResult) {
    this.homePlayerResult = homePlayerResult;
  }
  
  public Game _setHomePlayerResult(PlayerResult homePlayerResult) {
    this.homePlayerResult = homePlayerResult;
    return this;
  }
  
  private PlayerResult awayPlayerResult;
  
  public PlayerResult getAwayPlayerResult() {
    return awayPlayerResult;
  }
  
  public void setAwayPlayerResult(PlayerResult awayPlayerResult) {
    this.awayPlayerResult = awayPlayerResult;
  }
  
  public Game _setAwayPlayerResult(PlayerResult awayPlayerResult) {
    this.awayPlayerResult = awayPlayerResult;
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
    awayPlayerResult, homePlayerResult
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
    return "Game [id=" + id + ", homeScore=" + homeScore + ", awayScore=" + awayScore + "]";
  }
  
  public String toStringFull() {
    return "Game [id=" + id + ", homeScore=" + homeScore + ", awayPlayerResult=" + awayPlayerResult + ", homePlayerResult=" + homePlayerResult + ", awayScore=" + awayScore + "]";
  }
}
