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
  
  private boolean onlyIds_;
  
  public boolean isOnlyIds_() {
    return this.onlyIds_;
  }
  
  public void setOnlyIds_(final boolean onlyIds_) {
    this.onlyIds_ = onlyIds_;
  }
  
  public Groups _setOnlyIds_(final boolean onlyIds_) {
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
  
  public Groups _setIds_(final List<Integer> ids_) {
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
    return "Groups [id=" + id + ", name=" + name + ", numberOfHockey=" + numberOfHockey + ", type=" + type + ", indexOfFirstHockey=" + indexOfFirstHockey + ", tournament=" + tournament + ", copyResult=" + copyResult + ", playThirdPlace=" + playThirdPlace + ", playOff=" + playOff + ", playOffType=" + playOffType + ", participants=" + participants + ", playOffGames=" + playOffGames + ", onlyIds_=" + onlyIds_ + ", ids_=" + ids_ + "]";
  }
  
  public enum Association {
    tournament,
    
    participants,
    
    playOffGames;
  }
  
  private Set<String> initAssociations_ =  new java.util.HashSet<String>();
  
  public Set<String> getInitAssociations_() {
    return this.initAssociations_;
  }
  
  public void setInitAssociations_(final Set<String> initAssociations_) {
    this.initAssociations_ = initAssociations_;
  }
  
  public void setInit_(final Groups.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations_.add(association.name());
  }
  
  public Groups _setInit_(final Groups.Association... associations) {
    setInit_(associations);
    return this;
  }
  
  public void clearInit_(final Groups.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations_.remove(association.name());
  }
  
  public Groups _clearInit_(final Groups.Association... associations) {
    clearInit_(associations);
    return this;
  }
  
  public void setInit_(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations_.add(association);
  }
  
  public Groups _setInit_(final String... associations) {
    setInit_(associations);
    return this;
  }
  
  public void clearInit_(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations_.remove(association);
  }
  
  public Groups _clearInit_(final String... associations) {
    clearInit_(associations);
    return this;
  }
  
  public Boolean toInit_(final Groups.Association association) {
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
  
  public void setOp_(final String operator, final Groups.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.put(attribute.name(), operator);
  }
  
  public Groups _setOp_(final String operator, final Groups.OpAttribute... attributes) {
    setOp_(operator, attributes);
    return this;
  }
  
  public void clearOp_(final Groups.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.remove(attribute.name());
  }
  
  public Groups _clearOp_(final Groups.OpAttribute... attributes) {
    clearOp_(attributes);
    return this;
  }
  
  public void setOp_(final String operator, final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.put(attribute, operator);
  }
  
  public Groups _setOp_(final String operator, final String... attributes) {
    setOp_(operator, attributes);
    return this;
  }
  
  public void clearOp_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.remove(attribute);
  }
  
  public Groups _clearOp_(final String... attributes) {
    clearOp_(attributes);
    return this;
  }
  
  public void setNullOp_(final Groups.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.put(attribute.name(), "is null");
  }
  
  public Groups _setNullOp_(final Groups.OpAttribute... attributes) {
    setNullOp_(attributes);
    return this;
  }
  
  public void setNullOp_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.put(attribute, "is null");
  }
  
  public Groups _setNullOp_(final String... attributes) {
    setNullOp_(attributes);
    return this;
  }
  
  public void clearAllOps_() {
    operators_ = new java.util.HashMap<String, String>();
  }
}
