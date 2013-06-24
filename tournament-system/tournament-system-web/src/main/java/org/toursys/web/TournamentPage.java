package org.toursys.web;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;

@AuthorizeInstantiation(Roles.USER)
public class TournamentPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private static final int ITEMS_PER_PAGE = 10;

    private Season season;

    public TournamentPage() {
        throw new RestartResponseAtInterceptPageException(new SeasonPage());
    }

    public TournamentPage(PageParameters parameters) {
        super(parameters);
        checkPageParameters(parameters);
        season = getSeason(parameters);
        createPage();
    }

    private void checkPageParameters(PageParameters parameters) {
        if (parameters.get("seasonid").isNull()) {
            throw new RestartResponseAtInterceptPageException(new SeasonPage());
        }
    }

    protected void createPage() {
        IDataProvider<Tournament> tournamentDataProvider = createTournamentProvider();
        DataView<Tournament> dataView = createDataview(tournamentDataProvider);
        add(dataView);
        add(new PagingNavigator("navigator", dataView));
        add(new TournamentWebForm());
    }

    private DataView<Tournament> createDataview(IDataProvider<Tournament> tournamentDataProvider) {
        DataView<Tournament> dataView = new DataView<Tournament>("rows", tournamentDataProvider, ITEMS_PER_PAGE) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Tournament> listItem) {
                final Tournament tournament = listItem.getModelObject();
                listItem.setModel(new CompoundPropertyModel<Tournament>(tournament));
                listItem.add(link("details", tournament));
                // TODO zisti preco to funguje aj bez clone
                listItem.add(new EditTournamentForm(((Tournament) listItem.getDefaultModelObject())));
                listItem.add(new AjaxLink<Void>("deleteTournament") {

                    private static final long serialVersionUID = 1L;

                    public void onClick(AjaxRequestTarget target) {
                        tournamentService.deleteTournament(((Tournament) listItem.getDefaultModelObject()));
                        setResponsePage(TournamentPage.class, getPageParameters());
                    }

                    @Override
                    protected IAjaxCallDecorator getAjaxCallDecorator() {
                        return new AjaxCallDecorator() {

                            private static final long serialVersionUID = 1L;

                            @Override
                            public CharSequence decorateScript(Component c, CharSequence script) {
                                return "if(confirm(" + getString("del.tournament") + ")){" + script
                                        + "}else{return false;}";
                            }
                        };
                    }
                });
            }
        };

        return dataView;
    }

    private IDataProvider<Tournament> createTournamentProvider() {
        IDataProvider<Tournament> tournamentDataProvider = new IDataProvider<Tournament>() {

            private static final long serialVersionUID = 1L;
            private List<Tournament> tournaments = tournamentService.getListTournaments(new Tournament()
                    ._setSeason(season));

            @Override
            public Iterator<Tournament> iterator(int first, int count) {

                return tournaments.subList(first, first + count).iterator();
            }

            @Override
            public int size() {
                return tournaments.size();
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

        return tournamentDataProvider;
    }

    private class TournamentWebForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public TournamentWebForm() {
            super("tournamentForm");
            add(new Button("newTournament", new ResourceModel("newTournament")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new TournamentEditPage(season));
                }
            });

            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(SeasonPage.class);
                };
            }.setDefaultFormProcessing(false));
        }
    }

    private class EditTournamentForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public EditTournamentForm(final Tournament tournament) {
            super("editTournamentForm");
            add(new Button("editTournament", new ResourceModel("editTournament")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new TournamentEditPage(season, tournament));
                }
            });
        }
    }

    private static BookmarkablePageLink<Void> link(final String name, final Tournament tournament) {

        final BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>(name, RegistrationPage.class);

        link.getPageParameters().set("tournamentid", tournament.getId());
        link.getPageParameters().set("seasonid", tournament.getSeason().getId());
        link.add(new Label("name", tournament.getName()));
        return link;
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("selectTournament");
    }
}
