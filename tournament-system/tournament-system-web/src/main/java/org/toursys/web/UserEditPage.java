package org.toursys.web;

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
        add(new UserForm(user));
    }

    private class UserForm extends Form<User> {

        private static final long serialVersionUID = 1L;

        public UserForm(final User user) {
            super("userEditForm", new CompoundPropertyModel<User>(user));
            setOutputMarkupId(true);
            add(new RequiredTextField<String>("name"));
            add(new TextField<String>("surname"));
            add(new EmailTextField("email"));
            add(new PasswordTextField("password"));

            TextField<String> userName = new TextField<String>("userName");
            TextField<Integer> platnost = new TextField<Integer>("platnost");
            TextField<String> role = new TextField<String>("role");

            Label userNameLabel = new Label("userNameLabel", new ResourceModel("userName"));
            Label platnostLabel = new Label("platnostLabel", new ResourceModel("platnost"));
            Label roleLabel = new Label("roleLabel", new ResourceModel("role"));

            add(userName);
            add(platnost);
            add(role);

            add(userNameLabel);
            add(platnostLabel);
            add(roleLabel);

            if (!showHideField) {
                platnost.setVisible(false);
                role.setVisible(false);

                platnostLabel.setVisible(false);
                roleLabel.setVisible(false);
            }

            if (!showUsername) {
                userName.setVisible(false);
                userNameLabel.setVisible(false);
            }

            add(new Button("submit", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    try {
                        if (user.getId() != null) {
                            tournamentService.updateUser(user);
                        } else {
                            tournamentService.createUser(user);
                        }
                    } catch (SqlProcessorException e) {
                        logger.error("Error edit user: ", e);
                        error(getString("sql.db.exception"));
                    }
                    setResponsePage(new UserPage());
                }
            });

            add(new Button("back") {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new UserPage());
                }
            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("editUser");
    }
}
