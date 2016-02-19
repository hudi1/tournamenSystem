package org.tahom.repository.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sqlproc.engine.annotation.Pojo;
import org.tahom.repository.model.GroupsPlayOffType;
import org.tahom.repository.model.GroupsType;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.PlayOffGame;
import org.tahom.repository.model.Tournament;

@Pojo
@SuppressWarnings("all")
public class Groups implements Serializable {
  private final static long serialVersionUID = 1L;
  
  public final static String ORDER_BY_ID = "ID";
  
  public final static String ORDER_BY_NAME = "NAME";
  
  public final static String ORDER_BY_TOURNAMENT = "TOURNAMENT";
  
  public Groups() {
  }
  
  public Groups(final String name, final Integer numberOfHockey, final GroupsType type, final Integer indexOfFirstHockey, final Tournament tournament, final Boolean copyResult, final Boolean playThirdPlace, final Boolean playOff, final GroupsPlayOffType playOffType) {
    super();
    setName(name);
    setNumberOfHockey(numberOfHockey);
    setType(type);
    setIndexOfFirstHockey(indexOfFirstHockey);
    setTournament(tournament);
    setCopyResult(copyResult);
    setPlayThirdPlace(playThirdPlace);
    setPlayOff(playOff);
    setPlayOffType(playOffType);
  }
  
  private Integer id;
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(final Integer id) {
    this.id = id;
  }
  
