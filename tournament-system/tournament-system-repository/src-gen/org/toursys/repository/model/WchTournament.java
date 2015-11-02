package org.toursys.repository.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.WchQualification;

@Pojo
@SuppressWarnings("all")
public class WchTournament implements Serializable {
  private final static long serialVersionUID = 1L;
  
  public final static String ORDER_BY_ID = "ID";
  
  public final static String ORDER_BY_WCH_QUALIFICATION = "WCH_QUALIFICATION";
  
  public WchTournament() {
  }
  
  public WchTournament(final WchQualification wchQualification, final Date date) {
    super();
    setWchQualification(wchQualification);
    setDate(date);
  }
  
  private Integer id;
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(final Integer id) {
    this.id = id;
  }
  
  public WchTournament _setId(final Integer id) {
    this.id = id;
    return this;
  }
  
  private WchQualification wchQualification;
  
  public WchQualification getWchQualification() {
    return this.wchQualification;
  }
  
  public void setWchQualification(final WchQualification wchQualification) {
    this.wchQualification = wchQualification;
  }
  
  public WchTournament _setWchQualification(final WchQualification wchQualification) {
    this.wchQualification = wchQualification;
    return this;
  }
  
  private String name;
  
  public String getName() {
    return this.name;
  }
  
  public void setName(final String name) {
    this.name = name;
  }
  
  public WchTournament _setName(final String name) {
    this.name = name;
    return this;
  }
  
  private String series;
  
  public String getSeries() {
    return this.series;
  }
  
  public void setSeries(final String series) {
    this.series = series;
  }
  
  public WchTournament _setSeries(final String series) {
    this.series = series;
    return this;
  }
  
  private Date date;
  
  public Date getDate() {
    return this.date;
  }
  
  public void setDate(final Date date) {
    this.date = date;
  }
  
  public WchTournament _setDate(final Date date) {
    this.date = date;
    return this;
  }
  
  private Integer points;
  
  public Integer getPoints() {
    return this.points;
  }
  
  public void setPoints(final Integer points) {
    this.points = points;
  }
  
  public WchTournament _setPoints(final Integer points) {
    this.points = points;
    return this;
  }
  
  private boolean onlyIds;
  
  public boolean isOnlyIds() {
    return this.onlyIds;
  }
  
  public void setOnlyIds(final boolean onlyIds) {
    this.onlyIds = onlyIds;
  }
  
  public WchTournament _setOnlyIds(final boolean onlyIds) {
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
  
  public WchTournament _setIds(final List<Integer> ids) {
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
    WchTournament other = (WchTournament) obj;
    if (id == null || !id.equals(other.id))
    	return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "WchTournament [id=" + id + ", name=" + name + ", series=" + series + ", date=" + date + ", points=" + points + "]";
  }
  
  public String toStringFull() {
    return "WchTournament [id=" + id + ", wchQualification=" + wchQualification + ", name=" + name + ", series=" + series + ", date=" + date + ", points=" + points + ", onlyIds=" + onlyIds + ", ids=" + ids + "]";
  }
  
  public enum Attribute {
    name,
    
    series,
    
    points;
  }
  
  private Set<String> nullValues =  new java.util.HashSet<String>();
  
  public void setNull(final WchTournament.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues.add(attribute.name());
  }
  
  public WchTournament _setNull(final WchTournament.Attribute... attributes) {
    setNull(attributes);
    return this;
  }
  
  public void clearNull(final WchTournament.Attribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (Attribute attribute : attributes)
    	nullValues.remove(attribute.name());
  }
  
  public WchTournament _clearNull(final WchTournament.Attribute... attributes) {
    clearNull(attributes);
    return this;
  }
  
  public void setNull(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues.add(attribute);
  }
  
  public WchTournament _setNull(final String... attributes) {
    setNull(attributes);
    return this;
  }
  
  public void clearNull(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	nullValues.remove(attribute);
  }
  
  public WchTournament _clearNull(final String... attributes) {
    clearNull(attributes);
    return this;
  }
  
  public Boolean isNull(final WchTournament.Attribute attribute) {
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
    wchQualification;
  }
  
  private Set<String> initAssociations =  new java.util.HashSet<String>();
  
  public Set<String> getInitAssociations() {
    return this.initAssociations;
  }
  
  public void setInitAssociations(final Set<String> initAssociations) {
    this.initAssociations = initAssociations;
  }
  
  public void setInit(final WchTournament.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.add(association.name());
  }
  
  public WchTournament _setInit(final WchTournament.Association... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final WchTournament.Association... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (Association association : associations)
    	initAssociations.remove(association.name());
  }
  
  public WchTournament _clearInit(final WchTournament.Association... associations) {
    clearInit(associations);
    return this;
  }
  
  public void setInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.add(association);
  }
  
  public WchTournament _setInit(final String... associations) {
    setInit(associations);
    return this;
  }
  
  public void clearInit(final String... associations) {
    if (associations == null)
    	throw new IllegalArgumentException();
    for (String association : associations)
    	initAssociations.remove(association);
  }
  
  public WchTournament _clearInit(final String... associations) {
    clearInit(associations);
    return this;
  }
  
  public Boolean toInit(final WchTournament.Association association) {
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
    
    wchQualification,
    
    name,
    
    series,
    
    date,
    
    points,
    
    onlyIds,
    
    ids;
  }
  
  private Map<String, String> operators =  new java.util.HashMap<String, String>();
  
  public Map<String, String> getOperators() {
    return operators;
  }
  
  public void setOp(final String operator, final WchTournament.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), operator);
  }
  
  public WchTournament _setOp(final String operator, final WchTournament.OpAttribute... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final WchTournament.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.remove(attribute.name());
  }
  
  public WchTournament _clearOp(final WchTournament.OpAttribute... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setOp(final String operator, final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, operator);
  }
  
  public WchTournament _setOp(final String operator, final String... attributes) {
    setOp(operator, attributes);
    return this;
  }
  
  public void clearOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.remove(attribute);
  }
  
  public WchTournament _clearOp(final String... attributes) {
    clearOp(attributes);
    return this;
  }
  
  public void setNullOp(final WchTournament.OpAttribute... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (OpAttribute attribute : attributes)
    	operators.put(attribute.name(), "is null");
  }
  
  public WchTournament _setNullOp(final WchTournament.OpAttribute... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void setNullOp(final String... attributes) {
    if (attributes == null)
    	throw new IllegalArgumentException();
    for (String attribute : attributes)
    	operators.put(attribute, "is null");
  }
  
  public WchTournament _setNullOp(final String... attributes) {
    setNullOp(attributes);
    return this;
  }
  
  public void clearAllOps() {
    operators = new java.util.HashMap<String, String>();
  }
}
