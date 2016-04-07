package org.tahom.web;

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
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.tahom.processor.service.event.dto.EventDetailRecordDto;
import org.tahom.processor.service.event.dto.EventRecordDto;
import org.tahom.processor.service.event.dto.EventTableRecordDto;
import org.tahom.repository.model.User;
import org.tahom.repository.model.event.EventSeason;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentResourceButton;
import org.tahom.web.model.EvenOddReplaceModel;
import org.tahom.web.model.ItalicBoldReplaceModel;

public abstract class EventPage<C extends IRequestablePage> extends BasePage {

	private static final long serialVersionUID = 1L;

	private EventTableRecordDto selectedTableRecord;
	private EventSeason selectedSeason;
	private User user;

	public EventPage() {
		this(new PageParameters());
	}

	public EventPage(PageParameters parameters) {
		super(parameters);
		this.user = getTournamentSession().getUser();
		selectedSeason = getEvents().get(0);
		selectedTableRecord = eventService.getEventRecords(selectedSeason);
		createPage();
	}

	public abstract List<EventSeason> getEvents();

	protected abstract Class<C> getEventPage();

	protected void createPage() {
		add(new EventForm(Model.of(selectedTableRecord)));
	}

	private class EventForm extends Form<EventTableRecordDto> {

		private static final long serialVersionUID = 1L;

		public EventForm(final IModel<EventTableRecordDto> model) {
			super("eventForm", new CompoundPropertyModel<EventTableRecordDto>(Model.of(selectedTableRecord)));

			addEventTable();
			addEventButton();
			addSeasonDropDownChoice(Model.of(selectedSeason));
			addSeasonLabel();
		}

		void addSeasonLabel() {
			add(new ResourceLabel("season"));
		}

		private void addSeasonDropDownChoice(final IModel<EventSeason> model) {
			add(new DropDownChoice<EventSeason>("seasons", model, getEvents(), new IChoiceRenderer<EventSeason>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Object getDisplayValue(EventSeason season) {
					return season.getLabelName();
				}

				@Override
				public String getIdValue(EventSeason season, int index) {
					return season.getLabelName();
				}

				@Override
				public EventSeason getObject(String id, IModel<? extends List<? extends EventSeason>> choices) {
					List<? extends EventSeason> _choices = choices.getObject();
					for (int index = 0; index < _choices.size(); index++) {
						final EventSeason choice = _choices.get(index);
						if (getIdValue(choice, index).equals(id)) {
							return choice;
						}
					}
					return null;
				}
			}).add(new AjaxFormComponentUpdatingBehavior("change") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					selectedSeason = model.getObject();
					EventTableRecordDto actualRecord = eventService.getEventRecords(selectedSeason);
					EventForm.this.setModelObject(actualRecord);
					target.add(EventForm.this);
				}
			}));
		}

		private void addEventButton() {
			addUpdateButton();
		}

		private void addUpdateButton() {
			add(new TournamentResourceButton("update") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					ithfService.updateIthfTournaments(user, selectedSeason);
					getSession().info(getString("updateEventInfo"));
					setResponsePage(getEventPage(), getPageParameters());
				}

				@Override
				public boolean isVisible() {
					return user != null;
				}
			});
		}

		private void addEventTable() {
			addEventSeasonTableHeader();
			addEventTournamentTableHeader();
			addEventRecordsGroupListView();
		}

		private void addEventTournamentTableHeader() {
			ListView<String> seasonList = new PropertyListView<String>("eventTournamentHeaders") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<String> listItem) {
					listItem.add(new Label("tournament", listItem.getModelObject()));
				}
			};
			add(seasonList);
		}

		private void addEventSeasonTableHeader() {
			add(new ResourceLabel("name"));
			add(new ResourceLabel("rank"));
			add(new ResourceLabel("total"));

			final int colspan = getColspan();
			ListView<String> tournamentList = new PropertyListView<String>("eventSeasonHeaders") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<String> listItem) {
					listItem.add(new Label("season", listItem.getModelObject()).add(new AttributeModifier("colspan",
					        colspan)));
				}
			};
			add(tournamentList);
		}

		private void addEventRecordsGroupListView() {
			add(new PropertyListView<EventRecordDto>("records") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<EventRecordDto> listItem) {
					listItem.add(new Label("index", listItem.getIndex() + 1 + ""));
					listItem.add(new Label("playerName"));

					ListView<EventDetailRecordDto> seasonList = new PropertyListView<EventDetailRecordDto>(
					        "seasonRecords") {

						private static final long serialVersionUID = 1L;

						@Override
						protected void populateItem(ListItem<EventDetailRecordDto> listItem) {
							final EventDetailRecordDto record = listItem.getModelObject();
							listItem.add(new Label("points")
							        .add(new AttributeModifier("title", record.getTournament())).add(
							                new AttributeModifier("style", new ItalicBoldReplaceModel(record
							                        .isExcluded()))));
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
			return selectedTableRecord.getEventTournamentHeaders().size()
			        / Math.max(1, selectedTableRecord.getEventSeasonHeaders().size());
		}
	}
}