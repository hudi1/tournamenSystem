package org.tahom.web;

import java.util.Arrays;

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
import org.tahom.processor.service.finalStanding.dto.FinalStandingDto;
import org.tahom.processor.service.finalStanding.dto.FinalStandingPageDto;
import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.processor.service.group.dto.GroupPageOverviewDto;
import org.tahom.processor.service.group.dto.GroupsOverviewDto;
import org.tahom.processor.service.participant.dto.ParticipantDto;
import org.tahom.processor.service.playOffGame.dto.PlayOffGameDto;
import org.tahom.processor.service.playOffGame.dto.PlayOffGroupDto;
import org.tahom.processor.service.playOffGame.dto.PlayOffPageDto;
import org.tahom.repository.model.GameStatus;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.impl.Result;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.link.TournamentLink;
import org.tahom.web.model.EvenOddReplaceModel;
import org.tahom.web.model.FontStyleReplaceModel;

public class TournamentOverviewPage extends TournamentHomePage {

	private static final long serialVersionUID = 1L;
	private FinalStandingPageDto finalStandingPageDto;
	private GroupPageOverviewDto groupPageDto;
	private PlayOffPageDto playOffPageDto;

	private boolean isPlayOffOn = false;
	private boolean isGroupOn = false;
	private boolean isFinalStandingOn = false;

	// TODO zistit max zapasov
	private static final int MAX_MATCHES = 7;

	public TournamentOverviewPage() {
		this(new PageParameters());
	}

	public TournamentOverviewPage(PageParameters parameters) {
		super(parameters);
		prepareData(parameters);
		initFormVisibility(parameters);
		createPage();
	}

	@Override
	protected Tournament getTournament(PageParameters pageParameters) {
		return getTournament(pageParameters, true);
	}

	private void createPage() {
		add(new FinalStandingsForm());
		add(new GroupForm());
		add(new PlayOffForm());
		addLinks();
	}

	private void prepareData(PageParameters parameters) {
		groupPageDto = groupService.getGroupPageOverviewDto(tournament);
		playOffPageDto = playOffGameService.getPlayOffPageDto(tournament);
		finalStandingPageDto = finalStandingService.getFinalStandingPageDto(tournament);
	}

	private void initFormVisibility(PageParameters parameters) {
		if (!parameters.get(SHOW_TOURNAMENT_OVERVIEW_PAGE_OPTIONS).isNull()) {
			this.isFinalStandingOn = (parameters.get(SHOW_TOURNAMENT_OVERVIEW_PAGE_OPTIONS).toInt() & 1L) != 0L;
			this.isGroupOn = (parameters.get(SHOW_TOURNAMENT_OVERVIEW_PAGE_OPTIONS).toInt() & 2L) != 0L;
			this.isPlayOffOn = (parameters.get(SHOW_TOURNAMENT_OVERVIEW_PAGE_OPTIONS).toInt() & 4L) != 0L;
		}
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
		Label linkLabel = new ResourceLabel("finalStandingsOverview");
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
		Label linkLabel = new ResourceLabel("groupsOverview");
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
		Label linkLabel = new ResourceLabel("playOffOverview");
		linkLabel.setRenderBodyOnly(true);
		link.add(linkLabel);
		add(link);
	}

	private class PlayOffForm extends Form<PlayOffPageDto> {

		private static final long serialVersionUID = 1L;

		public PlayOffForm() {
			super("playOffForm", new CompoundPropertyModel<PlayOffPageDto>(playOffPageDto));
			addPlayOffTable();
			setVisible(isPlayOffOn);
		}

		private void addPlayOffTable() {
			addFinalGroupsGameListView();
		}

