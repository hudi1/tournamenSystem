package org.tahom.web;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.tahom.processor.callable.FinalStandingPdfCallable;
import org.tahom.processor.service.finalStanding.dto.FinalStandingDto;
import org.tahom.processor.service.finalStanding.dto.FinalStandingPageDto;
import org.tahom.web.components.TournamentBackResourceButton;
import org.tahom.web.link.DownloadModelLink;
import org.tahom.web.model.EvenOddReplaceModel;
import org.tahom.web.model.TournamentFileReadOnlyModel;

@AuthorizeInstantiation(Roles.USER)
public class FinalStandingsPage extends TournamentHomePage {

	private static final long serialVersionUID = 1L;
	private FinalStandingPageDto finalStandingPageDto;

	public FinalStandingsPage() {
		this(new PageParameters());
	}

	public FinalStandingsPage(PageParameters parameters) {
		finalStandingPageDto = finalStandingService.getFinalStandingPageDto(tournament);
		createPage();
	}

	private void createPage() {
		add(new FinalStandingsForm());
	}

	private class FinalStandingsForm extends Form<FinalStandingPageDto> {

		private static final long serialVersionUID = 1L;

		public FinalStandingsForm() {
			super("finalStandingsForm", new CompoundPropertyModel<FinalStandingPageDto>(finalStandingPageDto));

			addFinalStandingsTable();
			addFinalStandingsButton();
		}

		private void addFinalStandingsButton() {
			addBackButton();
			addPrintFinalStandingsButton();
		}

		private void addFinalStandingsTable() {
			add(new PropertyListView<FinalStandingDto>("finalStandings") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<FinalStandingDto> listItem) {
					listItem.add(new Label("rank"));
					listItem.add(new Label("name"));
					listItem.add(new Label("club"));
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
			add(new DownloadModelLink("printFinalStandings", new TournamentFileReadOnlyModel<FinalStandingPageDto>(
			        callableService, finalStandingPageDto, FinalStandingPdfCallable.class)));
		}

	}
}