package org.tahom.repository.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sqlproc.engine.annotation.Pojo;
import org.tahom.repository.model.FinalStanding;
import org.tahom.repository.model.IthfTournament;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.User;
import org.tahom.repository.model.impl.Surname;

@Pojo
@SuppressWarnings("all")
public class Player implements Serializable {
  private final static long serialVersionUID = 1L;
  
  public final static String ORDER_BY_ID = "ID";
  
  public final static String ORDER_BY_USER = "USER";
  
  public Player() {
  }
  
  public Player(final String name, final Surname surname, final User user) {
    super();
    setName(name);
    setSurname(surname);
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
  
  private Surname surname;
  
  public Surname getSurname() {
    return this.surname;
  }
  
  public void setSurname(final Surname surname) {
    this.surname = surname;
  }
  
  public Player _setSurname(final Surname surname) {
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
  
  private String country;
  
  public String getCountry() {
    return this.country;
  }
  
  public void setCountry(final String country) {
    this.country = country;
  }
  
  public Player _setCountry(final String country) {
    this.country = country;
    return this;
  }
  
  private Date lastUpdate;
  
  public Date getLastUpdate() {
    return this.lastUpdate;
  }
  
  public void setLastUpdate(final Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
  
  public Player _setLastUpdate(final Date lastUpdate) {
    this.lastUpdate = lastUpdate;
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
  
  private List<IthfTournament> ithfTournaments = new java.util.ArrayList<IthfTournament>();
  
  public List<IthfTournament> getIthfTournaments() {
    return this.ithfTournaments;
  }
  
  public void setIthfTournaments(final List<IthfTournament> ithfTournaments) {
    this.ithfTournaments = ithfTournaments;
  }
  
  public Player _setIthfTournaments(final List<IthfTournament> ithfTournaments) {
    this.ithfTournaments = ithfTournaments;
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
  
  private boolean onlyIds_;
  
  public boolean isOnlyIds_() {
    return this.onlyIds_;
  }
  
  public void setOnlyIds_(final boolean onlyIds_) {
    this.onlyIds_ = onlyIds_;
  }
  
  public Player _setOnlyIds_(final boolean onlyIds_) {
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
  
  public Player _setIds_(final List<Integer> ids_) {
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
    Player other = (Player) obj;
    if (id == null || !id.equals(other.id))
    	return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "Player [id=" + id + ", name=" + name + ", surname=" + surname + ", club=" + club + ", worldRanking=" + worldRanking + ", ithfId=" + ithfId + ", country=" + country + ", lastUpdate=" + lastUpdate + "]";
  }
  
  public String toStringFull() {
    return "Player [id=" + id + ", name=" + name + ", surname=" + surname + ", club=" + club + ", worldRanking=" + worldRanking + ", user=" + user + ", ithfId=" + ithfId + ", country=" + country + ", lastUpdate=" + lastUpdate + ", finalStandings=" + finalStandings + ", ithfTournaments=" + ithfTournaments + ", participants=" + participants + ", onlyIds_=" + onlyIds_ + ", ids_=" + ids_ + "]";
  }
  
  public enum Attribute {
    club,
    
    worldRanking,
    
    ithfId,
    
    country,
    
    lastUpdate;
  }
  
  private Set<String> nullValues_ =  new java.util.HashSet<String>();
  
  public void setNull_(final Player.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues_.add(attribute.name());
  }
  
  public Player _setNull_(final Player.Attribute... attributes) {
    setNull_(attributes);
    return this;
  }
  
  public void clearNull_(final Player.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues_.remove(attribute.name());
  }
  
  public Player _clearNull_(final Player.Attribute... attributes) {
    clearNull_(attributes);
    return this;
  }
  
  public void setNull_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues_.add(attribute);
  }
  
  public Player _setNull_(final String... attributes) {
    setNull_(attributes);
    return this;
  }
  
  public void clearNull_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues_.remove(attribute);
  }
  
  public Player _clearNull_(final String... attributes) {
    clearNull_(attributes);
    return this;
  }
  
  public Boolean isNull_(final Player.Attribute attribute) {
    if (attribute == null)
    	throw new IllegalArgumentException();
    return nullValues_.contains(attribute.name());
  }
  
  public Boolean isNull_(final String attrName) {
    if (attrName == null)
    	throw new IllegalArgumentException();
    return nullValues_.contains(attrName);
  }
  
  public Boolean isDef_(final String attrName, final Boolean isAttrNotNull) {
    if (attrName == null)
    	throw new IllegalArgumentException();
    if (nullValues_.contains(attrName))
    	return true;
    if (isAttrNotNull != null)
    	return isAttrNotNull;
    return false;
  }
  
  public void clearAllNull_() {
    nullValues_ = new java.util.HashSet<String>();
  }
  
  public enum Association {
    user,
    
    finalStandings,
    
    ithfTournaments,
    
    participants;
  }
  
  private Set<String> initAssociations_ =  new java.util.HashSet<String>();
  
  public Set<String> getInitAssociations_() {
    return this.initAssociations_;
  }
  
  public void setInitAssociations_(final Set<String> initAssociations_) {
    this.initAssociations_ = initAssociations_;
  }
  
  public void setInit_(final Player.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations_.add(association.name());
  }
  
  public Player _setInit_(final Player.Association... associations) {
    setInit_(associations);
    return this;
  }
  
  public void clearInit_(final Player.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations_.remove(association.name());
  }
  
  public Player _clearInit_(final Player.Association... associations) {
    clearInit_(associations);
    return this;
  }
  
  public void setInit_(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations_.add(association);
  }
  
  public Player _setInit_(final String... associations) {
    setInit_(associations);
    return this;
  }
  
  public void clearInit_(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations_.remove(association);
  }
  
  public Player _clearInit_(final String... associations) {
    clearInit_(associations);
    return this;
  }
  
  public Boolean toInit_(final Player.Association association) {
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
    
    surname,
    
    club,
    
    worldRanking,
    
    user,
    
    ithfId,
    
    country,
    
    lastUpdate,
    
    finalStandings,
    
    ithfTournaments,
    
    participants,
    
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
  
  public void setOp_(final String operator, final Player.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.put(attribute.name(), operator);
  }
  
  public Player _setOp_(final String operator, final Player.OpAttribute... attributes) {
    setOp_(operator, attributes);
    return this;
  }
  
  public void clearOp_(final Player.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.remove(attribute.name());
  }
  
  public Player _clearOp_(final Player.OpAttribute... attributes) {
    clearOp_(attributes);
    return this;
  }
  
  public void setOp_(final String operator, final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.put(attribute, operator);
  }
  
  public Player _setOp_(final String operator, final String... attributes) {
    setOp_(operator, attributes);
    return this;
  }
  
  public void clearOp_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.remove(attribute);
  }
  
  public Player _clearOp_(final String... attributes) {
    clearOp_(attributes);
    return this;
  }
  
  public void setNullOp_(final Player.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.put(attribute.name(), "is null");
  }
  
  public Player _setNullOp_(final Player.OpAttribute... attributes) {
    setNullOp_(attributes);
    return this;
  }
  
  public void setNullOp_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.put(attribute, "is null");
  }
  
  public Player _setNullOp_(final String... attributes) {
    setNullOp_(attributes);
    return this;
  }
  
  public void clearAllOps_() {
    operators_ = new java.util.HashMap<String, String>();
  }
}
