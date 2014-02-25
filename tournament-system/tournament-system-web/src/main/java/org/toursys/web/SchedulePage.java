package org.toursys.web;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
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
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.TournamentImpl;

@AuthorizeInstantiation(Roles.USER)
public class SchedulePage extends BasePage {

    private static final long serialVersionUID = 1L;
    private Groups group;
    private TournamentImpl tournament;
    private List<GameImpl> schedule;

    public SchedulePage() {
        throw new RestartResponseAtInterceptPageException(TournamentPage.class);
    }

    public SchedulePage(PageParameters parameters) {
        checkPageParameters(parameters);
        tournament = getTournament();
        group = getGroup(parameters);
        this.schedule = getSchedule();
        createPage();
    }

    private void checkPageParameters(PageParameters parameters) {
        if (parameters.get("tournamentid").isNull() || parameters.get("seasonid").isNull()
                || parameters.get("groupid").isNull()) {
            throw new RestartResponseAtInterceptPageException(new SeasonPage());
        }
    }

    private void createPage() {
        add(new ScheduleForm());
    }

    private List<GameImpl> getSchedule() {
        List<Participant> participants = participantService.getParticipants(new Participant()._setGroup(group));
        return scheduleService.getSchedule(group, participants,
                participantService.getAdvancedPlayersByGroup(group, tournament, participants));
    }

    private class ScheduleForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public ScheduleForm() {
            super("scheduleForm");
            IDataProvider<GameImpl> dataProvider = new IDataProvider<GameImpl>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Iterator<GameImpl> iterator(int first, int count) {
                    return schedule.subList(first, first + count).iterator();
                }

                @Override
                public int size() {
                    return schedule.size();
                }

                @Override
                public IModel<GameImpl> model(final GameImpl object) {
                    return new LoadableDetachableModel<GameImpl>() {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected GameImpl load() {
                            return object;
                        }
                    };
                }

                @Override
                public void detach() {
                }
            };

            final DataView<GameImpl> dataView = new DataView<GameImpl>("rows", dataProvider) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final Item<GameImpl> listItem) {
                    final GameImpl game = listItem.getModelObject();
                    listItem.setModel(new CompoundPropertyModel<GameImpl>(game));
                    Participant participant = game.getHomeParticipant();
                    Participant opponent = game.getAwayParticipant();
                    String playerName = "-";
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

                    if (participant.getPlayer() != null) {
                        playerName = participant.getPlayer().getName() + " " + participant.getPlayer().getSurname()
                                + " " + participant.getPlayer().getPlayerDiscriminator();
                    }

                    if (opponent.getPlayer() != null) {
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

                    listItem.add(new TextField<String>("homeScore", new PropertyModel<String>(game, "homeScore")).add(
                            new AjaxFormComponentUpdatingBehavior("onchange") {

                                private static final long serialVersionUID = 1L;

                                @Override
                                protected void onUpdate(AjaxRequestTarget target) {
                                    gameService.updateBothGames(game);
                                }
                            }).setVisible(participant.getPlayer() != null));
                    listItem.add(new TextField<String>("awayScore", new PropertyModel<String>(game, "awayScore")).add(
                            new AjaxFormComponentUpdatingBehavior("onchange") {

                                private static final long serialVersionUID = 1L;

                                @Override
                                protected void onUpdate(AjaxRequestTarget target) {
                                    gameService.updateBothGames(game);
                                }
                            }).setVisible(participant.getPlayer() != null));

                    listItem.add(new Label("round", game.getRound().toString()));
                    listItem.add(new Label("hockey", game.getHockey().toString()));

                    listItem.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public String getObject() {
                            return (listItem.getIndex() % 2 == 1) ? "even" : "odd";
                        }
                    }));
                }
            };
            add(dataView);
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
                    setResponsePage(SchedulePage.class, getPageParameters());
                };
            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("schedule");
    }

}
