package org.toursys.web;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;

@AuthorizeInstantiation(Roles.USER)
public class RegistrationPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private Tournament tournament;
    private Groups group;
    private User user;
    private final List<Participant> allTournamenPlayers;

    public RegistrationPage() {
        throw new RestartResponseAtInterceptPageException(new SeasonPage());
    }

    public RegistrationPage(PageParameters parameters) {
        super(parameters);
        this.user = getTournamentSession().getUser();
        checkPageParameters(parameters);
        createGroup(parameters);
        tournament = getTournament(parameters);
        allTournamenPlayers = participantService.getRegistratedParticipant(tournament);
        createPage();
    }

    private void createPage() {
        IDataProvider<Participant> registeredPlayerDataProvider = createRegisteredPlayerDataProvider();
        DataView<Participant> dataView = createDataview(registeredPlayerDataProvider);
        dataView.setOutputMarkupId(true);
        WebMarkupContainer dataViewContainer = new WebMarkupContainer("container");
        dataViewContainer.setOutputMarkupId(true);
        dataViewContainer.add(dataView);

        add(dataViewContainer);
        add(new GroupForm());
        add(new PlayerForm(dataViewContainer));
    }

    private class PlayerForm extends Form<Void> {

        private static final long serialVersionUID = 1L;
        private Player selectedPlayer = null;

        public PlayerForm(final WebMarkupContainer dataViewContainer) {
            super("playerForm");
            setOutputMarkupId(true);

            DropDownChoice<Player> playersDropDown = new DropDownChoice<Player>("players", new PropertyModel<Player>(
                    this, "selectedPlayer"), playerService.getPlayers(new Player()._setUser(user)),
                    new IChoiceRenderer<Player>() {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public Object getDisplayValue(Player player) {
                            return player.getName() + " " + player.getSurname() + " " + player.getPlayerDiscriminator();
                        }

                        @Override
                        public String getIdValue(Player player, int index) {
                            return player.getId().toString();
                        }
                    });
            playersDropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void onUpdate(AjaxRequestTarget target) {

                    if (selectedPlayer == null) {
                        return;
                    }

                    boolean createParticipant = true;

                    for (Participant participant : allTournamenPlayers) {
                        if (participant.getPlayer().equals(selectedPlayer)) {
                            participantService.deleteParticipant(participant);
                            allTournamenPlayers.remove(participant);
                            createParticipant = false;
                            break;
                        }
                    }

                    if (createParticipant) {
                        allTournamenPlayers.add(participantService.createBasicParticipant(tournament, selectedPlayer,
                                group));
                    }

                    target.add(PlayerForm.this);
                    target.add(dataViewContainer);
                    selectedPlayer = null;
                }
            });
            add(playersDropDown);
        }
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
                listItem.add(new Label("surname", participant.getPlayer().getSurname() + " "
                        + participant.getPlayer().getPlayerDiscriminator()));
                listItem.add(new Label("tableName", participant.getGroup().getName()));
                listItem.add(new Label("number", listItem.getIndex() + 1 + "."));
            }
        };

        return registeredDataView;
    }

    private IDataProvider<Participant> createRegisteredPlayerDataProvider() {
        IDataProvider<Participant> registeredPlayerDataProvider = new IDataProvider<Participant>() {

            private static final long serialVersionUID = 1L;

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

    private class GroupForm extends Form<Groups> {

        private static final long serialVersionUID = 1L;

        public GroupForm() {
            super("groupForm", new CompoundPropertyModel<Groups>(group));
            setOutputMarkupId(true);
            final TextField<String> groupTextField = new TextField<String>("name");
            groupTextField.setOutputMarkupId(true);
            add(new AjaxButton("plus", Model.of("+")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    Integer groupName = Integer.parseInt(group.getName());
                    groupName++;
                    group.setName(groupName.toString());
                    getPageParameters().set("groupid", groupName.toString());
                    target.add(groupTextField);
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
                    target.add(groupTextField);
                }
            });

            add(groupTextField);

        }
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

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("registration");
    }

}