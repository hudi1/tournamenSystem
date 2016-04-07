package org.tahom.web;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.User;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentBackResourceButton;
import org.tahom.web.components.TournamentResourceButton;

@AuthorizeInstantiation(Roles.USER)
public class PlayerEditPage extends TournamentHomePage {

	private static final long serialVersionUID = 1L;
	private User user;

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
	}
}