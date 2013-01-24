package org.toursys.repository.model;
  
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class Season implements Serializable {
  
  private static final long serialVersionUID = 1L;
  public static final int ORDER_BY_ID = 1;
	
  public Season() {
  }
  
  public Season(String name) {
    this.name = name;
  }
  
  private Integer id;
    
  public Integer getId() {
    return id;
  }
    
  public void setId(Integer id) {
    this.id = id;
  }
    
  public Season _setId(Integer id) {
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
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Season other = (Season) obj;
    if (!id.equals(other.id))
      return false;
    return true;
  }  
  
  public enum Association {
    tournaments
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
    return "Season [id=" + id + ", name=" + name + "]";
  }
  
  public String toStringFull() {
    return "Season [id=" + id + ", name=" + name + "]";
  }
}