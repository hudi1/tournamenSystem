package org.toursys.web;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.sqlproc.engine.SqlProcessorException;
import org.toursys.repository.model.Player;

public class PlayerEditPage extends BasePage {

    private static final long serialVersionUID = 1L;

    public PlayerEditPage() {
        throw new RestartResponseAtInterceptPageException(new PlayerPage());
    }

    public PlayerEditPage(Player player) {
        createPage(player);
    }

    protected void createPage(Player player) {
        add(new PlayerForm(player));
    }

    private class PlayerForm extends Form<Player> {

        private static final long serialVersionUID = 1L;

        public PlayerForm(final Player player) {
            super("playerEditForm", new CompoundPropertyModel<Player>(player));
            setOutputMarkupId(true);
            add(new RequiredTextField<String>("name"));
            add(new RequiredTextField<String>("surname"));
            add(new TextField<String>("club"));
            // add(new TextField<String>("playerDiscriminator").add(StringValidator.maximumLength(3)));

            add(new Button("submit", new ResourceModel("submit")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    try {
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
                    } catch (SqlProcessorException e) {
                        logger.error("Error edit player: ", e);
                        error(getString("sql.db.exception"));
                    }
                    setResponsePage(new PlayerPage());
                }
            });

            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new PlayerPage());
                };
            }.setDefaultFormProcessing(false));
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("editPlayer");
    }

}
