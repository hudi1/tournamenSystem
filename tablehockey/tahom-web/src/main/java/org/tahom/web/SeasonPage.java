package org.tahom.web;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.tahom.repository.model.Season;
import org.tahom.repository.model.User;
import org.tahom.web.components.PropertyPageableListView;
import org.tahom.web.components.TournamentAjaxEditableLabel;
import org.tahom.web.components.TournamentAjaxResourceButton;
import org.tahom.web.link.TournamentAjaxLink;
import org.tahom.web.model.EvenOddReplaceModel;

@AuthorizeInstantiation(Roles.USER)
public class SeasonPage extends BasePage {

	private static final long serialVersionUID = 1L;
	private User user;

	public SeasonPage() {
		this.user = getTournamentSession().getUser();
		createPage();
	}

	protected void createPage() {
		add(new SeasonForm(user));
	}

	private class SeasonForm extends Form<User> {

		private static final long serialVersionUID = 1L;

		public SeasonForm(User user) {
			super("seasonForm", new CompoundPropertyModel<User>(user));
			addSeasonListView();
			addSeasonAddButton();
		}

		private void addSeasonListView() {
			PropertyPageableListView<Season> listView;
			add(listView = new PropertyPageableListView<Season>("seasons", ITEM_PER_PAGE) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<Season> listItem) {
					listItem.add(new TournamentAjaxEditableLabel("name") {

						private static final long serialVersionUID = 1L;

						public void submit(AjaxRequestTarget target) {
							seasonService.updateSeason(listItem.getModelObject());
							getFeedbackMessages().clear();
							info(getString("updateSeasonInfo"));
							target.add(feedbackPanel);
						};

					});
					listItem.add(new TournamentAjaxLink("deleteSeason", getString("deleteSeasonQuestion")) {

						private static final long serialVersionUID = 1L;

						public void click(AjaxRequestTarget target) {
							Season season = listItem.getModelObject();
							seasonService.deleteSeason(season);
							SeasonForm.this.getModelObject().getSeasons().remove(season);
							getFeedbackMessages().clear();
							info(getString("deleteSeasonInfo"));
							target.add(feedbackPanel);
							target.add(SeasonForm.this);
						}

					}.add(new Image("img", new SharedResourceReference("delete"))).add(
					        new AttributeModifier("title", new ResourceModel("deleteSeason"))));

					listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
				}

			});
			add(new AjaxPagingNavigator("navigator", listView));
		}

		private void addSeasonAddButton() {
			add(new TournamentAjaxResourceButton("addSeason") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit(AjaxRequestTarget target, Form<?> form) {
					Season season = new Season();
					season.setName(getString("enterName"));
					seasonService.createSeason(user, season);
					SeasonForm.this.getModelObject().getSeasons().add(season);
					getFeedbackMessages().clear();
					info(getString("addSeasonInfo"));
					target.add(feedbackPanel);
					target.add(SeasonForm.this);
				}
			});
		}
	}
}