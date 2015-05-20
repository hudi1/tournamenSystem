package org.toursys.web;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Player;
import org.toursys.web.components.ModelAutoCompleteTextField;
import org.toursys.web.components.TournamentAjaxButton;
import org.toursys.web.components.TournamentButton;
import org.toursys.web.link.AjaxModelLink;

@AuthorizeInstantiation(Roles.USER)
public class RegistrationPage extends TournamentHomePage {

    private static final long serialVersionUID = 1L;
    private Groups group;
    private final List<Participant> tournamentParticipants;
    private final List<Player> notRegistratedPlayers;

    public RegistrationPage() {
        this(new PageParameters());
    }

    public RegistrationPage(PageParameters parameters) {
        super(parameters);
        createGroup(parameters);
        this.tournamentParticipants = participantService.getRegistratedParticipant(tournament);
        this.notRegistratedPlayers = playerService.getNotRegisteredPlayers(tournament);
        createPage();
    }

    private void createPage() {
        add(new GroupForm());
        add(new PlayerForm());
    }

    private class PlayerForm extends Form<Void> {

        private static final long serialVersionUID = 1L;
        private final Model<Player> playerModel = new Model<Player>(null);

        public PlayerForm() {
            super("playerForm");
            add(new Label("choosePlayer", new ResourceModel("choosePlayer")));
            add(new Label("name", new ResourceModel("name")));
            add(new Label("surname", new ResourceModel("surname")));
            add(new Label("tableName", new ResourceModel("tableName")));

            ModalWindow modalWindow;

            addShowPlayersButton();
            add(modalWindow = createModalWindow());
            addModalButton(modalWindow);
            ModelAutoCompleteTextField<Player> playersTextField = addAutoCompletePlayers();
            addTournamentparticipantListView(playersTextField);
            setDefaultButton(getRegisterPlayersButton());
        }

        private ModelAutoCompleteTextField<Player> addAutoCompletePlayers() {

            ModelAutoCompleteTextField<Player> playersTextField = new ModelAutoCompleteTextField<Player>("players",
                    playerModel, null, new AbstractAutoCompleteRenderer<Player>() {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void renderChoice(Player player, Response response, String criteria) {
                            response.write(getTextValue(player));
                        }

                        @Override
                        protected String getTextValue(Player player) {
                            return (player.getName() + " " + player.getSurname() + " " + player
                                    .getPlayerDiscriminator()).trim();
                        }

                    }, getCustomSettings(), notRegistratedPlayers) {

                private static final long serialVersionUID = 1L;

                @Override
                protected boolean add(String prefix, Player player) {
                    return player.getSurname().toLowerCase().startsWith(prefix.toLowerCase());
                }

                @Override
                protected String objectToString(Player player) {
                    return (player.getName() + " " + player.getSurname() + " " + player.getPlayerDiscriminator())
                            .trim();
                }

            };

            playersTextField.add(new AjaxFormComponentUpdatingBehavior("onchange") {

                private static final long serialVersionUID = 1L;
                private Component component;

                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    target.appendJavaScript(("document.getElementById('" + component.getMarkupId() + "').blur();"
                            + "document.getElementById('" + component.getMarkupId() + "').focus();"));

                    registerPlayer();
                    target.add(PlayerForm.this);
                }

                protected void onBind() {
                    this.component = getComponent();
                    component.setOutputMarkupId(true);
                }

            });

            add(playersTextField);

            return playersTextField;
        }

        private AutoCompleteSettings getCustomSettings() {
            AutoCompleteSettings settings = new AutoCompleteSettings();
            settings.setShowListOnEmptyInput(true);
            settings.setShowListOnFocusGain(true);
            settings.setAdjustInputWidth(true);
            settings.setMaxHeightInPx(200);
            settings.setCssClassName("allPlayers");
            return settings;
        }

