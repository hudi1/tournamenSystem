package org.toursys.web;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
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

    public UserEditPage() {
        throw new RestartResponseAtInterceptPageException(new UserPage());
    }

    public UserEditPage(User user) {
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
            add(new TextField<String>("userName"));
            add(new EmailTextField("email"));
            add(new TextField<Integer>("platnost"));
            add(new TextField<String>("role"));
            add(new PasswordTextField("password"));

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

            add(new AjaxLink<Void>("back") {

                private static final long serialVersionUID = 1L;

                @Override
                public void onClick(AjaxRequestTarget target) {
                    target.appendJavaScript(PREVISOUS_PAGE);
                }
            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("editUser");
    }
}
