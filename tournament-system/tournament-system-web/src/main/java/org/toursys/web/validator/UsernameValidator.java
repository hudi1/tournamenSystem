package org.toursys.web.validator;

import org.apache.wicket.validation.CompoundValidator;

public class UsernameValidator extends CompoundValidator<String> {

    private static final long serialVersionUID = 1L;

    public UsernameValidator() {

        // TODO
        // add(StringValidator.lengthBetween(5, 15));
        // add(new PatternValidator("[a-z0-9_-]+"));
        add(new ExistingUserValidator());
    }
}