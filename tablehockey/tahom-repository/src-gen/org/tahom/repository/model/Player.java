package org.tahom.repository.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sqlproc.engine.annotation.Pojo;
import org.tahom.repository.model.FinalStanding;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.User;

@Pojo
@SuppressWarnings("all")
public class Player implements Serializable {
  private final static long serialVersionUID = 1L;
  
  public final static String ORDER_BY_ID = "ID";
  
  public final static String ORDER_BY_USER = "USER";
  
  public Player() {
  }
  
  public Player(final String name, final String surname, final String playerDiscriminator, final User user) {
    super();
    setName(name);
    setSurname(surname);
    setPlayerDiscriminator(playerDiscriminator);
    setUser(user);
  }
  
  private Integer id;
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(final Integer id) {
    this.id = id;
  }
  
  public Player _setId(final Integer id) {
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
  
  public Player _setName(final String name) {
    this.name = name;
    return this;
  }
  
  private String surname;
  
  public String getSurname() {
    return this.surname;
  }
  
  public void setSurname(final String surname) {
    this.surname = surname;
  }
  
  public Player _setSurname(final String surname) {
    this.surname = surname;
    return this;
  }
  
  private String club;
  
  public String getClub() {
    return this.club;
  }
  
  public void setClub(final String club) {
    this.club = club;
  }
  
  public Player _setClub(final String club) {
    this.club = club;
    return this;
  }
  
  private String playerDiscriminator;
  
  public String getPlayerDiscriminator() {
    return this.playerDiscriminator;
  }
  
  public void setPlayerDiscriminator(final String playerDiscriminator) {
    this.playerDiscriminator = playerDiscriminator;
  }
  
  public Player _setPlayerDiscriminator(final String playerDiscriminator) {
    this.playerDiscriminator = playerDiscriminator;
    return this;
  }
  
  private Integer worldRanking;
  
  public Integer getWorldRanking() {
    return this.worldRanking;
  }
  
  public void setWorldRanking(final Integer worldRanking) {
    this.worldRanking = worldRanking;
  }
  
  public Player _setWorldRanking(final Integer worldRanking) {
    this.worldRanking = worldRanking;
    return this;
  }
  
  private User user;
  
  public User getUser() {
    return this.user;
  }
  
  public void setUser(final User user) {
    this.user = user;
  }
  
  public Player _setUser(final User user) {
    this.user = user;
    return this;
  }
  
  private Integer ithfId;
  
  public Integer getIthfId() {
    return this.ithfId;
  }
  
  public void setIthfId(final Integer ithfId) {
    this.ithfId = ithfId;
  }
  
  public Player _setIthfId(final Integer ithfId) {
    this.ithfId = ithfId;
    return this;
  }
  
  private List<FinalStanding> finalStandings = new java.util.ArrayList<FinalStanding>();
  
  public List<FinalStanding> getFinalStandings() {
    return this.finalStandings;
  }
  
  public void setFinalStandings(final List<FinalStanding> finalStandings) {
    this.finalStandings = finalStandings;
  }
  
  public Player _setFinalStandings(final List<FinalStanding> finalStandings) {
    this.finalStandings = finalStandings;
    return this;
  }
  
  private List<Participant> participants = new java.util.ArrayList<Participant>();
  
  public List<Participant> getParticipants() {
    return this.participants;
  }
  
  public void setParticipants(final List<Participant> participants) {
    this.participants = participants;
  }
  
  public Player _setParticipants(final List<Participant> participants) {
    this.participants = participants;
    return this;
  }
  
  private boolean onlyIds;
  
  public boolean isOnlyIds() {
    return this.onlyIds;
  }
  
  public void setOnlyIds(final boolean onlyIds) {
    this.onlyIds = onlyIds;
  }
  
  public Player _setOnlyIds(final boolean onlyIds) {
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
  
  public Player _setIds(final List<Integer> ids) {
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
    Player other = (Player) obj;
    if (id == null || !id.equals(other.id))
    	return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "Player [id=" + id + ", name=" + name + ", surname=" + surname + ", club=" + club + ", playerDiscriminator=" + playerDiscriminator + ", worldRanking=" + worldRanking + ", ithfId=" + ithfId + "]";
  }
  
  public String toStringFull() {
    return "Player [id=" + id + ", name=" + name + ", surname=" + surname + ", club=" + club + ", playerDiscriminator=" + playerDiscriminator + ", worldRanking=" + worldRanking + ", user=" + user + ", ithfId=" + ithfId + ", finalStandings=" + finalStandings + ", participants=" + participants + ", onlyIds=" + onlyIds + ", ids=" + ids + "]";
  }
  
  public enum Attribute {
    club,
    
    worldRanking,
    
    ithfId;
  }
  
  private Set<String> nullValues =  new java.util.HashSet<String>();
  
  public void setNull(final Player.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues.add(attribute.name());
  }
  
  public Player _setNull(final Player.Attribute... attributes) {
    setNull(attributes);
    return this;
  }
  
  public void clearNull(final Player.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues.remove(attribute.name());
  }
  
  public Player _clearNull(final Player.Attribute... attributes) {
    clearNull(attributes);
    return this;
  }
  
  public void setNull(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues.add(attribute);
  }
  
  public Player _setNull(final String... attributes) {
    setNull(attributes);
    return this;
  }
  
  public void clearNull(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues.remove(attribute);
  }
  
  public Player _clearNull(final String... attributes) {
    clearNull(attributes);
    return this;
  }
  
  public Boolean isNull(final Player.Attribute attribute) {
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
    user,
    
    finalStandings,
    
    participants;
  }
  
  private Set<String> initAssociations =  new java.util.HashSet<String>();
  
  public Set<String> getInitAssociations() {
    return this.initAssociations;
  }
  
  public void setInitAssociations(final Set<String> initAssociations) {
    this.initAssociations = initAssociations;
  }
  
  public void setInit(final Player.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.add(association.name());
  }
  
  public Player _setInit(final Player.Association... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final Player.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.remove(association.name());
  }
  
  public Player _clearInit(final Player.Association... associations) {
    clearInit(associations);
    return this;
  }
  
  public void setInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.add(association);
  }
  
  public Player _setInit(final String... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.remove(association);
  }
  
  public Player _clearInit(final String... associations) {
    clearInit(associations);
    return this;
  }
  
  public Boolean toInit(final Player.Association association) {
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
    
    surname,
    
    club,
    
    playerDiscriminator,
    
    worldRanking,
    
    user,
    
    ithfId,
    
    finalStandings,
    
    participants,
    
    onlyIds,
    
    ids;
  }
  
  private Map<String, String> operators =  new java.util.HashMap<String, String>();
  
  public Map<String, String> getOperators() {
    return operators;
  }
  
  public void setOp(final String operator, final Player.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), operator);
  }
  
  public Player _setOp(final String operator, final Player.OpAttribute... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final Player.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.remove(attribute.name());
  }
  
  public Player _clearOp(final Player.OpAttribute... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setOp(final String operator, final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, operator);
  }
  
  public Player _setOp(final String operator, final String... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.remove(attribute);
  }
  
  public Player _clearOp(final String... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setNullOp(final Player.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), "is null");
  }
  
  public Player _setNullOp(final Player.OpAttribute... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void setNullOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, "is null");
  }
  
  public Player _setNullOp(final String... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void clearAllOps() {
    operators = new java.util.HashMap<String, String>();
  }
}
