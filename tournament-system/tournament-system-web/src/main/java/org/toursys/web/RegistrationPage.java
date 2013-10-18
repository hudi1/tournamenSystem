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
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Tournament;

@AuthorizeInstantiation(Roles.USER)
public class RegistrationPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private Tournament tournament;
    private Groups group;
    private Player selectedPlayer;
    private HighlitableDataItem<Player> selectedItem;

    public RegistrationPage() {
        throw new RestartResponseAtInterceptPageException(new SeasonPage());
    }

    public RegistrationPage(PageParameters parameters) {
        super(parameters);
        checkPageParameters(parameters);
        createGroup(parameters);
        tournament = getTournament(parameters);
        createPage();
    }

    private void checkPageParameters(PageParameters parameters) {
        if (parameters.get("tournamentid").isNull() || parameters.get("seasonid").isNull()) {
            throw new RestartResponseAtInterceptPageException(new SeasonPage());
        }
    }

    private void createGroup(PageParameters parameters) {
        group = new Groups();
        group.setName(parameters.get("groupid").isNull() ? "1" : parameters.get("groupid").toString());
    }

    private void createPage() {
        IDataProvider<Participant> registeredPlayerDataProvider = createRegisteredPlayerDataProvider();
        DataView<Participant> dataView = createDataview(registeredPlayerDataProvider);
        add(dataView);

        add(new RegistrationForm());
        add(new CreateTournamentForm());
    }

    private DataView<Participant> createDataview(IDataProvider<Participant> registeredPlayerDataProvider) {

        DataView<Participant> registeredDataView = new DataView<Participant>("registeredRows",
                registeredPlayerDataProvider) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Participant> listItem) {
                final Participant participant = listItem.getModelObject();
                listItem.setModel(new CompoundPropertyModel<Participant>(participant));
                listItem.add(new Label("name", participant.getPlayer().getName()));
                listItem.add(new Label("surname", participant.getPlayer().getSurname()));
                listItem.add(new Label("tableName", participant.getGroup().getName()));
                listItem.add(new Label("number", listItem.getIndex() + 1 + "."));
                listItem.add(new AjaxLink<Void>("deletePlayer") {

                    private static final long serialVersionUID = 1L;

                    public void onClick(AjaxRequestTarget target) {
                        participantService.deleteParticipant(participant);
                        setResponsePage(RegistrationPage.class);
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

    private IDataProvider<Participant> createRegisteredPlayerDataProvider() {
        IDataProvider<Participant> registeredPlayerDataProvider = new IDataProvider<Participant>() {

            private static final long serialVersionUID = 1L;
            private List<Participant> allTournamenPlayers = participantService.getRegistratedParticipant(tournament);

            @Override
            public Iterator<Participant> iterator(int first, int count) {
                return allTournamenPlayers.subList(first, first + count).iterator();
            }

            @Override
            public int size() {
                return allTournamenPlayers.size();
            }

            @Override
            public IModel<Participant> model(final Participant object) {
                return new LoadableDetachableModel<Participant>() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    protected Participant load() {
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

        public RegistrationForm() {
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
                    getPageParameters().set("groupid", groupName.toString());
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
                    getPageParameters().set("groupid", groupName.toString());
                    target.add(text);
                }
            });

            add(text);

            IDataProvider<Player> playerDataProvider = new IDataProvider<Player>() {

                private static final long serialVersionUID = 1L;
                private List<Player> notRegistratedPlayer = playerService.getNotRegistratedPlayers(tournament);

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

                            HighlitableDataItem<Player> hitem = (HighlitableDataItem<Player>) listItem;
                            hitem.toggleHighlite();

                            /*
                             * Iterator<Item<Player>> items = getItems();
                             * 
                             * while (items.hasNext()) { HighlitableDataItem<Player> hitem =
                             * (HighlitableDataItem<Player>) items.next(); if (hitem.equals(listItem)) {
                             * hitem.toggleHighlite(); } else { hitem.toggleOff(); } target.add(hitem); }
                             */

                            if (selectedItem != null) {
                                selectedItem.toggleOff();
                                target.add(selectedItem);
                            }
                            if (hitem.equals(selectedItem)) {
                                selectedItem = null;
                            } else {
                                selectedItem = hitem;
                            }

                            if (player.equals(selectedPlayer)) {
                                selectedPlayer = null;
                            } else {
                                selectedPlayer = player;
                            }

                            target.add(hitem);
                        }
                    });

                    listItem.add(new AjaxEventBehavior("ondblclick") {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void onEvent(final AjaxRequestTarget target) {
                            if (player != null) {
                                participantService.createBasicParticipant(tournament, player, group);
                            }
                            setResponsePage(RegistrationPage.class, getPageParameters());
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
                        participantService.createBasicParticipant(tournament, selectedPlayer, group);
                    }
                    setResponsePage(RegistrationPage.class, getPageParameters());
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
                    List<Groups> groups = groupService.getBasicGroups(new Groups()._setTournament(tournament));
                    if (!groups.isEmpty()) {
                        group = groups.get(0);
                    }

                    getPageParameters().set("groupid", group.getId());
                    setResponsePage(GroupPage.class, getPageParameters());
                }
            });

            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    getPageParameters().remove("tournamentid");
                    getPageParameters().remove("groupid");
                    setResponsePage(TournamentPage.class, getPageParameters());
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