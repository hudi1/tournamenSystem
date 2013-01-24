package org.toursys.repository.model;
  
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class Player implements Serializable {
  
  private static final long serialVersionUID = 1L;
  public static final int ORDER_BY_ID = 1;
	
  public Player() {
  }
  
  public Player(String name, String surname) {
    this.name = name;
    this.surname = surname;
  }
  
  private Integer id;
    
  public Integer getId() {
    return id;
  }
    
  public void setId(Integer id) {
    this.id = id;
  }
    
  public Player _setId(Integer id) {
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
    
  public Player _setName(String name) {
    this.name = name;
    return this;
  }
  
  private String surname;
    
  public String getSurname() {
    return surname;
  }
    
  public void setSurname(String surname) {
    this.surname = surname;
  }
    
  public Player _setSurname(String surname) {
    this.surname = surname;
    return this;
  }
  
  private String club;
    
  public String getClub() {
    return club;
  }
    
  public void setClub(String club) {
    this.club = club;
  }
    
  public Player _setClub(String club) {
    this.club = club;
    return this;
  }
  
  private List<PlayOffGame> playOffGames = new ArrayList<PlayOffGame>();
    
  public List<PlayOffGame> getPlayOffGames() {
    return playOffGames;
  }
    
  public void setPlayOffGames(List<PlayOffGame> playOffGames) {
    this.playOffGames = playOffGames;
  }
    
  public Player _setPlayOffGames(List<PlayOffGame> playOffGames) {
    this.playOffGames = playOffGames;
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
    Player other = (Player) obj;
    if (!id.equals(other.id))
      return false;
    return true;
  }  
  
  public enum Association {
    playOffGames
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
    return "Player [id=" + id + ", club=" + club + ", name=" + name + ", surname=" + surname + "]";
  }
  
  public String toStringFull() {
    return "Player [id=" + id + ", club=" + club + ", name=" + name + ", surname=" + surname + "]";
  }
}