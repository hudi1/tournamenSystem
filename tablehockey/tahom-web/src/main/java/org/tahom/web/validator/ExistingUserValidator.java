package org.tahom.web.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.tahom.processor.service.user.UserService;
import org.tahom.repository.model.User;

public class ExistingUserValidator implements IValidator<String> {

	private UserService userService;
	private User user;

	private static final long serialVersionUID = 1L;

	public ExistingUserValidator(UserService userService, User user) {
		this.userService = userService;
		this.user = user;
	}

	@Override
	public void validate(IValidatable<String> validatable) {
		String username = validatable.getValue();
		if (username.equals(user.getUserName())) {
			return;
		}

		User user = userService.getUser(new User()._setUserName(username));
		if (user != null) {
			validatable.error(new ValidationError(this, "usernameError"));
		}
	}
}