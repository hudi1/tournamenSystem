package org.toursys.web;

import java.io.File;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
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
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;
import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffGameWinner;
import org.toursys.repository.model.Tournament;

@AuthorizeInstantiation(Roles.USER)
public class PlayOffPage extends TournamentHomePage {

    private static final long serialVersionUID = 1L;

    public PlayOffPage() {
        this(new PageParameters());
    }

    public PlayOffPage(PageParameters parameters) {
        super(parameters);
        createPage();
    }

    protected void createPage() {
        add(new PlayOffForm(Model.of(initPlayOffTournament())));
    }

    private Tournament initPlayOffTournament() {
        Tournament tournament = new Tournament();
        tournament.getGroups().addAll(groupService.getFinalGroups(new Groups()._setTournament(this.tournament)));
        for (Groups group : tournament.getGroups()) {
            group.getPlayOffGames().addAll(playOffGameService.getPlayOffGames(new PlayOffGame()._setGroup(group)));
        }
        return tournament;
    }

    private class PlayOffForm extends Form<Tournament> {

        private static final long serialVersionUID = 1L;

        public PlayOffForm(IModel<Tournament> model) {
            super("playOffForm", new CompoundPropertyModel<Tournament>(model));
            addFinalGroupsGameListView();
            addSubmitButton();
            addPdfPlayOffButton();
        }

        private void addFinalGroupsGameListView() {
            add(new PropertyListView<Groups>("groups") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<Groups> listItem) {
                    addPlayOffGames(listItem);
                }

                private void addPlayOffGames(final ListItem<Groups> groupListItem) {
                    groupListItem.add(new Label("name"));
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
                                    return PlayOffGameWinner.HOME.equals(playOffGame.getWinner());
                                }
                            }));
                            listItem.add(new Label("opponent", " " + opponentName + " " + opponentSurname + " "
                                    + opponentDiscriminator).add(new AttributeModifier("style", "font-weight:bold;") {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public boolean isEnabled(Component component) {
                                    return PlayOffGameWinner.AWAY.equals(playOffGame.getWinner());
                                }
                            }));

                            listItem.add(new Label("round", new ResourceModel(TournamentUtil.getRoundName(
                                    getViewSize(), listItem.getIndex() + 1))));

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
                                        return;
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
                    });
                }
            });
        }

        private void addPdfPlayOffButton() {
            add(new DownloadLink("pdfPlayOff", new AbstractReadOnlyModel<File>() {
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
            }).setCacheDuration(Duration.NONE).setDeleteAfterDownload(true));
        }

        private void addSubmitButton() {
            add(new Button("save", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    playOffGameService.updateNextRoundPlayOffGames(tournament);
                    finalStandingService.updatePromotingFinalStandings(tournament);
                    setResponsePage(PlayOffPage.class, getPageParameters());
                };
            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("playOff");
    }

}