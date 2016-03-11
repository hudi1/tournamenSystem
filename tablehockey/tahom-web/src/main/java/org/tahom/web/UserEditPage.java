package org.tahom.web;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.value.ValueMap;
import org.tahom.processor.util.TournamentUtil;
import org.tahom.repository.model.User;
import org.tahom.repository.model.UserImpl;
import org.tahom.repository.model.UserRole;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentBackResourceButton;
import org.tahom.web.components.TournamentResourceButton;
import org.tahom.web.validator.ExistingEmailValidator;
import org.tahom.web.validator.UsernameValidator;

public class UserEditPage extends BasePage {

	private static final long serialVersionUID = 1L;
	private boolean showHideField;
	private boolean showUsername;

	public UserEditPage() {
		throw new RestartResponseAtInterceptPageException(new UserPage());
	}

	public UserEditPage(User user, boolean showHideField) {
		this(user, showHideField, false);
	}

	public UserEditPage(User user, boolean showHideField, boolean showUsername) {
		this.showHideField = showHideField;
		this.showUsername = showUsername;
		createPage(user);
	}

	private void createPage(User user) {
		add(new UserForm(new UserImpl(user)));
	}

	private class UserForm extends Form<User> {

		private static final long serialVersionUID = 1L;

		public UserForm(final UserImpl user) {
			super("userEditForm", new CompoundPropertyModel<User>(user));

			// TODO dynamicka validacia policka nie len po submite
			addLegend(user);
			addName();
			addSurname();
			addUsername(user);
			addPassword(user);
			addEmail(user);
			addValidity();
			addAuthorization();
			addBackButton();
			addSaveButton(user);
		}

		private void addBackButton() {
			add(new TournamentBackResourceButton("back") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					setResponsePage(new UserPage());
				}
			});
		}

		private void addSaveButton(final User user) {
			add(new TournamentResourceButton("submit") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					if (user.getId() != null) {
						userService.updateUser(user);
						getSession().info(getString("updateUserEditInfo"));
						getTournamentSession().setUpdatedUser(user);
						setResponsePage(new UserEditPage(user, showHideField, showUsername));
					} else {
						userService.createUser(user);
						getSession().info(getString("addUserEditInfo"));
						setResponsePage(LoginPage.class);
					}
				}
			});
		}

		private void addAuthorization() {
			add(new Label("roleLabel", new ResourceModel("authorization")).setVisible(showHideField));
			add(new DropDownChoice<UserRole>("role", Arrays.asList(new UserRole[] { UserRole.ADMIN, UserRole.USER }))
			        .setVisible(showHideField));
		}

		private void addValidity() {
			add(new ResourceLabel("validityLabel").setVisible(showHideField));
			add(new TextField<Integer>("validity").setVisible(showHideField));
		}

		private void addEmail(User user) {
			add(new ResourceLabel("emailLabel").setVisible(!showHideField));
			add(new EmailTextField("email").setRequired(true).add(new ExistingEmailValidator(userService, user))
			        .setVisible(!showHideField));
		}

		private void addPassword(UserImpl user) {
			PasswordTextField passwordInput;
			add(new ResourceLabel("passwordLabel").setVisible(!showHideField));
			add(passwordInput = new PasswordTextField("password") {

				private static final long serialVersionUID = 1L;

				@Override
				public String getInput() {
					String input = super.getInput();
					if (StringUtils.isEmpty(input)) {
						return input;
					} else {
						return TournamentUtil.encryptUserPassword(super.getInput());
					}
				}
			});
			// passwordInput.add(StringValidator.minimumLength(6));

			PasswordTextField confirmPasswordInput;
			add(new ResourceLabel("confirmPasswordLabel").setVisible(!showHideField));
			add(confirmPasswordInput = new PasswordTextField("confirmPassword") {

				private static final long serialVersionUID = 1L;

				@Override
				public String getInput() {
					String input = super.getInput();
					if (StringUtils.isEmpty(input)) {
						return input;
					} else {
						return TournamentUtil.encryptUserPassword(super.getInput());
					}
				}
			});

			add(new EqualPasswordInputValidator(passwordInput, confirmPasswordInput));

			if (showHideField) {
				passwordInput.setVisible(false);
				passwordInput.setRequired(false);
				confirmPasswordInput.setVisible(false);
				confirmPasswordInput.setRequired(false);
			}

			if (user.getId() != null) {
				passwordInput.setRequired(false);
				confirmPasswordInput.setRequired(false);
			}
		}

		private void addSurname() {
			add(new ResourceLabel("surnameLabel"));
			add(new TextField<String>("surname"));
		}

		private void addName() {
			add(new ResourceLabel("nameLabel"));
			add(new TextField<String>("name"));
		}

		private void addLegend(User user) {
			ValueMap map = new ValueMap();
			if (user != null && user.getUserName() != null) {
				map.put("userLegend", user.getUserName());
			} else {
				map.put("userLegend", "");
			}

			add(new Label("userLegend", new StringResourceModel("userLegend", new Model<ValueMap>(map))));
		}

		private void addUsername(User user) {
			RequiredTextField<String> usernameInput;
			Label username;
			add(username = new Label("userNameLabel", new ResourceModel("username")));
			add(usernameInput = new RequiredTextField<String>("userName"));
			usernameInput.add(new UsernameValidator(userService, user));

			if (!showUsername) {
				username.setVisible(false);
				usernameInput.setVisible(false);
			}

			if (showUsername && showHideField) {
				usernameInput.setEnabled(false);
			}
		}
	}
}