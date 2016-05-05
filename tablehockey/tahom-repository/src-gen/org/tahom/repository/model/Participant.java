package org.tahom.repository.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sqlproc.engine.annotation.Pojo;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.PlayOffGame;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.impl.Score;

@Pojo
@SuppressWarnings("all")
public class Participant implements Serializable {
  private final static long serialVersionUID = 1L;
  
  public final static String ORDER_BY_ID = "ID";
  
  public final static String ORDER_BY_RANK = "RANK";
  
  public final static String ORDER_BY_GROUP = "GROUP";
  
  public final static String ORDER_BY_PLAYER = "PLAYER";
  
  public Participant() {
  }
  
  public Participant(final Integer points, final Groups group, final Player player, final Score score) {
    super();
    setPoints(points);
    setGroup(group);
    setPlayer(player);
    setScore(score);
  }
  
  private Integer id;
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(final Integer id) {
    this.id = id;
  }
  
  public Participant _setId(final Integer id) {
    this.id = id;
    return this;
  }
  
  private Integer points;
  
  public Integer getPoints() {
    return this.points;
  }
  
  public void setPoints(final Integer points) {
    this.points = points;
  }
  
  public Participant _setPoints(final Integer points) {
    this.points = points;
    return this;
  }
  
  private Integer rank;
  
  public Integer getRank() {
    return this.rank;
  }
  
  public void setRank(final Integer rank) {
    this.rank = rank;
  }
  
  public Participant _setRank(final Integer rank) {
    this.rank = rank;
    return this;
  }
  
  private Groups group;
  
  public Groups getGroup() {
    return this.group;
  }
  
  public void setGroup(final Groups group) {
    this.group = group;
  }
  
  public Participant _setGroup(final Groups group) {
    this.group = group;
    return this;
  }
  
  private Player player;
  
  public Player getPlayer() {
    return this.player;
  }
  
  public void setPlayer(final Player player) {
    this.player = player;
  }
  
  public Participant _setPlayer(final Player player) {
    this.player = player;
    return this;
  }
  
  private Score score;
  
  public Score getScore() {
    return this.score;
  }
  
  public void setScore(final Score score) {
    this.score = score;
  }
  
  public Participant _setScore(final Score score) {
    this.score = score;
    return this;
  }
  
  private Integer equalRank;
  
  public Integer getEqualRank() {
    return this.equalRank;
  }
  
  public void setEqualRank(final Integer equalRank) {
    this.equalRank = equalRank;
  }
  
  public Participant _setEqualRank(final Integer equalRank) {
    this.equalRank = equalRank;
    return this;
  }
  
  private boolean temp;
  
  public boolean isTemp() {
    return this.temp;
  }
  
  public void setTemp(final boolean temp) {
    this.temp = temp;
  }
  
  public Participant _setTemp(final boolean temp) {
    this.temp = temp;
    return this;
  }
  
  private List<Game> games = new java.util.ArrayList<Game>();
  
  public List<Game> getGames() {
    return this.games;
  }
  
  public void setGames(final List<Game> games) {
    this.games = games;
  }
  
  public Participant _setGames(final List<Game> games) {
    this.games = games;
    return this;
  }
  
  private List<PlayOffGame> playOffGames = new java.util.ArrayList<PlayOffGame>();
  
  public List<PlayOffGame> getPlayOffGames() {
    return this.playOffGames;
  }
  
  public void setPlayOffGames(final List<PlayOffGame> playOffGames) {
    this.playOffGames = playOffGames;
  }
  
