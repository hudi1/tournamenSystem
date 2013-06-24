package org.toursys.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.sqlproc.engine.SqlProcessorException;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.User;
import org.toursys.web.session.TournamentAuthenticatedWebSession;

@AuthorizeInstantiation(Roles.USER)
public class PlayerPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private static final int ITEMS_PER_PAGE = 10;
    private User user;

    public PlayerPage() {
        this.user = ((TournamentAuthenticatedWebSession) getSession()).getUser();
        createPage();
    }

    protected void createPage() {
        IDataProvider<Player> playerDataProvider = createPlayerProvider();
        DataView<Player> dataView = createDataview(playerDataProvider);
        add(dataView);
        add(new PagingNavigator("navigator", dataView));
        add(new PlayerForm());
    }

    private DataView<Player> createDataview(IDataProvider<Player> playerDataProvider) {
        final DataView<Player> dataView = new DataView<Player>("rows", playerDataProvider, ITEMS_PER_PAGE) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Player> listItem) {
                final Player player = listItem.getModelObject();
                listItem.setModel(new CompoundPropertyModel<Player>(player));
                listItem.add(new Label("name", player.getName()));
                listItem.add(new Label("surname", player.getSurname()));
                listItem.add(new Label("club", player.getClub()));
                // TODO zisti preco to funguje aj bez clone
                listItem.add(new EditPlayerForm(((Player) listItem.getDefaultModelObject())));
                listItem.add(new AjaxLink<Void>("deletePlayer") {

                    private static final long serialVersionUID = 1L;

                    public void onClick(AjaxRequestTarget target) {
                        try {
                            tournamentService.deletePlayer(player);
                        } catch (SqlProcessorException e) {
                            logger.error("Error delete player: ", e);
                            error(getString("error.del.player"));
                        }
                        setResponsePage(new PlayerPage());
                    }

                    @Override
                    protected IAjaxCallDecorator getAjaxCallDecorator() {
                        return new AjaxCallDecorator() {

                            private static final long serialVersionUID = 1L;

                            @Override
                            public CharSequence decorateScript(Component c, CharSequence script) {
                                return "if(confirm(" + getString("del.player") + ")){" + script
                                        + "}else{return false;}";
                            }
                        };
                    }
                });
            }
        };

        return dataView;
    }

    private IDataProvider<Player> createPlayerProvider() {

        IDataProvider<Player> playerDataProvider = new IDataProvider<Player>() {

            private static final long serialVersionUID = 1L;
            private List<Player> players = tournamentService.getListPlayer(new Player()._setUser(user));

            @Override
            public Iterator<Player> iterator(int first, int count) {
                List<Player> allPlayers = players;
                Collections.sort(allPlayers, new Comparator<Player>() {
                    @Override
                    public int compare(Player p1, Player p2) {
                        return p1.getSurname().compareTo(p2.getSurname());
                    }
                });
                return allPlayers.subList(first, first + count).iterator();
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

        return playerDataProvider;
    }

    private class PlayerForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public PlayerForm() {
            super("playerForm");
            add(new Button("newPlayer", new ResourceModel("newPlayer")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new PlayerEditPage(new Player()._setUser(user)));
                }
            });
        }
    }

    private class EditPlayerForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public EditPlayerForm(final Player player) {
            super("editPlayerForm");
            add(new Button("editPlayer", new ResourceModel("editPlayer")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new PlayerEditPage(player));
                }
            });
        }
    }

    @Override
    protected void setVisibility() {
        if (!((TournamentAuthenticatedWebSession) getSession()).isSignedIn()) {
            setVisible(false);
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("playerList");
    }

}
