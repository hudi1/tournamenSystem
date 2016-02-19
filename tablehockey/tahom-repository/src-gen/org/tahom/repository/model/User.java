package org.tahom.repository.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sqlproc.engine.annotation.Pojo;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.Season;
import org.tahom.repository.model.UserRole;

@Pojo
@SuppressWarnings("all")
public class User implements Serializable {
  private final static long serialVersionUID = 1L;
  
  public final static String ORDER_BY_ID = "ID";
  
  public final static String ORDER_BY_EMAIL = "EMAIL";
  
  public final static String ORDER_BY_USER_NAME = "USER_NAME";
  
  public User() {
  }
  
  public User(final String email, final String userName, final String password, final UserRole role, final Integer validity, final Boolean open) {
    super();
    setEmail(email);
    setUserName(userName);
    setPassword(password);
    setRole(role);
    setValidity(validity);
    setOpen(open);
  }
  
  private Integer id;
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(final Integer id) {
    this.id = id;
  }
  
  public User _setId(final Integer id) {
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
  
  public User _setName(final String name) {
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
  
  public User _setSurname(final String surname) {
    this.surname = surname;
    return this;
  }
  
  private String email;
  
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(final String email) {
    this.email = email;
  }
  
  public User _setEmail(final String email) {
    this.email = email;
    return this;
  }
  
  private String userName;
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(final String userName) {
    this.userName = userName;
  }
  
  public User _setUserName(final String userName) {
    this.userName = userName;
    return this;
  }
  
  private String password;
  
  public String getPassword() {
    return this.password;
  }
  
  public void setPassword(final String password) {
    this.password = password;
  }
  
  public User _setPassword(final String password) {
    this.password = password;
    return this;
  }
  
  private UserRole role;
  
  public UserRole getRole() {
    return this.role;
  }
  
  public void setRole(final UserRole role) {
    this.role = role;
  }
  
  public User _setRole(final UserRole role) {
    this.role = role;
    return this;
  }
  
  private Integer validity;
  
  public Integer getValidity() {
    return this.validity;
  }
  
  public void setValidity(final Integer validity) {
    this.validity = validity;
  }
  
  public User _setValidity(final Integer validity) {
    this.validity = validity;
    return this;
  }
  
  private Boolean open;
  
  public Boolean getOpen() {
    return this.open;
  }
  
  public void setOpen(final Boolean open) {
    this.open = open;
  }
  
  public User _setOpen(final Boolean open) {
    this.open = open;
    return this;
  }
  
  private List<Player> players = new java.util.ArrayList<Player>();
  
  public List<Player> getPlayers() {
    return this.players;
  }
  
  public void setPlayers(final List<Player> players) {
    this.players = players;
  }
  
  public User _setPlayers(final List<Player> players) {
    this.players = players;
    return this;
  }
  
  private List<Season> seasons = new java.util.ArrayList<Season>();
  
  public List<Season> getSeasons() {
    return this.seasons;
  }
  
  public void setSeasons(final List<Season> seasons) {
    this.seasons = seasons;
  }
  
  public User _setSeasons(final List<Season> seasons) {
    this.seasons = seasons;
    return this;
  }
  
  private boolean onlyIds;
  
  public boolean isOnlyIds() {
    return this.onlyIds;
  }
  
  public void setOnlyIds(final boolean onlyIds) {
    this.onlyIds = onlyIds;
  }
  
  public User _setOnlyIds(final boolean onlyIds) {
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
  
  public User _setIds(final List<Integer> ids) {
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
    User other = (User) obj;
    if (id == null || !id.equals(other.id))
    	return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", userName=" + userName + ", password=" + password + ", role=" + role + ", validity=" + validity + ", open=" + open + "]";
  }
  
  public String toStringFull() {
    return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", userName=" + userName + ", password=" + password + ", role=" + role + ", validity=" + validity + ", open=" + open + ", players=" + players + ", seasons=" + seasons + ", onlyIds=" + onlyIds + ", ids=" + ids + "]";
  }
  
  public enum Attribute {
    name,
    
    surname;
  }
  
  private Set<String> nullValues =  new java.util.HashSet<String>();
  
  public void setNull(final User.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues.add(attribute.name());
  }
  
  public User _setNull(final User.Attribute... attributes) {
    setNull(attributes);
    return this;
  }
  
  public void clearNull(final User.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues.remove(attribute.name());
  }
  
  public User _clearNull(final User.Attribute... attributes) {
    clearNull(attributes);
    return this;
  }
  
  public void setNull(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues.add(attribute);
  }
  
  public User _setNull(final String... attributes) {
    setNull(attributes);
    return this;
  }
  
  public void clearNull(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues.remove(attribute);
  }
  
  public User _clearNull(final String... attributes) {
    clearNull(attributes);
    return this;
  }
  
  public Boolean isNull(final User.Attribute attribute) {
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
    players,
    
    seasons;
  }
  
  private Set<String> initAssociations =  new java.util.HashSet<String>();
  
  public Set<String> getInitAssociations() {
    return this.initAssociations;
  }
  
  public void setInitAssociations(final Set<String> initAssociations) {
    this.initAssociations = initAssociations;
  }
  
  public void setInit(final User.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.add(association.name());
  }
  
  public User _setInit(final User.Association... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final User.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.remove(association.name());
  }
  
  public User _clearInit(final User.Association... associations) {
    clearInit(associations);
    return this;
  }
  
  public void setInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.add(association);
  }
  
  public User _setInit(final String... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.remove(association);
  }
  
  public User _clearInit(final String... associations) {
    clearInit(associations);
    return this;
  }
  
  public Boolean toInit(final User.Association association) {
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
    
    email,
    
    userName,
    
    password,
    
    role,
    
    validity,
    
    open,
    
    players,
    
    seasons,
    
    onlyIds,
    
    ids;
  }
  
  private Map<String, String> operators =  new java.util.HashMap<String, String>();
  
  public Map<String, String> getOperators() {
    return operators;
  }
  
  public void setOp(final String operator, final User.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), operator);
  }
  
  public User _setOp(final String operator, final User.OpAttribute... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final User.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.remove(attribute.name());
  }
  
  public User _clearOp(final User.OpAttribute... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setOp(final String operator, final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, operator);
  }
  
  public User _setOp(final String operator, final String... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.remove(attribute);
  }
  
  public User _clearOp(final String... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setNullOp(final User.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), "is null");
  }
  
  public User _setNullOp(final User.OpAttribute... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void setNullOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, "is null");
  }
  
  public User _setNullOp(final String... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void clearAllOps() {
    operators = new java.util.HashMap<String, String>();
  }
}
