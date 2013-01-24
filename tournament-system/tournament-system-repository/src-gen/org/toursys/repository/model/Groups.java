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
  public static final int ORDER_BY_TOURNAMENT = 3;
	
  public Groups() {
  }
  
  public Groups(String name, Integer numberOfHockey, String groupType, Integer indexOfFirstHockey, Tournament tournament, Boolean copyResult, Boolean playThirdPlace) {
    this.name = name;
    this.numberOfHockey = numberOfHockey;
    this.groupType = groupType;
    this.indexOfFirstHockey = indexOfFirstHockey;
    this.tournament = tournament;
    this.copyResult = copyResult;
    this.playThirdPlace = playThirdPlace;
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
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Groups other = (Groups) obj;
    if (!id.equals(other.id))
      return false;
    return true;
  }  
  
  public enum Association {
    playOffGames, tournament, playerResults
  }
  
  private Set<String> initAssociations = new HashSet<String>();
  
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
    return "Groups [groupType=" + groupType + ", id=" + id + ", playThirdPlace=" + playThirdPlace + ", indexOfFirstHockey=" + indexOfFirstHockey + ", copyResult=" + copyResult + ", name=" + name + ", numberOfHockey=" + numberOfHockey + "]";
  }
  
  public String toStringFull() {
    return "Groups [groupType=" + groupType + ", id=" + id + ", playThirdPlace=" + playThirdPlace + ", tournament=" + tournament + ", indexOfFirstHockey=" + indexOfFirstHockey + ", copyResult=" + copyResult + ", name=" + name + ", numberOfHockey=" + numberOfHockey + "]";
  }
}
