package org.toursys.repository.model;
  
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class Tournament implements Serializable {
  
  private static final long serialVersionUID = 1L;
  public static final int ORDER_BY_ID = 1;
  public static final int ORDER_BY_SEASON = 2;
	
  public Tournament() {
  }
  
  public Tournament(String name, Season season, Integer finalPromoting, Integer lowerPromoting, Integer winPoints, Integer playOffA, Integer playOffLower, Integer minPlayersInGroup) {
    this.name = name;
    this.season = season;
    this.finalPromoting = finalPromoting;
    this.lowerPromoting = lowerPromoting;
    this.winPoints = winPoints;
    this.playOffA = playOffA;
    this.playOffLower = playOffLower;
    this.minPlayersInGroup = minPlayersInGroup;
  }
  
  private Integer id;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public Tournament _setId(Integer id) {
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
  
  public Tournament _setName(String name) {
    this.name = name;
    return this;
  }
  
  private Season season;
  
  public Season getSeason() {
    return season;
  }
  
  public void setSeason(Season season) {
    this.season = season;
  }
  
  public Tournament _setSeason(Season season) {
    this.season = season;
    return this;
  }
  
  private Integer finalPromoting;
  
  public Integer getFinalPromoting() {
    return finalPromoting;
  }
  
  public void setFinalPromoting(Integer finalPromoting) {
    this.finalPromoting = finalPromoting;
  }
  
  public Tournament _setFinalPromoting(Integer finalPromoting) {
    this.finalPromoting = finalPromoting;
    return this;
  }
  
  private Integer lowerPromoting;
  
  public Integer getLowerPromoting() {
    return lowerPromoting;
  }
  
  public void setLowerPromoting(Integer lowerPromoting) {
    this.lowerPromoting = lowerPromoting;
  }
  
  public Tournament _setLowerPromoting(Integer lowerPromoting) {
    this.lowerPromoting = lowerPromoting;
    return this;
  }
  
  private Integer winPoints;
  
  public Integer getWinPoints() {
    return winPoints;
  }
  
  public void setWinPoints(Integer winPoints) {
    this.winPoints = winPoints;
  }
  
  public Tournament _setWinPoints(Integer winPoints) {
    this.winPoints = winPoints;
    return this;
  }
  
  private Integer playOffA;
  
  public Integer getPlayOffA() {
    return playOffA;
  }
  
  public void setPlayOffA(Integer playOffA) {
    this.playOffA = playOffA;
  }
  
  public Tournament _setPlayOffA(Integer playOffA) {
    this.playOffA = playOffA;
    return this;
  }
  
  private Integer playOffLower;
  
  public Integer getPlayOffLower() {
    return playOffLower;
  }
  
  public void setPlayOffLower(Integer playOffLower) {
    this.playOffLower = playOffLower;
  }
  
  public Tournament _setPlayOffLower(Integer playOffLower) {
    this.playOffLower = playOffLower;
    return this;
  }
  
  private Integer minPlayersInGroup;
  
  public Integer getMinPlayersInGroup() {
    return minPlayersInGroup;
  }
  
  public void setMinPlayersInGroup(Integer minPlayersInGroup) {
    this.minPlayersInGroup = minPlayersInGroup;
  }
  
  public Tournament _setMinPlayersInGroup(Integer minPlayersInGroup) {
    this.minPlayersInGroup = minPlayersInGroup;
    return this;
  }
  
  private List<Groups> groups = new ArrayList<Groups>();
  
  public List<Groups> getGroups() {
    return groups;
  }
  
  public void setGroups(List<Groups> groups) {
    this.groups = groups;
  }
  
  public Tournament _setGroups(List<Groups> groups) {
    this.groups = groups;
    return this;
  }
  
  private List<FinalStanding> finalStandings = new ArrayList<FinalStanding>();
  
  public List<FinalStanding> getFinalStandings() {
    return finalStandings;
  }
  
  public void setFinalStandings(List<FinalStanding> finalStandings) {
    this.finalStandings = finalStandings;
  }
  
  public Tournament _setFinalStandings(List<FinalStanding> finalStandings) {
    this.finalStandings = finalStandings;
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
    Tournament other = (Tournament) obj;
    if (id == null || !id.equals(other.id))
      return false;
    return true;
  }  
  
  public enum Association {
    season, finalStandings, groups
  }
  
  private Set<String> initAssociations = new HashSet<String>();
  
  public void setInit(Association... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (Association association : associations)
      initAssociations.add(association.name());
  }
  
  public Tournament  _setInit(Association... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(Association... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (Association association : associations)
      initAssociations.remove(association.name());
  }
  
  public Tournament _clearInit(Association... associations) {
    clearInit(associations);
    return this;
  }
  
  public void setInit(String... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (String association : associations)
      initAssociations.add(association);
  }
  
  public Tournament _setInit(String... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(String... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (String association : associations)
      initAssociations.remove(association);
  }
  
  public Tournament _clearInit(String... associations) {
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
    return "Tournament [id=" + id + ", playOffLower=" + playOffLower + ", season=" + season + ", playOffA=" + playOffA + ", winPoints=" + winPoints + ", finalPromoting=" + finalPromoting + ", name=" + name + ", lowerPromoting=" + lowerPromoting + ", minPlayersInGroup=" + minPlayersInGroup + "]";
  }
  
  public String toStringFull() {
    return "Tournament [id=" + id + ", name=" + name + ", season=" + season + ", finalPromoting=" + finalPromoting + ", lowerPromoting=" + lowerPromoting + ", winPoints=" + winPoints + ", playOffA=" + playOffA + ", playOffLower=" + playOffLower + ", minPlayersInGroup=" + minPlayersInGroup + ", groups=" + groups + ", finalStandings=" + finalStandings + "]";
  }
}
