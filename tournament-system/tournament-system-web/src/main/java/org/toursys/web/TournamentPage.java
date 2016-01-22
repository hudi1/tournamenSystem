package org.toursys.web;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.web.components.PropertyPageableListView;
import org.toursys.web.components.TournamentAjaxButton;
import org.toursys.web.components.TournamentAjaxCheckBox;
import org.toursys.web.components.TournamentAjaxEditableLabel;
import org.toursys.web.link.TournamentAjaxLink;

@AuthorizeInstantiation(Roles.USER)
public class TournamentPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private Season selectedSeason;
    private List<Season> seasons;

    public TournamentPage() {
        seasons = seasonService.getUserSeasons(getTournamentSession().getUser());
        selectedSeason = selectSeason();
        createPage();
    }

    private Season selectSeason() {
        Season season = getTournamentSession().getSeason();
        if (season == null) {
            if (!seasons.isEmpty()) {
                season = seasons.get(0);
            }
        }
        return season;
    }

    protected void createPage() {
        add(new TournamentForm(Model.of(selectedSeason)));
    }

    private class TournamentForm extends Form<Season> {

        private static final long serialVersionUID = 1L;

        public TournamentForm(final IModel<Season> model) {
            super("tournamentForm", new CompoundPropertyModel<Season>(model));
            addSeasonLabel();
            addSeasonDropDownChoice(model);
            addTournamentListView();
            addTournamentAddButton();
        }

        private void addTournamentAddButton() {
            add(new TournamentAjaxButton("addTournament", new ResourceModel("addTournament")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit(AjaxRequestTarget target, Form<?> form) {
                    Tournament tournament = new Tournament();
                    tournament.setName(getString("enterName"));
                    tournamentService.createTournament(selectedSeason, tournament);
                    selectedSeason.getTournaments().add(tournamentService.getTournament(tournament));
                    target.add(TournamentForm.this);
                }

                @Override
                public boolean isVisible() {
                    return selectedSeason != null;
                }

            });
        }

        private void addTournamentListView() {
            PropertyPageableListView<Tournament> listView;
            add(listView = new PropertyPageableListView<Tournament>("tournaments", ITEM_PER_PAGE) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<Tournament> listItem) {
                    listItem.add(new TournamentAjaxEditableLabel("name") {

                        private static final long serialVersionUID = 1L;

                        public void submit(AjaxRequestTarget target) {
                            tournamentService.updateTournament(listItem.getModelObject());
                        };

                    });
                    listItem.add(new TournamentAjaxLink("deleteTournament") {

                        private static final long serialVersionUID = 1L;

                        public void click(AjaxRequestTarget target) {
                            Tournament tournament = listItem.getModelObject();
                            TournamentForm.this.getModelObject().getTournaments().remove(tournament);
                            tournamentService.deleteTournament(tournament);
                            target.add(TournamentForm.this);
                        }

                        @Override
                        protected IAjaxCallDecorator getAjaxCallDecorator() {
                            return new AjaxCallDecorator() {

                                private static final long serialVersionUID = 1L;

                                @Override
                                public CharSequence decorateScript(Component c, CharSequence script) {
                                    return "if(!confirm('" + getString("del.tournament") + "')) return false;" + script;
                                }

                            };
                        }
                    }.add(new Image("imgDelete", new SharedResourceReference("delete"))).add(
                            AttributeModifier.replace("title", new AbstractReadOnlyModel<String>() {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public String getObject() {
                                    return getString("deleteTournament");
                                }
                            })));

                    listItem.add(new TournamentAjaxLink("enterTournament") {

                        private static final long serialVersionUID = 1L;

                        public void click(AjaxRequestTarget target) {
                            getTournamentSession().setTournament(listItem.getModelObject());
                            setResponsePage(RegistrationPage.class);
                        }

                    }.add(new Image("imgEnter", new SharedResourceReference("enter"))).add(
                            AttributeModifier.replace("title", new AbstractReadOnlyModel<String>() {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public String getObject() {
                                    return getString("enterTournament");
                                }
                            })));

                    listItem.add(new TournamentAjaxCheckBox("publish") {

                        private static final long serialVersionUID = 1L;

                        protected void update(AjaxRequestTarget target) {
                            tournamentService.updateTournament(listItem.getModelObject());
                        };

                    });

                    listItem.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public String getObject() {
                            return (listItem.getIndex() % 2 == 1) ? "even" : "odd";
                        }
                    }));
                }

            });
            AjaxPagingNavigator navigator = new AjaxPagingNavigator("navigator", listView);
            add(navigator);
        }

        private void addSeasonDropDownChoice(final IModel<Season> model) {
            add(new DropDownChoice<Season>("seasons", model, seasons, new IChoiceRenderer<Season>() {

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
            }).add(new AjaxFormComponentUpdatingBehavior("onchange") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    selectedSeason = model.getObject();
                    getTournamentSession().setSeason(selectedSeason);
                    target.add(TournamentForm.this);
                }
            }));
        }

        private void addSeasonLabel() {
            add(new Label("season", new ResourceModel("season")));
        }
    }

}
