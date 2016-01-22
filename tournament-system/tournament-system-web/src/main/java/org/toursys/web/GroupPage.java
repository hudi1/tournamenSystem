package org.toursys.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
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
import org.toursys.processor.pdf.PdfFactory;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.web.components.TournamentAjaxButton;
import org.toursys.web.components.TournamentButton;
import org.toursys.web.link.DownloadModelLink;
import org.toursys.web.model.TournamentFileReadOnlyModel;
import org.toursys.web.util.WebUtil;

@AuthorizeInstantiation(Roles.USER)
public class GroupPage extends TournamentHomePage {

    private static final long serialVersionUID = 1L;

    private Groups group;
    private List<Participant> participants;
    private ModalWindow modalWindow;

    public GroupPage() {
        this(new PageParameters());
    }

    public GroupPage(PageParameters parameters) {
        super(parameters);

        prepareData(parameters);
        clearPageParameters();
        createPage();
    }

    private void prepareData(PageParameters parameters) {
        group = getGroup(parameters, getTournament(parameters));

        if (group != null) {
            this.participants = participantService.getParticipandByGroup(group);
            setUpGoldGoalModalWindow();
        } else {
            participants = new ArrayList<Participant>();
        }

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

        private void addGroupOptionsButton() {
            addScheduleButton();
            addOptionsButton();
            addSheetsButton();
            addPrintScheduleButton();
            addFinalGroupButton();
            addCopyResultButton();
            addPrintGroupButton();
            addEqualRankButton();
        }

        private Button addEqualRankButton() {
            Button equalRankButton = new TournamentAjaxButton("editEqualRank", new ResourceModel("editEqualRank")) {

                private static final long serialVersionUID = 1L;

                protected void submit(AjaxRequestTarget target, Form<?> form) {
                    modalWindow.show(target);
                }
            };

            add(equalRankButton);
            equalRankButton.setVisible(modalWindow != null);
            return equalRankButton;
        }

        private DownloadLink addPrintGroupButton() {
            DownloadLink printGroup = new DownloadModelLink("printGroup", new TournamentFileReadOnlyModel() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getTournamentObject() {
                    return PdfFactory.createTable(WicketApplication.getFilesPath(), participants, group);
                }

                @Override
                public Component getFormComponent() {
                    return GroupForm.this;
                }
            });

            add(printGroup);
            printGroup.setVisible(group != null);
            return printGroup;
        }

        private Button addCopyResultButton() {
            Button copyResult = new TournamentButton("copyResult", new ResourceModel("copyResult")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    groupService.copyResult(tournament);
                    setResponsePage(GroupPage.class, getPageParameters());
                }
            };
            add(copyResult);
            copyResult.setVisible(group != null);
            return copyResult;
        }

        private Button addFinalGroupButton() {
            Button finalGroup = new TournamentButton("finalGroup", new ResourceModel("finalGroup")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    groupService.processFinalGroup(tournament);
                    setResponsePage(GroupPage.class, getPageParameters());
                }
            };

            add(finalGroup);
            finalGroup.setVisible(group != null);
            return finalGroup;
        }

        private DownloadLink addPrintScheduleButton() {
            DownloadLink printSchedule = new DownloadModelLink("printSchedule", new TournamentFileReadOnlyModel() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getTournamentObject() {
                    return PdfFactory.createSchedule(WicketApplication.getFilesPath(),
                            scheduleService.getSchedule(tournament, group, participants).getSchedule());
                }

                @Override
                public Component getFormComponent() {
                    return GroupForm.this;
                }

            });

            add(printSchedule);
            printSchedule.setVisible(group != null);
            return printSchedule;
        }

        private DownloadLink addSheetsButton() {
            DownloadLink sheets = new DownloadModelLink("sheets", new TournamentFileReadOnlyModel() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getTournamentObject() {
                    return PdfFactory.createSheets(WicketApplication.getFilesPath(),
                            scheduleService.getSchedule(tournament, group, participants).getSchedule(), group);
                }

                @Override
                public Component getFormComponent() {
                    return GroupForm.this;
                }
            });
            add(sheets);
            sheets.setVisible(group != null);
            return sheets;
        }

        private Button addScheduleButton() {
            Button schedule = new TournamentButton("schedule", new ResourceModel("schedule")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    getPageParameters().set(GID, group.getId());
                    setResponsePage(SchedulePage.class, getPageParameters());
                }
            };
            add(schedule);
            schedule.setVisible(group != null);
            return schedule;
        }

        private Button addOptionsButton() {
            Button options = new TournamentButton("options", new ResourceModel("options")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    getPageParameters().set(GID, group.getId());
                    getPageParameters().set(SHOW_TABLE_OPTIONS, true);
                    getPageParameters().set(SHOW_TOURNAMENT_OPTIONS, false);
                    setResponsePage(TournamentOptionsPage.class, getPageParameters());
                }
            };

            add(options);
            options.setVisible(group != null);
            return options;
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
                    listItem.add(new Label("name", WebUtil.getParticipandPlayerShortName(participant)));
                    listItem.add(new Label("score", participant.getScore().toString()));
                    listItem.add(new Label("points", ((Integer) participant.getPoints()).toString()));
                    listItem.add(new Label("rank", (participant.getRank() != null) ? participant.getRank().toString()
                            : " "));

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

    }

    private void setUpGoldGoalModalWindow() {
        Set<Participant> goldGoalParticipants = participantService.getGoldGoalParticipants(participants);

        if (!goldGoalParticipants.isEmpty()) {
            createModalWindow(goldGoalParticipants);
        }
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

}