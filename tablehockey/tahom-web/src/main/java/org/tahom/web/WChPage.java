package org.tahom.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.tahom.processor.service.wch.season.WCh2015Season;
import org.tahom.processor.service.wch.season.WCh2017Season;
import org.tahom.repository.model.User;
import org.tahom.repository.model.wch.WChSeason;
import org.tahom.repository.model.wch.WChTableRecord;
import org.tahom.repository.model.wch.WChTournamentDetailsRecord;
import org.tahom.repository.model.wch.season.WChRecord;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentResourceButton;
import org.tahom.web.model.EvenOddReplaceModel;

public class WChPage extends BasePage {

	private static final long serialVersionUID = 1L;

	private WChTableRecord selectedTableRecord;
	private WChSeason selectedSeason;
	private User user;
	private static List<WChSeason> WCH_SEASONS = new ArrayList<WChSeason>();

	static {
		// TODO zistit ci je mozno pridat vsechny instance WChSeason z package org.toursys.processor.service.wch.season
		WCH_SEASONS.add(WCh2017Season.getInstance());
		WCH_SEASONS.add(WCh2015Season.getInstance());
	}

	public WChPage() {
		this(new PageParameters());
	}

	public WChPage(PageParameters parameters) {
		super(parameters);
		this.user = getTournamentSession().getUser();
		selectedSeason = WCh2017Season.getInstance();
		selectedTableRecord = wChService.getWchRecords(selectedSeason);
		createPage();
	}

	protected void createPage() {
		add(new WChForm(Model.of(selectedTableRecord)));
	}

	private class WChForm extends Form<WChTableRecord> {

		private static final long serialVersionUID = 1L;

		public WChForm(final IModel<WChTableRecord> model) {
			super("wchForm", new CompoundPropertyModel<WChTableRecord>(model));

			addWChTable();
			addWChButton();
			addSeasonDropDownChoice(Model.of(selectedSeason));
			addSeasonLabel();
		}

		private void addSeasonLabel() {
			add(new ResourceLabel("season"));
		}

		private void addSeasonDropDownChoice(final IModel<WChSeason> model) {
			add(new DropDownChoice<WChSeason>("seasons", model, WCH_SEASONS, new IChoiceRenderer<WChSeason>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Object getDisplayValue(WChSeason season) {
					return season.getLabelName();
				}

				@Override
				public String getIdValue(WChSeason season, int index) {
					return season.getLabelName();
				}

				@Override
				public WChSeason getObject(String id, IModel<? extends List<? extends WChSeason>> choices) {
					List<? extends WChSeason> _choices = choices.getObject();
					for (int index = 0; index < _choices.size(); index++) {
						final WChSeason choice = _choices.get(index);
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
					WChTableRecord actualRecord = wChService.getWchRecords(selectedSeason);
					selectedTableRecord = actualRecord;
					target.add(WChForm.this);
				}
			}));
		}

		private void addWChButton() {
			addUpdateButton();
		}

		private void addUpdateButton() {
			add(new TournamentResourceButton("update") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					wChService.updateWch(user);
					getSession().info(getString("updateWChInfo"));
					setResponsePage(WChPage.class, getPageParameters());
				}

				@Override
				public boolean isVisible() {
					return user != null;
				}
			});
		}

		private void addWChTable() {
			addGWChSeasonTableHeader();
			addGWChTournamentTableHeader();
			addWChRecordsGroupListView();
		}

		private void addGWChTournamentTableHeader() {
			ListView<String> seasonList = new PropertyListView<String>("wchTournamentHeaders") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<String> listItem) {
					listItem.add(new Label("tournament", listItem.getModelObject()));
				}
			};
			add(seasonList);
		}

		private void addGWChSeasonTableHeader() {
			add(new ResourceLabel("name"));
			add(new ResourceLabel("rank"));
			add(new ResourceLabel("total"));

			final int colspan = getColspan();
			ListView<String> tournamentList = new PropertyListView<String>("wchSeasonHeaders") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<String> listItem) {
					listItem.add(new Label("season", listItem.getModelObject()).add(new AttributeModifier("colspan",
					        colspan)));
				}
			};
			add(tournamentList);
		}

		private void addWChRecordsGroupListView() {
			add(new PropertyListView<WChRecord>("records") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<WChRecord> listItem) {
					listItem.add(new Label("index", listItem.getIndex() + 1 + ""));
					listItem.add(new Label("playerName"));

					ListView<WChTournamentDetailsRecord> seasonList = new PropertyListView<WChTournamentDetailsRecord>(
					        "seasonRecords") {

						private static final long serialVersionUID = 1L;

						@Override
						protected void populateItem(ListItem<WChTournamentDetailsRecord> listItem) {
							final WChTournamentDetailsRecord record = listItem.getModelObject();
							listItem.add(new Label("points").add(new AttributeModifier("title", record.getTournament())));
						}
					};
					listItem.add(seasonList);
					listItem.add(new Label("totalPoints"));
					listItem.add(new Label("rank", listItem.getIndex() + 1 + ""));
					listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
				}
			});
		}

		private int getColspan() {
			return selectedTableRecord.getWchTournamentHeaders().size()
			        / Math.max(1, selectedTableRecord.getWchSeasonHeaders().size());
		}
	}
}