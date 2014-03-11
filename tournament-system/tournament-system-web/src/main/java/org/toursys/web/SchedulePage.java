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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.toursys.processor.schedule.RoundRobinSchedule;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.TournamentImpl;

@AuthorizeInstantiation(Roles.USER)
public class SchedulePage extends TournamentHomePage {

    private static final long serialVersionUID = 1L;
    private Groups group;
    private TournamentImpl tournament;
    private RoundRobinSchedule schedule;

    public SchedulePage() {
        throw new RestartResponseAtInterceptPageException(GroupPage.class);
    }

    public SchedulePage(PageParameters parameters) {
        tournament = getTournament(parameters);
        group = getGroup(parameters);
        this.schedule = getSchedule();
        createPage();
    }

    private void createPage() {
        add(new ScheduleForm(Model.of(schedule)));
    }

    private RoundRobinSchedule getSchedule() {
        logger.debug("Creating list to dataList start");
        long time = System.currentTimeMillis();
        List<Participant> participants = participantService.getParticipants(new Participant()._setGroup(group));
        RoundRobinSchedule a = scheduleService.getSchedule(group, participants,
                participantService.getAdvancedPlayersByGroup(group, tournament, participants));
        time = System.currentTimeMillis() - time;
        logger.debug("List: " + time + " ms");
        return a;
    }

    private class ScheduleForm extends Form<RoundRobinSchedule> {

        private static final long serialVersionUID = 1L;

        private void addScheduleListView() {
            add(new PropertyListView<GameImpl>("schedule") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<GameImpl> listItem) {
                    final GameImpl game = listItem.getModelObject();
                    Participant participant = game.getHomeParticipant();
                    Participant opponent = game.getAwayParticipant();
                    String playerName = "- ";
                    String opponentName = "-";
                    boolean winnerPlayer = false;
                    boolean winnerOpponent = false;

                    if (game.getAwayScore() != null && game.getHomeScore() != null) {
                        if (game.getAwayScore() < game.getHomeScore()) {
                            winnerPlayer = true;
                        } else if (game.getAwayScore() > game.getHomeScore()) {
                            winnerOpponent = true;
                        }
                    }

                    final boolean winner1 = winnerPlayer;
                    final boolean winner2 = winnerOpponent;

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
                                    return winner1;
                                }
                            }));

                    listItem.add(new Label("opponent", opponentName).add(new AttributeModifier("style",
                            "font-weight:bold;") {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public boolean isEnabled(Component component) {
                            return winner2;
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
            });
        }

        public ScheduleForm(IModel<RoundRobinSchedule> model) {
            super("scheduleForm", new CompoundPropertyModel<RoundRobinSchedule>(model));

            addScheduleListView();
            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    getPageParameters().set("update", true);
                    setResponsePage(GroupPage.class, getPageParameters());
                };
            }.setDefaultFormProcessing(false));

            add(new Button("save", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    logger.debug("Start submit ");
                    long time = System.currentTimeMillis();
                    gameService.updateBothGames(schedule.getSchedule());
                    time = System.currentTimeMillis() - time;
                    logger.debug("End submit: " + time + " ms");

                    setResponsePage(SchedulePage.class, getPageParameters());
                };
            });
        }
    }

    @Override
    protected void onBeforeRender() {
        logger.debug("Before render start");
        long time = System.currentTimeMillis();
        super.onBeforeRender();
        time = System.currentTimeMillis() - time;
        logger.debug("Before render: " + time + " ms");
    };

    @Override
    protected void onRender() {
        logger.debug("Render start");
        long time = System.currentTimeMillis();
        super.onRender();
        time = System.currentTimeMillis() - time;
        logger.debug("Render: " + time + " ms");
    };

    @Override
    protected void onAfterRender() {
        logger.debug("After render start");
        long time = System.currentTimeMillis();
        super.onAfterRender();
        time = System.currentTimeMillis() - time;
        logger.debug("After render: " + time + " ms");
    };

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("schedule");
    }

}
