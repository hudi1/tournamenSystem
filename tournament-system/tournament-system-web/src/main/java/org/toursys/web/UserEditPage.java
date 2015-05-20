package org.toursys.web;

import java.util.Arrays;

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
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.StringValidator;
import org.sqlproc.engine.SqlProcessorException;
import org.toursys.repository.model.User;
import org.toursys.repository.model.UserImpl;
import org.toursys.repository.model.UserRole;
import org.toursys.web.components.TournamentButton;
import org.toursys.web.validator.ExistingEmailValidator;
import org.toursys.web.validator.UsernameValidator;

public class UserEditPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private boolean showHideField;
    private boolean showUsername;

    public UserEditPage() {
        throw new RestartResponseAtInterceptPageException(new UserPage());
    }

    public UserEditPage(User user) {
        this(user, true, true);
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
            addLegend();
            addName(user);
            addSurname(user);
            addUsername(user);
            addPassword(user);
            addEmail(user);
            addValidity(user);
            addAuthorization(user);
            addSaveButton(user);
            addBackButton();
        }

        private void addBackButton() {
            add(new TournamentButton("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    setResponsePage(new UserPage());
                }
            }.setDefaultFormProcessing(false));
        }

        private void addSaveButton(final User user) {
            add(new TournamentButton("submit", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    if (user.getId() != null) {
                        userService.updateUser(user);
                        setResponsePage(new UserPage());
                    } else {
                        userService.createUser(user);
                        setResponsePage(new HomePage());
                    }
                }
            });
        }

        private void addAuthorization(User user) {
            final Label authorization = new Label("authorization", new ResourceModel("authorization"));
            final DropDownChoice<UserRole> authorizationInput = new DropDownChoice<UserRole>("authorizationInput",
                    new PropertyModel<UserRole>(user, "role"), Arrays.asList(new UserRole[] { UserRole.ADMIN,
                            UserRole.USER }));
            add(authorization);
            add(authorizationInput);

            if (!showHideField) {
                authorization.setVisible(false);
                authorizationInput.setVisible(false);
            }
        }

        private void addValidity(User user) {
            final Label validity = new Label("validity", new ResourceModel("validity"));
            final TextField<Integer> validityInput = new TextField<Integer>("validityInput",
                    new PropertyModel<Integer>(user, "platnost"));
            validityInput.setRequired(true);
            add(validity);
            add(validityInput);

            if (!showHideField) {
                validity.setVisible(false);
                validityInput.setVisible(false);
            }
        }

        private void addEmail(User user) {
            add(new Label("email", new ResourceModel("email")));
            final EmailTextField email = new EmailTextField("emailInput", new PropertyModel<String>(user, "email"));
            email.setRequired(true);
            email.add(new ExistingEmailValidator());
            add(email);
        }

        private void addPassword(User user) {
            PasswordTextField password;
            add(new Label("password", new ResourceModel("password")));
            add(password = new PasswordTextField("passwordInput", new PropertyModel<String>(user, "password")));
            password.add(StringValidator.minimumLength(6));

            PasswordTextField confirmPassword;
            add(new Label("confirmPassword", new ResourceModel("confirmPassword")));
            add(confirmPassword = new PasswordTextField("confirmPasswordInput", new PropertyModel<String>(user,
                    "confirmPassword")));

            add(new EqualPasswordInputValidator(password, confirmPassword));
        }

        private void addSurname(User user) {
            add(new Label("surname", new ResourceModel("surname")));
            add(new TextField<String>("surnameInput", new PropertyModel<String>(user, "surname")));
        }

        private void addName(User user) {
            add(new Label("name", new ResourceModel("name")));
            add(new TextField<String>("nameInput", new PropertyModel<String>(user, "name")));
        }

        private void addLegend() {
            add(new Label("user", new ResourceModel("user")));
        }

        private void addUsername(User user) {
            RequiredTextField<String> usernameInput;
            Label username;
            add(username = new Label("username", new ResourceModel("username")));
            add(usernameInput = new RequiredTextField<String>("usernameInput", new PropertyModel<String>(user,
                    "userName")));
            usernameInput.add(new UsernameValidator());

            if (!showUsername) {
                username.setVisible(false);
                usernameInput.setVisible(false);
            }
        }
    }

}
