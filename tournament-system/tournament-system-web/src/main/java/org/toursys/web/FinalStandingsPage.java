package org.toursys.web;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.time.Duration;
import org.toursys.processor.pdf.PdfFactory;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.TournamentImpl;

public class FinalStandingsPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private List<Player> players;
    private TournamentImpl tournament;

    public FinalStandingsPage() {
        throw new RestartResponseAtInterceptPageException(new SeasonPage());
    }

    public FinalStandingsPage(TournamentImpl tournament, Map<Groups, List<PlayOffGame>> plaOffGamesByGroup) {
        this.tournament = tournament;
        players = tournamentService.createFinalStandings(plaOffGamesByGroup);
        createPage();
    }

    private void createPage() {
        add(new FinalStandingsForm());
    }

    private class FinalStandingsForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public FinalStandingsForm() {
            super("finalStandingsForm");
            setOutputMarkupId(true);

            IDataProvider<Player> playerDataProvider = new IDataProvider<Player>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Iterator<Player> iterator(int first, int count) {
                    return players.subList(first, first + count).iterator();
                }

                @Override
                public int size() {
                    return players.size();
                }

                @Override
                public IModel<Player> model(final Player object) {
                    return new LoadableDetachableModel<Player>() {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected Player load() {
                            return object;
                        }
                    };
                }

                @Override
                public void detach() {
                }
            };

            DataView<Player> dataView = new DataView<Player>("rows", playerDataProvider) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final Item<Player> listItem) {
                    final Player player = listItem.getModelObject();
                    listItem.setModel(new CompoundPropertyModel<Player>(player));
                    listItem.add(new Label("name", player.getName()));
                    listItem.add(new Label("surname", player.getSurname()));
                    listItem.add(new Label("rank", listItem.getIndex() + 1 + "."));

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
                    setResponsePage(new PlayOffPage(tournament));
                }
            });

            DownloadLink finalStandings = new DownloadLink("finalStandings", new AbstractReadOnlyModel<File>() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getObject() {
                    File tempFile;
                    try {
                        tempFile = PdfFactory.createFinalStandings(WicketApplication.getFilesPath(), players);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    return tempFile;
                }
            });
            finalStandings.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);

            add(finalStandings);
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("finalStandings");
    }

}
