package org.tahom.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.tahom.processor.service.playOffGame.dto.PlayOffGameDto;
import org.tahom.repository.model.FinalStanding;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.GameStatus;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.PlayOffGame;
import org.tahom.repository.model.Result;
import org.tahom.repository.model.Tournament;
import org.tahom.web.link.TournamentLink;
import org.tahom.web.model.EvenOddReplaceModel;
import org.tahom.web.model.FontStyleReplaceModel;
import org.tahom.web.util.WebUtil;

public class TournamentOverviewPage extends BasePage {

	private static final long serialVersionUID = 1L;
	private List<FinalStanding> finalStandings;
	private Tournament tournament;
	private List<Groups> finalGroups;
	private List<Groups> groups;
	private boolean isPlayOffOn = false;
	private boolean isGroupOn = false;
	private boolean isFinalStandingOn = false;

	// TODO zistit max zapasov
	private static final int MAX_MATCHES = 7;

	public TournamentOverviewPage() {
		this(new PageParameters());
	}

	public TournamentOverviewPage(PageParameters parameters) {
		prepareData(parameters);
		initFormVisibility(parameters);
		createPage();
	}

	private void createPage() {
		add(new FinalStandingsForm());
		add(new GroupForm());
		add(new PlayOffForm());
		addLinks();
	}

	private void prepareData(PageParameters parameters) {
		tournament = getTournament(parameters, true);
		groups = getGroups();
		finalGroups = getFinalGroups();
		finalStandings = finalStandingService.getFinalStandings(tournament);
	}

	private void initFormVisibility(PageParameters parameters) {
		if (!parameters.get(SHOW_TOURNAMENT_OVERVIEW_PAGE_OPTIONS).isNull()) {
			this.isFinalStandingOn = (parameters.get(SHOW_TOURNAMENT_OVERVIEW_PAGE_OPTIONS).toInt() & 1L) != 0L;
			this.isGroupOn = (parameters.get(SHOW_TOURNAMENT_OVERVIEW_PAGE_OPTIONS).toInt() & 2L) != 0L;
			this.isPlayOffOn = (parameters.get(SHOW_TOURNAMENT_OVERVIEW_PAGE_OPTIONS).toInt() & 4L) != 0L;
		}
	}

	private List<Groups> getFinalGroups() {
		List<Groups> finalGroups = groupService.getFinalGroups(tournament);
		for (Groups group : finalGroups) {
			group.getPlayOffGames().addAll(playOffGameService.getPlayOffGames(new PlayOffGame()._setGroup(group)));
		}
		return finalGroups;
	}

	private List<Participant> getParticipants(Groups group) {
		List<Participant> participants;
		if (group != null) {
			participants = participantService.getParticipandByGroup(group);
		} else {
			participants = new ArrayList<Participant>();
		}
		return participants;
	}

	private List<Groups> getGroups() {
		return groupService.getGroups(new Groups()._setTournament(tournament));
	}

	private void addLinks() {
		addFinalStandingLink();
		addFinalGroupsLink();
		addFinalPlayOffLink();
	}

