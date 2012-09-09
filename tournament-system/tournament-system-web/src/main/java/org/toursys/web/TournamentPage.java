package org.toursys.web;

import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.StringResourceModel;
import org.toursys.repository.form.TournamentForm;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;

public class TournamentPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private TournamentForm tournamentForm;

    public TournamentPage() {
        this.tournamentForm = new TournamentForm(new Season());
        createPage();
    }

    public TournamentPage(Season season) {
        TournamentForm tournamentForm = new TournamentForm(season);
        this.tournamentForm = tournamentForm;
        createPage();
    }

    private void createPage() {
        IDataProvider<Tournament> tournamentDataProvider = new IDataProvider<Tournament>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Iterator<Tournament> iterator(int first, int count) {

                return tournamentService.findTournament(tournamentForm).subList(first, first + count).iterator();
            }

            @Override
            public int size() {
                return tournamentService.findTournament(tournamentForm).size();
            }

            @Override
            public IModel<Tournament> model(final Tournament object) {
                return new LoadableDetachableModel<Tournament>() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    protected Tournament load() {
                        return object;
                    }
                };
            }

            @Override
            public void detach() {
            }
        };

        DataView<Tournament> dataView = new DataView<Tournament>("rows", tournamentDataProvider, 10) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Tournament> listItem) {
                final Tournament tournament = listItem.getModelObject();
                listItem.setModel(new CompoundPropertyModel<Tournament>(tournament));
                listItem.add(link("details", tournament));
                listItem.add(new EditTournamentForm(((Tournament) listItem.getDefaultModelObject()).clone()));
                listItem.add(new AjaxLink<Void>("deleteTournament") {

                    private static final long serialVersionUID = 1L;

                    public void onClick(AjaxRequestTarget target) {
                        tournamentService.deleteTournament(((Tournament) listItem.getDefaultModelObject()));

                        setResponsePage(new TournamentPage(tournamentForm.getSeason()) {
                            private static final long serialVersionUID = 1L;
                        });
                    }

                    @Override
                    protected IAjaxCallDecorator getAjaxCallDecorator() {
                        return new AjaxCallDecorator() {

                            private static final long serialVersionUID = 1L;

                            @Override
                            public CharSequence decorateScript(Component c, CharSequence script) {
                                return "if(confirm('Opravdu odstranit tento turnaj ?')){" + script
                                        + "}else{return false;}";
                            }

                        };
                    }

                });

            }
        };

        add(dataView);
        add(new PagingNavigator("navigator", dataView));
        add(new TournamentWebForm());
        add(new FeedbackPanel("feedbackPanel"));

    }

    private class TournamentWebForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public TournamentWebForm() {
            super("tournamentForm");
            add(new Button("newTournament", new StringResourceModel("newTournament", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    Tournament tournament = new Tournament();

                    if (tournamentForm.getSeason() != null) {
                        tournament.setSeasonId(tournamentForm.getSeason().getSeasonId());
                    }
                    setResponsePage(new TournamentEditPage(TournamentPage.this, tournament) {

                        private static final long serialVersionUID = 1L;
                    });
                }
            });

            add(new Button("back", new StringResourceModel("back", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new SeasonPage());
                }
            });
        }
    }

    private class EditTournamentForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public EditTournamentForm(final Tournament tournament) {
            super("editTournamentForm");
            add(new Button("editTournament", new StringResourceModel("editTournament", null)) {

                private static final long serialVersionUID = 1L;

                private void edit() {
                    setResponsePage(new TournamentEditPage(TournamentPage.this, tournament) {

                        private static final long serialVersionUID = 1L;

                    });
                }

                @Override
                public void onSubmit() {
                    edit();
                }

            });
        }
    }

    private Link<Void> link(final String name, final Tournament tournament) {
        Link<Void> link = new Link<Void>(name) {

            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                setResponsePage(new RegistrationPage(tournament, TournamentPage.this));
            }
        };

        link.add(new Label("name", tournament.getName()));
        return link;
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new StringResourceModel("selectTournament", null);
    }
}
