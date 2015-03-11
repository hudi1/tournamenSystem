package org.toursys.web;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.toursys.repository.model.User;
import org.toursys.web.components.PropertyPageableListView;

@AuthorizeInstantiation(Roles.ADMIN)
public class UserPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private static final int ITEMS_PER_PAGE = 10;

    public UserPage() {
        createPage();
    }

    protected void createPage() {
        add(new UserForm());
    }

    private class UserForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public UserForm() {
            super("userForm");

            add(new Label("name", new ResourceModel("name")));
            addUserListView();
            addNewUserButton();

            /*
             * IDataProvider<User> userDataProvider = createUserProvider(); DataView<User> dataView =
             * createDataview(userDataProvider); add(dataView); add(new PagingNavigator("navigator", dataView));
             */

        }

        private void addUserListView() {
            PropertyPageableListView<User> listView;
            add(listView = new PropertyPageableListView<User>("users", userService.getAllUsers(), ITEMS_PER_PAGE) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(ListItem<User> item) {
                    final User user = item.getModelObject();
                    item.setModel(new CompoundPropertyModel<User>(user));
                    item.add(new Label("userName"));
                    item.add(new AjaxLink<Void>("enterUser") {

                        private static final long serialVersionUID = 1L;

                        public void onClick(AjaxRequestTarget target) {
                            setResponsePage(new UserEditPage(user));
                        }

                    }.add(new Image("imgEnter", new SharedResourceReference("enter"))).add(
                            AttributeModifier.replace("title", new AbstractReadOnlyModel<String>() {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public String getObject() {
                                    return getString("enterUser");
                                }
                            })));

                }
            });
            AjaxPagingNavigator navigator = new AjaxPagingNavigator("navigator", listView);
            add(navigator);
        }

        private void addNewUserButton() {
            add(new Button("newUser", new ResourceModel("newUser")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new UserEditPage(new User()));
                }
            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("selectUser");
    }

}