        private void addTournamentparticipantListView(final ModelAutoCompleteTextField<Player> playersTextField) {
            add(new PropertyListView<Participant>("participants", tournamentParticipants) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<Participant> listItem) {
                    final Participant participant = listItem.getModelObject();
                    listItem.add(new Label("number", listItem.getIndex() + 1 + "."));
                    listItem.add(new Label("name", participant.getPlayer().getName()));
                    listItem.add(new Label("surname", participant.getPlayer().getSurname() + " "
                            + participant.getPlayer().getPlayerDiscriminator()));
                    listItem.add(new Label("group", participant.getGroup().getName()));
                    listItem.add(new AjaxLink<Void>("deleteParticipant") {

                        private static final long serialVersionUID = 1L;

                        public void onClick(AjaxRequestTarget target) {
                            participantService.deletePlayerParticipant(participant, tournament);
                            tournamentParticipants.remove(participant);
                            playersTextField.getAllChoices().add(participant.getPlayer());
                            target.add(PlayerForm.this);
                        }

                        @Override
                        protected IAjaxCallDecorator getAjaxCallDecorator() {
                            return new AjaxCallDecorator() {

                                private static final long serialVersionUID = 1L;

                                @Override
                                public CharSequence decorateScript(Component c, CharSequence script) {
                                    return "if(!confirm('" + getString("del.participant") + "')) return false;"
                                            + script;
                                }

                            };
                        }
                    }.add(new Image("img", new SharedResourceReference("delete"))).add(
                            AttributeModifier.replace("title", new AbstractReadOnlyModel<String>() {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public String getObject() {
                                    return getString("deleteParticipant");
                                }
                            })));

                    listItem.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public String getObject() {
                            return (listItem.getIndex() % 2 == 1) ? "even" : "odd";
                        }
                    }));
                }

            });
        }

        private void addShowPlayersButton() {
            add(new TournamentButton("playersButton", new ResourceModel("players")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    setResponsePage(PlayerPage.class, getPageParameters());
                }
            });
        }

        private void addModalButton(final ModalWindow modalWindow) {
            add(new AjaxModelLink<Void>("showModalLink") {

                private static final long serialVersionUID = 1L;

                @Override
                public void click(AjaxRequestTarget target) {
                    modalWindow.show(target);
                }
            });
        }

        private ModalWindow createModalWindow() {
            final ModalWindow modal;
            add(modal = new ModalWindow("modal"));
            modal.setCookieName("modal-1");

            modal.setPageCreator(new ModalWindow.PageCreator() {

                private static final long serialVersionUID = 1L;

                public Page createPage() {
                    return new ImportTournamentPage(modal, getTournamentSession().getUser(), tournament);
                }
            });

            modal.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

                private static final long serialVersionUID = 1L;

                public boolean onCloseButtonClicked(AjaxRequestTarget target) {
                    return true;
                }
            });

            modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

                private static final long serialVersionUID = 10094L;

                public void onClose(AjaxRequestTarget target) {
                    setResponsePage(RegistrationPage.class, getPageParameters());
                }
            });
            return modal;
        }

        private Button getRegisterPlayersButton() {
            return new TournamentButton("registerButton") {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    registerPlayer();
                }
            };
        }

        private void registerPlayer() {

            if (playerModel.getObject() != null) {
                Player player = playerModel.getObject();
                notRegistratedPlayers.remove(player);
                tournamentParticipants.add(participantService.createBasicParticipant(tournament, player, new Groups()
                        ._setName(group.getName())._setType(GroupsType.BASIC)));
                playerModel.setObject(null);
            }

        }
    }

    private class GroupForm extends Form<Groups> {

        private static final long serialVersionUID = 1L;

        public GroupForm() {
            super("groupForm", new CompoundPropertyModel<Groups>(group));
            add(new Label("group", new ResourceModel("group")));

            TextField<String> groupTextField = getGroupTextField();
            addMinusButton(groupTextField);
            addPlusButton(groupTextField);
        }

        private void addPlusButton(final TextField<String> groupTextField) {
            add(new TournamentAjaxButton("minus", Model.of("-")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit(AjaxRequestTarget target, Form<?> form) {
                    Integer groupName = Integer.parseInt(group.getName());
                    if (groupName > 1) {
                        groupName--;
                    }
                    group.setName(groupName.toString());
                    getPageParameters().set(GID, groupName.toString());
                    target.add(groupTextField);
                }
            });
        }

        private void addMinusButton(final TextField<String> groupTextField) {
            add(new TournamentAjaxButton("plus", Model.of("+")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit(AjaxRequestTarget target, Form<?> form) {
                    Integer groupName = Integer.parseInt(group.getName());
                    groupName++;
                    group.setName(groupName.toString());
                    getPageParameters().set(GID, groupName.toString());
                    target.add(groupTextField);
                }
            });
        }

        private TextField<String> getGroupTextField() {
            final TextField<String> groupTextField = new TextField<String>("name");
            groupTextField.setOutputMarkupId(true);
            add(groupTextField);
            return groupTextField;
        }
    }

    private void createGroup(PageParameters parameters) {
        group = new Groups();
        group.setName(parameters.get("gid").isNull() ? "1" : parameters.get("gid").toString());
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return Model.of(getString(this.getClass().getSimpleName()) + ": " + tournament.getName());
    }

}