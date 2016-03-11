package org.tahom.web;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.tahom.processor.schedule.RoundRobinSchedule;
import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.repository.model.GameStatus;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Results;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentBackResourceButton;
import org.tahom.web.components.TournamentResourceButton;
import org.tahom.web.model.EvenOddReplaceModel;
import org.tahom.web.model.FontStyleReplaceModel;

@AuthorizeInstantiation(Roles.USER)
public class SchedulePage extends TournamentHomePage {

	private static final long serialVersionUID = 1L;
	private Groups group;
	private List<GameDto> scheduleGames;

	public SchedulePage() {
		throw new RestartResponseAtInterceptPageException(GroupPage.class);
	}

	public SchedulePage(PageParameters parameters, List<GameDto> schedule) {
		this.group = getGroup(parameters, tournament);
		this.scheduleGames = schedule;
		createPage();
	}

	private void createPage() {
		add(new ScheduleForm());
	}

	private class ScheduleForm extends Form<RoundRobinSchedule> {

		private static final long serialVersionUID = 1L;

		public ScheduleForm() {
			super("scheduleForm");

			add(new ResourceLabel("round"));
			add(new ResourceLabel("hockey"));
			add(new ResourceLabel("player"));
			add(new ResourceLabel("result"));

			addScheduleListView();
			addSaveButton();
			addBackButton();
		}

		private void addSaveButton() {
			add(new TournamentResourceButton("save") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					logger.debug("Submit schedule start ");
					scheduleService.saveSchedule(tournament, group, scheduleGames);
					getSession().info(getString("saveScheduleInfo"));
					setResponsePage(new SchedulePage(getPageParameters(), scheduleGames));
				};
			});
		}

		private void addBackButton() {
			add(new TournamentBackResourceButton("back") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					getPageParameters().set(UPDATE, true);
					setResponsePage(GroupPage.class, getPageParameters());
				};
			});
		}

		private void addScheduleListView() {
			add(new PropertyListView<GameDto>("schedule", scheduleGames) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<GameDto> listItem) {
					final GameDto game = listItem.getModelObject();

					listItem.add(new Label("player", game.getPlayerName()).add(new AttributeModifier("style",
					        new FontStyleReplaceModel(game)) {
						private static final long serialVersionUID = 1L;

						@Override
						public boolean isEnabled(Component component) {
							return GameStatus.WIN.equals(game.getGameStatus())
							        || GameStatus.DRAW.equals(game.getGameStatus());
						}
					}));

					listItem.add(new Label("opponent", game.getOpponentName()).add(new AttributeModifier("style",
					        new FontStyleReplaceModel(game)) {
						private static final long serialVersionUID = 1L;

						@Override
						public boolean isEnabled(Component component) {
							return GameStatus.LOSE.equals(game.getGameStatus())
							        || GameStatus.DRAW.equals(game.getGameStatus());

						}
					}));

					listItem.add(new TextField<Results>("result") {

						private static final long serialVersionUID = 1L;

						public void validate() {
							super.validate();
							if (!isValid()) {
								add(new AttributeModifier("class", "errorField"));
							}
						};

					});

					listItem.add(new Label("round"));
					listItem.add(new Label("hockey"));
					listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
				}

			}.setReuseItems(true));
		}
	}
}
