package org.tahom.repository.model.impl;

import java.io.Serializable;

public class Surname implements Serializable {

	private static final long serialVersionUID = 1L;

	private String value;
	private String discriminant;

	public Surname(String value) {
		value = value.trim();
		if (value.contains(" ")) {
			this.value = value.split(" ")[0];
			this.discriminant = value.split(" ")[1];
		} else {
			this.value = value;
			this.discriminant = "";
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDiscriminant() {
		return discriminant;
	}

	public void setDiscriminant(String discriminant) {
		this.discriminant = discriminant;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Surname other = (Surname) obj;
		if (discriminant == null) {
			if (other.discriminant != null)
				return false;
		} else if (!discriminant.equals(other.discriminant))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (discriminant == null || discriminant.isEmpty()) {
			return value;
		} else {
			return value + " " + discriminant;
		}
	}

}
