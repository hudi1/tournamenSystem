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

@AuthorizeInstantiation(Roles.USER)
public class PlayOffPage extends TournamentHomePage {

    private static final long serialVersionUID = 1L;

    public PlayOffPage() {
        this(new PageParameters());
    }

    public PlayOffPage(PageParameters parameters) {
        // checkPageParameters(parameters);
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
                    playOffGames = playOffGameService.getPlayOffGames(new PlayOffGame()._setGroup(group));

                    final ListView<PlayOffGame> dataView = new ListView<PlayOffGame>("rows", playOffGames) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void populateItem(final ListItem<PlayOffGame> listItem) {
                            final PlayOffGame playOffGame = listItem.getModelObject();
                            listItem.setModel(new CompoundPropertyModel<PlayOffGame>(playOffGame));
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
                                    + playerDiscriminator));
                            listItem.add(new Label("opponent", opponentName + " " + opponentSurname + " "
                                    + opponentDiscriminator));

                            int playerCount = getViewSize() + 1;
                            if (group.getPlayThirdPlace() && getViewSize() > 1) {
                                playerCount--;
                            }

                            listItem.add(new Label("round", new ResourceModel(TournamentUtil.getRoundName(playerCount,
                                    listItem.getIndex() + 1))));

                            listItem.add(new TextField<String>("results", new PropertyModel<String>(playOffGame,
                                    "results")).add(new AjaxFormComponentUpdatingBehavior("onchange") {

                                private static final long serialVersionUID = 1L;

                                @Override
                                protected void onUpdate(AjaxRequestTarget target) {
                                    try {
                                        playOffGameService.updatePlayOffGameResult(playOffGame);
                                    } catch (NumberFormatException e) {
                                        error(getString("bad.format.exception"));
                                        target.add(feedbackPanel);
                                    }
                                }
                            }));

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

            add(new Button("save", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    playOffGameService.updateNextRoundPlayOffGames(tournament);
                    setResponsePage(PlayOffPage.class, getPageParameters());
                };
            });

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
                    setResponsePage(FinalRankingPage.class, getPageParameters());
                }
            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("playOff");
    }

}
