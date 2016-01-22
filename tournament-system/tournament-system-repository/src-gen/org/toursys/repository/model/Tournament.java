package org.toursys.repository.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.TournamentSortType;

@Pojo
@SuppressWarnings("all")
public class Tournament implements Serializable {
  private final static long serialVersionUID = 1L;
  
  public final static String ORDER_BY_ID = "ID";
  
  public final static String ORDER_BY_SEASON = "SEASON";
  
  public Tournament() {
  }
  
  public Tournament(final String name, final Season season, final Integer finalPromoting, final Integer lowerPromoting, final Integer winPoints, final Integer playOffFinal, final Integer playOffLower, final Integer minPlayersInGroup, final TournamentSortType sortType, final Boolean publish) {
    super();
    setName(name);
    setSeason(season);
    setFinalPromoting(finalPromoting);
    setLowerPromoting(lowerPromoting);
    setWinPoints(winPoints);
    setPlayOffFinal(playOffFinal);
    setPlayOffLower(playOffLower);
    setMinPlayersInGroup(minPlayersInGroup);
    setSortType(sortType);
    setPublish(publish);
  }
  
  private Integer id;
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(final Integer id) {
    this.id = id;
  }
  
  public Tournament _setId(final Integer id) {
    this.id = id;
    return this;
  }
  
  private String name;
  
  public String getName() {
    return this.name;
  }
  
  public void setName(final String name) {
    this.name = name;
  }
  
  public Tournament _setName(final String name) {
    this.name = name;
    return this;
  }
  
  private Season season;
  
  public Season getSeason() {
    return this.season;
  }
  
  public void setSeason(final Season season) {
    this.season = season;
  }
  
  public Tournament _setSeason(final Season season) {
    this.season = season;
    return this;
  }
  
  private Integer finalPromoting;
  
  public Integer getFinalPromoting() {
    return this.finalPromoting;
  }
  
  public void setFinalPromoting(final Integer finalPromoting) {
    this.finalPromoting = finalPromoting;
  }
  
  public Tournament _setFinalPromoting(final Integer finalPromoting) {
    this.finalPromoting = finalPromoting;
    return this;
  }
  
  private Integer lowerPromoting;
  
  public Integer getLowerPromoting() {
    return this.lowerPromoting;
  }
  
  public void setLowerPromoting(final Integer lowerPromoting) {
    this.lowerPromoting = lowerPromoting;
  }
  
  public Tournament _setLowerPromoting(final Integer lowerPromoting) {
    this.lowerPromoting = lowerPromoting;
    return this;
  }
  
  private Integer winPoints;
  
  public Integer getWinPoints() {
    return this.winPoints;
  }
  
  public void setWinPoints(final Integer winPoints) {
    this.winPoints = winPoints;
  }
  
  public Tournament _setWinPoints(final Integer winPoints) {
    this.winPoints = winPoints;
    return this;
  }
  
  private Integer playOffFinal;
  
  public Integer getPlayOffFinal() {
    return this.playOffFinal;
  }
  
  public void setPlayOffFinal(final Integer playOffFinal) {
    this.playOffFinal = playOffFinal;
  }
  
  public Tournament _setPlayOffFinal(final Integer playOffFinal) {
    this.playOffFinal = playOffFinal;
    return this;
  }
  
  private Integer playOffLower;
  
  public Integer getPlayOffLower() {
    return this.playOffLower;
  }
  
  public void setPlayOffLower(final Integer playOffLower) {
    this.playOffLower = playOffLower;
  }
  
  public Tournament _setPlayOffLower(final Integer playOffLower) {
    this.playOffLower = playOffLower;
    return this;
  }
  
  private Integer minPlayersInGroup;
  
  public Integer getMinPlayersInGroup() {
    return this.minPlayersInGroup;
  }
  
  public void setMinPlayersInGroup(final Integer minPlayersInGroup) {
    this.minPlayersInGroup = minPlayersInGroup;
  }
  
  public Tournament _setMinPlayersInGroup(final Integer minPlayersInGroup) {
    this.minPlayersInGroup = minPlayersInGroup;
    return this;
  }
  
  private TournamentSortType sortType;
  
  public TournamentSortType getSortType() {
    return this.sortType;
  }
  
  public void setSortType(final TournamentSortType sortType) {
    this.sortType = sortType;
  }
  
  public Tournament _setSortType(final TournamentSortType sortType) {
    this.sortType = sortType;
    return this;
  }
  
  private Boolean publish;
  
  public Boolean getPublish() {
    return this.publish;
  }
  
  public void setPublish(final Boolean publish) {
    this.publish = publish;
  }
  
  public Tournament _setPublish(final Boolean publish) {
    this.publish = publish;
    return this;
  }
  
  private List<FinalStanding> finalStandings = new java.util.ArrayList<FinalStanding>();
  
  public List<FinalStanding> getFinalStandings() {
    return this.finalStandings;
  }
  
