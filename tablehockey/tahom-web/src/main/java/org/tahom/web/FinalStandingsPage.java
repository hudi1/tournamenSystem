package org.tahom.web;

import java.io.File;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.tahom.processor.pdf.PdfFactory;
import org.tahom.repository.model.FinalStanding;
import org.tahom.web.components.TournamentBackResourceButton;
import org.tahom.web.link.DownloadModelLink;
import org.tahom.web.model.EvenOddReplaceModel;
import org.tahom.web.model.TournamentFileReadOnlyModel;

@AuthorizeInstantiation(Roles.USER)
public class FinalStandingsPage extends TournamentHomePage {

	private static final long serialVersionUID = 1L;
	private List<FinalStanding> finalStandings;

	public FinalStandingsPage() {
		this(new PageParameters());
	}

	public FinalStandingsPage(PageParameters parameters) {
		finalStandings = finalStandingService.getFinalStandings(tournament);
		createPage();
	}

	private void createPage() {
		add(new FinalStandingsForm());
	}

	private class FinalStandingsForm extends Form<Void> {

		private static final long serialVersionUID = 1L;

		public FinalStandingsForm() {
			super("finalStandingsForm");

			addFinalStandingsTable();
			addFinalStandingsButton();
		}

		private void addFinalStandingsButton() {
			addBackButton();
			addPrintFinalStandingsButton();
		}

		private void addFinalStandingsTable() {
			add(new PropertyListView<FinalStanding>("rows", finalStandings) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<FinalStanding> listItem) {
					final FinalStanding finalStanding = listItem.getModelObject();
					listItem.setModel(new CompoundPropertyModel<FinalStanding>(finalStanding));
					listItem.add(new Label("name", (finalStanding.getPlayer() != null) ? finalStanding.getPlayer()
					        .getName() : ""));
					listItem.add(new Label("surname", (finalStanding.getPlayer() != null) ? finalStanding.getPlayer()
					        .getSurname() + " " + finalStanding.getPlayer().getPlayerDiscriminator() : ""));
					listItem.add(new Label("rank", finalStanding.getFinalRank() + "."));

					listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
				}
			});
		}

		private void addBackButton() {
			add(new TournamentBackResourceButton("back") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					setResponsePage(PlayOffPage.class, getPageParameters());
				};
			});
		}

		private void addPrintFinalStandingsButton() {
			add(new DownloadModelLink("finalStandings", new TournamentFileReadOnlyModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getFormComponent() {
					return FinalStandingsForm.this;
				}

				@Override
				public File getTournamentObject() {
					return PdfFactory.createFinalStandings(WicketApplication.getFilesPath(), finalStandings);
				}
			}));
		}
	}
}