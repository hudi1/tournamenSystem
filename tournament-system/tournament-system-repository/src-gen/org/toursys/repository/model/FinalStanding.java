package org.toursys.repository.model;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class FinalStanding implements Serializable {
  
  private static final long serialVersionUID = 1L;
  public static final int ORDER_BY_ID = 1;
  public static final int ORDER_BY_PLAYER = 3;
  public static final int ORDER_BY_TOURNAMENT = 2;
	
  public FinalStanding() {
  }
  
  public FinalStanding(Integer finalRank, Tournament tournament) {
    this.finalRank = finalRank;
    this.tournament = tournament;
  }
  
  private Integer id;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public FinalStanding _setId(Integer id) {
    this.id = id;
    return this;
  }
  
  private Player player;
  
  public Player getPlayer() {
    return player;
  }
  
  public void setPlayer(Player player) {
    this.player = player;
  }
  
  public FinalStanding _setPlayer(Player player) {
    this.player = player;
    return this;
  }
  
  private Integer finalRank;
  
  public Integer getFinalRank() {
    return finalRank;
  }
  
  public void setFinalRank(Integer finalRank) {
    this.finalRank = finalRank;
  }
  
  public FinalStanding _setFinalRank(Integer finalRank) {
    this.finalRank = finalRank;
    return this;
  }
  
  private Tournament tournament;
  
  public Tournament getTournament() {
    return tournament;
  }
  
  public void setTournament(Tournament tournament) {
    this.tournament = tournament;
  }
  
  public FinalStanding _setTournament(Tournament tournament) {
    this.tournament = tournament;
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
    FinalStanding other = (FinalStanding) obj;
    if (id == null || !id.equals(other.id))
      return false;
    return true;
  }  
  
  public enum Association {
    tournament, player
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
    return "FinalStanding [id=" + id + ", finalRank=" + finalRank + "]";
  }
  
  public String toStringFull() {
    return "FinalStanding [id=" + id + ", tournament=" + tournament + ", player=" + player + ", finalRank=" + finalRank + "]";
  }
}
