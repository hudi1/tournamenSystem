package org.tahom.web;

import java.io.File;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.convert.IConverter;
import org.tahom.processor.pdf.PdfFactory;
import org.tahom.processor.service.playOffGame.dto.PlayOffGameDto;
import org.tahom.repository.model.GameStatus;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Result;
import org.tahom.repository.model.Tournament;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentResourceButton;
import org.tahom.web.converter.ResultConverter;
import org.tahom.web.link.DownloadModelLink;
import org.tahom.web.model.EvenOddReplaceModel;
import org.tahom.web.model.FontStyleReplaceModel;
import org.tahom.web.model.TournamentFileReadOnlyModel;

@AuthorizeInstantiation(Roles.USER)
public class PlayOffPage extends TournamentHomePage {

	private static final long serialVersionUID = 1L;
	private Tournament playOffTournament;

	public PlayOffPage() {
		this(new PageParameters());
	}

	public PlayOffPage(PageParameters parameters) {
		super(parameters);
		createPage();
	}

	protected void createPage() {
		this.playOffTournament = initPlayOffTournament();
		add(new PlayOffForm(Model.of(playOffTournament)));
	}

	private Tournament initPlayOffTournament() {
		Tournament tournament = new Tournament();
		tournament.getGroups().addAll(groupService.getFinalGroups(this.tournament));
		return tournament;
	}

	private class PlayOffForm extends Form<Tournament> {

		private static final long serialVersionUID = 1L;

		public PlayOffForm(IModel<Tournament> model) {
			super("playOffForm", new CompoundPropertyModel<Tournament>(model));
			addPlayOffTable();
			addSubmitButton();
			addPdfPlayOffButton();
		}

		private void addPlayOffTable() {
			addFinalGroupsGameListView();
		}

		private void addFinalGroupsGameListView() {
			add(new PropertyListView<Groups>("groups") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<Groups> listItem) {
					addPlayOffGames(listItem);
				}

				private void addPlayOffGames(final ListItem<Groups> groupListItem) {
					final Groups group = groupListItem.getModelObject();

					groupListItem.add(new Label("name", group.getName()));
					groupListItem.add(new ResourceLabel("round"));
					groupListItem.add(new ResourceLabel("player"));
					groupListItem.add(new ResourceLabel("result"));
					groupListItem.add(new ListView<PlayOffGameDto>("playOffGames", playOffGameService
					        .getPlayOffGamesByGroup(group, tournament)) {

						private static final long serialVersionUID = 1L;

						@Override
						protected void populateItem(final ListItem<PlayOffGameDto> listItem) {
							final PlayOffGameDto game = listItem.getModelObject();

							listItem.add(new Label("player", game.getPlayerName()).add(new AttributeModifier("style",
							        new FontStyleReplaceModel(game)) {
								private static final long serialVersionUID = 1L;

								@Override
								public boolean isEnabled(Component component) {
									return GameStatus.WIN.equals(game.getGameStatus());
								}
							}));

							listItem.add(new Label("opponent", game.getOpponentName()).add(new AttributeModifier(
							        "style", new FontStyleReplaceModel(game)) {
								private static final long serialVersionUID = 1L;

								@Override
								public boolean isEnabled(Component component) {
									return GameStatus.LOSE.equals(game.getGameStatus());
								}
							}));
							listItem.add(new Label("roundName", new ResourceModel(game.getRoundName())));
							listItem.add(new TextField<Result>("result", new PropertyModel<Result>(game, "result")) {
								private static final long serialVersionUID = 1L;

								@Override
								@SuppressWarnings("unchecked")
								public final <Results> IConverter<Results> getConverter(Class<Results> type) {
									return (IConverter<Results>) ResultConverter.getInstance();
								}
							}.add(new AjaxFormComponentUpdatingBehavior("onchange") {

								private static final long serialVersionUID = 1L;

								@Override
								protected void onUpdate(AjaxRequestTarget target) {
									playOffGameService.updatePlayOffGameResult(game);
								}
							}));

							listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
						}
					});
				}
			}.setReuseItems(true));
		}

		private void addPdfPlayOffButton() {
			add(new DownloadModelLink("printPlayOff", new TournamentFileReadOnlyModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public File getTournamentObject() {
					return PdfFactory.createPlayOff(WicketApplication.getFilesPath(), playOffTournament);
				}

				@Override
				public Component getFormComponent() {
					return PlayOffForm.this;
				}
			}));
		}

		private void addSubmitButton() {
			add(new TournamentResourceButton("save") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					playOffGameService.updateNextRoundPlayOffGames(tournament);
					finalStandingService.updatePromotingFinalStandings(tournament);
					setResponsePage(PlayOffPage.class, getPageParameters());
				};
			});
		}
	}
}