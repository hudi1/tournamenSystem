package org.tahom.web;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.tahom.repository.model.User;
import org.tahom.web.components.PropertyPageableListView;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentResourceButton;
import org.tahom.web.link.TournamentAjaxLink;

@AuthorizeInstantiation(Roles.ADMIN)
public class UserPage extends BasePage {

	private static final long serialVersionUID = 1L;

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
			add(new ResourceLabel("name"));
			addUserListView();
			addNewUserButton();
		}

		private void addUserListView() {
			PropertyPageableListView<User> listView;
			add(listView = new PropertyPageableListView<User>("users", userService.getAllUsers(), ITEM_PER_PAGE) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<User> item) {
					final User user = item.getModelObject();
					item.add(new Label("userName"));
					item.add(new TournamentAjaxLink("enterUser") {

						private static final long serialVersionUID = 1L;

						public void click(AjaxRequestTarget target) {
							setResponsePage(new UserEditPage(user, true));
						}

					}.add(new Image("imgEnter", new SharedResourceReference("enter"))).add(
					        new AttributeModifier("title", getString("enterUser"))));
				}
			});
			add(new AjaxPagingNavigator("navigator", listView));
		}

		private void addNewUserButton() {
			add(new TournamentResourceButton("newUser") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					setResponsePage(new UserEditPage(new User(), false, true));
				}
			});
		}
	}
}