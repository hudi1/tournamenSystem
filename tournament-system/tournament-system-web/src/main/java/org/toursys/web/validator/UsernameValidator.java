package org.toursys.web.validator;

import org.apache.wicket.validation.CompoundValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class UsernameValidator extends CompoundValidator<String> {

    private static final long serialVersionUID = 1L;

    public UsernameValidator() {

        add(StringValidator.lengthBetween(3, 100));
        add(new ExistingUserValidator());
    }
}