  public Groups _setId(final Integer id) {
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
  
  public Groups _setName(final String name) {
    this.name = name;
    return this;
  }
  
  private Integer numberOfHockey;
  
  public Integer getNumberOfHockey() {
    return this.numberOfHockey;
  }
  
  public void setNumberOfHockey(final Integer numberOfHockey) {
    this.numberOfHockey = numberOfHockey;
  }
  
  public Groups _setNumberOfHockey(final Integer numberOfHockey) {
    this.numberOfHockey = numberOfHockey;
    return this;
  }
  
  private GroupsType type;
  
  public GroupsType getType() {
    return this.type;
  }
  
  public void setType(final GroupsType type) {
    this.type = type;
  }
  
  public Groups _setType(final GroupsType type) {
    this.type = type;
    return this;
  }
  
  private Integer indexOfFirstHockey;
  
  public Integer getIndexOfFirstHockey() {
    return this.indexOfFirstHockey;
  }
  
  public void setIndexOfFirstHockey(final Integer indexOfFirstHockey) {
    this.indexOfFirstHockey = indexOfFirstHockey;
  }
  
  public Groups _setIndexOfFirstHockey(final Integer indexOfFirstHockey) {
    this.indexOfFirstHockey = indexOfFirstHockey;
    return this;
  }
  
  private Tournament tournament;
  
  public Tournament getTournament() {
    return this.tournament;
  }
  
  public void setTournament(final Tournament tournament) {
    this.tournament = tournament;
  }
  
  public Groups _setTournament(final Tournament tournament) {
    this.tournament = tournament;
    return this;
  }
  
  private Boolean copyResult;
  
  public Boolean getCopyResult() {
    return this.copyResult;
  }
  
  public void setCopyResult(final Boolean copyResult) {
    this.copyResult = copyResult;
  }
  
  public Groups _setCopyResult(final Boolean copyResult) {
    this.copyResult = copyResult;
    return this;
  }
  
  private Boolean playThirdPlace;
  
  public Boolean getPlayThirdPlace() {
    return this.playThirdPlace;
  }
  
  public void setPlayThirdPlace(final Boolean playThirdPlace) {
    this.playThirdPlace = playThirdPlace;
  }
  
  public Groups _setPlayThirdPlace(final Boolean playThirdPlace) {
    this.playThirdPlace = playThirdPlace;
    return this;
  }
  
  private Boolean playOff;
  
  public Boolean getPlayOff() {
    return this.playOff;
  }
  
  public void setPlayOff(final Boolean playOff) {
    this.playOff = playOff;
  }
  
  public Groups _setPlayOff(final Boolean playOff) {
    this.playOff = playOff;
    return this;
  }
  
  private GroupsPlayOffType playOffType;
  
  public GroupsPlayOffType getPlayOffType() {
    return this.playOffType;
  }
  
  public void setPlayOffType(final GroupsPlayOffType playOffType) {
    this.playOffType = playOffType;
  }
  
  public Groups _setPlayOffType(final GroupsPlayOffType playOffType) {
    this.playOffType = playOffType;
    return this;
  }
  
  private List<Participant> participants = new java.util.ArrayList<Participant>();
  
  public List<Participant> getParticipants() {
    return this.participants;
  }
  
  public void setParticipants(final List<Participant> participants) {
    this.participants = participants;
  }
  
  public Groups _setParticipants(final List<Participant> participants) {
    this.participants = participants;
    return this;
  }
  
  private List<PlayOffGame> playOffGames = new java.util.ArrayList<PlayOffGame>();
  
  public List<PlayOffGame> getPlayOffGames() {
    return this.playOffGames;
  }
  
  public void setPlayOffGames(final List<PlayOffGame> playOffGames) {
    this.playOffGames = playOffGames;
  }
  
  public Groups _setPlayOffGames(final List<PlayOffGame> playOffGames) {
    this.playOffGames = playOffGames;
    return this;
  }
  
  private boolean onlyIds;
  
  public boolean isOnlyIds() {
    return this.onlyIds;
  }
  
  public void setOnlyIds(final boolean onlyIds) {
    this.onlyIds = onlyIds;
  }
  
  public Groups _setOnlyIds(final boolean onlyIds) {
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
  
  public Groups _setIds(final List<Integer> ids) {
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
    Groups other = (Groups) obj;
    if (id == null || !id.equals(other.id))
    	return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "Groups [id=" + id + ", name=" + name + ", numberOfHockey=" + numberOfHockey + ", type=" + type + ", indexOfFirstHockey=" + indexOfFirstHockey + ", copyResult=" + copyResult + ", playThirdPlace=" + playThirdPlace + ", playOff=" + playOff + ", playOffType=" + playOffType + "]";
  }
  
  public String toStringFull() {
    return "Groups [id=" + id + ", name=" + name + ", numberOfHockey=" + numberOfHockey + ", type=" + type + ", indexOfFirstHockey=" + indexOfFirstHockey + ", tournament=" + tournament + ", copyResult=" + copyResult + ", playThirdPlace=" + playThirdPlace + ", playOff=" + playOff + ", playOffType=" + playOffType + ", participants=" + participants + ", playOffGames=" + playOffGames + ", onlyIds=" + onlyIds + ", ids=" + ids + "]";
  }
  
  public enum Association {
    tournament,
    
    participants,
    
    playOffGames;
  }
  
  private Set<String> initAssociations =  new java.util.HashSet<String>();
  
  public Set<String> getInitAssociations() {
    return this.initAssociations;
  }
  
  public void setInitAssociations(final Set<String> initAssociations) {
    this.initAssociations = initAssociations;
  }
  
  public void setInit(final Groups.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.add(association.name());
  }
  
  public Groups _setInit(final Groups.Association... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final Groups.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.remove(association.name());
  }
  
  public Groups _clearInit(final Groups.Association... associations) {
    clearInit(associations);
    return this;
  }
  
  public void setInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.add(association);
  }
  
  public Groups _setInit(final String... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.remove(association);
  }
  
  public Groups _clearInit(final String... associations) {
    clearInit(associations);
    return this;
  }
  
  public Boolean toInit(final Groups.Association association) {
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
    
    numberOfHockey,
    
    type,
    
    indexOfFirstHockey,
    
    tournament,
    
    copyResult,
    
    playThirdPlace,
    
    playOff,
    
    playOffType,
    
    participants,
    
    playOffGames,
    
    onlyIds,
    
    ids;
  }
  
  private Map<String, String> operators =  new java.util.HashMap<String, String>();
  
  public Map<String, String> getOperators() {
    return operators;
  }
  
  public void setOp(final String operator, final Groups.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), operator);
  }
  
  public Groups _setOp(final String operator, final Groups.OpAttribute... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final Groups.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.remove(attribute.name());
  }
  
  public Groups _clearOp(final Groups.OpAttribute... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setOp(final String operator, final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, operator);
  }
  
  public Groups _setOp(final String operator, final String... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.remove(attribute);
  }
  
  public Groups _clearOp(final String... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setNullOp(final Groups.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), "is null");
  }
  
  public Groups _setNullOp(final Groups.OpAttribute... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void setNullOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, "is null");
  }
  
  public Groups _setNullOp(final String... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void clearAllOps() {
    operators = new java.util.HashMap<String, String>();
  }
}
