package org.tahom.repository.model;

public class Surname {

	private String value;
	private String discriminant;

	public Surname(String value) {
		this(value, null);
	}

	public Surname(String value, String discriminant) {
		this.value = value;
		this.discriminant = discriminant;
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
	public String toString() {
		if (discriminant == null) {
			return value;
		} else {
			return value + " " + discriminant;
		}
	}

}
