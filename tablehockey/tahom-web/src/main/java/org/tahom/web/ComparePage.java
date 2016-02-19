package org.tahom.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.PropertyModel;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Participant;
import org.tahom.web.components.TournamentAjaxResourceButton;
import org.tahom.web.util.WebUtil;

@AuthorizeInstantiation(Roles.USER)
public class ComparePage extends AbstractBasePage {

	private static final long serialVersionUID = 1L;

	private List<Participant> players;

	public ComparePage(Groups group, final ModalWindow window, Set<Participant> players) {
		this.players = new ArrayList<Participant>(players);
		createPage(window);
	}

	private void createPage(final ModalWindow window) {
		add(new PlayerForm(players, window));
	}

	private class PlayerForm extends Form<Participant> {

		private static final long serialVersionUID = 1L;

		public PlayerForm(final List<Participant> players, final ModalWindow window) {
			super("playerEditForm");

			addParticipantsListView(players);
			addCloseButton(window);
		}

		private void addCloseButton(final ModalWindow window) {
			add(new TournamentAjaxResourceButton("saveClose") {
				private static final long serialVersionUID = 1L;

				protected void submit(AjaxRequestTarget target, Form<?> form) {
					window.close(target);
				}
			});
		}

		private void addParticipantsListView(final List<Participant> players) {
			add(new PropertyListView<Participant>("participants", players) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<Participant> listItem) {
					final Participant participant = listItem.getModelObject();
					listItem.add(new Label("player", WebUtil.getParticipandPlayerFullName(participant)));
					listItem.add(new TextField<Integer>("rank", new PropertyModel<Integer>(participant, "rank")).add(
					        new AjaxFormComponentUpdatingBehavior("onchange") {

						        private static final long serialVersionUID = 1L;

						        @Override
						        protected void onUpdate(AjaxRequestTarget target) {
							        // Participant updatedParticipant = new Participant()._setId(participant.getId())
							        // ._setRank(participant.getRank());
							        participantService.updateParticipant(participant); // TODO test
						        }
					        }).setVisible(participant.getPlayer() != null));
				}
			});
		}
	}
}