package org.tahom.web;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.User;
import org.tahom.web.behavior.CloseOnESCBehavior;
import org.tahom.web.components.PropertyPageableListView;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentResourceButton;
import org.tahom.web.link.AjaxModelLink;
import org.tahom.web.link.TournamentAjaxLink;
import org.tahom.web.mask.MaskIndicatingAjaxButton;
import org.tahom.web.model.EvenOddReplaceModel;

@AuthorizeInstantiation(Roles.USER)
public class PlayerPage extends TournamentHomePage {

	private static final long serialVersionUID = 1L;
	private User user;
	private Player newPlayer;

	public PlayerPage() {
		this(new PageParameters());
	}

	public PlayerPage(PageParameters pageParameters) {
		this(pageParameters, null);
	}

	public PlayerPage(PageParameters pageParameters, Player player) {
		super(pageParameters);
		this.user = getUserWithPlayers();
		this.newPlayer = player;
		createPage();
	}

	protected void createPage() {
		add(new PlayerForm(user));
	}

	private User getUserWithPlayers() {
		User user = new User();
		user.getPlayers().addAll(
		        playerService.getSortedUserPlayers(getTournamentSession().getUser(), getSession().getLocale()));
		return user;
	}

	private class PlayerForm extends Form<User> {

		private static final long serialVersionUID = 1L;

		public PlayerForm(User user) {
			super("playerForm", new CompoundPropertyModel<User>(user));
			ModalWindow modalWindow;

			add(new ResourceLabel("name"));
			add(new ResourceLabel("surname"));
			add(new ResourceLabel("club"));

			addPlayerListView();
			addNewPlayerButton();
			addUpdateOnlinePlayerButton(user.getPlayers());
			add(modalWindow = createModalWindow());
			add(new CloseOnESCBehavior(modalWindow));
			addModalButton(modalWindow);
		}

		private void addPlayerListView() {
			PropertyPageableListView<Player> listView;
			add(listView = new PropertyPageableListView<Player>("players", ITEM_PER_PAGE) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<Player> listItem) {
					final Player player = listItem.getModelObject();
					listItem.setModel(new CompoundPropertyModel<Player>(player));
					listItem.add(new TextField<String>("name").add(new AjaxFormComponentUpdatingBehavior("change") {

						private static final long serialVersionUID = 1L;

						@Override
						protected void onUpdate(AjaxRequestTarget target) {
							playerService.updatePlayer(listItem.getModelObject());
						}
					}));

					listItem.add(new TextField<String>("surname").add(new AjaxFormComponentUpdatingBehavior("change") {

						private static final long serialVersionUID = 1L;

						@Override
						protected void onUpdate(AjaxRequestTarget target) {
							Player player = listItem.getModelObject();
							playerService.updatePlayer(player);
						}
					}));

					listItem.add(new TextField<String>("club").add(new AjaxFormComponentUpdatingBehavior("change") {

						private static final long serialVersionUID = 1L;

						@Override
						protected void onUpdate(AjaxRequestTarget target) {
							playerService.updatePlayer(listItem.getModelObject());
						}
					}));

					listItem.add(new TournamentAjaxLink("deletePlayer", getString("deletePlayerQuestion")) {

						private static final long serialVersionUID = 1L;

						public void click(AjaxRequestTarget target) {
							Player player = listItem.getModelObject();
							playerService.deletePlayer(player);
							PlayerForm.this.getModelObject().getPlayers().remove(player);
							getFeedbackMessages().clear();
							info(getString("deletePlayerInfo"));
							target.add(feedbackPanel);
							target.add(PlayerForm.this);
						}

					}.add(new Image("img", new SharedResourceReference("delete"))).add(
					        new AttributeModifier("title", getString("deletePlayer"))));

					listItem.add(new TournamentAjaxLink("enterPlayer") {

						private static final long serialVersionUID = 1L;

						public void click(AjaxRequestTarget target) {
							setResponsePage(new PlayerEditPage(player, getPageParameters()));
						}

					}.add(new Image("img", new SharedResourceReference("enter"))).add(
					        new AttributeModifier("title", getString("enterPlayer"))));
					listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
				}

			});
			AjaxPagingNavigator navigator = new AjaxPagingNavigator("navigator", listView);
			navigator.getPageable().setCurrentPage(getObjectPage(user.getPlayers(), newPlayer));
			add(navigator);
		}

		private void addModalButton(final ModalWindow modalWindow) {
			add(new AjaxModelLink<Void>("showModalLinkPlayer") {

				private static final long serialVersionUID = 1L;

				@Override
				public void click(AjaxRequestTarget target) {
					modalWindow.show(target);
				}
			});
		}

		private void addNewPlayerButton() {
			add(new TournamentResourceButton("newPlayer") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					setResponsePage(PlayerEditPage.class, getPageParameters());
				}
			});
		}

		public void addUpdateOnlinePlayerButton(final List<Player> players) {
			add(new MaskIndicatingAjaxButton("updateOnlinePlayer") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void submit(AjaxRequestTarget target, Form<?> form) {
					List<Player> notUpdatedplayers = importService.updateOnlinePlayers(players);
					if (!notUpdatedplayers.isEmpty()) {
						logWarnMessage(notUpdatedplayers);
					}
					target.add(feedbackPanel);
					target.add(PlayerForm.this);
				}

			});
		}

		protected void logWarnMessage(List<Player> players) {
			warn(getString("playersNotFound"));

			// TODO
			for (Player player : players) {
				warn(player.getName() + " " + player.getSurname());
			}
		}

		private ModalWindow createModalWindow() {
			final ModalWindow modal;
			add(modal = new ModalWindow("modal"));
			modal.setCookieName("modal-1");

			modal.setPageCreator(new ModalWindow.PageCreator() {

				private static final long serialVersionUID = 1L;

				public Page createPage() {
					return new ImportPlayerPage(modal, user, feedbackPanel);
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
					setResponsePage(PlayerPage.class, getPageParameters());
				}
			});
			return modal;
		}
	}
}