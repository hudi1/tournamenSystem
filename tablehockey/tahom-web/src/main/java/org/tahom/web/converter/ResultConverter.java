package org.tahom.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.tahom.repository.model.Results;

public class ResultConverter extends AbstractConverter<Results> {
    private static final long serialVersionUID = 1L;

    /**
     * The singleton instance for a results converter
     */
    /** singleton instance */
    private static final ResultConverter INSTANCE = new ResultConverter();

    private ResultConverter() {
    }

    /**
     * Retrieves the singleton instance of <code>ResultValidator</code>.
     * 
     * @return the singleton instance of <code>ResultValidator</code>
     */
    public static ResultConverter getInstance() {
        return INSTANCE;
    }

    /**
     * @see org.apache.wicket.util.convert.IConverter#convertToObject(java.lang.String,Locale)
     */
    public Results convertToObject(final String value, final Locale locale) {
        try {
            return new Results(value);
        } catch (Exception e) {
            throw new ConversionException(e.getMessage()).setResourceKey("invalidResult");
        }

    }

    /**
     * @see org.apache.wicket.util.convert.converter.AbstractConverter#getTargetType()
     */
    @Override
    protected Class<Results> getTargetType() {
        return Results.class;
    }
}