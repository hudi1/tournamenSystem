package org.toursys.repository.model;
  
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;

public class Tournament implements Serializable {
  
  private static final long serialVersionUID = 1L;
	
  public Tournament() {
  }
  
  public Tournament(String name, Season season, int finalPromoting, int lowerPromoting, int winPoints, int playOffA, int playOffLower) {
    this.name = name;
    this.season = season;
    this.finalPromoting = finalPromoting;
    this.lowerPromoting = lowerPromoting;
    this.winPoints = winPoints;
    this.playOffA = playOffA;
    this.playOffLower = playOffLower;
  }
  
  private int id;
    
  public int getId() {
    return id;
  }
    
  public void setId(int id) {
    this.id = id;
  }
    
  public Tournament _setId(int id) {
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
  
  private int finalPromoting;
    
  public int getFinalPromoting() {
    return finalPromoting;
  }
    
  public void setFinalPromoting(int finalPromoting) {
    this.finalPromoting = finalPromoting;
  }
    
  public Tournament _setFinalPromoting(int finalPromoting) {
    this.finalPromoting = finalPromoting;
    return this;
  }
  
  private int lowerPromoting;
    
  public int getLowerPromoting() {
    return lowerPromoting;
  }
    
  public void setLowerPromoting(int lowerPromoting) {
    this.lowerPromoting = lowerPromoting;
  }
    
  public Tournament _setLowerPromoting(int lowerPromoting) {
    this.lowerPromoting = lowerPromoting;
    return this;
  }
  
  private int winPoints;
    
  public int getWinPoints() {
    return winPoints;
  }
    
  public void setWinPoints(int winPoints) {
    this.winPoints = winPoints;
  }
    
  public Tournament _setWinPoints(int winPoints) {
    this.winPoints = winPoints;
    return this;
  }
  
  private int playOffA;
    
  public int getPlayOffA() {
    return playOffA;
  }
    
  public void setPlayOffA(int playOffA) {
    this.playOffA = playOffA;
  }
    
  public Tournament _setPlayOffA(int playOffA) {
    this.playOffA = playOffA;
    return this;
  }
  
  private int playOffLower;
    
  public int getPlayOffLower() {
    return playOffLower;
  }
    
  public void setPlayOffLower(int playOffLower) {
    this.playOffLower = playOffLower;
  }
    
  public Tournament _setPlayOffLower(int playOffLower) {
    this.playOffLower = playOffLower;
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
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Tournament other = (Tournament) obj;
    if (id != other.id)
      return false;
    return true;
  }  
  
  @Override
  public String toString() {
    return "Tournament [id=" + id + ", playOffLower=" + playOffLower + ", playOffA=" + playOffA + ", winPoints=" + winPoints + ", finalPromoting=" + finalPromoting + ", name=" + name + ", lowerPromoting=" + lowerPromoting + "]";
  }
  
  public String toStringFull() {
    return "Tournament [id=" + id + ", playOffLower=" + playOffLower + ", season=" + season + ", playOffA=" + playOffA + ", winPoints=" + winPoints + ", finalPromoting=" + finalPromoting + ", name=" + name + ", lowerPromoting=" + lowerPromoting + "]";
  }
}
