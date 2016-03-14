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
import org.tahom.repository.model.Player;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.mask.BusyIndicatingMaskAppender;
import org.tahom.web.mask.MaskIndicatingAjaxButton;

public class StatisticPage extends BasePage {

	private static final long serialVersionUID = 1L;
	private PlayerStatisticDto statisticDto;

	public StatisticPage() {
		statisticDto = statisticService.getStatisticDto(getTournamentSession().getUser(), getSession().getLocale());
		createPage();
	}

	private void createPage() {
		add(new PlayerStatisticWebForm());
		// add(new Image("work", new ContextRelativeResource(getLocaleImagePath("/img/work.png"))));
	}

	private class PlayerStatisticWebForm extends Form<PlayerStatisticDto> {

		private static final long serialVersionUID = 1L;

		private final Model<Player> playerModel = new Model<Player>(statisticDto.getPlayer());

		public PlayerStatisticWebForm() {
			super("playerStatisticForm", new CompoundPropertyModel<PlayerStatisticDto>(statisticDto));
			addPlayerDropDown();
			addPlayerStatisticListView();
			addPlayerStatisticHeader();
			addSubmitButton();
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

		private void addPlayerDropDown() {
			add(new DropDownChoice<Player>("players", playerModel, statisticDto.getPlayers(),
			        new IChoiceRenderer<Player>() {

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
			}).add(new BusyIndicatingMaskAppender()));
		}

		private void addSubmitButton() {
			add(new MaskIndicatingAjaxButton("choosePlayer") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void submit(AjaxRequestTarget target, Form<?> form) {
					statisticDto.getPlayerInfos().clear();
					target.add(PlayerStatisticWebForm.this);

					PlayerStatisticDto dto = statisticService.getPlayerStatistic(playerModel.getObject());
					statisticDto.getPlayerInfos().putAll(dto.getPlayerInfos());
					target.add(PlayerStatisticWebForm.this);
				}

			});
		}
	}

}
