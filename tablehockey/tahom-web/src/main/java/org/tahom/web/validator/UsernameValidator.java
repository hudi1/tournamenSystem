package org.tahom.web.validator;

import org.apache.wicket.validation.CompoundValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.tahom.processor.service.user.UserService;
import org.tahom.repository.model.User;

public class UsernameValidator extends CompoundValidator<String> {

	private static final long serialVersionUID = 1L;

	public UsernameValidator(UserService userService, User user) {
		add(StringValidator.lengthBetween(3, 100));
		add(new ExistingUserValidator(userService, user));
	}

}