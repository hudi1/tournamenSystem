package org.toursys.web;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;
import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffResult;
import org.toursys.repository.model.TournamentImpl;

@AuthorizeInstantiation(Roles.USER)
public class PlayOffPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private TournamentImpl tournament;

    public PlayOffPage() {
        throw new RestartResponseAtInterceptPageException(new SeasonPage());
    }

    public PlayOffPage(PageParameters parameters) {
        checkPageParameters(parameters);
        tournament = getTournament(parameters);
        createPage();
    }

    private void checkPageParameters(PageParameters parameters) {
        if (parameters.get("tournamentid").isNull() || parameters.get("seasonid").isNull()
                || parameters.get("groupid").isNull()) {
            throw new RestartResponseAtInterceptPageException(new SeasonPage());
        }
    }

    protected void createPage() {
        add(new PlayOffForm());
    }

    private class PlayOffForm extends Form<Void> {

        private static final long serialVersionUID = 1L;
        private List<Groups> finalGroups;

        public PlayOffForm() {
            super("playOffForm");
            finalGroups = groupService.getFinalGroups(new Groups()._setTournament(tournament));

            final IDataProvider<Groups> groupsProvider = new IDataProvider<Groups>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Iterator<Groups> iterator(int first, int count) {
                    return finalGroups.subList(first, first + count).iterator();
                }

                @Override
                public int size() {
                    return finalGroups.size();
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

            final DataView<Groups> groupsDataView = new DataView<Groups>("group", groupsProvider) {

                private static final long serialVersionUID = 1L;
                private List<PlayOffGame> playOffGames;

                @Override
                protected void populateItem(final Item<Groups> listItem) {
                    final Groups group = listItem.getModelObject();
                    playOffGames = playOffGameService.processPlayOffGames(tournament, group);

                    final ListView<PlayOffGame> dataView = new ListView<PlayOffGame>("rows", playOffGames) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void populateItem(final ListItem<PlayOffGame> listItem) {
                            final PlayOffGame playOffGame = listItem.getModelObject();
                            listItem.setModel(new CompoundPropertyModel<PlayOffGame>(playOffGame));
                            Participant participant = playOffGame.getHomeParticipant();
                            Participant opponentResult = playOffGame.getAwayParticipant();

                            listItem.add(new Label(
                                    "players",
                                    ((participant != null && participant.getPlayer() != null) ? (participant
                                            .getPlayer().getName() + " " + participant.getPlayer().getSurname()) : " ")
                                            + "           :           "
                                            + ((opponentResult != null && opponentResult.getPlayer() != null) ? (opponentResult
                                                    .getPlayer().getName() + " " + opponentResult.getPlayer()
                                                    .getSurname()) : " ")));

                            int playerCount = getViewSize() + 1;
                            if (group.getPlayThirdPlace() && getViewSize() > 1) {
                                playerCount--;
                            }

                            listItem.add(new Label("round", TournamentUtil.getRound(playerCount,
                                    listItem.getIndex() + 1) + "."));

                            ListView<PlayOffResult> gameList = new ListView<PlayOffResult>("gameList",
                                    playOffGame.getPlayOffResults()) {

                                private static final long serialVersionUID = 1L;

                                @Override
                                protected void populateItem(ListItem<PlayOffResult> gameItem) {
                                    final PlayOffResult playOffResult = gameItem.getModelObject();
                                    gameItem.setModel(new CompoundPropertyModel<PlayOffResult>(playOffResult));

                                    gameItem.add(new TextField<Integer>("homeScore", new PropertyModel<Integer>(
                                            playOffResult, "homeScore")).add(new AjaxFormComponentUpdatingBehavior(
                                            "onchange") {

                                        private static final long serialVersionUID = 1L;

                                        @Override
                                        protected void onUpdate(AjaxRequestTarget target) {
                                            playOffResultService.updatePlayOffResult(playOffResult);
                                        }
                                    }));
                                    gameItem.add(new TextField<String>("awayScore", new PropertyModel<String>(
                                            playOffResult, "awayScore") {

                                        private static final long serialVersionUID = 1L;

                                        @Override
                                        public String getObject() {
                                            Object value = super.getObject();
                                            if (value == null) {
                                                return null;
                                            }
                                            if (playOffResult.getOvertime()) {
                                                return value + "P";
                                            }
                                            return value.toString();
                                        }
                                    }) {

                                        private static final long serialVersionUID = 1L;

                                        @Override
                                        protected void convertInput() {
                                            if (getInput().isEmpty()) {
                                                super.convertInput();
                                            } else {
                                                try {
                                                    setConvertedInput(Integer.parseInt(getInput()) + "");
                                                } catch (NumberFormatException e) {
                                                    try {
                                                        int result = Integer.parseInt(getInput().substring(0,
                                                                getInput().length() - 1));
                                                        playOffResult.setOvertime(true);
                                                        setConvertedInput(result + "");
                                                    } catch (NumberFormatException e1) {
                                                        super.convertInput();
                                                    }
                                                }
                                            }
                                        }
                                    }.add(new AjaxFormComponentUpdatingBehavior("onchange") {

                                        private static final long serialVersionUID = 1L;

                                        @Override
                                        protected void onUpdate(AjaxRequestTarget target) {
                                            playOffResultService.updatePlayOffResult(playOffResult);
                                        }
                                    }));
                                }
                            };

                            listItem.add(gameList);

                            listItem.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public String getObject() {
                                    return (listItem.getIndex() % 2 == 1) ? "even" : "odd";
                                }
                            }));

                        }
                    };

                    listItem.add(new Label("name", group.getName()));
                    listItem.add(dataView);

                }
            };

            add(groupsDataView);

            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(GroupPage.class, getPageParameters());
                };
            }.setDefaultFormProcessing(false));

            DownloadLink pdfPlayOff = new DownloadLink("pdfPlayOff", new AbstractReadOnlyModel<File>() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getObject() {
                    File tempFile = null;
                    try {
                        // tempFile = PdfFactory.createPlayOff(WicketApplication.getFilesPath(), plaOffGamesByGroup);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    return tempFile;
                }
            });
            pdfPlayOff.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);

            add(pdfPlayOff);

            add(new Button("finalStandings", new ResourceModel("finalStandings")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    // tournamentService.createFinalStandings(tournament);
                    setResponsePage(FinalStandingsPage.class, getPageParameters());
                }
            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("playOff");
    }

}
