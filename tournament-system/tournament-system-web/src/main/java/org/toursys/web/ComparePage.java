package org.toursys.web;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.toursys.processor.service.GroupService;
import org.toursys.processor.service.ParticipantService;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;

@AuthorizeInstantiation(Roles.USER)
public class ComparePage extends WebPage {

    private static final long serialVersionUID = 1L;

    @SpringBean(name = "participantService")
    protected ParticipantService participantService;

    @SpringBean(name = "groupService")
    protected GroupService groupService;

    private Participant participant1;
    private Participant participant2;
    private Groups group;

    public ComparePage(Groups group, final ModalWindow window) {
        this(group, window, null, null);
    }

    public ComparePage(Groups group, final ModalWindow window, Participant participant1, Participant participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.group = group;
        createPage(window);
    }

    private void createPage(final ModalWindow window) {
        add(new PlayerForm("playerEditForm", participant1, participant2, window));
    }

    private class PlayerForm extends Form<Participant> {

        private static final long serialVersionUID = 1L;

        public PlayerForm(String id, final Participant player1, final Participant player2, final ModalWindow window) {
            super(id);
            setOutputMarkupId(true);
            Label nameLabel1 = new Label("name1", (player1 != null) ? player1.getPlayer().getSurname() + " "
                    + player1.getPlayer().getName() : "");
            TextField<Integer> rankTextField1 = new TextField<Integer>("equalRank1", new PropertyModel<Integer>(
                    (player1 != null) ? player1 : new Participant(), "equalRank"));
            Label rank1 = new Label("rank1", new ResourceModel("rank"));

            Label nameLabel2 = new Label("name2", (player2 != null) ? player2.getPlayer().getSurname() + " "
                    + player2.getPlayer().getName() : "");
            TextField<Integer> rankTextField2 = new TextField<Integer>("equalRank2", new PropertyModel<Integer>(
                    (player2 != null) ? player2 : new Participant(), "equalRank"));
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
                    participantService.updateParticipant(player1);
                    participantService.updateParticipant(player2);
                    window.close(target);
                }
            });

            add(new AjaxButton("reset", new ResourceModel("reset")) {
                private static final long serialVersionUID = 1L;

                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    groupService.resetEqualRank(group);
                    window.close(target);
                }
            });
        }
    }

    public Participant getParticipant1() {
        return participant1;
    }

    public void setParticipant1(Participant participant1) {
        this.participant1 = participant1;
    }

    public Participant getParticipant2() {
        return participant2;
    }

    public void setParticipant2(Participant participant2) {
        this.participant2 = participant2;
    }

}
