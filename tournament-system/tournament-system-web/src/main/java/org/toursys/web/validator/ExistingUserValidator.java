package org.toursys.web.validator;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;
import org.toursys.processor.service.user.UserService;
import org.toursys.repository.model.User;

public class ExistingUserValidator extends AbstractValidator<String> {

    @SpringBean(name = "userService")
    protected UserService userService;

    private static final long serialVersionUID = 1L;

    @Override
    protected void onValidate(IValidatable<String> validatable) {
        String username = validatable.getValue();
        User user = userService.getUser(new User()._setUserName(username));
        if (user != null) {
            error(validatable);
        }

    }

    @Override
    protected String resourceKey() {
        return "usernameError";
    }

}
