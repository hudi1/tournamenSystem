package org.toursys.repository.model;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class Player implements Serializable {
  
  private static final long serialVersionUID = 1L;
  public static final int ORDER_BY_ID = 1;
  public static final int ORDER_BY_USER = 3;
	
  public Player() {
  }
  
  public Player(String name, String surname, User user) {
    this.name = name;
    this.surname = surname;
    this.user = user;
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
  
  private String playerDiscriminator;
  
  public String getPlayerDiscriminator() {
    return playerDiscriminator;
  }
  
  public void setPlayerDiscriminator(String playerDiscriminator) {
    this.playerDiscriminator = playerDiscriminator;
  }
  
  public Player _setPlayerDiscriminator(String playerDiscriminator) {
    this.playerDiscriminator = playerDiscriminator;
    return this;
  }
  
  private Integer worldRanking;
  
  public Integer getWorldRanking() {
    return worldRanking;
  }
  
  public void setWorldRanking(Integer worldRanking) {
    this.worldRanking = worldRanking;
  }
  
  public Player _setWorldRanking(Integer worldRanking) {
    this.worldRanking = worldRanking;
    return this;
  }
  
  private User user;
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  public Player _setUser(User user) {
    this.user = user;
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
    if (id == null || !id.equals(other.id))
      return false;
    return true;
  }  
  
  public enum Association {
    user
  }
  
  private Set<String> initAssociations = new HashSet<String>();
  
  public void setInit(Association... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (Association association : associations)
      initAssociations.add(association.name());
  }
  
  public Player  _setInit(Association... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(Association... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (Association association : associations)
      initAssociations.remove(association.name());
  }
  
  public Player _clearInit(Association... associations) {
    clearInit(associations);
    return this;
  }
  
  public void setInit(String... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (String association : associations)
      initAssociations.add(association);
  }
  
  public Player _setInit(String... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(String... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (String association : associations)
      initAssociations.remove(association);
  }
  
  public Player _clearInit(String... associations) {
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
    return "Player [playerDiscriminator=" + playerDiscriminator + ", id=" + id + ", club=" + club + ", name=" + name + ", surname=" + surname + ", worldRanking=" + worldRanking + "]";
  }
  
  public String toStringFull() {
    return "Player [id=" + id + ", name=" + name + ", surname=" + surname + ", club=" + club + ", playerDiscriminator=" + playerDiscriminator + ", worldRanking=" + worldRanking + ", user=" + user + "]";
  }
}
