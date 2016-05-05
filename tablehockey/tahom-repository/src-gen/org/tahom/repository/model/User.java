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
  
  private boolean onlyIds_;
  
  public boolean isOnlyIds_() {
    return this.onlyIds_;
  }
  
  public void setOnlyIds_(final boolean onlyIds_) {
    this.onlyIds_ = onlyIds_;
  }
  
  public User _setOnlyIds_(final boolean onlyIds_) {
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
  
  public User _setIds_(final List<Integer> ids_) {
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
    return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", userName=" + userName + ", password=" + password + ", role=" + role + ", validity=" + validity + ", open=" + open + ", players=" + players + ", seasons=" + seasons + ", onlyIds_=" + onlyIds_ + ", ids_=" + ids_ + "]";
  }
  
  public enum Attribute {
    name,
    
    surname;
  }
  
  private Set<String> nullValues_ =  new java.util.HashSet<String>();
  
  public void setNull_(final User.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues_.add(attribute.name());
  }
  
  public User _setNull_(final User.Attribute... attributes) {
    setNull_(attributes);
    return this;
  }
  
  public void clearNull_(final User.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues_.remove(attribute.name());
  }
  
  public User _clearNull_(final User.Attribute... attributes) {
    clearNull_(attributes);
    return this;
  }
  
  public void setNull_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues_.add(attribute);
  }
  
  public User _setNull_(final String... attributes) {
    setNull_(attributes);
    return this;
  }
  
  public void clearNull_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues_.remove(attribute);
  }
  
  public User _clearNull_(final String... attributes) {
    clearNull_(attributes);
    return this;
  }
  
  public Boolean isNull_(final User.Attribute attribute) {
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
    players,
    
    seasons;
  }
  
  private Set<String> initAssociations_ =  new java.util.HashSet<String>();
  
  public Set<String> getInitAssociations_() {
    return this.initAssociations_;
  }
  
  public void setInitAssociations_(final Set<String> initAssociations_) {
    this.initAssociations_ = initAssociations_;
  }
  
  public void setInit_(final User.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations_.add(association.name());
  }
  
  public User _setInit_(final User.Association... associations) {
    setInit_(associations);
    return this;
  }
  
  public void clearInit_(final User.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations_.remove(association.name());
  }
  
  public User _clearInit_(final User.Association... associations) {
    clearInit_(associations);
    return this;
  }
  
  public void setInit_(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations_.add(association);
  }
  
  public User _setInit_(final String... associations) {
    setInit_(associations);
    return this;
  }
  
  public void clearInit_(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations_.remove(association);
  }
  
  public User _clearInit_(final String... associations) {
    clearInit_(associations);
    return this;
  }
  
  public Boolean toInit_(final User.Association association) {
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
    
    email,
    
    userName,
    
    password,
    
    role,
    
    validity,
    
    open,
    
    players,
    
    seasons,
    
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
  
  public void setOp_(final String operator, final User.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.put(attribute.name(), operator);
  }
  
  public User _setOp_(final String operator, final User.OpAttribute... attributes) {
    setOp_(operator, attributes);
    return this;
  }
  
  public void clearOp_(final User.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.remove(attribute.name());
  }
  
  public User _clearOp_(final User.OpAttribute... attributes) {
    clearOp_(attributes);
    return this;
  }
  
  public void setOp_(final String operator, final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.put(attribute, operator);
  }
  
  public User _setOp_(final String operator, final String... attributes) {
    setOp_(operator, attributes);
    return this;
  }
  
  public void clearOp_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.remove(attribute);
  }
  
  public User _clearOp_(final String... attributes) {
    clearOp_(attributes);
    return this;
  }
  
  public void setNullOp_(final User.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators_.put(attribute.name(), "is null");
  }
  
  public User _setNullOp_(final User.OpAttribute... attributes) {
    setNullOp_(attributes);
    return this;
  }
  
  public void setNullOp_(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators_.put(attribute, "is null");
  }
  
  public User _setNullOp_(final String... attributes) {
    setNullOp_(attributes);
    return this;
  }
  
  public void clearAllOps_() {
    operators_ = new java.util.HashMap<String, String>();
  }
}
