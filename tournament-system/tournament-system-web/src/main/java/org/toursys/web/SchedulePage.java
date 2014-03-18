package org.toursys.web;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.toursys.processor.schedule.RoundRobinSchedule;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Tournament;

@AuthorizeInstantiation(Roles.USER)
public class SchedulePage extends TournamentHomePage {

    private static final long serialVersionUID = 1L;
    private Groups group;
    private Tournament tournament;
    private List<GameImpl> scheduleGames;

    public SchedulePage() {
        throw new RestartResponseAtInterceptPageException(GroupPage.class);
    }

    public SchedulePage(PageParameters parameters) {
        this.tournament = getTournament(parameters);
        this.group = getGroup(parameters);
        this.scheduleGames = getSchedule().getSchedule();
        createPage();
    }

    private void createPage() {
        add(new ScheduleForm());
    }

    private RoundRobinSchedule getSchedule() {
        logger.debug("Creating schedule start");
        long time = System.currentTimeMillis();
        List<Participant> participants = participantService.getParticipants(new Participant()._setGroup(group));
        RoundRobinSchedule schedule = scheduleService.getSchedule(tournament, group, participants);
        time = System.currentTimeMillis() - time;
        logger.debug("Creating schedule end: " + time + " ms");
        return schedule;
    }

    private class ScheduleForm extends Form<RoundRobinSchedule> {

        private static final long serialVersionUID = 1L;

        public ScheduleForm() {
            super("scheduleForm");

            addScheduleListView();
            addBackButton();
            addSaveButton();
        }

        private void addSaveButton() {
            add(new Button("save", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    logger.debug("Submit schedule start ");
                    long time = System.currentTimeMillis();
                    gameService.updateBothGames(scheduleGames);
                    time = System.currentTimeMillis() - time;
                    logger.debug("Submit schedule end: " + time + " ms");

                    setResponsePage(SchedulePage.class, getPageParameters());
                };
            });
        }

        private void addBackButton() {
            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    getPageParameters().remove("update");
                    setResponsePage(GroupPage.class, getPageParameters());
                };
            }.setDefaultFormProcessing(false));
        }

        private void addScheduleListView() {
            add(new PropertyListView<GameImpl>("schedule", scheduleGames) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<GameImpl> listItem) {
                    final GameImpl game = listItem.getModelObject();
                    Participant participant = game.getHomeParticipant();
                    Participant opponent = game.getAwayParticipant();
                    String playerName = "- ";
                    String opponentName = "-";
                    boolean winner = false;

                    if (game.getAwayScore() != null && game.getHomeScore() != null) {
                        if (game.getAwayScore() < game.getHomeScore()) {
                            winner = true;
                        } else if (game.getAwayScore() > game.getHomeScore()) {
                            winner = true;
                        }
                    }

                    final boolean gameWinner = winner;

                    if (participant != null && participant.getPlayer() != null) {
                        playerName = participant.getPlayer().getName() + " " + participant.getPlayer().getSurname()
                                + " " + participant.getPlayer().getPlayerDiscriminator();
                    }

                    if (opponent != null && opponent.getPlayer() != null) {
                        opponentName = opponent.getPlayer().getName() + " " + opponent.getPlayer().getSurname() + " "
                                + opponent.getPlayer().getPlayerDiscriminator();
                    }

                    listItem.add(new Label("player", playerName)
                            .add(new AttributeModifier("style", "font-weight:bold;") {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public boolean isEnabled(Component component) {
                                    return gameWinner;
                                }
                            }));

                    listItem.add(new Label("opponent", opponentName).add(new AttributeModifier("style",
                            "font-weight:bold;") {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public boolean isEnabled(Component component) {
                            return gameWinner;
                        }
                    }));

                    listItem.add(new TextField<String>("homeScore"));
                    listItem.add(new TextField<String>("awayScore"));

                    listItem.add(new Label("round"));
                    listItem.add(new Label("hockey"));

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
        return new ResourceModel("schedule");
    }

}
