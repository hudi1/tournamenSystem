package org.toursys.repository.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.WchTournament;

@Pojo
@SuppressWarnings("all")
public class WchQualification implements Serializable {
  private final static long serialVersionUID = 1L;
  
  public final static String ORDER_BY_ID = "ID";
  
  public final static String ORDER_BY_ITHF_ID = "ITHF_ID";
  
  public WchQualification() {
  }
  
  public WchQualification(final Integer ithfId, final Date lastUpdate, final String name) {
    super();
    setIthfId(ithfId);
    setLastUpdate(lastUpdate);
    setName(name);
  }
  
  private Integer id;
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(final Integer id) {
    this.id = id;
  }
  
  public WchQualification _setId(final Integer id) {
    this.id = id;
    return this;
  }
  
  private Integer ithfId;
  
  public Integer getIthfId() {
    return this.ithfId;
  }
  
  public void setIthfId(final Integer ithfId) {
    this.ithfId = ithfId;
  }
  
  public WchQualification _setIthfId(final Integer ithfId) {
    this.ithfId = ithfId;
    return this;
  }
  
  private Date lastUpdate;
  
  public Date getLastUpdate() {
    return this.lastUpdate;
  }
  
  public void setLastUpdate(final Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
  
  public WchQualification _setLastUpdate(final Date lastUpdate) {
    this.lastUpdate = lastUpdate;
    return this;
  }
  
  private String name;
  
  public String getName() {
    return this.name;
  }
  
  public void setName(final String name) {
    this.name = name;
  }
  
  public WchQualification _setName(final String name) {
    this.name = name;
    return this;
  }
  
  private List<WchTournament> wchTournaments = new java.util.ArrayList<WchTournament>();
  
  public List<WchTournament> getWchTournaments() {
    return this.wchTournaments;
  }
  
  public void setWchTournaments(final List<WchTournament> wchTournaments) {
    this.wchTournaments = wchTournaments;
  }
  
  public WchQualification _setWchTournaments(final List<WchTournament> wchTournaments) {
    this.wchTournaments = wchTournaments;
    return this;
  }
  
  private boolean onlyIds;
  
  public boolean isOnlyIds() {
    return this.onlyIds;
  }
  
  public void setOnlyIds(final boolean onlyIds) {
    this.onlyIds = onlyIds;
  }
  
  public WchQualification _setOnlyIds(final boolean onlyIds) {
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
  
  public WchQualification _setIds(final List<Integer> ids) {
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
    WchQualification other = (WchQualification) obj;
    if (id == null || !id.equals(other.id))
    	return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "WchQualification [id=" + id + ", ithfId=" + ithfId + ", lastUpdate=" + lastUpdate + ", name=" + name + "]";
  }
  
  public String toStringFull() {
    return "WchQualification [id=" + id + ", ithfId=" + ithfId + ", lastUpdate=" + lastUpdate + ", name=" + name + ", wchTournaments=" + wchTournaments + ", onlyIds=" + onlyIds + ", ids=" + ids + "]";
  }
  
  public enum Association {
    wchTournaments;
  }
  
  private Set<String> initAssociations =  new java.util.HashSet<String>();
  
  public Set<String> getInitAssociations() {
    return this.initAssociations;
  }
  
  public void setInitAssociations(final Set<String> initAssociations) {
    this.initAssociations = initAssociations;
  }
  
  public void setInit(final WchQualification.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.add(association.name());
  }
  
  public WchQualification _setInit(final WchQualification.Association... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final WchQualification.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.remove(association.name());
  }
  
  public WchQualification _clearInit(final WchQualification.Association... associations) {
    clearInit(associations);
    return this;
  }
  
  public void setInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.add(association);
  }
  
  public WchQualification _setInit(final String... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.remove(association);
  }
  
  public WchQualification _clearInit(final String... associations) {
    clearInit(associations);
    return this;
  }
  
  public Boolean toInit(final WchQualification.Association association) {
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
    
    ithfId,
    
    lastUpdate,
    
    name,
    
    wchTournaments,
    
    onlyIds,
    
    ids;
  }
  
  private Map<String, String> operators =  new java.util.HashMap<String, String>();
  
  public Map<String, String> getOperators() {
    return operators;
  }
  
  public void setOp(final String operator, final WchQualification.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), operator);
  }
  
  public WchQualification _setOp(final String operator, final WchQualification.OpAttribute... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final WchQualification.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.remove(attribute.name());
  }
  
  public WchQualification _clearOp(final WchQualification.OpAttribute... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setOp(final String operator, final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, operator);
  }
  
  public WchQualification _setOp(final String operator, final String... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.remove(attribute);
  }
  
  public WchQualification _clearOp(final String... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setNullOp(final WchQualification.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), "is null");
  }
  
  public WchQualification _setNullOp(final WchQualification.OpAttribute... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void setNullOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, "is null");
  }
  
  public WchQualification _setNullOp(final String... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void clearAllOps() {
    operators = new java.util.HashMap<String, String>();
  }
}
