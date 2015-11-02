package org.toursys.repository.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Score;

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
  
  private boolean onlyIds;
  
  public boolean isOnlyIds() {
    return this.onlyIds;
  }
  
  public void setOnlyIds(final boolean onlyIds) {
    this.onlyIds = onlyIds;
  }
  
  public Participant _setOnlyIds(final boolean onlyIds) {
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
  
  public Participant _setIds(final List<Integer> ids) {
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
    return "Participant [id=" + id + ", points=" + points + ", rank=" + rank + ", group=" + group + ", player=" + player + ", score=" + score + ", equalRank=" + equalRank + ", temp=" + temp + ", games=" + games + ", playOffGames=" + playOffGames + ", onlyIds=" + onlyIds + ", ids=" + ids + "]";
  }
  
  public enum Attribute {
    rank,
    
    equalRank,
    
    temp;
  }
  
  private Set<String> nullValues =  new java.util.HashSet<String>();
  
  public void setNull(final Participant.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues.add(attribute.name());
  }
  
  public Participant _setNull(final Participant.Attribute... attributes) {
    setNull(attributes);
    return this;
  }
  
  public void clearNull(final Participant.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues.remove(attribute.name());
  }
  
  public Participant _clearNull(final Participant.Attribute... attributes) {
    clearNull(attributes);
    return this;
  }
  
  public void setNull(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues.add(attribute);
  }
  
  public Participant _setNull(final String... attributes) {
    setNull(attributes);
    return this;
  }
  
  public void clearNull(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues.remove(attribute);
  }
  
  public Participant _clearNull(final String... attributes) {
    clearNull(attributes);
    return this;
  }
  
  public Boolean isNull(final Participant.Attribute attribute) {
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
    group,
    
    player,
    
    games,
    
    playOffGames;
  }
  
  private Set<String> initAssociations =  new java.util.HashSet<String>();
  
  public Set<String> getInitAssociations() {
    return this.initAssociations;
  }
  
  public void setInitAssociations(final Set<String> initAssociations) {
    this.initAssociations = initAssociations;
  }
  
  public void setInit(final Participant.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.add(association.name());
  }
  
  public Participant _setInit(final Participant.Association... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final Participant.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.remove(association.name());
  }
  
  public Participant _clearInit(final Participant.Association... associations) {
    clearInit(associations);
    return this;
  }
  
  public void setInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.add(association);
  }
  
  public Participant _setInit(final String... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.remove(association);
  }
  
  public Participant _clearInit(final String... associations) {
    clearInit(associations);
    return this;
  }
  
  public Boolean toInit(final Participant.Association association) {
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
    
    points,
    
    rank,
    
    group,
    
    player,
    
    score,
    
    equalRank,
    
    temp,
    
    games,
    
    playOffGames,
    
    onlyIds,
    
    ids;
  }
  
  private Map<String, String> operators =  new java.util.HashMap<String, String>();
  
  public Map<String, String> getOperators() {
    return operators;
  }
  
  public void setOp(final String operator, final Participant.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), operator);
  }
  
  public Participant _setOp(final String operator, final Participant.OpAttribute... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final Participant.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.remove(attribute.name());
  }
  
  public Participant _clearOp(final Participant.OpAttribute... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setOp(final String operator, final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, operator);
  }
  
  public Participant _setOp(final String operator, final String... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.remove(attribute);
  }
  
  public Participant _clearOp(final String... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setNullOp(final Participant.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), "is null");
  }
  
  public Participant _setNullOp(final Participant.OpAttribute... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void setNullOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, "is null");
  }
  
  public Participant _setNullOp(final String... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void clearAllOps() {
    operators = new java.util.HashMap<String, String>();
  }
}
