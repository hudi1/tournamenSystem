package org.tahom.web;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.tahom.processor.service.registration.dto.RegistrationOptions;
import org.tahom.processor.service.registration.dto.RegistrationPageDto;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Player;
import org.tahom.web.behavior.CloseOnESCBehavior;
import org.tahom.web.components.ModelAutoCompleteTextField;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentAjaxButton;
import org.tahom.web.components.TournamentButton;
import org.tahom.web.components.TournamentResourceButton;
import org.tahom.web.link.AjaxModelLink;
import org.tahom.web.link.TournamentAjaxLink;
import org.tahom.web.model.EvenOddReplaceModel;

@AuthorizeInstantiation(Roles.USER)
public class RegistrationPage extends TournamentHomePage {

	private static final long serialVersionUID = 1L;

	private RegistrationPageDto registrationPageDto;

	public RegistrationPage() {
		this(new PageParameters());
	}

	public RegistrationPage(PageParameters parameters) {
		super(parameters);
		registrationPageDto = registrationService.getRegistrationPageDto(tournament, getGroupName(parameters));
		createPage();
	}

	private void createPage() {
		add(new GroupForm());
		add(new PlayerForm());
	}

	private class PlayerForm extends Form<RegistrationPageDto> {

		private static final long serialVersionUID = 1L;
		private final Model<Player> playerModel = new Model<Player>(null);

		public PlayerForm() {
			super("playerForm", new CompoundPropertyModel<RegistrationPageDto>(registrationPageDto));
			add(new ResourceLabel("choosePlayer"));
			add(new ResourceLabel("chooseRegistrationSystem"));
			add(new ResourceLabel("name"));
			add(new ResourceLabel("surname"));
			add(new ResourceLabel("tableName"));

			ModalWindow importModalWindow;

			addShowPlayersButton();
			add(importModalWindow = createModalWindow());
			add(new CloseOnESCBehavior(importModalWindow));
			addModalButton(importModalWindow);
			addAutoCompletePlayers();
			addTournamentparticipantListView();
			setDefaultButton(getRegisterPlayersButton());
			addSeasonDropDownChoice();
			addProcessRegistrationButton();
		}

		private void addProcessRegistrationButton() {
			add(new TournamentButton("registrationButton", new ResourceModel("confirmRegistration")) {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					groupService.createGroupsWithSnakeSystem(tournament, registrationPageDto.getParticipants(),
					        registrationPageDto.getGroupName());
					setResponsePage(RegistrationPage.class, getPageParameters());
				}

				@Override
				public boolean isVisible() {
					return registrationPageDto.getSelectOptions().equals(RegistrationOptions.SNAKE);
				}
			});

		}

