package org.toursys.repository.model;
  
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;

public class Groups implements Serializable {
  
  private static final long serialVersionUID = 1L;
	
  public Groups() {
  }
  
  public Groups(String name, int numberOfHockey, String groupType, int indexOfFirstHockey, Tournament tournament) {
    this.name = name;
    this.numberOfHockey = numberOfHockey;
    this.groupType = groupType;
    this.indexOfFirstHockey = indexOfFirstHockey;
    this.tournament = tournament;
  }
  
  private int id;
    
  public int getId() {
    return id;
  }
    
  public void setId(int id) {
    this.id = id;
  }
    
  public Groups _setId(int id) {
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
  
  private int numberOfHockey;
    
  public int getNumberOfHockey() {
    return numberOfHockey;
  }
    
  public void setNumberOfHockey(int numberOfHockey) {
    this.numberOfHockey = numberOfHockey;
  }
    
  public Groups _setNumberOfHockey(int numberOfHockey) {
    this.numberOfHockey = numberOfHockey;
    return this;
  }
  
  private String groupType;
    
  public String getGroupType() {
    return groupType;
  }
    
  public void setGroupType(String groupType) {
    this.groupType = groupType;
  }
    
  public Groups _setGroupType(String groupType) {
    this.groupType = groupType;
    return this;
  }
  
  private int indexOfFirstHockey;
    
  public int getIndexOfFirstHockey() {
    return indexOfFirstHockey;
  }
    
  public void setIndexOfFirstHockey(int indexOfFirstHockey) {
    this.indexOfFirstHockey = indexOfFirstHockey;
  }
    
  public Groups _setIndexOfFirstHockey(int indexOfFirstHockey) {
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
  
  private List<PlayerResult> playerResults = new ArrayList<PlayerResult>();
    
  public List<PlayerResult> getPlayerResults() {
    return playerResults;
  }
    
  public void setPlayerResults(List<PlayerResult> playerResults) {
    this.playerResults = playerResults;
  }
    
  public Groups _setPlayerResults(List<PlayerResult> playerResults) {
    this.playerResults = playerResults;
    return this;
  }
  
  @Override
  public String toString() {
    return "Groups [groupType=" + groupType + ", id=" + id + ", indexOfFirstHockey=" + indexOfFirstHockey + ", name=" + name + ", numberOfHockey=" + numberOfHockey + "]";
  }
  
  public String toStringFull() {
    return "Groups [groupType=" + groupType + ", id=" + id + ", tournament=" + tournament + ", indexOfFirstHockey=" + indexOfFirstHockey + ", name=" + name + ", numberOfHockey=" + numberOfHockey + "]";
  }
}
