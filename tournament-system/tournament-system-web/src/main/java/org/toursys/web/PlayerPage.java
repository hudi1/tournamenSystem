package org.toursys.web;

import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.StringResourceModel;
import org.toursys.repository.model.Player;

public class PlayerPage extends BasePage {

    private static final long serialVersionUID = 1L;

    public PlayerPage() {
        createPage();
    }

    private void createPage() {
        IDataProvider<Player> playerDataProvider = new IDataProvider<Player>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Iterator<Player> iterator(int first, int count) {
                return tournamentService.getAllPlayer().subList(first, first + count).iterator();
            }

            @Override
            public int size() {
                return tournamentService.getAllPlayer().size();
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

        DataView<Player> dataView = new DataView<Player>("rows", playerDataProvider, 10) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Player> listItem) {
                final Player player = listItem.getModelObject();
                listItem.setModel(new CompoundPropertyModel<Player>(player));
                listItem.add(new Label("name", player.getName()));
                listItem.add(new Label("surname", player.getSurname()));
                listItem.add(new Label("club", player.getClub()));
                listItem.add(new EditPlayerForm(((Player) listItem.getDefaultModelObject()).clone()));
                listItem.add(new AjaxLink<Void>("deletePlayer") {

                    private static final long serialVersionUID = 1L;

                    public void onClick(AjaxRequestTarget target) {
                        try {
                            tournamentService.deletePlayer(player);
                        } catch (Exception e) {
                            error("Cannot delete player. Make sure this player is not in any tournament");
                        }

                        setResponsePage(new PlayerPage() {
                            private static final long serialVersionUID = 1L;
                        });
                    }

                    @Override
                    protected IAjaxCallDecorator getAjaxCallDecorator() {
                        return new AjaxCallDecorator() {

                            private static final long serialVersionUID = 1L;

                            @Override
                            public CharSequence decorateScript(Component c, CharSequence script) {
                                return "if(confirm('Opravdu odstranit tohto hrace ?')){" + script
                                        + "}else{return false;}";
                            }

                        };
                    }

                });

            }
        };

        add(dataView);
        add(new PagingNavigator("navigator", dataView));
        add(new PlayerForm());
        add(new FeedbackPanel("feedbackPanel"));

    }

    private class PlayerForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public PlayerForm() {
            super("playerForm");
            add(new Button("newPlayer", new StringResourceModel("newPlayer", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new PlayerEditPage(PlayerPage.this, new Player()) {

                        private static final long serialVersionUID = 1L;
                    });
                }
            });
        }
    }

    private class EditPlayerForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public EditPlayerForm(final Player player) {
            super("editPlayerForm");
            add(new Button("editPlayer", new StringResourceModel("editPlayer", null)) {

                private static final long serialVersionUID = 1L;

                private void edit() {
                    setResponsePage(new PlayerEditPage(PlayerPage.this, player) {

                        private static final long serialVersionUID = 1L;

                    });
                }

                @Override
                public void onSubmit() {
                    edit();
                }

            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new StringResourceModel("playerList", null);
    }

}
