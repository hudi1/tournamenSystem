package org.tahom.repository.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sqlproc.engine.annotation.Pojo;
import org.tahom.repository.model.FinalStanding;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Season;
import org.tahom.repository.model.TournamentSortType;
import org.tahom.repository.model.TournamentType;

@Pojo
@SuppressWarnings("all")
public class Tournament implements Serializable {
  private final static long serialVersionUID = 1L;
  
  public final static String ORDER_BY_ID = "ID";
  
  public final static String ORDER_BY_SEASON = "SEASON";
  
  public Tournament() {
  }
  
  public Tournament(final String name, final Season season, final Integer finalPromoting, final Integer lowerPromoting, final Integer winPoints, final Integer playOffFinal, final Integer playOffLower, final Integer minPlayersInGroup, final TournamentSortType sortType, final Boolean open, final TournamentType type) {
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
    setOpen(open);
    setType(type);
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
  
  private Boolean open;
  
  public Boolean getOpen() {
    return this.open;
  }
  
  public void setOpen(final Boolean open) {
    this.open = open;
  }
  
  public Tournament _setOpen(final Boolean open) {
    this.open = open;
    return this;
  }
  
  private TournamentType type;
  
  public TournamentType getType() {
    return this.type;
  }
  
  public void setType(final TournamentType type) {
    this.type = type;
  }
  
  public Tournament _setType(final TournamentType type) {
    this.type = type;
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
  
  private boolean onlyIds_;
  
  public boolean isOnlyIds_() {
    return this.onlyIds_;
  }
  
  public void setOnlyIds_(final boolean onlyIds_) {
    this.onlyIds_ = onlyIds_;
  }
  
  public Tournament _setOnlyIds_(final boolean onlyIds_) {
    this.onlyIds_ = onlyIds_;
    return this;
  }
  
  private List<Integer> ids_ = new java.util.ArrayList<Integer>();
  
  public List<Integer> getIds_() {
    return this.ids_;
  }
  
  public void setIds_(final List<Integer> ids_) {
    this.ids_ = ids_;
  }
  
  public Tournament _setIds_(final List<Integer> ids_) {
    this.ids_ = ids_;
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
    return "Tournament [id=" + id + ", name=" + name + ", finalPromoting=" + finalPromoting + ", lowerPromoting=" + lowerPromoting + ", winPoints=" + winPoints + ", playOffFinal=" + playOffFinal + ", playOffLower=" + playOffLower + ", minPlayersInGroup=" + minPlayersInGroup + ", sortType=" + sortType + ", open=" + open + ", type=" + type + "]";
  }
  
  public String toStringFull() {
    return "Tournament [id=" + id + ", name=" + name + ", season=" + season + ", finalPromoting=" + finalPromoting + ", lowerPromoting=" + lowerPromoting + ", winPoints=" + winPoints + ", playOffFinal=" + playOffFinal + ", playOffLower=" + playOffLower + ", minPlayersInGroup=" + minPlayersInGroup + ", sortType=" + sortType + ", open=" + open + ", type=" + type + ", finalStandings=" + finalStandings + ", groups=" + groups + ", onlyIds_=" + onlyIds_ + ", ids_=" + ids_ + "]";
  }
  
  public enum Association {
    season,
    
    finalStandings,
    
    groups;
  }
  
  private Set<String> initAssociations_ =  new java.util.HashSet<String>();
  
  public Set<String> getInitAssociations_() {
    return this.initAssociations_;
  }
  
  public void setInitAssociations_(final Set<String> initAssociations_) {
    this.initAssociations_ = initAssociations_;
  }
  
  public void setInit_(final Tournament.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations_.add(association.name());
  }
  
  public Tournament _setInit_(final Tournament.Association... associations) {
    setInit_(associations);
    return this;
  }
  
  public void clearInit_(final Tournament.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations_.remove(association.name());
  }
  
  public Tournament _clearInit_(final Tournament.Association... associations) {
    clearInit_(associations);
    return this;
  }
  
  public void setInit_(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations_.add(association);
  }
  
  public Tournament _setInit_(final String... associations) {
    setInit_(associations);
    return this;
  }
  
  public void clearInit_(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations_.remove(association);
  }
  
  public Tournament _clearInit_(final String... associations) {
    clearInit_(associations);
    return this;
  }
  
  public Boolean toInit_(final Tournament.Association association) {
    if (association == null)
    	throw new IllegalArgumentException();
    return initAssociations_.contains(association.name());
  }
  
  public Boolean toInit_(final String association) {
    if (association == null)
    	throw new IllegalArgumentException();
    return initAssociations_.contains(association);
  }
  
  public void clearAllInit_() {
    initAssociations_ = new java.util.HashSet<String>();
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
    
    open,
    
    type,
    
    finalStandings,
    
    groups,
    
    onlyIds_,
    
    ids_;
  }
  
  private Map<String, String> operators_ =  new java.util.HashMap<String, String>();
  
  public Map<String, String> getOperators_() {
    return operators_;
  }
  
  public String getOp_(final String attrName) {
    if (attrName == null)
    	throw new IllegalArgumentException();
    return operators_.get(attrName);
  }
  
  public void setOp_(final String operator, final Tournament.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.put(attribute.name(), operator);
  }
  
  public Tournament _setOp_(final String operator, final Tournament.OpAttribute... attributes) {
    setOp_(operator, attributes);
    return this;
  }
  
  public void clearOp_(final Tournament.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.remove(attribute.name());
  }
  
  public Tournament _clearOp_(final Tournament.OpAttribute... attributes) {
    clearOp_(attributes);
    return this;
  }
  
  public void setOp_(final String operator, final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.put(attribute, operator);
  }
  
  public Tournament _setOp_(final String operator, final String... attributes) {
    setOp_(operator, attributes);
    return this;
  }
  
  public void clearOp_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.remove(attribute);
  }
  
  public Tournament _clearOp_(final String... attributes) {
    clearOp_(attributes);
    return this;
  }
  
  public void setNullOp_(final Tournament.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.put(attribute.name(), "is null");
  }
  
  public Tournament _setNullOp_(final Tournament.OpAttribute... attributes) {
    setNullOp_(attributes);
    return this;
  }
  
  public void setNullOp_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.put(attribute, "is null");
  }
  
  public Tournament _setNullOp_(final String... attributes) {
    setNullOp_(attributes);
    return this;
  }
  
  public void clearAllOps_() {
    operators_ = new java.util.HashMap<String, String>();
  }
}
