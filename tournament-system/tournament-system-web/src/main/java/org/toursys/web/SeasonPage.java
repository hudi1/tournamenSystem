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
import org.toursys.repository.model.Season;

public class SeasonPage extends BasePage {

    private static final long serialVersionUID = 1L;

    public SeasonPage() {
        createPage();
    }

    private void createPage() {
        IDataProvider<Season> seasonDataProvider = new IDataProvider<Season>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Iterator<Season> iterator(int first, int count) {
                return tournamentService.getAllSeason().subList(first, first + count).iterator();
            }

            @Override
            public int size() {
                return tournamentService.getAllSeason().size();
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

        DataView<Season> dataView = new DataView<Season>("rows", seasonDataProvider, 10) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Season> listItem) {
                final Season season = listItem.getModelObject();
                listItem.setModel(new CompoundPropertyModel<Season>(season));
                listItem.add(link("details", season));
                listItem.add(new EditSeasonForm(((Season) listItem.getDefaultModelObject()).clone()));
                listItem.add(new AjaxLink<Void>("deleteSeason") {

                    private static final long serialVersionUID = 1L;

                    public void onClick(AjaxRequestTarget target) {
                        tournamentService.deleteSeason(((Season) listItem.getDefaultModelObject()));

                        setResponsePage(new SeasonPage() {
                            private static final long serialVersionUID = 1L;
                        });
                    }

                    @Override
                    protected IAjaxCallDecorator getAjaxCallDecorator() {
                        return new AjaxCallDecorator() {

                            private static final long serialVersionUID = 1L;

                            @Override
                            public CharSequence decorateScript(Component c, CharSequence script) {
                                return "if(confirm('Opravdu odstranit tuto sezonu ?')){" + script
                                        + "}else{return false;}";
                            }

                        };
                    }

                });

            }
        };

        add(dataView);
        add(new PagingNavigator("navigator", dataView));
        add(new SeasonForm());
        add(new FeedbackPanel("feedbackPanel"));

    }

    private class SeasonForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public SeasonForm() {
            super("seasonForm");
            add(new Button("newSeason", new StringResourceModel("newSeason", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new SeasonEditPage(SeasonPage.this, new Season()) {

                        private static final long serialVersionUID = 1L;
                    });
                }
            });
        }
    }

    private class EditSeasonForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public EditSeasonForm(final Season season) {
            super("editSeasonForm");
            add(new Button("editSeason", new StringResourceModel("editSeason", null)) {

                private static final long serialVersionUID = 1L;

                private void edit() {
                    setResponsePage(new SeasonEditPage(SeasonPage.this, season) {

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

    public static Link<Void> link(final String name, final Season season) {
        Link<Void> link = new Link<Void>(name) {

            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                setResponsePage(new TournamentPage(season));
            }
        };

        link.add(new Label("name", season.getName()));
        return link;
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new StringResourceModel("selectSeason", null);
    }
}
