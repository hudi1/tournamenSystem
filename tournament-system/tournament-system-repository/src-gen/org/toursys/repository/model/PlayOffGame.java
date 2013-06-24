package org.toursys.repository.model;
  
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class PlayOffGame implements Serializable {
  
  private static final long serialVersionUID = 1L;
  public static final int ORDER_BY_ID = 1;
  public static final int ORDER_BY_HOME_PLAYER = 2;
  public static final int ORDER_BY_AWAY_PLAYER = 3;
  public static final int ORDER_BY_GROUP = 5;
  public static final int ORDER_BY_POSITION = 4;
	
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
  
  private Player homePlayer;
  
  public Player getHomePlayer() {
    return homePlayer;
  }
  
  public void setHomePlayer(Player homePlayer) {
    this.homePlayer = homePlayer;
  }
  
  public PlayOffGame _setHomePlayer(Player homePlayer) {
    this.homePlayer = homePlayer;
    return this;
  }
  
  private Player awayPlayer;
  
  public Player getAwayPlayer() {
    return awayPlayer;
  }
  
  public void setAwayPlayer(Player awayPlayer) {
    this.awayPlayer = awayPlayer;
  }
  
  public PlayOffGame _setAwayPlayer(Player awayPlayer) {
    this.awayPlayer = awayPlayer;
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
  
  private List<PlayOffResult> playOffResults = new ArrayList<PlayOffResult>();
  
  public List<PlayOffResult> getPlayOffResults() {
    return playOffResults;
  }
  
  public void setPlayOffResults(List<PlayOffResult> playOffResults) {
    this.playOffResults = playOffResults;
  }
  
  public PlayOffGame _setPlayOffResults(List<PlayOffResult> playOffResults) {
    this.playOffResults = playOffResults;
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
    awayPlayer, playOffResults, group, homePlayer
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
    return "PlayOffGame [position=" + position + ", id=" + id + "]";
  }
  
  public String toStringFull() {
    return "PlayOffGame [position=" + position + ", id=" + id + ", awayPlayer=" + awayPlayer + ", group=" + group + ", homePlayer=" + homePlayer + "]";
  }
}
