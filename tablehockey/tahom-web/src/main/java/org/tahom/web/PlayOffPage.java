package org.tahom.web;

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
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.tahom.processor.callable.PlayOffPdfCallable;
import org.tahom.processor.service.playOffGame.dto.PlayOffGameDto;
import org.tahom.processor.service.playOffGame.dto.PlayOffGroupDto;
import org.tahom.processor.service.playOffGame.dto.PlayOffPageDto;
import org.tahom.repository.model.GameStatus;
import org.tahom.repository.model.impl.Result;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentResourceButton;
import org.tahom.web.link.DownloadModelLink;
import org.tahom.web.model.EvenOddReplaceModel;
import org.tahom.web.model.FontStyleReplaceModel;
import org.tahom.web.model.TournamentFileReadOnlyModel;

@AuthorizeInstantiation(Roles.USER)
public class PlayOffPage extends TournamentHomePage {

	private static final long serialVersionUID = 1L;
	private PlayOffPageDto playOffPageDto;

	public PlayOffPage() {
		this(new PageParameters());
	}

	public PlayOffPage(PageParameters parameters) {
		super(parameters);
		createPage();
	}

	protected void createPage() {
		this.playOffPageDto = playOffGameService.getPlayOffPageDto(tournament);
		add(new PlayOffForm());
	}

	private class PlayOffForm extends Form<PlayOffPageDto> {

		private static final long serialVersionUID = 1L;

		public PlayOffForm() {
			super("playOffForm", new CompoundPropertyModel<PlayOffPageDto>(playOffPageDto));
			addPlayOffTable();
			addSubmitButton();
			addPdfPlayOffButton();
		}

		private void addPlayOffTable() {
			addFinalGroupsGameListView();
		}

		private void addFinalGroupsGameListView() {
			add(new PropertyListView<PlayOffGroupDto>("playOffGroups") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<PlayOffGroupDto> listItem) {
					addPlayOffGames(listItem);
				}

				private void addPlayOffGames(final ListItem<PlayOffGroupDto> groupListItem) {

					groupListItem.add(new Label("name"));
					groupListItem.add(new ResourceLabel("round"));
					groupListItem.add(new ResourceLabel("player"));
					groupListItem.add(new ResourceLabel("result"));
					groupListItem.add(new PropertyListView<PlayOffGameDto>("playOffGames") {

						private static final long serialVersionUID = 1L;

						@Override
						protected void populateItem(final ListItem<PlayOffGameDto> listItem) {
							final PlayOffGameDto game = listItem.getModelObject();

							listItem.add(new Label("playerName").add(new AttributeModifier("style",
							        new FontStyleReplaceModel(game)) {
								private static final long serialVersionUID = 1L;

								@Override
								public boolean isEnabled(Component component) {
									return GameStatus.WIN.equals(game.getGameStatus());
								}
							}));

							listItem.add(new Label("opponentName").add(new AttributeModifier("style",
							        new FontStyleReplaceModel(game)) {
								private static final long serialVersionUID = 1L;

								@Override
								public boolean isEnabled(Component component) {
									return GameStatus.LOSE.equals(game.getGameStatus());
								}
							}));
							listItem.add(new Label("roundName", new ResourceModel(game.getRoundName())));
							listItem.add(new TextField<Result>("result").add(new AjaxFormComponentUpdatingBehavior(
							        "change") {

								private static final long serialVersionUID = 1L;

								@Override
								protected void onUpdate(AjaxRequestTarget target) {
									playOffGameService.updatePlayOffGameResult(game);
								}

								protected void onError(AjaxRequestTarget target, RuntimeException e) {
									target.add(feedbackPanel);
								};
							}));

							listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
						}
					});
				}
			}.setReuseItems(true));
		}

		private void addPdfPlayOffButton() {
			add(new DownloadModelLink("printPlayOff", new TournamentFileReadOnlyModel<PlayOffPageDto>(callableService,
			        playOffPageDto, PlayOffPdfCallable.class)));
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