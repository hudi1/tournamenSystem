package org.toursys.web;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.sqlproc.engine.SqlProcessorException;
import org.toursys.repository.model.User;
import org.toursys.repository.model.UserImpl;
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
            setOutputMarkupId(true);

            // TODO dynamicka validacia policka nie len po submite

            add(new RequiredTextField<String>("name"));
            add(new RequiredTextField<String>("surname"));
            add(new PasswordTextField("password"));
            add(new PasswordTextField("confirmPassword"));

            TextField<Integer> platnost = new TextField<Integer>("platnost");
            TextField<String> role = new TextField<String>("role");
            final TextField<String> username = new TextField<String>("userName");
            final EmailTextField email = new EmailTextField("email");

            username.add(new UsernameValidator());
            email.add(new ExistingEmailValidator());

            Label usernameLabel = new Label("userNameLabel", new ResourceModel("userName"));
            Label platnostLabel = new Label("platnostLabel", new ResourceModel("platnost"));
            Label roleLabel = new Label("roleLabel", new ResourceModel("role"));

            add(username);
            add(email);
            add(platnost);
            add(role);

            add(usernameLabel);
            add(platnostLabel);
            add(roleLabel);

            if (!showHideField) {
                platnost.setVisible(false);
                role.setVisible(false);

                platnostLabel.setVisible(false);
                roleLabel.setVisible(false);
            }

            if (!showUsername) {
                username.setVisible(false);
                usernameLabel.setVisible(false);
            }

            add(new Button("submit", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    try {

                        if (!user.getPassword().equals(user.getConfirmPassword())) {
                            error(getString("notSamePassword"));
                            return;
                        }

                        if (user.getId() != null) {
                            userService.updateUser(user);
                            setResponsePage(new UserPage());
                        } else {
                            userService.createUser(user);
                            setResponsePage(new HomePage());
                        }
                    } catch (SqlProcessorException e) {
                        logger.error("Error edit user: ", e);
                        error(getString("sql.db.exception"));
                        return;
                    }
                }

            });

            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new UserPage());
                }
            }.setDefaultFormProcessing(false));
        }
    }

    private static class HighliteTextField<T> extends TextField<T> {
        private static final long serialVersionUID = 1L;

        private boolean highlite = false;

        public void toggleOff() {
            highlite = false;
        }

        public void toggleOn() {
            highlite = true;
        }

        public HighliteTextField(String id) {
            super(id);
            add(new AttributeModifier("style", "border:2px solid #ff0000;") {
                private static final long serialVersionUID = 1L;

                @Override
                public boolean isEnabled(Component component) {
                    return HighliteTextField.this.highlite;
                }
            });
        }
    }

    private boolean isExistingUsername(String username) {
        return userService.getUser(new User()._setUserName(username)) != null;
    }

    private boolean isExistingEmail(String email) {
        return userService.getUser(new User()._setEmail(email)) != null;
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("editUser");
    }
}
