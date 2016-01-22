package org.toursys.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.convert.IConverter;
import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Result;
import org.toursys.repository.model.Tournament;
import org.toursys.web.components.TournamentButton;
import org.toursys.web.converter.ResultConverter;
import org.toursys.web.link.TournamentLink;

public class TournamentOverviewPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private List<FinalStanding> finalStandings;
    private Tournament tournament;
    private Groups group;
    private List<Participant> participants;
    private List<Groups> finalGroups;
    private boolean isPlayOffOn = false;
    private boolean isGroupOn = false;
    private boolean isFinalStandingOn = false;

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
        group = getGroup(parameters, tournament, true);
        participants = getParticipants();
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

    private List<Participant> getParticipants() {
        List<Participant> participants;
        if (group != null) {
            participants = participantService.getParticipandByGroup(group);
        } else {
            participants = new ArrayList<Participant>();
        }
        return participants;
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
                    groupListItem.add(new Label("player", new ResourceModel("player")));
                    groupListItem.add(new Label("result", new ResourceModel("result")));
                    groupListItem.add(new ListView<PlayOffGame>("playOffGames") {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void populateItem(final ListItem<PlayOffGame> listItem) {
                            final PlayOffGame playOffGame = listItem.getModelObject();

                            Participant participant = playOffGame.getHomeParticipant();
                            Participant opponent = playOffGame.getAwayParticipant();

                            String playerName = "";
                            String playerSurname = "";
                            String playerDiscriminator = "";

                            String opponentName = "";
                            String opponentSurname = "";
                            String opponentDiscriminator = "";

                            if (participant != null && participant.getPlayer() != null) {
                                if (participant.getPlayer().getName() != null) {
                                    playerName = participant.getPlayer().getName();
                                }
                                if (participant.getPlayer().getSurname() != null) {
                                    playerSurname = participant.getPlayer().getSurname();
                                }
                                playerDiscriminator = participant.getPlayer().getPlayerDiscriminator();
                            }

                            if (opponent != null && opponent.getPlayer() != null) {
                                if (opponent.getPlayer().getName() != null) {
                                    opponentName = opponent.getPlayer().getName();
                                }
                                if (opponent.getPlayer().getSurname() != null) {
                                    opponentSurname = opponent.getPlayer().getSurname();
                                }
                                opponentDiscriminator = opponent.getPlayer().getPlayerDiscriminator();
                            }

                            listItem.add(new Label("player", playerName + " " + playerSurname + " "
                                    + playerDiscriminator).add(new AttributeModifier("style", "font-weight:bold;") {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public boolean isEnabled(Component component) {
                                    int homeWinnerCount = 0;
                                    int awayWinnerCount = 0;

                                    if (playOffGame == null || playOffGame.getResult() == null) {
                                        return false;
                                    }

                                    for (Result result : playOffGame.getResult().getResults()) {
                                        if (result.getLeftSide() > result.getRightSide()) {
                                            homeWinnerCount++;
                                        } else if (result.getLeftSide() < result.getRightSide()) {
                                            awayWinnerCount++;
                                        }
                                    }
                                    if (homeWinnerCount > awayWinnerCount) {
                                        return true;
                                    }
                                    return false;
                                }
                            }));
                            listItem.add(new Label("opponent", " " + opponentName + " " + opponentSurname + " "
                                    + opponentDiscriminator).add(new AttributeModifier("style", "font-weight:bold;") {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public boolean isEnabled(Component component) {
                                    int homeWinnerCount = 0;
                                    int awayWinnerCount = 0;

                                    if (playOffGame == null || playOffGame.getResult() == null) {
                                        return false;
                                    }

                                    for (Result result : playOffGame.getResult().getResults()) {
                                        if (result.getLeftSide() > result.getRightSide()) {
                                            homeWinnerCount++;
                                        } else if (result.getLeftSide() < result.getRightSide()) {
                                            awayWinnerCount++;
                                        }
                                    }
                                    if (homeWinnerCount < awayWinnerCount) {
                                        return true;
                                    }
                                    return false;
                                }
                            }));

                            listItem.add(new Label("roundName", new ResourceModel(TournamentUtil.getRoundName(
                                    tournament, group, playOffGame.getPosition()))));

                            listItem.add(new Label("result", new PropertyModel<Result>(playOffGame, "result")) {

                                private static final long serialVersionUID = 1L;

                                @Override
                                @SuppressWarnings("unchecked")
                                public final <Results> IConverter<Results> getConverter(Class<Results> type) {
                                    return (IConverter<Results>) ResultConverter.getInstance();
                                }
                            });

                            listItem.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public String getObject() {
                                    return (listItem.getIndex() % 2 == 1) ? "even" : "odd";
                                }
                            }));
                        }
                    });
                }
            }.setReuseItems(true));
        }

    }

    private class GroupForm extends Form<Void> {

        private static final long serialVersionUID = 1L;
        private List<Groups> groups = groupService.getGroups(new Groups()._setTournament(tournament));

        public GroupForm() {
            super("groupForm");

            addGroupTable();
            addGroupsButton();
            setVisibilityAllowed(isGroupOn);
        }

        private void addGroupsButton() {
            IDataProvider<Groups> dataProvider = new IDataProvider<Groups>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Iterator<Groups> iterator(long first, long count) {
                    return groups.subList((int) first, (int) (first + count)).iterator();
                }

                @Override
                public long size() {
                    return groups.size();
                }

                @Override
                public IModel<Groups> model(final Groups object) {
                    return new LoadableDetachableModel<Groups>() {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected Groups load() {
                            return object;
                        }
                    };
                }

                @Override
                public void detach() {
                }
            };

            GridView<Groups> gridView = new GridView<Groups>("rows", dataProvider) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final Item<Groups> listItem) {
                    final Groups group = listItem.getModelObject();

                    Button button = new TournamentButton("group", Model.of(group.getName())) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public void submit() {
                            getPageParameters().set(GID, group.getId());
                            setResponsePage(TournamentOverviewPage.class, getPageParameters());
                        }
                    };

                    if (TournamentOverviewPage.this.group != null
                            && TournamentOverviewPage.this.group.getName().equals(group.getName())) {
                        button.add(new AttributeModifier("class", "activeTournamentButton"));
                    }

                    listItem.add(button);
                }

                @Override
                protected void populateEmptyItem(Item<Groups> listItem) {
                }
            };
            gridView.setRows(1);
            gridView.setColumns((int) Math.max(1, dataProvider.size()));
            add(gridView);
        }

        private void addParticipantsGroupListView() {
            add(new PropertyListView<Participant>("participants", participants) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<Participant> listItem) {
                    final Participant participant = listItem.getModelObject();
                    listItem.setModel(new CompoundPropertyModel<Participant>(participant));
                    listItem.add(new Label("index", listItem.getIndex() + 1 + ""));
                    listItem.add(new Label("name", participant.getPlayer().getName().charAt(0) + ". "
                            + participant.getPlayer().getSurname() + " "
                            + participant.getPlayer().getPlayerDiscriminator()));
                    listItem.add(new Label("score", participant.getScore().toString()));
                    listItem.add(new Label("points", ((Integer) participant.getPoints()).toString()));
                    listItem.add(new Label("rank", (participant.getRank() != null) ? participant.getRank().toString()
                            : " "));

                    ListView<Participant> scoreList = new ListView<Participant>("gameList", participants) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void populateItem(ListItem<Participant> gameItem) {
                            final Participant participant1 = gameItem.getModelObject();

                            if (participant.equals(participant1)) {
                                gameItem.add(new Label("game", "X"));
                            } else {
                                Game game = null;
                                String result = "";
                                for (Game pomGame : participant.getGames()) {
                                    if (pomGame.getAwayParticipant().getId().equals(participant1.getId())) {
                                        game = pomGame;
                                        break;
                                    }
                                }
                                if (game.getResult() != null) {
                                    result = game.getResult().toString(" + ");
                                }
                                // TODO AJAX EDITABLE LABEL
                                gameItem.add(new Label("game", result));
                            }
                        }
                    };
                    listItem.add(scoreList);
                    listItem.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public String getObject() {
                            return (listItem.getIndex() % 2 == 1) ? "even" : "odd";
                        }
                    }));
                }

            });
        }

        private void addGroupHeaderPlayerIndex() {
            add(new ListView<Participant>("nameList", participants) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(ListItem<Participant> gameItem) {
                    gameItem.add(new Label("playerName", gameItem.getIndex() + 1 + ""));

                }
            });
        }

        private void addGroupTable() {
            addGroupTableHeader();
            addParticipantsGroupListView();
        }

        private void addGroupTableHeader() {
            addGroupHeaderPlayerIndex();
            add(new Label("name", new ResourceModel("name")));
            add(new Label("rank", new ResourceModel("rank")));
            add(new Label("points", new ResourceModel("points")));
            add(new Label("score", new ResourceModel("score")));
        }
    }

    private class FinalStandingsForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public FinalStandingsForm() {
            super("finalStandingsForm");

            addFinalStandingListView();
            setVisible(isFinalStandingOn);
        }

        private void addFinalStandingListView() {
            add(new PropertyListView<FinalStanding>("rows", finalStandings) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<FinalStanding> listItem) {
                    final FinalStanding finalStanding = listItem.getModelObject();
                    listItem.setModel(new CompoundPropertyModel<FinalStanding>(finalStanding));
                    listItem.add(new Label("name", (finalStanding.getPlayer() != null) ? finalStanding.getPlayer()
                            .getName() : ""));
                    listItem.add(new Label("surname", (finalStanding.getPlayer() != null) ? finalStanding.getPlayer()
                            .getSurname() + " " + finalStanding.getPlayer().getPlayerDiscriminator() : ""));
                    listItem.add(new Label("rank", finalStanding.getFinalRank() + "."));

                    listItem.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public String getObject() {
                            return (listItem.getIndex() % 2 == 1) ? "even" : "odd";
                        }
                    }));
                }
            }.setReuseItems(true));
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return Model.of(getString(this.getClass().getSimpleName()) + ": " + tournament.getName());
    }

}