  public void setFinalStandings(final List<FinalStanding> finalStandings) {
    this.finalStandings = finalStandings;
  }
  
  public Tournament _setFinalStandings(final List<FinalStanding> finalStandings) {
    this.finalStandings = finalStandings;
    return this;
  }
  
  private List<Groups> groups = new java.util.ArrayList<Groups>();
  
  public List<Groups> getGroups() {
    return this.groups;
  }
  
  public void setGroups(final List<Groups> groups) {
    this.groups = groups;
  }
  
  public Tournament _setGroups(final List<Groups> groups) {
    this.groups = groups;
    return this;
  }
  
  private boolean onlyIds;
  
  public boolean isOnlyIds() {
    return this.onlyIds;
  }
  
  public void setOnlyIds(final boolean onlyIds) {
    this.onlyIds = onlyIds;
  }
  
  public Tournament _setOnlyIds(final boolean onlyIds) {
    this.onlyIds = onlyIds;
    return this;
  }
  
  private List<Integer> ids = new java.util.ArrayList<Integer>();
  
  public List<Integer> getIds() {
    return this.ids;
  }
  
  public void setIds(final List<Integer> ids) {
    this.ids = ids;
  }
  
  public Tournament _setIds(final List<Integer> ids) {
    this.ids = ids;
    return this;
  }
  
  @Override
  public boolean equals(final Object obj) {
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
  
  @Override
  public String toString() {
    return "Tournament [id=" + id + ", name=" + name + ", finalPromoting=" + finalPromoting + ", lowerPromoting=" + lowerPromoting + ", winPoints=" + winPoints + ", playOffFinal=" + playOffFinal + ", playOffLower=" + playOffLower + ", minPlayersInGroup=" + minPlayersInGroup + ", sortType=" + sortType + ", publish=" + publish + "]";
  }
  
  public String toStringFull() {
    return "Tournament [id=" + id + ", name=" + name + ", season=" + season + ", finalPromoting=" + finalPromoting + ", lowerPromoting=" + lowerPromoting + ", winPoints=" + winPoints + ", playOffFinal=" + playOffFinal + ", playOffLower=" + playOffLower + ", minPlayersInGroup=" + minPlayersInGroup + ", sortType=" + sortType + ", publish=" + publish + ", finalStandings=" + finalStandings + ", groups=" + groups + ", onlyIds=" + onlyIds + ", ids=" + ids + "]";
  }
  
  public enum Association {
    season,
    
    finalStandings,
    
    groups;
  }
  
  private Set<String> initAssociations =  new java.util.HashSet<String>();
  
  public Set<String> getInitAssociations() {
    return this.initAssociations;
  }
  
  public void setInitAssociations(final Set<String> initAssociations) {
    this.initAssociations = initAssociations;
  }
  
  public void setInit(final Tournament.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.add(association.name());
  }
  
  public Tournament _setInit(final Tournament.Association... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final Tournament.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.remove(association.name());
  }
  
  public Tournament _clearInit(final Tournament.Association... associations) {
    clearInit(associations);
    return this;
  }
  
  public void setInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.add(association);
  }
  
  public Tournament _setInit(final String... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.remove(association);
  }
  
  public Tournament _clearInit(final String... associations) {
    clearInit(associations);
    return this;
  }
  
  public Boolean toInit(final Tournament.Association association) {
    if (association == null)
    	throw new IllegalArgumentException();
    return initAssociations.contains(association.name());
  }
  
  public Boolean toInit(final String association) {
    if (association == null)
    	throw new IllegalArgumentException();
    return initAssociations.contains(association);
  }
  
  public void clearAllInit() {
    initAssociations = new java.util.HashSet<String>();
  }
  
  public enum OpAttribute {
    id,
    
    name,
    
    season,
    
    finalPromoting,
    
    lowerPromoting,
    
    winPoints,
    
    playOffFinal,
    
    playOffLower,
    
    minPlayersInGroup,
    
    sortType,
    
    publish,
    
    finalStandings,
    
    groups,
    
    onlyIds,
    
    ids;
  }
  
  private Map<String, String> operators =  new java.util.HashMap<String, String>();
  
  public Map<String, String> getOperators() {
    return operators;
  }
  
  public void setOp(final String operator, final Tournament.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), operator);
  }
  
  public Tournament _setOp(final String operator, final Tournament.OpAttribute... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final Tournament.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.remove(attribute.name());
  }
  
  public Tournament _clearOp(final Tournament.OpAttribute... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setOp(final String operator, final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, operator);
  }
  
  public Tournament _setOp(final String operator, final String... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.remove(attribute);
  }
  
  public Tournament _clearOp(final String... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setNullOp(final Tournament.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), "is null");
  }
  
  public Tournament _setNullOp(final Tournament.OpAttribute... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void setNullOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, "is null");
  }
  
  public Tournament _setNullOp(final String... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void clearAllOps() {
    operators = new java.util.HashMap<String, String>();
  }
}
