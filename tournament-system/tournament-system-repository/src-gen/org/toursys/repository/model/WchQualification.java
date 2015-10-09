package org.toursys.repository.model;
	
import java.util.Date;
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class WchQualification implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final int ORDER_BY_ID = 1;
	public static final int ORDER_BY_ITHF_ID = 2;
	
	public WchQualification() {
	}
		
	public WchQualification(Integer ithfId, Date lastUpdate, String name) {
		this.ithfId = ithfId;
		this.lastUpdate = lastUpdate;
		this.name = name;
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WchQualification _setId(Integer id) {
		this.id = id;
		return this;
	}

	private Integer ithfId;

	public Integer getIthfId() {
		return ithfId;
	}

	public void setIthfId(Integer ithfId) {
		this.ithfId = ithfId;
	}

	public WchQualification _setIthfId(Integer ithfId) {
		this.ithfId = ithfId;
		return this;
	}

	private Date lastUpdate;

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public WchQualification _setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
		return this;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WchQualification _setName(String name) {
		this.name = name;
		return this;
	}

	private List<WchTournament> wchTournaments = new ArrayList<WchTournament>();

	public List<WchTournament> getWchTournaments() {
		return wchTournaments;
	}

	public void setWchTournaments(List<WchTournament> wchTournaments) {
		this.wchTournaments = wchTournaments;
	}

	public WchQualification _setWchTournaments(List<WchTournament> wchTournaments) {
		this.wchTournaments = wchTournaments;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
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

	public enum Association {
		wchTournaments
	}

	private Set<String> initAssociations = new HashSet<String>();

	public void setInit(Association... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (Association association : associations)
			initAssociations.add(association.name());
	}
	
	public WchQualification	_setInit(Association... associations) {
		setInit(associations);
		return this;
	}

	public void clearInit(Association... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (Association association : associations)
			initAssociations.remove(association.name());
	}

	public WchQualification _clearInit(Association... associations) {
		clearInit(associations);
		return this;
	}

	public void setInit(String... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (String association : associations)
			initAssociations.add(association);
	}

	public WchQualification _setInit(String... associations) {
		setInit(associations);
		return this;
	}

	public void clearInit(String... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (String association : associations)
			initAssociations.remove(association);
	}

	public WchQualification _clearInit(String... associations) {
		clearInit(associations);
		return this;
	}

	public Boolean toInit(String association) {
		if (association == null)
			throw new IllegalArgumentException();
		return initAssociations.contains(association);
	}

	public void clearAllInit() {
		initAssociations = new HashSet<String>();
	}

	public enum Attribute {
	}

	private Set<String> nullValues = new HashSet<String>();

	public void setNull(Attribute... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (Attribute attribute : attributes)
			nullValues.add(attribute.name());
	}

	public WchQualification _setNull(Attribute... attributes) {
		setNull(attributes);
		return this;
	}

	public void clearNull(Attribute... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (Attribute attribute : attributes)
			nullValues.remove(attribute.name());
	}

	public WchQualification _clearNull(Attribute... attributes) {
		clearNull(attributes);
		return this;
	}

	public void setNull(String... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (String attribute : attributes)
			nullValues.add(attribute);
	}

	public WchQualification _setNull(String... attributes) {
		setNull(attributes);
		return this;
	}

	public void clearNull(String... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (String attribute : attributes)
			nullValues.remove(attribute);
	}

	public WchQualification _clearNull(String... attributes) {
		clearNull(attributes);
		return this;
	}

	public Boolean isNull(String attrName) {
		if (attrName == null)
			throw new IllegalArgumentException();
		return nullValues.contains(attrName);
	}

	public Boolean isNull(Attribute attribute) {
		if (attribute == null)
			throw new IllegalArgumentException();
		return nullValues.contains(attribute.name());
	}

	public Boolean isDef(String attrName) {
		if (attrName == null)
			throw new IllegalArgumentException();
		if (nullValues.contains(attrName))
			return true;
		try {
			Object result = MethodUtils.invokeMethod(this, "get" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1, attrName.length()), null);
			return (result != null) ? true : false;
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		try {
			Object result = MethodUtils.invokeMethod(this, "is" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1, attrName.length()), null);
			return (result != null) ? true : false;
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public void clearAllNull() {
		nullValues = new HashSet<String>();
	}

	@Override
	public String toString() {
		return "WchQualification [ithfId=" + ithfId + ", id=" + id + ", lastUpdate=" + lastUpdate + ", name=" + name + "]";
	}

	public String toStringFull() {
		return "WchQualification [id=" + id + ", ithfId=" + ithfId + ", lastUpdate=" + lastUpdate + ", name=" + name + ", wchTournaments=" + wchTournaments + "]";
	}
}
