package org.toursys.web;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
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
import org.toursys.repository.model.Season;
import org.toursys.repository.model.User;
import org.toursys.web.session.TournamentAuthenticatedWebSession;

@AuthorizeInstantiation(Roles.USER)
public class SeasonPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private static final int ITEMS_PER_PAGE = 10;
    private User user;

    public SeasonPage() {
        this.user = ((TournamentAuthenticatedWebSession) getSession()).getUser();
        createPage();
    }

    protected void createPage() {
        IDataProvider<Season> seasonDataProvider = createSeasonProvider();
        DataView<Season> dataView = createDataview(seasonDataProvider);
        add(dataView);
        add(new PagingNavigator("navigator", dataView));
        add(new SeasonForm());
    }

    private DataView<Season> createDataview(IDataProvider<Season> seasonDataProvider) {
        DataView<Season> dataView = new DataView<Season>("rows", seasonDataProvider, ITEMS_PER_PAGE) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Season> listItem) {
                final Season season = listItem.getModelObject();
                listItem.setModel(new CompoundPropertyModel<Season>(season));
                listItem.add(link("details", season));
                // TODO zisti preco to funguje aj bez clone
                listItem.add(new EditSeasonForm(((Season) listItem.getDefaultModelObject())));
                listItem.add(new AjaxLink<Void>("deleteSeason") {

                    private static final long serialVersionUID = 1L;

                    public void onClick(AjaxRequestTarget target) {
                        seasonService.deleteSeason(((Season) listItem.getDefaultModelObject()));
                        setResponsePage(SeasonPage.class);
                    }

                    @Override
                    protected IAjaxCallDecorator getAjaxCallDecorator() {
                        return new AjaxCallDecorator() {

                            private static final long serialVersionUID = 1L;

                            @Override
                            public CharSequence decorateScript(Component c, CharSequence script) {
                                return "if(confirm(" + getString("del.season") + ")){ " + script
                                        + "}else{return false;}";
                            }

                        };
                    }
                });
            }
        };
        return dataView;
    }

    private IDataProvider<Season> createSeasonProvider() {
        IDataProvider<Season> seasonDataProvider = new IDataProvider<Season>() {

            private static final long serialVersionUID = 1L;
            private List<Season> seasons = seasonService.getSeasons(new Season()._setUser(user));

            @Override
            public Iterator<Season> iterator(int first, int count) {
                return seasons.subList(first, first + count).iterator();
            }

            @Override
            public int size() {
                return seasons.size();
            }

            @Override
            public IModel<Season> model(final Season object) {
                return new LoadableDetachableModel<Season>() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    protected Season load() {
                        return object;
                    }
                };
            }

            @Override
            public void detach() {
            }
        };

        return seasonDataProvider;
    }

    private class SeasonForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public SeasonForm() {
            super("seasonForm");
            add(new Button("newSeason", new ResourceModel("newSeason")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new SeasonEditPage(new Season()._setUser(user)));
                }
            });
        }
    }

    private class EditSeasonForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public EditSeasonForm(final Season season) {
            super("editSeasonForm");
            add(new Button("editSeason", new ResourceModel("editSeason")) {

                private static final long serialVersionUID = 1L;

                private void edit() {
                    setResponsePage(new SeasonEditPage(season));
                }

                @Override
                public void onSubmit() {
                    edit();
                }

            });
        }
    }

    private static BookmarkablePageLink<Void> link(final String name, final Season season) {

        final BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>(name, TournamentPage.class);

        link.getPageParameters().set("seasonid", season.getId());
        link.add(new Label("name", season.getName()));
        return link;
    }

    @Override
    protected void setVisibility() {
        if (!((TournamentAuthenticatedWebSession) getSession()).isSignedIn()) {
            setVisible(false);
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("selectSeason");
    }
}
