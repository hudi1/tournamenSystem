package org.toursys.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.DownloadLink;
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
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;
import org.toursys.processor.pdf.PdfFactory;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Participant;

@AuthorizeInstantiation(Roles.USER)
public class GroupPage extends TournamentHomePage {

    private static final long serialVersionUID = 1L;

    private Groups group;
    private List<Participant> participants;
    private ModalWindow modalWindow;
    private boolean sortParticipants;

    public GroupPage() {
        this(new PageParameters());
    }

    public GroupPage(PageParameters parameters) {
        super(parameters);

        prepareData(parameters);
        clearPageParameters();
        createPage();
    }

    protected void createPage() {
        addModalWindow();
        add(new GroupForm());
    }

    private void clearPageParameters() {
        getPageParameters().remove(UPDATE);
    }

    private void addModalWindow() {
        if (modalWindow == null) {
            add(new ModalWindow("modalWindow"));
        } else {
            add(modalWindow);
        }
    }

    private class GroupForm extends Form<Void> {

        private static final long serialVersionUID = 1L;
        private List<Groups> groups = groupService.getGroups(new Groups()._setTournament(tournament));

        public GroupForm() {
            super("groupForm");

            addGroupTable();
            addGroupsButton();
            addGroupOptionsButton();
        }

        private void addGroupOptionsButton() {
            Button schedule = new Button("schedule", new ResourceModel("schedule")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    getPageParameters().set(GID, group.getId());
                    setResponsePage(SchedulePage.class, getPageParameters());
                }
            };

            add(schedule);

            Button options = new Button("options", new ResourceModel("options")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    getPageParameters().set(GID, group.getId());
                    getPageParameters().set(SHOW_TABLE_OPTIONS, true);
                    getPageParameters().set(SHOW_TOURNAMENT_OPTIONS, false);
                    setResponsePage(TournamentOptionsPage.class, getPageParameters());
                }
            };

            add(options);

            DownloadLink sheets = new DownloadLink("sheets", new AbstractReadOnlyModel<File>() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getObject() {
                    File tempFile;
                    try {
                        tempFile = PdfFactory.createSheets(WicketApplication.getFilesPath(), scheduleService
                                .getSchedule(tournament, group, participants).getSchedule(), group);
                    } catch (Exception e) {
                        logger.error("!! GroupPage error: ", e);
                        // TODO osetrit nejakym chybovym hlasenim vo feedback panelu
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
                        tempFile = PdfFactory.createSchedule(WicketApplication.getFilesPath(), scheduleService
                                .getSchedule(tournament, group, participants).getSchedule());
                    } catch (Exception e) {
                        logger.error("!! GroupPage error: ", e);
                        // TODO osetrit nejakym chybovym hlasenim vo feedback panelu
                        throw new RuntimeException(e);
                    }
                    return tempFile;
                }
            });
            pdfSchedule.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);

            add(pdfSchedule);

            Button finalGroup = new Button("finalGroup", new ResourceModel("finalGroup")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    groupService.createFinalGroup(tournament);
                    finalStandingService.processFinalStandings(tournament);
                    playOffGameService.processPlayOffGames(tournament);
                    setResponsePage(GroupPage.class, getPageParameters());
                }
            };

            add(finalGroup);

            Button copyResult = new Button("copyResult", new ResourceModel("copyResult")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    groupService.copyResult(tournament);
                    setResponsePage(GroupPage.class, getPageParameters());
                }
            };
            add(copyResult);

            DownloadLink printGroup = new DownloadLink("pdfTable", new AbstractReadOnlyModel<File>() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getObject() {
                    File tempFile;
                    try {
                        tempFile = PdfFactory.createTable(WicketApplication.getFilesPath(), participants, group);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    return tempFile;
                }
            });
            printGroup.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);

            add(printGroup);

            Button equalRankButton = new AjaxButton("editEqualRank", new ResourceModel("editEqualRank")) {

                private static final long serialVersionUID = 1L;

                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    modalWindow.show(target);
                }
            };

            add(equalRankButton);

            equalRankButton.setVisible(modalWindow != null);

            if (group == null) {
                options.setVisible(false);
                schedule.setVisible(false);
                sheets.setVisible(false);
                pdfSchedule.setVisible(false);
                printGroup.setVisible(false);
                copyResult.setVisible(false);
                finalGroup.setVisible(false);
            }
        }

        private void addGroupsButton() {
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
                            getPageParameters().set(GID, group.getId());
                            setResponsePage(GroupPage.class, getPageParameters());
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
                                for (Game pomGame : participant.getGames()) {
                                    if (pomGame.getAwayParticipant().getId().equals(participant1.getId())) {
                                        game = pomGame;
                                        break;
                                    }
                                }

                                String result = (game.getHomeScore() == null) ? "" : game.getHomeScore() + ":"
                                        + ((game.getAwayScore() == null) ? "" : game.getAwayScore());
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

    private boolean getSortParticipants(PageParameters parameters) {
        if (parameters.get(UPDATE).isNull()) {
            return false;
        } else {
            return parameters.get(UPDATE).toBoolean();
        }
    }

    private void prepareData(PageParameters parameters) {
        group = getGroup(parameters);
        sortParticipants = getSortParticipants(parameters);

        if (group != null) {
            this.participants = getParticipants();
            setUpGoldGoalModalWindow();
            updateFinalStanding();
        } else {
            participants = new ArrayList<Participant>();
        }

    }

    private void updateFinalStanding() {
        if (group.getType().equals(GroupsType.FINAL) && sortParticipants) {
            finalStandingService.updateNotPromotingFinalStandings(participants, group, tournament);
        }
    }

    private void setUpGoldGoalModalWindow() {
        Set<Participant> goldGoalParticipants = participantService.getGoldGoalParticipants(participants);

        if (!goldGoalParticipants.isEmpty()) {
            createModalWindow(goldGoalParticipants);
        }
    }

    private List<Participant> getParticipants() {
        List<Participant> participants = participantService.getSortedParticipants(new Participant()._setGroup(group));
        gameService.processGames(participants);

        if (sortParticipants) {
            participantService.sortParticipants(participants, tournament);
        }
        return participants;
    }

    private void createModalWindow(final Set<Participant> samePlayerRankParticipants) {

        modalWindow = createDefaultModalWindow();

        modalWindow.setPageCreator(new ModalWindow.PageCreator() {

            private static final long serialVersionUID = 1L;

            public Page createPage() {
                return new ComparePage(group, modalWindow, samePlayerRankParticipants);
            }
        });

    }

    private ModalWindow createDefaultModalWindow() {

        modalWindow = new ModalWindow("modalWindow");
        modalWindow.setResizable(false);
        modalWindow.setCssClassName(ModalWindow.CSS_CLASS_BLUE);

        modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

            private static final long serialVersionUID = 1L;

            public void onClose(AjaxRequestTarget target) {
                setResponsePage(GroupPage.class, getPageParameters());
            }
        });

        modalWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

            private static final long serialVersionUID = 1L;

            public boolean onCloseButtonClicked(AjaxRequestTarget target) {
                return true;
            }
        });
        return modalWindow;
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return Model.of(getString("group"));
    }

}