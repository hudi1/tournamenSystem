package org.tahom.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.tahom.repository.model.Surname;

public class SurnameConverter extends AbstractConverter<Surname> {
	private static final long serialVersionUID = 1L;

	/**
	 * The singleton instance for a results converter
	 */
	/** singleton instance */
	private static final SurnameConverter INSTANCE = new SurnameConverter();

	private SurnameConverter() {
	}

	/**
	 * Retrieves the singleton instance of <code>ResultValidator</code>.
	 * 
	 * @return the singleton instance of <code>ResultValidator</code>
	 */
	public static SurnameConverter getInstance() {
		return INSTANCE;
	}

	/**
	 * @see org.apache.wicket.util.convert.IConverter#convertToObject(java.lang.String,Locale)
	 */
	public Surname convertToObject(final String value, final Locale locale) {
		if (value == null || value.isEmpty()) {
			return null;
		} else {
			return new Surname(value);
		}
	}

	/**
	 * @see org.apache.wicket.util.convert.converter.AbstractConverter#getTargetType()
	 */
	@Override
	protected Class<Surname> getTargetType() {
		return Surname.class;
	}
}