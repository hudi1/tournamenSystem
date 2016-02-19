package org.tahom.web;

import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Season;
import org.tahom.repository.model.Tournament;
import org.tahom.web.components.PropertyPageableListView;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.link.TournamentAjaxLink;
import org.tahom.web.model.EvenOddReplaceModel;

public class PublicTournamentPage extends BasePage {

	private static final long serialVersionUID = 1L;

	private Season selectedSeason;
	private List<Season> seasons;

	public PublicTournamentPage() {
		seasons = seasonService.getAllSeasons();
		selectedSeason = selectSeason();
		createPage();
	}

	private Season selectSeason() {
		Season season = getTournamentSession().getSeason();
		if (season == null) {
			if (!seasons.isEmpty()) {
				season = seasons.get(0);
			}
		}
		return season;
	}

	protected void createPage() {
		add(new TournamentForm(Model.of(selectedSeason)));
	}

	private class TournamentForm extends Form<Season> {

		private static final long serialVersionUID = 1L;

		public TournamentForm(final IModel<Season> model) {
			super("tournamentForm", new CompoundPropertyModel<Season>(model));
			addSeasonLabel();
			addSeasonDropDownChoice(model);
			addTournamentListView();
		}

		private void addTournamentListView() {
			PropertyPageableListView<Tournament> listView;
			add(listView = new PropertyPageableListView<Tournament>("tournaments", ITEM_PER_PAGE) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<Tournament> listItem) {
					if (BooleanUtils.isFalse(listItem.getModelObject().getOpen())) {
						listItem.setVisible(false);
					}
					listItem.add(new Label("name"));

					listItem.add(new TournamentAjaxLink("enterTournament") {

						private static final long serialVersionUID = 1L;

						public void click(AjaxRequestTarget target) {
							getPageParameters().set(TID, listItem.getModelObject().getId());
							List<Groups> groups = groupService.getGroups(new Groups()._setTournament(new Tournament()
							        ._setId(listItem.getModelObject().getId())));
							if (!groups.isEmpty()) {
								getPageParameters().set(GID, groups.get(0).getId());
							}
							getPageParameters().set(SHOW_TOURNAMENT_OVERVIEW_PAGE_OPTIONS, 1);
							setResponsePage(TournamentOverviewPage.class, getPageParameters());
						}

					}.add(new Image("imgEnter", new SharedResourceReference("enter"))).add(
					        new AttributeModifier("title", new ResourceModel("enterTournament"))));

					listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
				}

			});
			add(new AjaxPagingNavigator("navigator", listView));
		}

		private void addSeasonDropDownChoice(final IModel<Season> model) {
			add(new DropDownChoice<Season>("seasons", model, seasons, new IChoiceRenderer<Season>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Object getDisplayValue(Season season) {
					return season.getName();
				}

				@Override
				public String getIdValue(Season season, int index) {
					if (season == null || season.getId() == null) {
						return null;
					} else {
						return season.getId().toString();
					}
				}

				@Override
				public Season getObject(String id, IModel<? extends List<? extends Season>> choices) {
					List<? extends Season> _choices = choices.getObject();
					for (int index = 0; index < _choices.size(); index++) {
						final Season choice = _choices.get(index);
						if (getIdValue(choice, index).equals(id)) {
							return choice;
						}
					}
					return null;
				}
			}).add(new AjaxFormComponentUpdatingBehavior("onchange") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					selectedSeason = model.getObject();
					getTournamentSession().setSeason(selectedSeason);
					target.add(TournamentForm.this);
				}
			}));
		}

		private void addSeasonLabel() {
			add(new ResourceLabel("season"));
		}
	}
}