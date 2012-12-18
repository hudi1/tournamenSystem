package org.toursys.repository.model;

import java.io.Serializable;

public class Player implements Serializable {
  
  private static final long serialVersionUID = 1L;
	
  public Player() {
  }
  
  public Player(String name, String surname) {
    this.name = name;
    this.surname = surname;
  }
  
  private int id;
    
  public int getId() {
    return id;
  }
    
  public void setId(int id) {
    this.id = id;
  }
    
  public Player _setId(int id) {
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
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Player other = (Player) obj;
    if (id != other.id)
      return false;
    return true;
  }  
  
  @Override
  public String toString() {
    return "Player [id=" + id + ", club=" + club + ", name=" + name + ", surname=" + surname + "]";
  }
  
  public String toStringFull() {
    return "Player [id=" + id + ", club=" + club + ", name=" + name + ", surname=" + surname + "]";
  }
}
