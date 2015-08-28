package org.toursys.web;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.User;
import org.toursys.web.components.TournamentButton;

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
        createPage(player._setUser(user));
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
            add(new TournamentButton("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    setResponsePage(PlayerPage.class, getPageParameters());
                };
            }.setDefaultFormProcessing(false));
        }

        private void addSaveButton(final Player player) {
            add(new TournamentButton("submit", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    if (player.getSurname().contains(" ")) {
                        player.setPlayerDiscriminator(player.getSurname().split(" ")[1].substring(0,
                                Math.min(player.getSurname().split(" ")[1].length(), 3)));
                        player.setSurname(player.getSurname().split(" ")[0]);
                    }
                    if (player.getId() != null) {
                        playerService.updatePlayer(player);
                    } else {
                        playerService.createPlayer(player);
                    }

                    setResponsePage(PlayerPage.class, getPageParameters());
                }
            });
        }

        private void addPlayerTextFields(final Player player) {
            add(new Label("player", new ResourceModel("player")));
            add(new Label("name", new ResourceModel("name")));
            add(new RequiredTextField<String>("nameInput", new PropertyModel<String>(player, "name")));
            add(new Label("surname", new ResourceModel("surname")));
            add(new RequiredTextField<String>("surnameInput", new PropertyModel<String>(player, "surname")));
            add(new Label("club", new ResourceModel("club")));
            add(new TextField<String>("clubInput", new PropertyModel<String>(player, "club")));
            add(new Label("worldRanking", new ResourceModel("worldRanking")));
            add(new TextField<String>("worldRankingInput", new PropertyModel<String>(player, "worldRanking")));
            add(new Label("ithfId", new ResourceModel("ithfId")));
            add(new TextField<String>("ithfIdInput", new PropertyModel<String>(player, "ithfId")));
        }
    }

}
