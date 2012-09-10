package org.toursys.web;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.StringResourceModel;
import org.toursys.repository.form.PlayerForm;
import org.toursys.repository.form.PlayerResultForm;
import org.toursys.repository.form.TableForm;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Table;
import org.toursys.repository.model.TableType;
import org.toursys.repository.model.Tournament;
import org.wicketstuff.minis.behavior.spinner.Spinner;

public class RegistrationPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private Tournament tournament;
    private Player selectedPlayer;
    private BasePage basePage;
    private List<Player> notRegistratedPlayer;
    private List<PlayerResult> allPlayers;

    public RegistrationPage(Tournament tournament, BasePage basePage) {
        this(tournament, basePage, new Table());
    }

    public RegistrationPage(Tournament tournament, BasePage basePage, Table table) {
        this.basePage = basePage;
        this.tournament = tournament;
        this.notRegistratedPlayer = tournamentService.getNotRegistrationPlayer(new PlayerForm(tournament));

        Table basicTable = new Table();
        basicTable.setTableType(TableType.B);

        this.allPlayers = tournamentService.findPlayerResult(new PlayerResultForm(tournament, basicTable));
        createPage(table);
    }

    private class RegistrationForm extends Form<Table> {

        private static final long serialVersionUID = 1L;

        public RegistrationForm(final Table table) {
            super("registrationForm", new CompoundPropertyModel<Table>(table));
            setOutputMarkupId(true);
            final TextField<String> text = new TextField<String>("name");
            Spinner spinnerBehavior = new Spinner() {

                private static final long serialVersionUID = 1L;

                @Override
                protected void configure(Properties p) {
                    p.put("min", 1);
                    super.configure(p);
                }
            };
            text.add(spinnerBehavior);
            add(text);

            IDataProvider<Player> playerDataProvider = new IDataProvider<Player>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Iterator<Player> iterator(int first, int count) {
                    return notRegistratedPlayer.subList(first, first + count).iterator();
                }

                @Override
                public int size() {
                    return notRegistratedPlayer.size();
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
                    listItem.add(new AjaxEventBehavior("onclick") {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void onEvent(final AjaxRequestTarget target) {
                            Iterator<Item<Player>> items = getItems();

                            while (items.hasNext()) {
                                HighlitableDataItem<Player> hitem = (HighlitableDataItem<Player>) items.next();
                                if (hitem.equals(listItem)) {
                                    hitem.toggleHighlite();
                                } else {
                                    hitem.toggleOff();
                                }
                                target.add(hitem);
                            }
                            if (player.equals(selectedPlayer)) {
                                selectedPlayer = null;
                            } else {
                                selectedPlayer = player;
                            }
                        }
                    });

                    listItem.add(new AjaxEventBehavior("ondblclick") {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void onEvent(final AjaxRequestTarget target) {

                            // TODO vymysliet ako tu dostat hodnotu z textFieldu ktory ovlada spinner behavior ktory
                            // obsahuje meno tabulky
                        }

                    });

                    listItem.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public String getObject() {
                            return (listItem.getIndex() % 2 == 1) ? "even" : "odd";
                        }
                    }));
                }

                @Override
                protected Item<Player> newItem(String id, int index, IModel<Player> model) {
                    return new HighlitableDataItem<Player>(id, index, model);
                }

            };

            add(dataView);

            add(new Button("submit", new StringResourceModel("registrate", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {

                    if (selectedPlayer != null) {
                        tournamentService.createPlayerResult(tournament, selectedPlayer, table, TableType.B);
                    }
                    setResponsePage(new RegistrationPage(tournament, basePage, table));
                }
            });
        }
    }

    private class CreateTournamentForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public CreateTournamentForm() {
            super("createTournamentForm");
            setOutputMarkupId(true);

            add(new Button("submit", new StringResourceModel("createTournament", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    Table table = new Table();
                    List<Table> tables = tournamentService.findTable(new TableForm(null, tournament));
                    if (!tables.isEmpty()) {
                        table = tables.get(0);
                        List<PlayerResult> player = tournamentService.findPlayerResult(new PlayerResultForm(tournament,
                                table));
                        tournamentService.createGames(player);
                    }
                    setResponsePage(new GroupPage(tournament, table, basePage));
                }
            });

            add(new Button("back", new StringResourceModel("back", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(basePage);
                }
            });
        }
    }

    private void createPage(Table table) {

        IDataProvider<PlayerResult> registeredPlayerDataProvider = new IDataProvider<PlayerResult>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Iterator<PlayerResult> iterator(int first, int count) {
                return allPlayers.subList(first, first + count).iterator();
            }

            @Override
            public int size() {
                return allPlayers.size();
            }

            @Override
            public IModel<PlayerResult> model(final PlayerResult object) {
                return new LoadableDetachableModel<PlayerResult>() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    protected PlayerResult load() {
                        return object;
                    }
                };
            }

            @Override
            public void detach() {
            }
        };

        DataView<PlayerResult> registeredDataView = new DataView<PlayerResult>("registeredRows",
                registeredPlayerDataProvider) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<PlayerResult> listItem) {
                final PlayerResult playerResult = listItem.getModelObject();
                listItem.setModel(new CompoundPropertyModel<PlayerResult>(playerResult));
                listItem.add(new Label("name", playerResult.getPlayer().getName()));
                listItem.add(new Label("surname", playerResult.getPlayer().getSurname()));
                listItem.add(new Label("tableName", playerResult.getTournamentTable().getName()));
                listItem.add(new AjaxLink<Void>("deletePlayer") {

                    private static final long serialVersionUID = 1L;

                    public void onClick(AjaxRequestTarget target) {
                        tournamentService.deletePlayerResultAndTable(playerResult, tournament);

                        setResponsePage(new RegistrationPage(tournament, basePage) {
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

        add(registeredDataView);

        add(new FeedbackPanel("feedbackPanel"));
        add(new RegistrationForm(table));
        add(new CreateTournamentForm());

    }

    private static class HighlitableDataItem<T> extends Item<T> {
        private static final long serialVersionUID = 1L;

        private boolean highlite = false;

        public void toggleHighlite() {
            highlite = !highlite;
        }

        public void toggleOff() {
            highlite = false;
        }

        /**
         * Constructor
         * 
         * @param id
         * @param index
         * @param model
         */
        public HighlitableDataItem(String id, int index, IModel<T> model) {
            super(id, index, model);
            add(new AttributeModifier("style", "background-color:#80b6ed;") {
                private static final long serialVersionUID = 1L;

                @Override
                public boolean isEnabled(Component component) {
                    return HighlitableDataItem.this.highlite;
                }
            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new StringResourceModel("registration", null);
    }

}
