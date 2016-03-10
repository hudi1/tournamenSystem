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
import org.tahom.processor.service.participant.dto.ParticipantDto;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Participant;
import org.tahom.web.components.TournamentAjaxResourceButton;

@AuthorizeInstantiation(Roles.USER)
public class ComparePage extends AbstractBasePage {

	private static final long serialVersionUID = 1L;

	private final List<ParticipantDto> players;

	public ComparePage(Groups group, final ModalWindow window, Set<ParticipantDto> players) {
		this.players = new ArrayList<ParticipantDto>(players);
		createPage(window);
	}

	private void createPage(final ModalWindow window) {
		add(new PlayerForm(window));
	}

	private class PlayerForm extends Form<Void> {

		private static final long serialVersionUID = 1L;

		public PlayerForm(final ModalWindow window) {
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

		private void addParticipantsListView(final List<ParticipantDto> players) {
			add(new PropertyListView<ParticipantDto>("participants", players) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<ParticipantDto> listItem) {
					final ParticipantDto participant = listItem.getModelObject();
					listItem.add(new Label("name"));
					listItem.add(new TextField<Integer>("rank").add(new AjaxFormComponentUpdatingBehavior("change") {

						private static final long serialVersionUID = 1L;

						@Override
						protected void onUpdate(AjaxRequestTarget target) {
							Participant updatedParticipant = new Participant()._setId(participant.getId())._setRank(
							        participant.getRank());
							participantService.updateParticipant(updatedParticipant); // TODO test
						}
					}));
				}
			});
		}
	}
}