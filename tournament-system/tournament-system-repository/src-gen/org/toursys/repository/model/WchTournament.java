package org.toursys.repository.model;
	
import java.util.Date;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.MethodUtils;

public class WchTournament implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final int ORDER_BY_ID = 1;
	public static final int ORDER_BY_WCH_QUALIFICATION = 2;
	
	public WchTournament() {
	}
		
	public WchTournament(WchQualification wchQualification, Date date) {
		this.wchQualification = wchQualification;
		this.date = date;
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WchTournament _setId(Integer id) {
		this.id = id;
		return this;
	}

	private WchQualification wchQualification;

	public WchQualification getWchQualification() {
		return wchQualification;
	}

	public void setWchQualification(WchQualification wchQualification) {
		this.wchQualification = wchQualification;
	}

	public WchTournament _setWchQualification(WchQualification wchQualification) {
		this.wchQualification = wchQualification;
		return this;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WchTournament _setName(String name) {
		this.name = name;
		return this;
	}

	private String series;

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public WchTournament _setSeries(String series) {
		this.series = series;
		return this;
	}

	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public WchTournament _setDate(Date date) {
		this.date = date;
		return this;
	}

	private Integer points;

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public WchTournament _setPoints(Integer points) {
		this.points = points;
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
		WchTournament other = (WchTournament) obj;
		if (id == null || !id.equals(other.id))
		return false;
		return true;
	}	

	public enum Association {
		wchQualification
	}

	private Set<String> initAssociations = new HashSet<String>();

	public void setInit(Association... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (Association association : associations)
			initAssociations.add(association.name());
	}
	
	public WchTournament	_setInit(Association... associations) {
		setInit(associations);
		return this;
	}

	public void clearInit(Association... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (Association association : associations)
			initAssociations.remove(association.name());
	}

	public WchTournament _clearInit(Association... associations) {
		clearInit(associations);
		return this;
	}

	public void setInit(String... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (String association : associations)
			initAssociations.add(association);
	}

	public WchTournament _setInit(String... associations) {
		setInit(associations);
		return this;
	}

	public void clearInit(String... associations) {
		if (associations == null)
			throw new IllegalArgumentException();
		for (String association : associations)
			initAssociations.remove(association);
	}

	public WchTournament _clearInit(String... associations) {
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
		series, name, points
	}

	private Set<String> nullValues = new HashSet<String>();

	public void setNull(Attribute... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (Attribute attribute : attributes)
			nullValues.add(attribute.name());
	}

	public WchTournament _setNull(Attribute... attributes) {
		setNull(attributes);
		return this;
	}

	public void clearNull(Attribute... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (Attribute attribute : attributes)
			nullValues.remove(attribute.name());
	}

	public WchTournament _clearNull(Attribute... attributes) {
		clearNull(attributes);
		return this;
	}

	public void setNull(String... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (String attribute : attributes)
			nullValues.add(attribute);
	}

	public WchTournament _setNull(String... attributes) {
		setNull(attributes);
		return this;
	}

	public void clearNull(String... attributes) {
		if (attributes == null)
			throw new IllegalArgumentException();
		for (String attribute : attributes)
			nullValues.remove(attribute);
	}

	public WchTournament _clearNull(String... attributes) {
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
		return "WchTournament [id=" + id + ", series=" + series + ", name=" + name + ", points=" + points + ", date=" + date + "]";
	}

	public String toStringFull() {
		return "WchTournament [id=" + id + ", wchQualification=" + wchQualification + ", name=" + name + ", series=" + series + ", date=" + date + ", points=" + points + "]";
	}
}
