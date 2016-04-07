package org.tahom.repository.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sqlproc.engine.annotation.Pojo;
import org.tahom.repository.model.GameStatus;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.impl.Results;

@Pojo
@SuppressWarnings("all")
public class PlayOffGame implements Serializable {
  private final static long serialVersionUID = 1L;
  
  public final static String ORDER_BY_ID = "ID";
  
  public final static String ORDER_BY_HOME_PARTICIPANT = "HOME_PARTICIPANT";
  
  public final static String ORDER_BY_AWAY_PARTICIPANT = "AWAY_PARTICIPANT";
  
  public final static String ORDER_BY_GROUP = "GROUP";
  
  public final static String ORDER_BY_POSITION = "POSITION";
  
  public PlayOffGame() {
  }
  
  public PlayOffGame(final Groups group, final Integer position) {
    super();
    setGroup(group);
    setPosition(position);
  }
  
  private Integer id;
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(final Integer id) {
    this.id = id;
  }
  
  public PlayOffGame _setId(final Integer id) {
    this.id = id;
    return this;
  }
  
  private Participant homeParticipant;
  
  public Participant getHomeParticipant() {
    return this.homeParticipant;
  }
  
  public void setHomeParticipant(final Participant homeParticipant) {
    this.homeParticipant = homeParticipant;
  }
  
  public PlayOffGame _setHomeParticipant(final Participant homeParticipant) {
    this.homeParticipant = homeParticipant;
    return this;
  }
  
  private Participant awayParticipant;
  
  public Participant getAwayParticipant() {
    return this.awayParticipant;
  }
  
  public void setAwayParticipant(final Participant awayParticipant) {
    this.awayParticipant = awayParticipant;
  }
  
  public PlayOffGame _setAwayParticipant(final Participant awayParticipant) {
    this.awayParticipant = awayParticipant;
    return this;
  }
  
  private GameStatus status;
  
  public GameStatus getStatus() {
    return this.status;
  }
  
  public void setStatus(final GameStatus status) {
    this.status = status;
  }
  
  public PlayOffGame _setStatus(final GameStatus status) {
    this.status = status;
    return this;
  }
  
  private Results result;
  
  public Results getResult() {
    return this.result;
  }
  
  public void setResult(final Results result) {
    this.result = result;
  }
  
  public PlayOffGame _setResult(final Results result) {
    this.result = result;
    return this;
  }
  
  private Groups group;
  
  public Groups getGroup() {
    return this.group;
  }
  
  public void setGroup(final Groups group) {
    this.group = group;
  }
  
  public PlayOffGame _setGroup(final Groups group) {
    this.group = group;
    return this;
  }
  
  private Integer position;
  
  public Integer getPosition() {
    return this.position;
  }
  
  public void setPosition(final Integer position) {
    this.position = position;
  }
  
  public PlayOffGame _setPosition(final Integer position) {
    this.position = position;
    return this;
  }
  
  private boolean onlyIds;
  
  public boolean isOnlyIds() {
    return this.onlyIds;
  }
  
  public void setOnlyIds(final boolean onlyIds) {
    this.onlyIds = onlyIds;
  }
  