  public Participant _setPlayOffGames(final List<PlayOffGame> playOffGames) {
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
  
  public Participant _setOnlyIds_(final boolean onlyIds_) {
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
  
  public Participant _setIds_(final List<Integer> ids_) {
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
    Participant other = (Participant) obj;
    if (id == null || !id.equals(other.id))
    	return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "Participant [id=" + id + ", points=" + points + ", rank=" + rank + ", score=" + score + ", equalRank=" + equalRank + ", temp=" + temp + "]";
  }
  
  public String toStringFull() {
    return "Participant [id=" + id + ", points=" + points + ", rank=" + rank + ", group=" + group + ", player=" + player + ", score=" + score + ", equalRank=" + equalRank + ", temp=" + temp + ", games=" + games + ", playOffGames=" + playOffGames + ", onlyIds_=" + onlyIds_ + ", ids_=" + ids_ + "]";
  }
  
  public enum Attribute {
    rank,
    
    equalRank,
    
    temp;
  }
  
  private Set<String> nullValues_ =  new java.util.HashSet<String>();
  
  public void setNull_(final Participant.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues_.add(attribute.name());
  }
  
  public Participant _setNull_(final Participant.Attribute... attributes) {
    setNull_(attributes);
    return this;
  }
  
  public void clearNull_(final Participant.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues_.remove(attribute.name());
  }
  
  public Participant _clearNull_(final Participant.Attribute... attributes) {
    clearNull_(attributes);
    return this;
  }
  
  public void setNull_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues_.add(attribute);
  }
  
  public Participant _setNull_(final String... attributes) {
    setNull_(attributes);
    return this;
  }
  
  public void clearNull_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues_.remove(attribute);
  }
  
  public Participant _clearNull_(final String... attributes) {
    clearNull_(attributes);
    return this;
  }
  
  public Boolean isNull_(final Participant.Attribute attribute) {
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
    group,
    
    player,
    
    games,
    
    playOffGames;
  }
  
  private Set<String> initAssociations_ =  new java.util.HashSet<String>();
  
  public Set<String> getInitAssociations_() {
    return this.initAssociations_;
  }
  
  public void setInitAssociations_(final Set<String> initAssociations_) {
    this.initAssociations_ = initAssociations_;
  }
  
  public void setInit_(final Participant.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations_.add(association.name());
  }
  
  public Participant _setInit_(final Participant.Association... associations) {
    setInit_(associations);
    return this;
  }
  
  public void clearInit_(final Participant.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations_.remove(association.name());
  }
  
  public Participant _clearInit_(final Participant.Association... associations) {
    clearInit_(associations);
    return this;
  }
  
  public void setInit_(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations_.add(association);
  }
  
  public Participant _setInit_(final String... associations) {
    setInit_(associations);
    return this;
  }
  
  public void clearInit_(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations_.remove(association);
  }
  
  public Participant _clearInit_(final String... associations) {
    clearInit_(associations);
    return this;
  }
  
  public Boolean toInit_(final Participant.Association association) {
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
    
    points,
    
    rank,
    
    group,
    
    player,
    
    score,
    
    equalRank,
    
    temp,
    
    games,
    
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
  
  public void setOp_(final String operator, final Participant.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.put(attribute.name(), operator);
  }
  
  public Participant _setOp_(final String operator, final Participant.OpAttribute... attributes) {
    setOp_(operator, attributes);
    return this;
  }
  
  public void clearOp_(final Participant.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.remove(attribute.name());
  }
  
  public Participant _clearOp_(final Participant.OpAttribute... attributes) {
    clearOp_(attributes);
    return this;
  }
  
  public void setOp_(final String operator, final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.put(attribute, operator);
  }
  
  public Participant _setOp_(final String operator, final String... attributes) {
    setOp_(operator, attributes);
    return this;
  }
  
  public void clearOp_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.remove(attribute);
  }
  
  public Participant _clearOp_(final String... attributes) {
    clearOp_(attributes);
    return this;
  }
  
  public void setNullOp_(final Participant.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.put(attribute.name(), "is null");
  }
  
  public Participant _setNullOp_(final Participant.OpAttribute... attributes) {
    setNullOp_(attributes);
    return this;
  }
  
  public void setNullOp_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.put(attribute, "is null");
  }
  
  public Participant _setNullOp_(final String... attributes) {
    setNullOp_(attributes);
    return this;
  }
  
  public void clearAllOps_() {
    operators_ = new java.util.HashMap<String, String>();
  }
}
