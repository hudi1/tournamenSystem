package org.toursys.repository.model;
  
import java.util.List;
import org.toursys.repository.model.Score;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerResult implements Serializable {
  
  private static final long serialVersionUID = 1L;
	
  public PlayerResult() {
  }
  
  public PlayerResult(int points, Groups group, Player player, Score score) {
    this.points = points;
    this.group = group;
    this.player = player;
    this.score = score;
  }
  
  private int id;
    
  public int getId() {
    return id;
  }
    
  public void setId(int id) {
    this.id = id;
  }
    
  public PlayerResult _setId(int id) {
    this.id = id;
    return this;
  }
  
  private int points;
    
  public int getPoints() {
    return points;
  }
    
  public void setPoints(int points) {
    this.points = points;
  }
    
  public PlayerResult _setPoints(int points) {
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
    
  public PlayerResult _setRank(Integer rank) {
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
    
  public PlayerResult _setGroup(Groups group) {
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
    
  public PlayerResult _setPlayer(Player player) {
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
    
  public PlayerResult _setScore(Score score) {
    this.score = score;
    return this;
  }
  
  private List<Game> games = new ArrayList<Game>();
    
  public List<Game> getGames() {
    return games;
  }
    
  public void setGames(List<Game> games) {
    this.games = games;
  }
    
  public PlayerResult _setGames(List<Game> games) {
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
    PlayerResult other = (PlayerResult) obj;
    if (id != other.id)
      return false;
    return true;
  }  
  
  @Override
  public String toString() {
    return "PlayerResult [id=" + id + ", rank=" + rank + ", score=" + score + ", points=" + points + "]";
  }
  
  public String toStringFull() {
    return "PlayerResult [id=" + id + ", rank=" + rank + ", player=" + player + ", score=" + score + ", points=" + points + ", group=" + group + "]";
  }
}
