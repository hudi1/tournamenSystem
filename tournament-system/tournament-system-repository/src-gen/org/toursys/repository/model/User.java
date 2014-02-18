package org.toursys.repository.model;
  
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class User implements Serializable {
  
  private static final long serialVersionUID = 1L;
  public static final int ORDER_BY_ID = 1;
  public static final int ORDER_BY_EMAIL = 3;
  public static final int ORDER_BY_USER_NAME = 2;
	
  public User() {
  }
  
  public User(String email, String userName, String password, Integer platnost) {
    this.email = email;
    this.userName = userName;
    this.password = password;
    this.platnost = platnost;
  }
  
  private Integer id;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public User _setId(Integer id) {
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
  
  public User _setName(String name) {
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
  
  public User _setSurname(String surname) {
    this.surname = surname;
    return this;
  }
  
  private String email;
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public User _setEmail(String email) {
    this.email = email;
    return this;
  }
  
  private String userName;
  
  public String getUserName() {
    return userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public User _setUserName(String userName) {
    this.userName = userName;
    return this;
  }
  
  private String password;
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public User _setPassword(String password) {
    this.password = password;
    return this;
  }
  
  private UserRole role;
  
  public UserRole getRole() {
    return role;
  }
  
  public void setRole(UserRole role) {
    this.role = role;
  }
  
  public User _setRole(UserRole role) {
    this.role = role;
    return this;
  }
  
  private Integer platnost;
  
  public Integer getPlatnost() {
    return platnost;
  }
  
  public void setPlatnost(Integer platnost) {
    this.platnost = platnost;
  }
  
  public User _setPlatnost(Integer platnost) {
    this.platnost = platnost;
    return this;
  }
  
  private List<Player> players = new ArrayList<Player>();
  
  public List<Player> getPlayers() {
    return players;
  }
  
  public void setPlayers(List<Player> players) {
    this.players = players;
  }
  
  public User _setPlayers(List<Player> players) {
    this.players = players;
    return this;
  }
  
  private List<Season> seasons = new ArrayList<Season>();
  
  public List<Season> getSeasons() {
    return seasons;
  }
  
  public void setSeasons(List<Season> seasons) {
    this.seasons = seasons;
  }
  
  public User _setSeasons(List<Season> seasons) {
    this.seasons = seasons;
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
    User other = (User) obj;
    if (id == null || !id.equals(other.id))
      return false;
    return true;
  }  
  
  public enum Association {
    seasons, players
  }
  
  private Set<String> initAssociations = new HashSet<String>();
  
  public void setInit(Association... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (Association association : associations)
      initAssociations.add(association.name());
  }
  
  public User  _setInit(Association... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(Association... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (Association association : associations)
      initAssociations.remove(association.name());
  }
  
  public User _clearInit(Association... associations) {
    clearInit(associations);
    return this;
  }
  
  public void setInit(String... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (String association : associations)
      initAssociations.add(association);
  }
  
  public User _setInit(String... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(String... associations) {
    if (associations == null)
      throw new IllegalArgumentException();
    for (String association : associations)
      initAssociations.remove(association);
  }
  
  public User _clearInit(String... associations) {
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
    return "User [id=" + id + ", email=" + email + ", name=" + name + ", platnost=" + platnost + ", role=" + role + ", userName=" + userName + ", surname=" + surname + ", password=" + password + "]";
  }
  
  public String toStringFull() {
    return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", userName=" + userName + ", password=" + password + ", role=" + role + ", platnost=" + platnost + ", players=" + players + ", seasons=" + seasons + "]";
  }
}
