package org.toursys.web.validator;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;
import org.toursys.processor.service.user.UserService;
import org.toursys.repository.model.User;

public class ExistingEmailValidator extends AbstractValidator<String> {

    @SpringBean(name = "userService")
    protected UserService userService;

    private static final long serialVersionUID = 1L;

    @Override
    protected void onValidate(IValidatable<String> validatable) {
        String email = validatable.getValue();
        User user = userService.getUser(new User()._setEmail(email));
        if (user != null) {
            error(validatable);
        }

    }

    @Override
    protected String resourceKey() {
        return "emailError";
    }

}
