package org.toursys.web;

import java.io.File;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
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
import org.apache.wicket.util.convert.IConverter;
import org.toursys.processor.pdf.PdfFactory;
import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Result;
import org.toursys.repository.model.Tournament;
import org.toursys.web.components.TournamentButton;
import org.toursys.web.converter.ResultConverter;
import org.toursys.web.link.DownloadModelLink;
import org.toursys.web.model.TournamentFileReadOnlyModel;

@AuthorizeInstantiation(Roles.USER)
public class PlayOffPage extends TournamentHomePage {

    private static final long serialVersionUID = 1L;
    private Tournament playOffTournament;

    public PlayOffPage() {
        this(new PageParameters());
    }

    public PlayOffPage(PageParameters parameters) {
        super(parameters);
        createPage();
    }

    protected void createPage() {
        this.playOffTournament = initPlayOffTournament();
        add(new PlayOffForm(Model.of(playOffTournament)));
    }

    private Tournament initPlayOffTournament() {
        Tournament tournament = new Tournament();
        tournament.getGroups().addAll(groupService.getFinalGroups(this.tournament));
        for (Groups group : tournament.getGroups()) {
            group.getPlayOffGames().addAll(playOffGameService.getPlayOffGames(new PlayOffGame()._setGroup(group)));
        }
        return tournament;
    }

    private class PlayOffForm extends Form<Tournament> {

        private static final long serialVersionUID = 1L;

        public PlayOffForm(IModel<Tournament> model) {
            super("playOffForm", new CompoundPropertyModel<Tournament>(model));
            addPlayOffTable();
            addSubmitButton();
            addPdfPlayOffButton();
        }

        private void addPlayOffTable() {
            addPlayOffTableHeader();
            addFinalGroupsGameListView();
        }

        private void addPlayOffTableHeader() {

        }

        private void addFinalGroupsGameListView() {
            add(new PropertyListView<Groups>("groups") {

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

                            listItem.add(new TextField<Result>("result", new PropertyModel<Result>(playOffGame,
                                    "result")) {

                                private static final long serialVersionUID = 1L;

                                @Override
                                @SuppressWarnings("unchecked")
                                public final <Results> IConverter<Results> getConverter(Class<Results> type) {
                                    return (IConverter<Results>) ResultConverter.getInstance();
                                }
                            }.add(new AjaxFormComponentUpdatingBehavior("onchange") {

                                private static final long serialVersionUID = 1L;

                                @Override
                                protected void onUpdate(AjaxRequestTarget target) {
                                    playOffGameService.updatePlayOffGameResult(playOffGame);
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
            }.setReuseItems(true));
        }

        private void addPdfPlayOffButton() {
            add(new DownloadModelLink("printPlayOff", new TournamentFileReadOnlyModel() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getTournamentObject() {
                    return PdfFactory.createPlayOff(WicketApplication.getFilesPath(), playOffTournament);
                }

                @Override
                public Component getFormComponent() {
                    return PlayOffForm.this;
                }
            }));
        }

        private void addSubmitButton() {
            add(new TournamentButton("save", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    playOffGameService.updateNextRoundPlayOffGames(tournament);
                    finalStandingService.updatePromotingFinalStandings(tournament);
                    setResponsePage(PlayOffPage.class, getPageParameters());
                };
            });
        }
    }

}