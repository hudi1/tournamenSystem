package org.toursys.web;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
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

    private List<Participant> players;
    private Groups group;

    public ComparePage(Groups group, final ModalWindow window, List<Participant> players) {
        this.players = players;
        this.group = group;
        createPage(window);
    }

    private void createPage(final ModalWindow window) {
        add(new PlayerForm(players, window));
    }

    private class PlayerForm extends Form<Participant> {

        private static final long serialVersionUID = 1L;

        public PlayerForm(final List<Participant> players, final ModalWindow window) {
            super("playerEditForm");

            IDataProvider<Participant> dataProvider = new IDataProvider<Participant>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Iterator<Participant> iterator(int first, int count) {
                    return players.subList(first, first + count).iterator();
                }

                @Override
                public int size() {
                    return players.size();
                }

                @Override
                public IModel<Participant> model(final Participant object) {
                    return new LoadableDetachableModel<Participant>() {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected Participant load() {
                            return object;
                        }
                    };
                }

                @Override
                public void detach() {
                }
            };

            final DataView<Participant> dataView = new DataView<Participant>("rows", dataProvider) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final Item<Participant> listItem) {
                    final Participant participant = listItem.getModelObject();
                    listItem.setModel(new CompoundPropertyModel<Participant>(participant));
                    String playerName = "";

                    if (participant.getPlayer() != null) {
                        playerName = participant.getPlayer().getName() + " " + participant.getPlayer().getSurname()
                                + " " + participant.getPlayer().getPlayerDiscriminator();
                    }

                    listItem.add(new Label("player", playerName));

                    listItem.add(new TextField<Integer>("equalRank", new PropertyModel<Integer>(participant,
                            "equalRank")).add(new AjaxFormComponentUpdatingBehavior("onchange") {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void onUpdate(AjaxRequestTarget target) {
                            participantService.updateParticipant(participant);
                        }
                    }).setVisible(participant.getPlayer() != null));

                }
            };
            add(dataView);

            add(new AjaxButton("close", new ResourceModel("close")) {
                private static final long serialVersionUID = 1L;

                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
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
}
