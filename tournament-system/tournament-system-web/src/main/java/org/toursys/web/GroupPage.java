package org.toursys.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.time.Duration;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.processor.pdf.PdfFactory;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.GroupType;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.TournamentImpl;

public class GroupPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private TournamentImpl tournament;
    private Groups group;
    private List<PlayerResult> playerResults;

    public GroupPage() {
        throw new RestartResponseAtInterceptPageException(new SeasonPage());
    }

    public GroupPage(TournamentImpl tournament) {
        this(tournament, null);
    }

    public GroupPage(TournamentImpl tournament, Groups group) {
        this(tournament, group, false);
    }

    public GroupPage(TournamentImpl tournament, Groups group, boolean calculateResult) {
        this.group = group;
        this.tournament = tournament;
        if (group != null) {
            this.playerResults = tournamentService.getPlayerResultInGroup(new PlayerResult()._setGroup(group));
            tournamentService.createGames(playerResults);
            if (calculateResult || group.getGroupType() != GroupType.B.name())
                calculatePlayerResults();
        } else {
            playerResults = new ArrayList<PlayerResult>();
        }

        createPage();
    }

    protected void createPage() {
        add(new GroupForm());
        createGroups();
    }

    private void calculatePlayerResults() {
        tournamentService.calculatePlayerResults(playerResults, tournament);
    }

    private void createGroups() {

        IDataProvider<PlayerResult> dataProvider = new IDataProvider<PlayerResult>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Iterator<PlayerResult> iterator(int first, int count) {
                return playerResults.subList(first, first + count).iterator();
            }

            @Override
            public int size() {
                return playerResults.size();
            }

            @Override
            public IModel<PlayerResult> model(final PlayerResult object) {
                return new LoadableDetachableModel<PlayerResult>() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    protected PlayerResult load() {
                        return object;
                    }
                };
            }

            @Override
            public void detach() {
            }
        };

        DataView<PlayerResult> dataView = new DataView<PlayerResult>("rows", dataProvider) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<PlayerResult> listItem) {
                final PlayerResult playerResult = listItem.getModelObject();
                listItem.setModel(new CompoundPropertyModel<PlayerResult>(playerResult));
                listItem.add(new Label("name", playerResult.getPlayer().getSurname() + " "
                        + playerResult.getPlayer().getName().charAt(0) + "."));
                listItem.add(new Label("score", playerResult.getScore().toString()));
                listItem.add(new Label("points", ((Integer) playerResult.getPoints()).toString()));
                listItem.add(new Label("rank", (playerResult.getRank() != null) ? playerResult.getRank().toString()
                        : " "));

                ListView<PlayerResult> scoreList = new ListView<PlayerResult>("gameList", playerResults) {

                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void populateItem(ListItem<PlayerResult> gameItem) {
                        final PlayerResult playerResult1 = gameItem.getModelObject();

                        if (playerResult.equals(playerResult1)) {
                            gameItem.add(new Label("game", "X"));
                        } else {
                            Game game = null;
                            for (Game pomGame : playerResult.getGames()) {
                                if (pomGame.getAwayPlayerResult().getId().equals(playerResult1.getId())) {
                                    game = pomGame;
                                    break;
                                }
                            }

                            String result = (game.getHomeScore() == null) ? "" : game.getHomeScore() + ":"
                                    + ((game.getAwayScore() == null) ? "" : game.getAwayScore());
                            gameItem.add(new Label("game", result));
                        }
                    }
                };
                listItem.add(scoreList);
            }
        };

        Collections.sort(playerResults, new RankComparator());

        ListView<PlayerResult> nameList = new ListView<PlayerResult>("nameList", playerResults) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<PlayerResult> gameItem) {
                final PlayerResult playerResult1 = gameItem.getModelObject();
                String name = playerResult1.getPlayer().getName().charAt(0) + "."
                        + playerResult1.getPlayer().getSurname().charAt(0);
                gameItem.add(new Label("playerName", name));

            }
        };

        Label name = new Label("name", new ResourceModel("name"));
        Label rank = new Label("rank", new ResourceModel("rank"));
        Label points = new Label("points", new ResourceModel("points"));
        Label score = new Label("score", new ResourceModel("score"));

        if (group == null) {
            dataView.setVisible(false);
            nameList.setVisible(false);
            name.setVisible(false);
            points.setVisible(false);
            score.setVisible(false);
            rank.setVisible(false);
        }
        add(name);
        add(rank);
        add(points);
        add(score);
        add(nameList);
        add(dataView);
    }

    private class GroupForm extends Form<Void> {

        private static final long serialVersionUID = 1L;
        private List<Groups> groups = new ArrayList<Groups>();

        public GroupForm() {
            super("groupForm");
            List<Groups> basicGroups = tournamentService.getBasicGroups(new Groups()._setTournament(tournament));
            List<Groups> finalGroups = tournamentService.getFinalGroups(new Groups()._setTournament(tournament));
            groups.addAll(basicGroups);
            groups.addAll(finalGroups);

            IDataProvider<Groups> dataProvider = new IDataProvider<Groups>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Iterator<Groups> iterator(int first, int count) {
                    return groups.subList(first, first + count).iterator();
                }

                @Override
                public int size() {
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

                    Button button = new Button("group", Model.of(group.getName())) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public void onSubmit() {
                            setResponsePage(new GroupPage(tournament, group));
                        }
                    };

                    if (GroupPage.this.group != null && GroupPage.this.group.getName().equals(group.getName())) {
                        button.add(new AttributeModifier("class", "activeTournamentButton"));
                    }

                    listItem.add(button);
                }

                @Override
                protected void populateEmptyItem(Item<Groups> listItem) {
                }
            };
            gridView.setRows(1);
            gridView.setColumns(Math.max(1, dataProvider.size()));
            add(gridView);

            Button schedule = new Button("schedule", new ResourceModel("schedule")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new SchedulePage(tournament, group));
                }
            };

            add(schedule);

            Button options = new Button("options", new ResourceModel("options")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new TournamentOptionsPage(tournament, group, true, false));
                }
            };

            add(options);

            DownloadLink sheets = new DownloadLink("sheets", new AbstractReadOnlyModel<File>() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getObject() {
                    File tempFile;
                    try {
                        tempFile = PdfFactory.createSheets(WicketApplication.getFilesPath(),
                                tournamentService.getSchedule(group, tournament, playerResults), group);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    return tempFile;
                }
            });
            sheets.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);

            add(sheets);

            DownloadLink pdfSchedule = new DownloadLink("pdfSchedule", new AbstractReadOnlyModel<File>() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getObject() {
                    File tempFile;
                    try {
                        tempFile = PdfFactory.createSchedule(WicketApplication.getFilesPath(),
                                tournamentService.getSchedule(group, tournament, playerResults));
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    return tempFile;
                }
            });
            pdfSchedule.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);

            add(pdfSchedule);

            Button playOff = new Button("playOff") {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new PlayOffPage(tournament));

                }
            };
            add(playOff);

            add(new AjaxButton("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    target.appendJavaScript(PREVISOUS_PAGE);
                };
            }.setDefaultFormProcessing(false));

            Button finalGroup = new Button("finalGroup", new ResourceModel("finalGroup")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    tournamentService.createFinalGroup(tournament);
                    setResponsePage(new GroupPage(tournament, group));
                }
            };

            add(finalGroup);

            Button copyResult = new Button("copyResult", new ResourceModel("copyResult")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    tournamentService.copyResult(tournament);
                    setResponsePage(new GroupPage(tournament, group, true));
                }
            };
            add(copyResult);

            DownloadLink printGroup = new DownloadLink("pdfTable", new AbstractReadOnlyModel<File>() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getObject() {
                    File tempFile;
                    try {
                        tempFile = PdfFactory.createTable(WicketApplication.getFilesPath(), playerResults, group);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    return tempFile;
                }
            });
            printGroup.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);

            add(printGroup);

            if (group == null) {
                options.setVisible(false);
                schedule.setVisible(false);
                sheets.setVisible(false);
                pdfSchedule.setVisible(false);
                printGroup.setVisible(false);
                copyResult.setVisible(false);
                playOff.setVisible(false);
                finalGroup.setVisible(false);
            }
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("group");
    }

}
