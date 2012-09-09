package org.toursys.web;

import java.util.Iterator;

import org.apache.wicket.AttributeModifier;
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
import org.apache.wicket.model.StringResourceModel;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Table;
import org.toursys.repository.model.Tournament;

public class SchedulePage extends BasePage {

    private static final long serialVersionUID = 1L;
    private Table table;
    private Tournament tournament;
    private BasePage basePage;

    public SchedulePage(Tournament tournament, Table table, BasePage basePage) {
        this.table = table;
        this.tournament = tournament;
        this.basePage = basePage;
        createPage();
    }

    private void createPage() {
        add(new ScheduleForm());
    }

    private class ScheduleForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public ScheduleForm() {
            super("scheduleForm");
            IDataProvider<Game> dataProvider = new IDataProvider<Game>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Iterator<Game> iterator(int first, int count) {
                    return tournamentService.getSchedule(table, tournament).subList(first, first + count).iterator();
                }

                @Override
                public int size() {
                    return tournamentService.getSchedule(table, tournament).size();
                }

                @Override
                public IModel<Game> model(final Game object) {
                    return new LoadableDetachableModel<Game>() {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected Game load() {
                            return object;
                        }
                    };
                }

                @Override
                public void detach() {
                }
            };

            final DataView<Game> dataView = new DataView<Game>("rows", dataProvider) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final Item<Game> listItem) {
                    final Game game = listItem.getModelObject();
                    listItem.setModel(new CompoundPropertyModel<Game>(game));
                    PlayerResult playerResult = game.getPlayerResult();
                    PlayerResult opponent = game.getOpponent();
                    listItem.add(new Label("players", (playerResult.getPlayer() == null) ? "-" : playerResult
                            .getPlayer().getName()
                            + " "
                            + playerResult.getPlayer().getSurname()
                            + " : "
                            + ((opponent.getPlayer() == null) ? "-" : opponent.getPlayer().getName() + " "
                                    + opponent.getPlayer().getSurname())));

                    listItem.add(new TextField<String>("leftSide", new PropertyModel<String>(game, "result.leftSide")));
                    listItem.add(new TextField<String>("rightSide", new PropertyModel<String>(game, "result.rightSide")));
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
            add(new Button("back", new StringResourceModel("back", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new GroupPage(tournament, table, basePage));
                }
            });

            add(new Button("submit", new StringResourceModel("save", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    Iterator<Item<Game>> a = dataView.getItems();
                    while (a.hasNext()) {
                        Game game = a.next().getModelObject();
                        tournamentService.updateGame(game);
                    }
                    setResponsePage(new SchedulePage(tournament, table, basePage));
                }
            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new StringResourceModel("schedule", null);
    }

}
