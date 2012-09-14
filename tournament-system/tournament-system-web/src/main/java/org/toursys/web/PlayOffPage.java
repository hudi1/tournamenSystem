package org.toursys.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
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
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.util.value.ValueMap;
import org.toursys.processor.pdf.PdfFactory;
import org.toursys.repository.form.GameForm;
import org.toursys.repository.form.TableForm;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Tournament;

public class PlayOffPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private Tournament tournament;
    private BasePage basePage;
    private List<Game> games;

    public PlayOffPage(Tournament tournament, BasePage basePage) {
        this.tournament = tournament;
        this.basePage = basePage;
        this.games = tournamentService.createPlayOffGames(tournament);
        createPage();
    }

    private void createPage() {
        add(new PlayOffForm());
    }

    private class PlayOffForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public PlayOffForm() {
            super("playOffForm");
            final IDataProvider<Game> dataProvider = new IDataProvider<Game>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Iterator<Game> iterator(int first, int count) {
                    return games.subList(first, first + count).iterator();
                }

                @Override
                public int size() {
                    return games.size();
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
                private Integer round = 1;
                private byte[] nextTableByte = new byte[1];

                @Override
                protected void populateItem(final Item<Game> listItem) {
                    final Game game = listItem.getModelObject();
                    listItem.setModel(new CompoundPropertyModel<Game>(game));
                    PlayerResult playerResult = game.getPlayerResult();
                    PlayerResult opponent = game.getOpponent();

                    if (listItem.getIndex() == 0) {
                        nextTableByte[0] = (byte) 65;
                    }

                    List<Game> actualGames;
                    if (playerResult != null && opponent != null) {
                        listItem.add(new Label("players", (playerResult.getPlayer() == null) ? "-" : playerResult
                                .getPlayer().getName()
                                + " "
                                + playerResult.getPlayer().getSurname()
                                + " : "
                                + ((opponent.getPlayer() == null) ? "-" : opponent.getPlayer().getName() + " "
                                        + opponent.getPlayer().getSurname())));
                        actualGames = tournamentService.findGame(new GameForm(playerResult, opponent));
                    } else if (game.getGameId() == 0) {

                        ValueMap map = new ValueMap();
                        map.put("roundCount", round);
                        if (getItemCount() != listItem.getIndex() + 1) {
                            listItem.add(new Label("players",
                                    new StringResourceModel("round", new Model<ValueMap>(map))));
                            round++;
                        } else {
                            listItem.add(new Label("players", ""));
                        }
                        actualGames = new ArrayList<Game>();
                    } else {
                        ValueMap map = new ValueMap();
                        map.put("groupName", new String(nextTableByte));
                        listItem.add(new Label("players", new StringResourceModel("group", new Model<ValueMap>(map))));
                        actualGames = new ArrayList<Game>();
                        round = 1;
                        nextTableByte[0]++;
                    }

                    ListView<Game> gameList = new ListView<Game>("gameList", actualGames) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void populateItem(ListItem<Game> gameItem) {
                            final Game game = gameItem.getModelObject();

                            gameItem.add(new TextField<String>("leftSide", new PropertyModel<String>(game,
                                    "result.leftSide")));
                            gameItem.add(new TextField<String>("rightSide", new PropertyModel<String>(game,
                                    "result.rightSideOvertime")));
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
            add(dataView);
            add(new Button("back", new StringResourceModel("back", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new GroupPage(tournament, tournamentService.findTable(new TableForm(tournament))
                            .get(0), basePage));
                }
            });

            add(new Button("submit", new StringResourceModel("save", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {

                    for (int i = 0; i < dataProvider.size(); i++) {
                        Item<Game> a = (Item<Game>) dataView.get(i);
                        ListView<Game> b = (ListView<Game>) a.get(1);
                        List<? extends Game> c = b.getList();
                        for (Game game : c) {
                            tournamentService.updateGame(game);
                        }
                    }

                    setResponsePage(new PlayOffPage(tournament, basePage));
                }
            });

            DownloadLink pdfPlayOff = new DownloadLink("pdfPlayOff", new AbstractReadOnlyModel<File>() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getObject() {
                    File tempFile;
                    try {
                        tempFile = PdfFactory.createPlayOff(WicketApplication.getFilesPath(), games);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    return tempFile;
                }
            }/* , new ResourceModel("sheets") /* TODO this doesnt work ?? */);
            pdfPlayOff.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);

            add(pdfPlayOff);
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new StringResourceModel("playOff", null);
    }

}
