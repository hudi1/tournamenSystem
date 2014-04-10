package org.toursys.web;

import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Result;
import org.toursys.repository.model.Score;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.StatisticForm;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;
import org.toursys.web.mask.MaskIndicatingAjaxButton;

@AuthorizeInstantiation(Roles.USER)
public class StatisticPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private final User user;

    public StatisticPage() {
        this.user = getTournamentSession().getUser();

        createPage();
    }

    private void createPage() {
        add(new StatisticWebForm(new StatisticForm()._setUser(user)));
        // add(new Image("work", new ContextRelativeResource(getLocaleImagePath("/img/work.png"))));
    }

    private class StatisticWebForm extends Form<StatisticForm> {

        private static final long serialVersionUID = 1L;

        public StatisticWebForm(final StatisticForm statisticForm) {
            super("statisticForm", new CompoundPropertyModel<StatisticForm>(statisticForm));

            addPlayerListView();
            add(new DropDownChoice<Tournament>("tournament", tournamentService.getTournaments(new Tournament()
                    ._setSeason(new Season()._setUser(user))), new IChoiceRenderer<Tournament>() {

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
            }));

            add(new DropDownChoice<Season>("season", seasonService.getSeasons(new Season()._setUser(user)),
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
                    }));

            add(new MaskIndicatingAjaxButton("showPlayers") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    StatisticWebForm.this.getModelObject().getPlayers().clear();
                    StatisticWebForm.this.getModelObject().getPlayers()
                            .addAll(playerService.getPlayersGames(statisticForm));
                    target.add(StatisticWebForm.this);
                }

                @Override
                public String maskText() {
                    return StatisticPage.this.getString("maskText");
                }

            });
        }

        private void addPlayerListView() {
            add(new PropertyListView<Player>("players") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<Player> listItem) {
                    final Player player = listItem.getModelObject();
                    listItem.add(new Label("surname"));
                    listItem.add(new Label("playerDiscriminator"));
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
    }

    private String getLocaleImagePath(String path) {
        Locale locale = Session.get().getLocale();
        String[] splitPath = path.split("\\.");
        String finalPath = splitPath[0] + "_" + locale.getLanguage() + "." + splitPath[1];
        return finalPath;
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return Model.of("Statistic page");
    }

}
