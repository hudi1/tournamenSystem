package org.toursys.web;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.toursys.repository.model.User;

@AuthorizeInstantiation(Roles.ADMIN)
public class UserPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private static final int ITEMS_PER_PAGE = 10;

    public UserPage() {
        createPage();
    }

    protected void createPage() {
        IDataProvider<User> userDataProvider = createUserProvider();
        DataView<User> dataView = createDataview(userDataProvider);
        add(dataView);
        add(new PagingNavigator("navigator", dataView));
        add(new UserForm());
    }

    private DataView<User> createDataview(IDataProvider<User> userDataProvider) {
        DataView<User> dataView = new DataView<User>("rows", userDataProvider, ITEMS_PER_PAGE) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<User> listItem) {
                final User user = listItem.getModelObject();
                listItem.setModel(new CompoundPropertyModel<User>(user));
                listItem.add(new Label("userName"));
                // TODO zisti preco to funguje aj bez clone
                listItem.add(new EditUserForm(((User) listItem.getDefaultModelObject())));
            }
        };
        return dataView;
    }

    private IDataProvider<User> createUserProvider() {
        IDataProvider<User> userDataProvider = new IDataProvider<User>() {

            private static final long serialVersionUID = 1L;
            private List<User> users = tournamentService.getAllUsers();

            @Override
            public Iterator<User> iterator(int first, int count) {
                return users.subList(first, first + count).iterator();
            }

            @Override
            public int size() {
                return users.size();
            }

            @Override
            public IModel<User> model(final User object) {
                return new LoadableDetachableModel<User>() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    protected User load() {
                        return object;
                    }
                };
            }

            @Override
            public void detach() {
            }
        };

        return userDataProvider;
    }

    private class UserForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public UserForm() {
            super("userForm");
            add(new Button("newUser", new ResourceModel("newUser")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new UserEditPage(new User()));
                }
            });
        }
    }

    private class EditUserForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public EditUserForm(final User user) {
            super("editUserForm");
            add(new Button("editUser", new ResourceModel("editUser")) {

                private static final long serialVersionUID = 1L;

                private void edit() {
                    setResponsePage(new UserEditPage(user));
                }

                @Override
                public void onSubmit() {
                    edit();
                }

            });
        }
    }

    @Override
    protected void setVisibility() {
        if (!((TournamentAuthenticatedWebSession) getSession()).getRoles().contains(Roles.ADMIN)) {
            setVisible(false);
        }
    };

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("selectUser");
    }

}
