package org.toursys.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
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
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.time.Duration;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.processor.pdf.PdfFactory;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.GroupType;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Tournament;

public class GroupPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private Tournament tournament;
    private Groups group;
    private List<PlayerResult> playerResults;

    public GroupPage(Tournament tournament) {
        this.tournament = tournament;
        // this.playerResults = null; // = tournamentService.findPlayerResult(new PlayerResultForm(tournament, group));
        // calculatePlayerResults();
        createPage();
    }

    public GroupPage(Tournament tournament, Groups group) {
        this.group = group;
        this.tournament = tournament;
        // this.playerResults = null; // = tournamentService.findPlayerResult(new PlayerResultForm(tournament, group));
        // calculatePlayerResults();
        createPage();
    }

    private void calculatePlayerResults() {
        tournamentService.calculatePlayerResults(playerResults, tournament);
    }

    protected void createPage() {
        add(new GroupForm());
        createGroups();
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
                listItem.add(new Label("rank", playerResult.getRank().toString()));

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
                                if (pomGame.getAwayPlayerResult().equals(playerResult1)) {
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

        Label name = new Label("name", new StringResourceModel("name", null));
        Label rank = new Label("rank", new StringResourceModel("rank", null));
        Label points = new Label("points", new StringResourceModel("points", null));
        Label score = new Label("score", new StringResourceModel("score", null));

        if (group.getId() == 0) {
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

        public GroupForm() {
            super("groupForm");

            IDataProvider<Groups> dataProvider = new IDataProvider<Groups>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Iterator<Groups> iterator(int first, int count) {
                    List<GroupType> tablesType = new ArrayList<GroupType>();
                    tablesType.add(GroupType.B);
                    tablesType.add(GroupType.F);
                    return null;// tournamentService.findTable(new GroupForm(tournament, tablesType))
                    // .subList(first, first + count).iterator();

                }

                @Override
                public int size() {
                    List<GroupType> tablesType = new ArrayList<GroupType>();
                    tablesType.add(GroupType.B);
                    tablesType.add(GroupType.F);
                    return 0;// tournamentService.findTable(new GroupForm(tournament, tablesType)).size();
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
                            // tournamentService.createGames(tournamentService.findPlayerResult(new PlayerResultForm(
                            // tournament, table)));
                            setResponsePage(new GroupPage(tournament, group));
                        }
                    };

                    if (GroupPage.this.group.getName().equals(group.getName())) {
                        System.out.println("?????????");
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

            Button schedule = new Button("schedule", new StringResourceModel("schedule", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new SchedulePage(tournament, group));
                }
            };

            add(schedule);

            Button options = new Button("options", new StringResourceModel("options", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new TournamentOptionsPage(tournament, group, false, false));
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
                                tournamentService.getSchedule(group, tournament), group);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    return tempFile;
                }
            }/* , new ResourceModel("sheets") /* TODO this doesnt work ?? */);
            sheets.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);

            add(sheets);

            DownloadLink pdfSchedule = new DownloadLink("pdfSchedule", new AbstractReadOnlyModel<File>() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getObject() {
                    File tempFile;
                    try {
                        tempFile = PdfFactory.createSchedule(WicketApplication.getFilesPath(),
                                tournamentService.getSchedule(group, tournament));
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    return tempFile;
                }
            }/* , new StringResourceModel("sheets", null) /* TODO this doesnt work ?? */);
            pdfSchedule.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);

            add(pdfSchedule);

            add(new Button("playOff") {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new PlayOffPage(tournament));

                }
            });

            add(new Button("back", new StringResourceModel("back", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new RegistrationPage(tournament));

                }
            });

            add(new Button("finalGroup", new StringResourceModel("finalGroup", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    tournamentService.createFinalGroup(tournament);
                    setResponsePage(new GroupPage(tournament, group) {

                        private static final long serialVersionUID = 1L;
                    });
                }
            });

            add(new Button("copyResult", new StringResourceModel("copyResult", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    tournamentService.copyResult(tournament);
                    setResponsePage(new GroupPage(tournament, group) {

                        private static final long serialVersionUID = 1L;
                    });
                }
            });

            DownloadLink printTable = new DownloadLink("pdfTable", new AbstractReadOnlyModel<File>() {
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
            }/* , new ResourceModel("sheets") /* TODO this doesnt work ?? */);
            sheets.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);

            add(printTable);

            if (group.getId() == 0) {
                options.setVisible(false);
                schedule.setVisible(false);
                sheets.setVisible(false);
                pdfSchedule.setVisible(false);
            }
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("group");
    }

}