	private void addFinalStandingLink() {
		Link<Void> link = new TournamentLink("finalStandingsLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void click() {
				PageParameters pageParameters = getPageParameters();
				pageParameters.set(SHOW_TOURNAMENT_OVERVIEW_PAGE_OPTIONS, 1);
				setResponsePage(TournamentOverviewPage.class, pageParameters);
			}
		};
		Label linkLabel = new Label("finalStandingsOverview", new ResourceModel("finalStandingsOverview"));
		linkLabel.setRenderBodyOnly(true);
		link.add(linkLabel);
		add(link);
	}

	private void addFinalGroupsLink() {
		Link<Void> link = new TournamentLink("groupsLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void click() {
				PageParameters pageParameters = getPageParameters();
				pageParameters.set(SHOW_TOURNAMENT_OVERVIEW_PAGE_OPTIONS, 2);
				setResponsePage(TournamentOverviewPage.class, pageParameters);
			}
		};
		Label linkLabel = new Label("groupsOverview", new ResourceModel("groupsOverview"));
		linkLabel.setRenderBodyOnly(true);
		link.add(linkLabel);
		add(link);
	}

	private void addFinalPlayOffLink() {
		Link<Void> link = new TournamentLink("playOffLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void click() {
				PageParameters pageParameters = getPageParameters();
				pageParameters.set(SHOW_TOURNAMENT_OVERVIEW_PAGE_OPTIONS, 4);
				setResponsePage(TournamentOverviewPage.class, pageParameters);
			}
		};
		Label linkLabel = new Label("playOffOverview", new ResourceModel("playOffOverview"));
		linkLabel.setRenderBodyOnly(true);
		link.add(linkLabel);
		add(link);
	}

	private class PlayOffForm extends Form<Tournament> {

		private static final long serialVersionUID = 1L;

		public PlayOffForm() {
			super("playOffForm");
			addPlayOffTable();
			setVisible(isPlayOffOn);
		}

		private void addPlayOffTable() {
			addFinalGroupsGameListView();
		}

		private void addFinalGroupsGameListView() {
			add(new PropertyListView<Groups>("groups", finalGroups) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<Groups> listItem) {
					addPlayOffGames(listItem);
				}

				private void addPlayOffGames(final ListItem<Groups> groupListItem) {
					final Groups group = groupListItem.getModelObject();

					groupListItem.add(new Label("name", group.getName()));
					groupListItem.add(new Label("round", new ResourceModel("round")));
					groupListItem.add(new Label("players", new ResourceModel("players")));
					groupListItem.add(new PropertyListView<String>("matches", Arrays.asList(new String[MAX_MATCHES])) {

						private static final long serialVersionUID = 1L;

						@Override
						protected void populateItem(ListItem<String> item) {
							item.add(new Label("matchNumber", item.getIndex() + 1));
						}

					});

					groupListItem.add(new Label("score", new ResourceModel("score")));
					groupListItem.add(new PropertyListView<PlayOffGameDto>("playOffGames", playOffGameService
					        .getPlayOffGamesByGroup(group, tournament)) {

						private static final long serialVersionUID = 1L;

						@Override
						protected void populateItem(final ListItem<PlayOffGameDto> listItem) {
							final PlayOffGameDto game = listItem.getModelObject();

							listItem.add(new Label("player", game.getPlayerName()).add(new AttributeModifier("style",
							        new FontStyleReplaceModel(game)) {
								private static final long serialVersionUID = 1L;

								@Override
								public boolean isEnabled(Component component) {
									return GameStatus.WIN.equals(game.getGameStatus());
								}
							}));

							listItem.add(new Label("opponent", game.getOpponentName()).add(new AttributeModifier(
							        "style", new FontStyleReplaceModel(game)) {
								private static final long serialVersionUID = 1L;

								@Override
								public boolean isEnabled(Component component) {
									return GameStatus.LOSE.equals(game.getGameStatus());
								}
							}));
							listItem.add(new Label("roundName", new ResourceModel(game.getRoundName())));
							listItem.add(new PropertyListView<Result>("result", game.getResult().getResults()) {

								private static final long serialVersionUID = 1L;

								@Override
								protected void populateItem(ListItem<Result> item) {
									item.add(new Label("item", item.getModelObject()));
								}

							});
							listItem.add(new PropertyListView<String>("emptyResult", Arrays
							        .asList(new String[MAX_MATCHES - game.getResult().getResults().size()])) {

								private static final long serialVersionUID = 1L;

								@Override
								protected void populateItem(ListItem<String> item) {
									item.add(new Label("item", ""));
								}

							});

							listItem.add(new Label("score", game.getScore()));
							listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
						}
					});
				}
			}.setReuseItems(true));
		}
	}

	private class GroupForm extends Form<Void> {

		private static final long serialVersionUID = 1L;

		public GroupForm() {
			super("groupForm");

			addGroups();
			setVisibilityAllowed(isGroupOn);
		}

		private void addGroups() {
			add(new PropertyListView<Groups>("groups", groups) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<Groups> groupsItem) {
					Groups group = groupsItem.getModelObject();
					groupsItem.add(new Label("groupName", group.getName()));
					final List<Participant> participants = getParticipants(group);

					groupsItem.add(new ListView<Participant>("nameList", participants) {

						private static final long serialVersionUID = 1L;

						@Override
						protected void populateItem(ListItem<Participant> gameItem) {
							gameItem.add(new Label("playerName", gameItem.getIndex() + 1 + ""));

						}
					});
					groupsItem.add(new Label("name", new ResourceModel("name")));
					groupsItem.add(new Label("rank", new ResourceModel("rank")));
					groupsItem.add(new Label("points", new ResourceModel("points")));
					groupsItem.add(new Label("score", new ResourceModel("score")));

					groupsItem.add(new PropertyListView<Participant>("participants", participants) {

						private static final long serialVersionUID = 1L;

						@Override
						protected void populateItem(final ListItem<Participant> listItem) {
							final Participant participant = listItem.getModelObject();
							listItem.setModel(new CompoundPropertyModel<Participant>(participant));
							listItem.add(new Label("index", listItem.getIndex() + 1 + ""));
							listItem.add(new Label("name", WebUtil.getParticipandPlayerShortName(participant)));
							listItem.add(new Label("score", participant.getScore().toString()));
							listItem.add(new Label("points", ((Integer) participant.getPoints()).toString()));
							listItem.add(new Label("rank", (participant.getRank() != null) ? participant.getRank()
							        .toString() : " "));

							ListView<Participant> scoreList = new ListView<Participant>("gameList", participants) {

								private static final long serialVersionUID = 1L;

								@Override
								protected void populateItem(ListItem<Participant> gameItem) {
									final Participant rowParticipant = gameItem.getModelObject();

									if (participant.equals(rowParticipant)) {
										gameItem.add(new Label("game", "X"));
									} else {
										Game game = WebUtil.findParticipantGame(participant, rowParticipant);
										String result = "";

										if (game != null && game.getResult() != null) {
											result = game.getResult().toString(" + ");
										}
										// TODO AJAX EDITABLE LABEL
										gameItem.add(new Label("game", result));
									}
								}
							};
							listItem.add(scoreList);
							listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
						}
					});
				}
			});
		}
	}

	private class FinalStandingsForm extends Form<Void> {

		private static final long serialVersionUID = 1L;

		public FinalStandingsForm() {
			super("finalStandingsForm");

			addFinalStandingHeader();
			addFinalStandingListView();
			setVisible(isFinalStandingOn);
		}

		private void addFinalStandingHeader() {
			add(new Label("rank", new ResourceModel("rank")));
			add(new Label("name", new ResourceModel("name")));
			add(new Label("club", new ResourceModel("club")));
		}

		private void addFinalStandingListView() {
			add(new PropertyListView<FinalStanding>("rows", finalStandings) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<FinalStanding> listItem) {
					final FinalStanding finalStanding = listItem.getModelObject();
					listItem.setModel(new CompoundPropertyModel<FinalStanding>(finalStanding));
					listItem.add(new Label("rank", finalStanding.getFinalRank()));
					listItem.add(new Label("name", (finalStanding.getPlayer() != null) ? finalStanding.getPlayer()
					        .getName() + " " + finalStanding.getPlayer().getSurname() : ""));
					listItem.add(new Label("club", (finalStanding.getPlayer() != null) ? finalStanding.getPlayer()
					        .getClub().toString() : ""));
					listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
				}
			}.setReuseItems(true));
		}
	}

	@Override
	protected IModel<String> newHeadingModel() {
		return Model.of(getString(this.getClass().getSimpleName()) + ": " + tournament.getName());
	}

}