		private void addFinalGroupsGameListView() {
			add(new PropertyListView<PlayOffGroupDto>("playOffGroups") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<PlayOffGroupDto> listItem) {
					addPlayOffGames(listItem);
				}

				private void addPlayOffGames(final ListItem<PlayOffGroupDto> groupListItem) {

					groupListItem.add(new Label("name"));
					groupListItem.add(new ResourceLabel("round"));
					groupListItem.add(new ResourceLabel("players"));
					groupListItem.add(new PropertyListView<String>("matches", Arrays.asList(new String[MAX_MATCHES])) {

						private static final long serialVersionUID = 1L;

						@Override
						protected void populateItem(ListItem<String> item) {
							item.add(new Label("matchNumber", item.getIndex() + 1));
						}

					});
					groupListItem.add(new Label("score", new ResourceModel("score")));

					groupListItem.add(new PropertyListView<PlayOffGameDto>("playOffGames") {

						private static final long serialVersionUID = 1L;

						@Override
						protected void populateItem(final ListItem<PlayOffGameDto> listItem) {
							final PlayOffGameDto game = listItem.getModelObject();

							listItem.add(new Label("playerName").add(new AttributeModifier("style",
							        new FontStyleReplaceModel(game)) {
								private static final long serialVersionUID = 1L;

								@Override
								public boolean isEnabled(Component component) {
									return GameStatus.WIN.equals(game.getGameStatus());
								}
							}));

							listItem.add(new Label("opponentName").add(new AttributeModifier("style",
							        new FontStyleReplaceModel(game)) {
								private static final long serialVersionUID = 1L;

								@Override
								public boolean isEnabled(Component component) {
									return GameStatus.LOSE.equals(game.getGameStatus());
								}
							}));
							listItem.add(new Label("roundName", new ResourceModel(game.getRoundName())));
							listItem.add(new PropertyListView<Result>("results") {

								private static final long serialVersionUID = 1L;

								@Override
								protected void populateItem(ListItem<Result> item) {
									item.add(new Label("item", item.getModelObject()));
								}

							});
							listItem.add(new PropertyListView<String>("emptyResults", Arrays
							        .asList(new String[MAX_MATCHES - game.getResults().size()])) {
								private static final long serialVersionUID = 1L;

								@Override
								protected void populateItem(ListItem<String> item) {
									item.add(new Label("item", ""));
								}

							});
							listItem.add(new Label("score"));
							listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
						}
					});
				}
			}.setReuseItems(true));
		}

	}

	private class GroupForm extends Form<GroupPageOverviewDto> {

		private static final long serialVersionUID = 1L;

		public GroupForm() {
			super("groupForm", new CompoundPropertyModel<>(groupPageDto));
			addGroups();
			setVisibilityAllowed(isGroupOn);
		}

		private void addGroups() {
			add(new PropertyListView<GroupsOverviewDto>("groups") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<GroupsOverviewDto> groupsItem) {
					groupsItem.add(new Label("groupName"));
					addGroupTableHeader(groupsItem);
					addParticipantsGroupListView(groupsItem);
				}
			});
		}

		private void addGroupTableHeader(ListItem<GroupsOverviewDto> groupsItem) {
			addGroupHeaderPlayerIndex(groupsItem);
			groupsItem.add(new ResourceLabel("name"));
			groupsItem.add(new ResourceLabel("rank"));
			groupsItem.add(new ResourceLabel("points"));
			groupsItem.add(new ResourceLabel("score"));
		}

		private void addGroupHeaderPlayerIndex(ListItem<GroupsOverviewDto> groupsItem) {
			groupsItem.add(new PropertyListView<ParticipantDto>("participantsHeader") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<ParticipantDto> listItem) {
					listItem.add(new Label("name", listItem.getIndex() + 1 + ""));
				}
			});
		}

		private void addParticipantsGroupListView(ListItem<GroupsOverviewDto> groupsItem) {
			groupsItem.add(new PropertyListView<ParticipantDto>("participants") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<ParticipantDto> listItem) {
					listItem.add(new Label("index", listItem.getIndex() + 1 + ""));
					listItem.add(new Label("name"));
					listItem.add(new Label("score"));
					listItem.add(new Label("points"));
					listItem.add(new Label("rank"));

					ListView<GameDto> games = new PropertyListView<GameDto>("games") {

						private static final long serialVersionUID = 1L;

						@Override
						protected void populateItem(ListItem<GameDto> gameItem) {
							gameItem.add(new Label("result"));
						}
					};
					listItem.add(games);
					listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
				}
			});
		}
	}

	private class FinalStandingsForm extends Form<FinalStandingPageDto> {

		private static final long serialVersionUID = 1L;

		public FinalStandingsForm() {
			super("finalStandingsForm", new CompoundPropertyModel<FinalStandingPageDto>(finalStandingPageDto));

			addFinalStandingHeader();
			addFinalStandingsTable();
			setVisible(isFinalStandingOn);
		}

		private void addFinalStandingHeader() {
			add(new Label("rank", new ResourceModel("rank")));
			add(new Label("name", new ResourceModel("name")));
			add(new Label("club", new ResourceModel("club")));
		}

		private void addFinalStandingsTable() {
			add(new PropertyListView<FinalStandingDto>("finalStandings") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<FinalStandingDto> listItem) {
					listItem.add(new Label("name"));
					listItem.add(new Label("rank"));
					listItem.add(new Label("club"));
					listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
				}
			});
		}

	}

	@Override
	protected IModel<String> newHeadingModel() {
		return Model.of(getString(this.getClass().getSimpleName()) + ": " + tournament.getName());
	}

}
