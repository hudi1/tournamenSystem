package org.toursys.web;

import java.util.Iterator;
import java.util.Properties;

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
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.toursys.processor.service.TournamentService;
import org.toursys.repository.form.PlayerForm;
import org.toursys.repository.form.PlayerResultForm;
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

    @SpringBean(name = "tournamentService")
    TournamentService tournamentService;

    public RegistrationPage() {
        this(new Tournament(), new Table());
    }

    public RegistrationPage(Tournament tournament, Table table) {
        this.tournament = tournament;
        createPage(table);
    }

    private class RegistrationForm extends Form<Table> {

        private static final long serialVersionUID = 1L;

        public RegistrationForm(final Table table) {
            super("registrationForm", new CompoundPropertyModel<Table>(table));
            setOutputMarkupId(true);
            TextField<String> text = new TextField<String>("name");
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
            add(new Button("submit") {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {

                    if (selectedPlayer != null) {
                        tournamentService.createPlayerResult(tournament, selectedPlayer, table, TableType.B);

                    }
                    setResponsePage(new RegistrationPage(tournament, table));
                }
            });

        }
    }

    private class CreateTournamentForm extends Form<Table> {

        private static final long serialVersionUID = 1L;

        public CreateTournamentForm() {
            super("createTournamentForm", new CompoundPropertyModel<Table>(new Table()));
            setOutputMarkupId(true);
            add(new Button("submit") {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                }
            });

        }
    }

    private void createPage(final Table table) {
        IDataProvider<Player> playerDataProvider = new IDataProvider<Player>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Iterator<Player> iterator(int first, int count) {
                return tournamentService.getNotRegistrationPlayer(new PlayerForm(tournament))
                        .subList(first, first + count).iterator();
            }

            @Override
            public int size() {
                return tournamentService.getNotRegistrationPlayer(new PlayerForm(tournament)).size();
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
                listItem.add(new AjaxEventBehavior("onclick") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onEvent(final AjaxRequestTarget target) {
                        selectedPlayer = player;
                    }
                });
            }

        };

        IDataProvider<PlayerResult> registeredPlayerDataProvider = new IDataProvider<PlayerResult>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Iterator<PlayerResult> iterator(int first, int count) {
                return tournamentService.findPlayerResult(new PlayerResultForm(tournament))
                        .subList(first, first + count).iterator();

            }

            @Override
            public int size() {
                return tournamentService.findPlayerResult(new PlayerResultForm(tournament)).size();
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
                registeredPlayerDataProvider, 10) {

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
                        tournamentService.deletePlayerResult(playerResult);

                        setResponsePage(new RegistrationPage(tournament, table) {
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

        add(new PagingNavigator("navigator", dataView));
        add(dataView);

        add(new PagingNavigator("registeredNavigator", registeredDataView));
        add(registeredDataView);

        add(new FeedbackPanel("feedbackPanel"));
        add(new RegistrationForm(table));
        add(new CreateTournamentForm());

    }

    @Override
    protected IModel<String> newHeadingModel() {
        return Model.of("Registration");
    }

}
