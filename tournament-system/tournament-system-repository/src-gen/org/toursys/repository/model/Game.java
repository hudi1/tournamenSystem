package org.toursys.repository.model;

import java.io.Serializable;

public class Game implements Serializable {
  
  private static final long serialVersionUID = 1L;
	
  public Game() {
  }
  
  public Game(PlayerResult homePlayerResult, PlayerResult awayPlayerResult, boolean overtime) {
    this.homePlayerResult = homePlayerResult;
    this.awayPlayerResult = awayPlayerResult;
    this.overtime = overtime;
  }
  
  private int id;
    
  public int getId() {
    return id;
  }
    
  public void setId(int id) {
    this.id = id;
  }
    
  public Game _setId(int id) {
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
  
  private boolean overtime;
    
  public boolean getOvertime() {
    return overtime;
  }
    
  public void setOvertime(boolean overtime) {
    this.overtime = overtime;
  }
    
  public Game _setOvertime(boolean overtime) {
    this.overtime = overtime;
    return this;
  }
  
  @Override
  public String toString() {
    return "Game [id=" + id + ", homeScore=" + homeScore + ", overtime=" + overtime + ", awayScore=" + awayScore + "]";
  }
  
  public String toStringFull() {
    return "Game [id=" + id + ", homeScore=" + homeScore + ", awayPlayerResult=" + awayPlayerResult + ", homePlayerResult=" + homePlayerResult + ", overtime=" + overtime + ", awayScore=" + awayScore + "]";
  }
}
