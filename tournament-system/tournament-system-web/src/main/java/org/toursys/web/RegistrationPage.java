package org.toursys.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
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
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.TournamentImpl;

public class RegistrationPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private Tournament tournament;
    private Player selectedPlayer;

    public RegistrationPage() {
        throw new RestartResponseAtInterceptPageException(new SeasonPage());
    }

    public RegistrationPage(Tournament tournament) {
        this(tournament, new Groups()._setName("1"));
    }

    public RegistrationPage(Tournament tournament, Groups group) {
        this.tournament = tournament;
        this.tournament.getSeason()._setUser(((TournamentAuthenticatedWebSession) getSession()).getUser());
        createPage(group);
    }

    private void createPage(Groups group) {
        IDataProvider<PlayerResult> registeredPlayerDataProvider = createRegisteredPlayerDataProvider();
        DataView<PlayerResult> dataView = createDataview(registeredPlayerDataProvider);
        add(dataView);

        add(new RegistrationForm(group));
        add(new CreateTournamentForm());
    }

    private DataView<PlayerResult> createDataview(IDataProvider<PlayerResult> registeredPlayerDataProvider) {

        DataView<PlayerResult> registeredDataView = new DataView<PlayerResult>("registeredRows",
                registeredPlayerDataProvider) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<PlayerResult> listItem) {
                final PlayerResult playerResult = listItem.getModelObject();
                listItem.setModel(new CompoundPropertyModel<PlayerResult>(playerResult));
                listItem.add(new Label("name", playerResult.getPlayer().getName()));
                listItem.add(new Label("surname", playerResult.getPlayer().getSurname()));
                listItem.add(new Label("tableName", playerResult.getGroup().getName()));
                listItem.add(new Label("number", listItem.getIndex() + 1 + "."));
                listItem.add(new AjaxLink<Void>("deletePlayer") {

                    private static final long serialVersionUID = 1L;

                    public void onClick(AjaxRequestTarget target) {
                        tournamentService.deletePlayerResult(playerResult, tournament);
                        setResponsePage(new RegistrationPage(tournament));
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

        return registeredDataView;
    }

    private IDataProvider<PlayerResult> createRegisteredPlayerDataProvider() {
        IDataProvider<PlayerResult> registeredPlayerDataProvider = new IDataProvider<PlayerResult>() {

            private static final long serialVersionUID = 1L;
            private List<PlayerResult> allTournamenPlayers = tournamentService.getRegistratedPlayerResult(tournament);

            @Override
            public Iterator<PlayerResult> iterator(int first, int count) {
                return allTournamenPlayers.subList(first, first + count).iterator();
            }

            @Override
            public int size() {
                return allTournamenPlayers.size();
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

        return registeredPlayerDataProvider;
    }

    private class RegistrationForm extends Form<Groups> {

        private static final long serialVersionUID = 1L;

        public RegistrationForm(final Groups group) {
            super("registrationForm", new CompoundPropertyModel<Groups>(group));
            setOutputMarkupId(true);
            final TextField<String> text = new TextField<String>("name");
            text.setOutputMarkupId(true);
            add(new AjaxButton("plus", Model.of("+")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    Integer groupName = Integer.parseInt(group.getName());
                    groupName++;
                    group.setName(groupName.toString());
                    target.add(text);
                }
            });

            add(new AjaxButton("minus", Model.of("-")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    Integer groupName = Integer.parseInt(group.getName());
                    if (groupName > 1) {
                        groupName--;
                    }
                    group.setName(groupName.toString());
                    target.add(text);
                }
            });

            add(text);

            IDataProvider<Player> playerDataProvider = new IDataProvider<Player>() {

                private static final long serialVersionUID = 1L;
                private List<Player> notRegistratedPlayer = tournamentService.getNotRegistratedPlayers(tournament);

                @Override
                public Iterator<Player> iterator(int first, int count) {
                    Collections.sort(notRegistratedPlayer, new Comparator<Player>() {
                        @Override
                        public int compare(Player p1, Player p2) {
                            return p1.getSurname().compareTo(p2.getSurname());
                        }
                    });
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
                            if (player != null) {
                                tournamentService.createBasicPlayerResult(tournament, player, group);
                            }
                            setResponsePage(new RegistrationPage(tournament, group));
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
                        tournamentService.createBasicPlayerResult(tournament, selectedPlayer, group);
                    }
                    setResponsePage(new RegistrationPage(tournament, group));
                }
            });
        }
    }

    private class CreateTournamentForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public CreateTournamentForm() {
            super("createTournamentForm");
            setOutputMarkupId(true);

            add(new Button("submit", new ResourceModel("createTournament")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {

                    Groups group = null;
                    List<Groups> groups = tournamentService.getBasicGroups(new Groups()._setTournament(tournament));
                    if (!groups.isEmpty()) {
                        group = groups.get(0);
                    }

                    setResponsePage(new GroupPage(new TournamentImpl(tournament), group));
                }
            });

            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new TournamentPage(tournament.getSeason()));
                };
            }.setDefaultFormProcessing(false));
        }
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
        return new ResourceModel("registration");
    }

}