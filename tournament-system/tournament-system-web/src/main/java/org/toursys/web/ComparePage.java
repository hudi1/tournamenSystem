package org.toursys.web;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.processor.service.TournamentService;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayerResult;

public class ComparePage extends WebPage {

    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @SpringBean(name = "tournamentService")
    protected TournamentService tournamentService;

    private PlayerResult playerResult1;
    private PlayerResult playerResult2;
    private Groups group;

    public ComparePage(Groups group, final ModalWindow window) {
        this(group, window, null, null);
    }

    public ComparePage(Groups group, final ModalWindow window, PlayerResult playerResult1, PlayerResult playerResult2) {
        this.playerResult1 = playerResult1;
        this.playerResult2 = playerResult2;
        this.group = group;
        createPage(window);
    }

    private void createPage(final ModalWindow window) {
        add(new PlayerForm("playerEditForm", playerResult1, playerResult2, window));
    }

    private class PlayerForm extends Form<PlayerResult> {

        private static final long serialVersionUID = 1L;

        public PlayerForm(String id, final PlayerResult player1, final PlayerResult player2, final ModalWindow window) {
            super(id);
            setOutputMarkupId(true);
            Label nameLabel1 = new Label("name1", (player1 != null) ? player1.getPlayer().getSurname() + " "
                    + player1.getPlayer().getName() : "");
            TextField<Integer> rankTextField1 = new TextField<Integer>("equalRank1", new PropertyModel<Integer>(
                    (player1 != null) ? player1 : new PlayerResult(), "equalRank"));
            Label rank1 = new Label("rank1", new ResourceModel("rank"));

            Label nameLabel2 = new Label("name2", (player2 != null) ? player2.getPlayer().getSurname() + " "
                    + player2.getPlayer().getName() : "");
            TextField<Integer> rankTextField2 = new TextField<Integer>("equalRank2", new PropertyModel<Integer>(
                    (player2 != null) ? player2 : new PlayerResult(), "equalRank"));
            Label rank2 = new Label("rank2", new ResourceModel("rank"));

            if (player1 == null || player2 == null) {
                nameLabel1.setVisible(false);
                rankTextField1.setVisible(false);
                rank1.setVisible(false);
                nameLabel2.setVisible(false);
                rankTextField2.setVisible(false);
                rank2.setVisible(false);
            }

            add(nameLabel1);
            add(rankTextField1);
            add(rank1);
            add(nameLabel2);
            add(rankTextField2);
            add(rank2);

            add(new AjaxButton("close", new ResourceModel("close")) {
                private static final long serialVersionUID = 1L;

                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    tournamentService.updatePlayerResult(player1);
                    tournamentService.updatePlayerResult(player2);
                    window.close(target);
                }
            });

            add(new AjaxButton("reset", new ResourceModel("reset")) {
                private static final long serialVersionUID = 1L;

                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    tournamentService.resetEqualRank(group);
                    window.close(target);
                }
            });
        }
    }

    public PlayerResult getPlayerResult1() {
        return playerResult1;
    }

    public void setPlayerResult1(PlayerResult playerResult1) {
        this.playerResult1 = playerResult1;
    }

    public PlayerResult getPlayerResult2() {
        return playerResult2;
    }

    public void setPlayerResult2(PlayerResult playerResult2) {
        this.playerResult2 = playerResult2;
    }

}