		private ModelAutoCompleteTextField<Player> addAutoCompletePlayers() {

			ModelAutoCompleteTextField<Player> playersTextField = new ModelAutoCompleteTextField<Player>("allPlayers",
			        playerModel, new AbstractAutoCompleteRenderer<Player>() {

				        private static final long serialVersionUID = 1L;

				        @Override
				        protected void renderChoice(Player player, Response response, String criteria) {
					        response.write(getTextValue(player));
				        }

				        @Override
				        protected String getTextValue(Player player) {
					        return (player.getName() + " " + player.getSurname()).trim();
				        }

			        }, getCustomSettings(), registrationPageDto.getNotRegistratedPlayers()) {

				private static final long serialVersionUID = 1L;

				@Override
				protected boolean add(String prefix, Player player) {
					return player.getSurname().toString().toLowerCase().startsWith(prefix.toLowerCase().toString());
				}

				@Override
				protected String objectToString(Player player) {
					return (player.getName() + " " + player.getSurname()).trim();
				}

			};

			playersTextField.add(new AjaxFormComponentUpdatingBehavior("change") {

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

		private void addTournamentparticipantListView() {
			add(new PropertyListView<Participant>("participants") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<Participant> listItem) {
					final Participant participant = listItem.getModelObject();
					listItem.add(new Label("number", listItem.getIndex() + 1 + "."));
					listItem.add(new Label("name", participant.getPlayer().getName()));
					listItem.add(new Label("surname", participant.getPlayer().getSurname().toString()));
					listItem.add(new Label("group", (participant.getGroup() != null) ? participant.getGroup().getName()
					        : ""));
					listItem.add(new TournamentAjaxLink("deleteParticipant", getString("deleteParticipantQuestion")) {

						private static final long serialVersionUID = 1L;

						public void click(AjaxRequestTarget target) {
							participantService.deletePlayerParticipant(participant, tournament);
							registrationPageDto.getTournamentParticipants().remove(participant);
							registrationPageDto.getNotRegistratedPlayers().add(participant.getPlayer());
							target.add(PlayerForm.this);
						}

					}.add(new Image("img", new SharedResourceReference("delete"))).add(
					        new AttributeModifier("title", new ResourceModel("deleteParticipant"))));

					listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
				}
			});
		}

		private void addShowPlayersButton() {
			add(new TournamentResourceButton("players") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					setResponsePage(PlayerPage.class, getPageParameters());
				}
			});
		}

		private void addModalButton(final ModalWindow modalWindow) {
			add(new AjaxModelLink<Void>("showModalLinkTournament") {

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
					return new ImportTournamentPage(modal, getTournamentSession().getUser(), tournament, feedbackPanel);
				}
			});

			modal.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

				private static final long serialVersionUID = 1L;

				public boolean onCloseButtonClicked(AjaxRequestTarget target) {
					return true;
				}
			});

			modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

				private static final long serialVersionUID = 1;

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

				Participant particiapnt = null;
				if (registrationPageDto.getSelectOptions().equals(RegistrationOptions.MANUAL)) {
					particiapnt = participantService.createBasicParticipant(tournament, player,
					        registrationPageDto.getGroupName());
					gameService.createGames(registrationPageDto.getParticipants(), particiapnt);
				} else if (registrationPageDto.getSelectOptions().equals(RegistrationOptions.SNAKE)) {
					particiapnt = participantService.createBasicParticipant(tournament, player, null);
				}

				if (particiapnt != null) {
					registrationPageDto.getParticipants().add(particiapnt);
				}

				registrationPageDto.getNotRegistratedPlayers().remove(player);
				playerModel.setObject(null);
			}
		}

		private void addSeasonDropDownChoice() {
			add(new DropDownChoice<String>("selectOptions", registrationPageDto.getRegistrationOptions(),
			        new IChoiceRenderer<String>() {

				        private static final long serialVersionUID = 1L;

				        @Override
				        public Object getDisplayValue(String object) {
					        return getString(object);
				        }

				        @Override
				        public String getIdValue(String object, int index) {
					        return index + "";
				        }

				        @Override
				        public String getObject(String id, IModel<? extends List<? extends String>> choices) {
					        List<? extends String> _choices = choices.getObject();
					        for (int index = 0; index < _choices.size(); index++) {
						        final String choice = _choices.get(index);
						        if (getIdValue(choice, index).equals(id)) {
							        return choice;
						        }
					        }
					        return null;
				        }

			        }).add(new AjaxFormComponentUpdatingBehavior("change") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					target.add(PlayerForm.this);
				}
			}));
		}
	}

	private class GroupForm extends Form<RegistrationPageDto> {

		private static final long serialVersionUID = 1L;

		public GroupForm() {
			super("groupForm", new CompoundPropertyModel<RegistrationPageDto>(registrationPageDto));
			add(new ResourceLabel("group"));

			TextField<String> groupTextField = getGroupTextField();
			addMinusButton(groupTextField);
			addPlusButton(groupTextField);
		}

		private void addPlusButton(final TextField<String> groupTextField) {
			add(new TournamentAjaxButton("minus", Model.of("-")) {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit(AjaxRequestTarget target, Form<?> form) {
					Integer groupName = Integer.parseInt(registrationPageDto.getGroupName());
					if (groupName > 1) {
						groupName--;
					}
					registrationPageDto.setGroupName(groupName.toString());
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
					Integer groupName = Integer.parseInt(registrationPageDto.getGroupName());
					groupName++;
					registrationPageDto.setGroupName(groupName.toString());
					getPageParameters().set(GID, groupName.toString());
					target.add(groupTextField);
				}
			});
		}

		private TextField<String> getGroupTextField() {
			final TextField<String> groupTextField = new TextField<String>("groupName");
			groupTextField.setOutputMarkupId(true);
			add(groupTextField);
			return groupTextField;
		}
	}

	private String getGroupName(PageParameters parameters) {
		return parameters.get("gid").isNull() ? "1" : parameters.get("gid").toString();
	}

	@Override
	protected IModel<String> newHeadingModel() {
		return Model.of(getString(this.getClass().getSimpleName()) + ": " + tournament.getName());
	}

}