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
import org.apache.wicket.util.convert.IConverter;
import org.tahom.processor.schedule.RoundRobinSchedule;
import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.repository.model.GameStatus;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.GroupsType;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Result;
import org.tahom.repository.model.Tournament;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentBackResourceButton;
import org.tahom.web.components.TournamentResourceButton;
import org.tahom.web.converter.ResultConverter;
import org.tahom.web.model.EvenOddReplaceModel;
import org.tahom.web.model.FontStyleReplaceModel;

@AuthorizeInstantiation(Roles.USER)
public class SchedulePage extends TournamentHomePage {

	private static final long serialVersionUID = 1L;
	private Groups group;
	private Tournament tournament;
	private List<GameDto> scheduleGames;

	public SchedulePage() {
		throw new RestartResponseAtInterceptPageException(GroupPage.class);
	}

	public SchedulePage(PageParameters parameters) {
		this.tournament = getTournament(parameters);
		this.group = getGroup(parameters, tournament);
		this.scheduleGames = getSchedule().getSchedule();
		createPage();
	}

	private void createPage() {
		add(new ScheduleForm());
	}

	private RoundRobinSchedule getSchedule() {
		logger.debug("Creating schedule start");
		long time = System.currentTimeMillis();
		List<Participant> participants = participantService.getParticipandByGroup(group);
		RoundRobinSchedule schedule = scheduleService.getSchedule(tournament, group, participants);
		time = System.currentTimeMillis() - time;
		logger.debug("Creating schedule end: " + time + " ms");
		return schedule;
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
					long time = System.currentTimeMillis();
					gameService.updateBothGames(scheduleGames);
					time = System.currentTimeMillis() - time;
					List<Participant> participants = sortParticipants();

					if (GroupsType.FINAL.equals(group.getType())) {
						playOffGameService.updatePlayOffGames(tournament, group);
						finalStandingService.updateNotPromotingFinalStandings(participants, group, tournament);
					}
					logger.debug("Submit schedule end: " + time + " ms");
					setResponsePage(SchedulePage.class, getPageParameters());
				};
			});
		}

		private List<Participant> sortParticipants() {
			List<Participant> participants = participantService.getParticipandByGroup(group);

			participantService.sortParticipantsByRank(participants, tournament);
			return participants;
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

					listItem.add(new TextField<Result>("result") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unchecked")
						@Override
						public final <Results> IConverter<Results> getConverter(Class<Results> type) {
							return (IConverter<Results>) ResultConverter.getInstance();
						}

					});

					listItem.add(new Label("round"));
					listItem.add(new Label("hockey"));

					listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));

				}
			}.setReuseItems(true));
		}
	}
}
