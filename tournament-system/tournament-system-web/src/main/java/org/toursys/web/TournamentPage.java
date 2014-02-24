package org.toursys.web;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;

@AuthorizeInstantiation(Roles.USER)
public class TournamentPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private Season selectedSeason;
    private List<Season> seasons;

    public TournamentPage() {
        seasons = seasonService.getAllSeasons();
        selectedSeason = getTournamentSession().getSeason();
        createPage();
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
            add(new AjaxButton("addTournament", new ResourceModel("addTournament")) {

                private static final long serialVersionUID = 1L;

                public void onClick(AjaxRequestTarget target) {
                    Tournament tournament = new Tournament();
                    tournament.setName(getString("enterName"));
                    selectedSeason.getTournaments().add(tournamentService.createTournament(selectedSeason, tournament));
                    target.add(TournamentForm.this);
                }

                @Override
                public boolean isVisible() {
                    return selectedSeason != null;
                };

                @Override
                protected void onError(AjaxRequestTarget target, Form<?> form) {
                    onClick(target);
                }

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    onClick(target);
                }

            });
        }

        private void addTournamentListView() {
            add(new PropertyListView<Tournament>("tournaments") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<Tournament> listItem) {
                    listItem.add(new AjaxEditableLabel<String>("name") {

                        private static final long serialVersionUID = 1L;

                        public void onSubmit(AjaxRequestTarget target) {
                            super.onSubmit(target);
                            tournamentService.updateTournament(listItem.getModelObject());
                        };

                    });
                    listItem.add(new AjaxLink<Void>("deleteTournament") {

                        private static final long serialVersionUID = 1L;

                        public void onClick(AjaxRequestTarget target) {
                            TournamentForm.this.getModelObject().getTournaments().remove(listItem.getModelObject());
                            target.add(TournamentForm.this);
                            tournamentService.deleteTournament((listItem.getModelObject()));
                        }

                        @Override
                        protected IAjaxCallDecorator getAjaxCallDecorator() {
                            return new AjaxCallDecorator() {

                                private static final long serialVersionUID = 1L;

                                @Override
                                public CharSequence decorateScript(Component c, CharSequence script) {
                                    return "if(confirm(" + getString("del.tournament") + ")){ " + script
                                            + "}else{return false;}";
                                }

                            };
                        }
                    }.add(AttributeModifier.replace("title", new AbstractReadOnlyModel<String>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public String getObject() {
                            return getString("deleteTournament");
                        }
                    })));

                    listItem.add(new AjaxLink<Void>("enterTournament") {

                        private static final long serialVersionUID = 1L;

                        public void onClick(AjaxRequestTarget target) {
                            PageParameters pageParameters = getPageParameters();
                            pageParameters.add("tournamentId", listItem.getModelObject().getId());
                            setResponsePage(RegistrationPage.class, pageParameters);
                        }

                    }.add(AttributeModifier.replace("title", new AbstractReadOnlyModel<String>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public String getObject() {
                            return getString("enterTournament");
                        }
                    })));

                    listItem.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public String getObject() {
                            return (listItem.getIndex() % 2 == 1) ? "even" : "odd";
                        }
                    }));
                }

            });
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

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("selectTournament");
    }
}
