package org.tahom.web;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.tahom.processor.service.statistic.dto.PlayerStatisticDto;
import org.tahom.processor.service.statistic.dto.PlayerStatisticInfo;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.GroupsType;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.Result;
import org.tahom.repository.model.Score;
import org.tahom.repository.model.Season;
import org.tahom.repository.model.StatisticForm;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.User;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.mask.BusyIndicatingMaskAppender;
import org.tahom.web.mask.MaskIndicatingAjaxButton;

//@AuthorizeInstantiation(Roles.USER)
public class StatisticPage extends BasePage {

	private static final long serialVersionUID = 1L;
	private final User user;
	private final Season actualSeason;
	private List<Player> players;
	private PlayerStatisticDto selectedDto;

	public StatisticPage() {
		this.user = getTournamentSession().getUser();
		this.actualSeason = null;
		// TODO user's player ?
		this.players = playerService.getSortedUserPlayers(null, getTournamentSession().getLocale());
		this.selectedDto = new PlayerStatisticDto();
		selectedDto.setPlayer(players.get(0));
		createPage();
	}

	private void createPage() {
		// add(new TournamentStatisticWebForm(new StatisticForm()._setUser(user)));
		add(new PlayerStatisticWebForm(Model.of(selectedDto)));
		// add(new Image("work", new ContextRelativeResource(getLocaleImagePath("/img/work.png"))));
	}

	private class PlayerStatisticWebForm extends Form<PlayerStatisticDto> {

		private static final long serialVersionUID = 1L;

		public PlayerStatisticWebForm(final IModel<PlayerStatisticDto> model) {
			super("playerStatisticForm", new CompoundPropertyModel<PlayerStatisticDto>(model));
			Model<Player> playerModel = Model.of(model.getObject().getPlayer());
			addPlayerDropDown(playerModel);
			addPlayerStatisticListView();
			addPlayerStatisticHeader();
			addSubmitButton(playerModel);
		}

		private void addPlayerStatisticHeader() {
			add(new ResourceLabel("tournamentName"));
			add(new ResourceLabel("rank"));
			add(new ResourceLabel("matches"));
			add(new ResourceLabel("winners"));
			add(new ResourceLabel("draws"));
			add(new ResourceLabel("loses"));
			add(new ResourceLabel("playerScore"));
			add(new ResourceLabel("playerPoints"));
			add(new ResourceLabel("percentage"));
		}

