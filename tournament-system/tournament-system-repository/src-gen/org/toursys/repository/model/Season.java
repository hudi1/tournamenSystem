package org.toursys.repository.model;
  
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;

public class Season implements Serializable {
  
  private static final long serialVersionUID = 1L;
	
  public Season() {
  }
  
  public Season(String name) {
    this.name = name;
  }
  
  private int id;
    
  public int getId() {
    return id;
  }
    
  public void setId(int id) {
    this.id = id;
  }
    
  public Season _setId(int id) {
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
    
  public Season _setName(String name) {
    this.name = name;
    return this;
  }
  
  private List<Tournament> tournaments = new ArrayList<Tournament>();
    
  public List<Tournament> getTournaments() {
    return tournaments;
  }
    
  public void setTournaments(List<Tournament> tournaments) {
    this.tournaments = tournaments;
  }
    
  public Season _setTournaments(List<Tournament> tournaments) {
    this.tournaments = tournaments;
    return this;
  }
  
  @Override
  public String toString() {
    return "Season [id=" + id + ", name=" + name + "]";
  }
  
  public String toStringFull() {
    return "Season [id=" + id + ", name=" + name + "]";
  }
}
