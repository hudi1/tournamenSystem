package org.toursys.web;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RestartResponseAtInterceptPageException;
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
import org.toursys.repository.model.Game;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.TournamentImpl;

public class SchedulePage extends BasePage {

    private static final long serialVersionUID = 1L;
    private Groups group;
    private TournamentImpl tournament;
    private List<GameImpl> schedule;

    public SchedulePage() {
        throw new RestartResponseAtInterceptPageException(new SeasonPage());
    }

    public SchedulePage(TournamentImpl tournament, Groups group) {
        this.group = group;
        this.tournament = tournament;
        this.schedule = getSchedule();
        createPage();
    }

    private void createPage() {
        add(new ScheduleForm());
    }

    private List<GameImpl> getSchedule() {
        return tournamentService.getSchedule(group, tournament,
                tournamentService.getPlayerResultInGroup(new PlayerResult()._setGroup(group)));
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
                    PlayerResult playerResult = game.getHomePlayerResult();
                    PlayerResult opponent = game.getAwayPlayerResult();
                    listItem.add(new Label("players", (playerResult.getPlayer() == null) ? "-" : playerResult
                            .getPlayer().getName()
                            + " "
                            + playerResult.getPlayer().getSurname()
                            + " : "
                            + ((opponent.getPlayer() == null) ? "-" : opponent.getPlayer().getName() + " "
                                    + opponent.getPlayer().getSurname())));

                    listItem.add(new TextField<String>("homeScore", new PropertyModel<String>(game, "homeScore")));
                    listItem.add(new TextField<String>("awayScore", new PropertyModel<String>(game, "awayScore")));
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
                    setResponsePage(new GroupPage(tournament, group, true));
                }
            });

            add(new Button("submit", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    Iterator<Item<GameImpl>> games = dataView.getItems();
                    while (games.hasNext()) {
                        Game game = games.next().getModelObject();
                        tournamentService.updateGame(game);
                    }
                }
            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("schedule");
    }

}
