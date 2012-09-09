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
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.time.Duration;
import org.toursys.processor.comparators.RankComparator;
import org.toursys.processor.pdf.PdfFactory;
import org.toursys.repository.form.PlayerResultForm;
import org.toursys.repository.form.TableForm;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Table;
import org.toursys.repository.model.TableType;
import org.toursys.repository.model.Tournament;

public class GroupPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private Tournament tournament;
    private Table table;
    private List<PlayerResult> playerResults;
    private BasePage basePage;

    public GroupPage(Tournament tournament, Table group, BasePage basePage) {
        this.basePage = basePage;
        this.table = group;
        this.tournament = tournament;
        this.playerResults = tournamentService.findPlayerResult(new PlayerResultForm(tournament, table));
        calculatePlayerResults();
        createPage();

    }

    private void calculatePlayerResults() {
        tournamentService.calculatePlayerResults(playerResults, tournament);
    }

    private void createPage() {
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
                listItem.add(new Label("score", playerResult.getScore()));
                listItem.add(new Label("points", playerResult.getPoints().toString()));
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
                                if (pomGame.getOpponent().equals(playerResult1)) {
                                    game = pomGame;
                                    break;
                                }
                            }
                            String result = (game.getResult().getLeftSide() == null) ? ""
                                    : game.getResult().getLeftSide()
                                            + ":"
                                            + ((game.getResult().getRightSide() == null) ? "" : game.getResult()
                                                    .getRightSide());
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

        if (table.getTableId() == 0) {
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

            IDataProvider<Table> dataProvider = new IDataProvider<Table>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Iterator<Table> iterator(int first, int count) {
                    List<TableType> tablesType = new ArrayList<TableType>();
                    tablesType.add(TableType.B);
                    tablesType.add(TableType.F);
                    return tournamentService.findTable(new TableForm(tournament, tablesType))
                            .subList(first, first + count).iterator();

                }

                @Override
                public int size() {
                    List<TableType> tablesType = new ArrayList<TableType>();
                    tablesType.add(TableType.B);
                    tablesType.add(TableType.F);
                    return tournamentService.findTable(new TableForm(tournament, tablesType)).size();
                }

                @Override
                public IModel<Table> model(final Table object) {
                    return new LoadableDetachableModel<Table>() {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected Table load() {
                            return object;
                        }
                    };
                }

                @Override
                public void detach() {
                }
            };

            GridView<Table> gridView = new GridView<Table>("rows", dataProvider) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final Item<Table> listItem) {
                    final Table table = listItem.getModelObject();

                    Button button = new Button("group", Model.of(table.getName())) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public void onSubmit() {
                            tournamentService.createGames(table, tournament);
                            setResponsePage(new GroupPage(tournament, table, basePage));
                        }
                    };

                    if (GroupPage.this.table.equals(table)) {
                        button.add(new AttributeModifier("class", "activeSeasoneButton"));
                    }

                    listItem.add(button);
                }

                @Override
                protected void populateEmptyItem(Item<Table> listItem) {
                }
            };
            gridView.setRows(1);
            gridView.setColumns(Math.max(1, dataProvider.size()));
            add(gridView);

            Button schedule = new Button("schedule", new StringResourceModel("schedule", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new SchedulePage(tournament, table, basePage));
                }
            };

            add(schedule);

            Button options = new Button("options", new StringResourceModel("options", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new TournamentOptionsPage(tournament, table, basePage) {

                        private static final long serialVersionUID = 1L;
                    });
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
                                tournamentService.getSchedule(table, tournament), table);
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
                                tournamentService.getSchedule(table, tournament));
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
                    setResponsePage(new PlayOffPage(tournament, basePage));

                }
            });

            add(new Button("back", new StringResourceModel("back", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new RegistrationPage(tournament, basePage));

                }
            });

            add(new Button("finalGroup", new StringResourceModel("finalGroup", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    tournamentService.createFinalGroup(tournament);
                    setResponsePage(new GroupPage(tournament, table, basePage) {

                        private static final long serialVersionUID = 1L;
                    });
                }
            });

            add(new Button("copyResult", new StringResourceModel("copyResult", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    tournamentService.copyResult(tournament);
                    setResponsePage(new GroupPage(tournament, table, basePage) {

                        private static final long serialVersionUID = 1L;
                    });
                }
            });

            if (table.getTableId() == 0) {
                options.setVisible(false);
                schedule.setVisible(false);
                sheets.setVisible(false);
                pdfSchedule.setVisible(false);
            }
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new StringResourceModel("group", null);
    }

}
