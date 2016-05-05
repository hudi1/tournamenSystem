package org.tahom.web;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.User;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentBackResourceButton;
import org.tahom.web.components.TournamentResourceButton;
import org.tahom.web.mask.BusyIndicatingMaskAppender;

@AuthorizeInstantiation(Roles.USER)
public class PlayerEditPage extends TournamentHomePage {

	private static final long serialVersionUID = 1L;
	private User user;

	private final Model<Player> playerModel = new Model<Player>(null);

	public PlayerEditPage() {
		this(new PageParameters());
	}

	public PlayerEditPage(PageParameters pageParameters) {
		this(new Player(), pageParameters);
	}

	public PlayerEditPage(Player player, PageParameters pageParameters) {
		super(pageParameters);
		this.user = getTournamentSession().getUser();
		createPage(player);
	}

	protected void createPage(Player player) {
		add(new PlayerForm(player));
	}

	private class PlayerForm extends Form<Player> {

		private static final long serialVersionUID = 1L;

		public PlayerForm(final Player player) {
			super("playerEditForm", new CompoundPropertyModel<Player>(player));

			addPlayerTextFields(player);
			addSaveButton(player);
			addBackButton();
			addPlayerDropDown();
			addMergeButton(player);
		}

		private void addBackButton() {
			add(new TournamentBackResourceButton("back") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					setResponsePage(PlayerPage.class, getPageParameters());
				};
			});
		}

		private void addMergeButton(final Player originalPlayer) {
			add(new TournamentResourceButton("merge") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					getSession().info(getString("mergePlayerInfo"));
					playerService.mergePlayers(originalPlayer, playerModel.getObject());
					setResponsePage(PlayerPage.class, getPageParameters());
				};
			});
		}

		private void addSaveButton(final Player player) {
			add(new TournamentResourceButton("submit") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					if (player.getId() != null) {
						playerService.updatePlayer(player);
						getSession().info(getString("updatePlayerInfo"));
						setResponsePage(new PlayerEditPage(player, getPageParameters()));
					} else {
						playerService.createPlayer(user, player);
						getSession().info(getString("addPlayerInfo", Model.of(player)));
						setResponsePage(new PlayerPage(getPageParameters(), player));
					}

				}
			});
		}

		private void addPlayerTextFields(final Player player) {
			add(new ResourceLabel("player"));
			add(new ResourceLabel("nameLabel"));
			add(new RequiredTextField<String>("name"));
			add(new ResourceLabel("surnameLabel"));
			add(new RequiredTextField<String>("surname"));
			add(new ResourceLabel("clubLabel"));
			add(new TextField<String>("club"));
			add(new ResourceLabel("countryLabel"));
			add(new TextField<String>("country"));
			add(new ResourceLabel("worldRankingLabel"));
			add(new TextField<String>("worldRanking"));
			add(new ResourceLabel("ithfIdLabel"));
			add(new TextField<String>("ithfId"));
		}

		private void addPlayerDropDown() {
			add(new DropDownChoice<Player>("players", playerModel,
			        playerService.getSortedUserPlayers(user, getLocale()), new IChoiceRenderer<Player>() {

				        private static final long serialVersionUID = 1L;

				        @Override
				        public Object getDisplayValue(Player player) {
					        return (player.getSurname().toString() + " " + player.getName());
				        }

				        @Override
				        public String getIdValue(Player player, int index) {
					        if (player == null || player.getId() == null) {
						        return null;
					        } else {
						        return player.getId().toString();
					        }
				        }

				        @Override
				        public Player getObject(String id, IModel<? extends List<? extends Player>> choices) {
					        List<? extends Player> _choices = choices.getObject();
					        for (int index = 0; index < _choices.size(); index++) {
						        final Player choice = _choices.get(index);
						        if (getIdValue(choice, index) != null && getIdValue(choice, index).equals(id)) {
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
			}).add(new BusyIndicatingMaskAppender()));
		}

	}
}