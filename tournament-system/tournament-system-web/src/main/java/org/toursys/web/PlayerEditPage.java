package org.toursys.web;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.toursys.processor.service.TournamentService;
import org.toursys.repository.model.Player;

public abstract class PlayerEditPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private BasePage pageFrom;

    @SpringBean(name = "tournamentService")
    TournamentService tournamentService;

    private class PlayerForm extends Form<Player> {

        private static final long serialVersionUID = 1L;

        public PlayerForm(final Player player) {
            super("playerEditForm", new CompoundPropertyModel<Player>(player));
            setOutputMarkupId(true);
            add(new TextField<String>("name"));
            add(new TextField<String>("surname"));
            add(new TextField<String>("club"));

            add(new Button("submit") {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    if (player.getPlayerId() != 0) {
                        tournamentService.updatePlayer(player);
                    } else {
                        tournamentService.createPlayer(player);
                    }
                    setResponsePage(pageFrom);
                }

            });

            add(new Button("back") {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(pageFrom);
                }

                @Override
                public void onError() {
                    getSession().cleanupFeedbackMessages();
                    setResponsePage(pageFrom);
                }
            });
        }

    }

    public PlayerEditPage(BasePage pageFrom, Player player) {
        this.pageFrom = pageFrom;
        add(new PlayerForm(player));
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return Model.of("Edit player");
    }

}
