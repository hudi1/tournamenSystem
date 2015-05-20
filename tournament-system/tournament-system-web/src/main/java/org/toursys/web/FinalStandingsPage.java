package org.toursys.web;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.toursys.processor.pdf.PdfFactory;
import org.toursys.repository.model.FinalStanding;
import org.toursys.web.components.TournamentButton;
import org.toursys.web.link.DownloadModelLink;
import org.toursys.web.model.TournamentFileReadOnlyModel;

@AuthorizeInstantiation(Roles.USER)
public class FinalStandingsPage extends TournamentHomePage {

    private static final long serialVersionUID = 1L;
    private List<FinalStanding> finalStandings;

    public FinalStandingsPage() {
        this(new PageParameters());
    }

    public FinalStandingsPage(PageParameters parameters) {
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

            final DataView<FinalStanding> dataView = new DataView<FinalStanding>("rows", playerDataProvider) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final Item<FinalStanding> listItem) {
                    final FinalStanding finalStanding = listItem.getModelObject();
                    listItem.setModel(new CompoundPropertyModel<FinalStanding>(finalStanding));
                    listItem.add(new Label("name", (finalStanding.getPlayer() != null) ? finalStanding.getPlayer()
                            .getName() : ""));
                    listItem.add(new Label("surname", (finalStanding.getPlayer() != null) ? finalStanding.getPlayer()
                            .getSurname() + " " + finalStanding.getPlayer().getPlayerDiscriminator() : ""));
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

            add(new TournamentButton("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    setResponsePage(PlayOffPage.class, getPageParameters());
                };
            }.setDefaultFormProcessing(false));

            add(new DownloadModelLink("finalStandings", new TournamentFileReadOnlyModel() {
                private static final long serialVersionUID = 1L;

                @Override
                public Component getFormComponent() {
                    return FinalStandingsForm.this;
                }

                @Override
                public File getTournamentObject() {
                    return PdfFactory.createFinalStandings(WicketApplication.getFilesPath(), finalStandings);
                }
            }));
        }
    }

}