		private void addPlayerStatisticListView() {
			add(new PropertyListView<PlayerStatisticInfo>("playerInfoValues") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<PlayerStatisticInfo> listItem) {
					listItem.add(new Label("tournamentName"));
					listItem.add(new Label("finalRank"));
					listItem.add(new Label("matchesCount"));
					listItem.add(new Label("winnersCount"));
					listItem.add(new Label("drawsCount"));
					listItem.add(new Label("losesCount"));
					listItem.add(new Label("score"));
					listItem.add(new Label("points"));
					listItem.add(new Label("percentage"));
				}
			});

		}

		private void addPlayerDropDown(final IModel<Player> model) {
			add(new DropDownChoice<Player>("players", model, players, new IChoiceRenderer<Player>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Object getDisplayValue(Player player) {
					return (player.getSurname().toString() + " " + player.getName());
				}

				@Override
				public String getIdValue(Player player, int index) {
					if (player == null || player.getId() == null) {
						return null;
					} else {
						return player.getId().toString();
					}
				}

				@Override
				public Player getObject(String id, IModel<? extends List<? extends Player>> choices) {
					List<? extends Player> _choices = choices.getObject();
					for (int index = 0; index < _choices.size(); index++) {
						final Player choice = _choices.get(index);
						if (getIdValue(choice, index) != null && getIdValue(choice, index).equals(id)) {
							return choice;
						}
					}
					return null;
				}
			}).add(new AjaxFormComponentUpdatingBehavior("change") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {

				}
			}).add(new BusyIndicatingMaskAppender(getString("maskText"))));
		}

		private void addSubmitButton(final Model<Player> playerModel) {
			add(new MaskIndicatingAjaxButton("choosePlayer") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void submit(AjaxRequestTarget target, Form<?> form) {
					selectedDto.getPlayerInfos().clear();
					target.add(PlayerStatisticWebForm.this);

					PlayerStatisticDto dto = statisticService.getPlayerStatistic(playerModel.getObject());
					selectedDto.getPlayerInfos().putAll(dto.getPlayerInfos());
					target.add(PlayerStatisticWebForm.this);
				}

				@Override
				public String maskText() {
					return getString("maskText");
				}

			});
		}
	}

	private class TournamentStatisticWebForm extends Form<StatisticForm> {

		private static final long serialVersionUID = 1L;

		public TournamentStatisticWebForm(final StatisticForm statisticForm) {
			super("statisticForm", new CompoundPropertyModel<StatisticForm>(statisticForm));

			addPlayerListView();
			addDropDownTournament();
			addDropDownSeason();
			addSubmitButton(statisticForm);

		}

		private void addPlayerListView() {
			add(new PropertyListView<Player>("players") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<Player> listItem) {
					final Player player = listItem.getModelObject();
					listItem.add(new Label("surname"));
					listItem.add(new Label("name"));

					int leftSide = 0;
					int rightSide = 0;

					Integer zeroCount = 0;
					Integer tenPlusCount = 0;
					Integer matches = 0;

					for (Participant participant : player.getParticipants()) {
						// System.out.println(pp);
						for (Game game : participant.getGames()) {
							if (game.getResult() == null) {
								continue;
							}
							for (Result result : game.getResult().getResults()) {
								if (GroupsType.FINAL.equals(participant.getGroup().getType()) && result.isContumacy()) {
									continue;
								}

								matches++;

								if (result.getLeftSide() > 9) {
									tenPlusCount++;
								}

								if (result.getRightSide() == 0) {
									zeroCount++;
								}

								leftSide += result.getLeftSide();
								rightSide += result.getRightSide();
							}
						}
					}
					Score score = new Score(leftSide, rightSide);

					listItem.add(new Label("score", score.toString()));
					listItem.add(new Label("zeroCount", zeroCount.toString()));
					listItem.add(new Label("tenPlusCount", tenPlusCount.toString()));
					listItem.add(new Label("matches", matches.toString()));
				}
			});
		}

		public void addDropDownTournament() {
			// TODO brat sezonu z DropDownChoice
			Season season = new Season();
			if (actualSeason != null) {
				season.getTournaments().addAll(actualSeason.getTournaments());
			}
			add(new DropDownChoice<Tournament>("tournament", season.getTournaments(),
			        new IChoiceRenderer<Tournament>() {

				        private static final long serialVersionUID = 1L;

				        @Override
				        public Object getDisplayValue(Tournament tournament) {
					        return tournament.getName();
				        }

				        @Override
				        public String getIdValue(Tournament tournament, int index) {
					        if (tournament == null || tournament.getId() == null) {
						        return null;
					        } else {
						        return tournament.getId().toString();
					        }
				        }

				        @Override
				        public Tournament getObject(String id, IModel<? extends List<? extends Tournament>> choices) {
					        List<? extends Tournament> _choices = choices.getObject();
					        for (int index = 0; index < _choices.size(); index++) {
						        // Get next choice
						        final Tournament choice = _choices.get(index);
						        if (getIdValue(choice, index).equals(id)) {
							        return choice;
						        }
					        }
					        return null;
				        }
			        }));
		}

		public void addDropDownSeason() {
			add(new DropDownChoice<Season>("season", Model.of(actualSeason), seasonService.getUserSeasons(user),
			        new IChoiceRenderer<Season>() {

				        private static final long serialVersionUID = 1L;

				        @Override
				        public Object getDisplayValue(Season season) {
					        return season.getName();
				        }

				        @Override
				        public String getIdValue(Season season, int index) {
					        if (season == null || season.getId() == null) {
						        return null;
					        } else {
						        return season.getId().toString();
					        }
				        }

				        @Override
				        public Season getObject(String id, IModel<? extends List<? extends Season>> choices) {
					        List<? extends Season> _choices = choices.getObject();
					        for (int index = 0; index < _choices.size(); index++) {
						        // Get next choice
						        final Season choice = _choices.get(index);
						        if (getIdValue(choice, index).equals(id)) {
							        return choice;
						        }
					        }
					        return null;
				        }

			        }));
		}

		private void addSubmitButton(final StatisticForm statisticForm) {
			add(new MaskIndicatingAjaxButton("showPlayers") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void submit(AjaxRequestTarget target, Form<?> form) {
					TournamentStatisticWebForm.this.getModelObject().getPlayers().clear();
					TournamentStatisticWebForm.this.getModelObject().getPlayers()
					        .addAll(playerService.getPlayersGames(statisticForm));
					target.add(TournamentStatisticWebForm.this);
				}

				@Override
				public String maskText() {
					return getString("maskText");
				}

			});
		}
	}

}
