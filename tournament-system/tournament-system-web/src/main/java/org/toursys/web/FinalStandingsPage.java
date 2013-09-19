package org.toursys.web;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;
import org.toursys.processor.pdf.PdfFactory;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.TournamentImpl;

@AuthorizeInstantiation(Roles.USER)
public class FinalStandingsPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private List<FinalStanding> finalStandings;
    private TournamentImpl tournament;

    public FinalStandingsPage() {
        throw new RestartResponseAtInterceptPageException(new SeasonPage());
    }

    public FinalStandingsPage(PageParameters parameters) {
        tournament = getTournament(parameters);
        finalStandings = finalStandingService.getFinalStandings(tournament);
        createPage();
    }

    private void createPage() {
        add(new FinalStandingsForm());
    }

    private class FinalStandingsForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public FinalStandingsForm() {
            super("finalStandingsForm");
            setOutputMarkupId(true);

            IDataProvider<FinalStanding> playerDataProvider = new IDataProvider<FinalStanding>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Iterator<FinalStanding> iterator(int first, int count) {
                    return finalStandings.subList(first, first + count).iterator();
                }

                @Override
                public int size() {
                    return finalStandings.size();
                }

                @Override
                public IModel<FinalStanding> model(final FinalStanding object) {
                    return new LoadableDetachableModel<FinalStanding>() {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected FinalStanding load() {
                            return object;
                        }
                    };
                }

                @Override
                public void detach() {
                }
            };

            DataView<FinalStanding> dataView = new DataView<FinalStanding>("rows", playerDataProvider) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final Item<FinalStanding> listItem) {
                    final FinalStanding finalStanding = listItem.getModelObject();
                    listItem.setModel(new CompoundPropertyModel<FinalStanding>(finalStanding));
                    listItem.add(new Label("name", (finalStanding.getPlayer() != null) ? finalStanding.getPlayer()
                            .getName() : ""));
                    listItem.add(new Label("surname", (finalStanding.getPlayer() != null) ? finalStanding.getPlayer()
                            .getSurname() : ""));
                    listItem.add(new Label("rank", finalStanding.getFinalRank() + "."));

                    listItem.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public String getObject() {
                            return (listItem.getIndex() % 2 == 1) ? "even" : "odd";
                        }
                    }));
                }

            };
            add(dataView);

            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(PlayOffPage.class, getPageParameters());
                };
            }.setDefaultFormProcessing(false));

            DownloadLink finalStandingsLink = new DownloadLink("finalStandings", new AbstractReadOnlyModel<File>() {
                private static final long serialVersionUID = 1L;

                @Override
                public File getObject() {
                    File tempFile;
                    try {
                        tempFile = PdfFactory.createFinalStandings(WicketApplication.getFilesPath(), finalStandings);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    return tempFile;
                }
            });
            finalStandingsLink.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);

            add(finalStandingsLink);
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("finalStandings");
    }

}