  public PlayOffGame _setOnlyIds(final boolean onlyIds) {
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
  
  public PlayOffGame _setIds(final List<Integer> ids) {
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
    PlayOffGame other = (PlayOffGame) obj;
    if (id == null || !id.equals(other.id))
    	return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "PlayOffGame [id=" + id + ", status=" + status + ", result=" + result + ", position=" + position + "]";
  }
  
  public String toStringFull() {
    return "PlayOffGame [id=" + id + ", homeParticipant=" + homeParticipant + ", awayParticipant=" + awayParticipant + ", status=" + status + ", result=" + result + ", group=" + group + ", position=" + position + ", onlyIds=" + onlyIds + ", ids=" + ids + "]";
  }
  
  public enum Attribute {
    homeParticipant,
    
    awayParticipant,
    
    status,
    
    result;
  }
  
  private Set<String> nullValues =  new java.util.HashSet<String>();
  
  public void setNull(final PlayOffGame.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues.add(attribute.name());
  }
  
  public PlayOffGame _setNull(final PlayOffGame.Attribute... attributes) {
    setNull(attributes);
    return this;
  }
  
  public void clearNull(final PlayOffGame.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues.remove(attribute.name());
  }
  
  public PlayOffGame _clearNull(final PlayOffGame.Attribute... attributes) {
    clearNull(attributes);
    return this;
  }
  
  public void setNull(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues.add(attribute);
  }
  
  public PlayOffGame _setNull(final String... attributes) {
    setNull(attributes);
    return this;
  }
  
  public void clearNull(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues.remove(attribute);
  }
  
  public PlayOffGame _clearNull(final String... attributes) {
    clearNull(attributes);
    return this;
  }
  
  public Boolean isNull(final PlayOffGame.Attribute attribute) {
    if (attribute == null)
    	throw new IllegalArgumentException();
    return nullValues.contains(attribute.name());
  }
  
  public Boolean isNull(final String attrName) {
    if (attrName == null)
    	throw new IllegalArgumentException();
    return nullValues.contains(attrName);
  }
  
  public Boolean isDef(final String attrName, final Boolean isAttrNotNull) {
    if (attrName == null)
    	throw new IllegalArgumentException();
    if (nullValues.contains(attrName))
    	return true;
    if (isAttrNotNull != null)
    	return isAttrNotNull;
    return false;
  }
  
  public void clearAllNull() {
    nullValues = new java.util.HashSet<String>();
  }
  
  public enum Association {
    homeParticipant,
    
    awayParticipant,
    
    group;
  }
  
  private Set<String> initAssociations =  new java.util.HashSet<String>();
  
  public Set<String> getInitAssociations() {
    return this.initAssociations;
  }
  
  public void setInitAssociations(final Set<String> initAssociations) {
    this.initAssociations = initAssociations;
  }
  
  public void setInit(final PlayOffGame.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.add(association.name());
  }
  
  public PlayOffGame _setInit(final PlayOffGame.Association... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final PlayOffGame.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.remove(association.name());
  }
  
  public PlayOffGame _clearInit(final PlayOffGame.Association... associations) {
    clearInit(associations);
    return this;
  }
  
  public void setInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.add(association);
  }
  
  public PlayOffGame _setInit(final String... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.remove(association);
  }
  
  public PlayOffGame _clearInit(final String... associations) {
    clearInit(associations);
    return this;
  }
  
  public Boolean toInit(final PlayOffGame.Association association) {
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
    
    homeParticipant,
    
    awayParticipant,
    
    status,
    
    result,
    
    group,
    
    position,
    
    onlyIds,
    
    ids;
  }
  
  private Map<String, String> operators =  new java.util.HashMap<String, String>();
  
  public Map<String, String> getOperators() {
    return operators;
  }
  
  public void setOp(final String operator, final PlayOffGame.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), operator);
  }
  
  public PlayOffGame _setOp(final String operator, final PlayOffGame.OpAttribute... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final PlayOffGame.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.remove(attribute.name());
  }
  
  public PlayOffGame _clearOp(final PlayOffGame.OpAttribute... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setOp(final String operator, final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, operator);
  }
  
  public PlayOffGame _setOp(final String operator, final String... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.remove(attribute);
  }
  
  public PlayOffGame _clearOp(final String... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setNullOp(final PlayOffGame.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), "is null");
  }
  
  public PlayOffGame _setNullOp(final PlayOffGame.OpAttribute... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void setNullOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, "is null");
  }
  
  public PlayOffGame _setNullOp(final String... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void clearAllOps() {
    operators = new java.util.HashMap<String, String>();
  }
}
