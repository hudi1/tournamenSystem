package org.toursys.web;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.toursys.repository.model.User;
import org.toursys.repository.model.WChRecord;
import org.toursys.repository.model.wch.WCh2017Season;
import org.toursys.web.components.TournamentButton;

public class WChPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private List<WChRecord> records;
    private User user;

    private void prepareData(PageParameters parameters) {
        records = wChService.getWchRecords(WCh2017Season.getInstance());
    }

    public WChPage() {
        this(new PageParameters());
    }

    public WChPage(PageParameters parameters) {
        super(parameters);
        this.user = getTournamentSession().getUser();

        prepareData(parameters);
        createPage();
    }

    protected void createPage() {
        add(new WChForm());
    }

    private class WChForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public WChForm() {
            super("wchForm");

            addWChTable();
            addWChButton();

        }

        private void addWChButton() {
            addUpdateButton();
        }

        private Button addUpdateButton() {
            Button schedule = new TournamentButton("update", new ResourceModel("update")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    wChService.updateWch(user);

                    setResponsePage(WChPage.class, getPageParameters());
                }

                @Override
                public boolean isVisible() {
                    return user != null;
                }
            };
            add(schedule);
            return schedule;
        }

        private void addWChTable() {
            addGWChTableHeader();
            addWChRecordsGroupListView();
        }

        private void addGWChTableHeader() {
            add(new Label("name", new ResourceModel("name")));
            add(new Label("rank", new ResourceModel("rank")));
        }

        private void addWChRecordsGroupListView() {
            add(new PropertyListView<WChRecord>("records", records) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<WChRecord> listItem) {
                    final WChRecord record = listItem.getModelObject();

                    String tournamentPoint1year1 = (record.getTournamentPoints1().size() > 0 && record
                            .getTournamentPoints1().get(0) != null) ? record.getTournamentPoints1().get(0).toString()
                            : "";
                    String tournamentPoint1year2 = (record.getTournamentPoints1().size() > 1 && record
                            .getTournamentPoints1().get(0) != null) ? record.getTournamentPoints1().get(1).toString()
                            : "";
                    String tournamentPoint1year3 = (record.getTournamentPoints1().size() > 2 && record
                            .getTournamentPoints1().get(0) != null) ? record.getTournamentPoints1().get(2).toString()
                            : "";
                    String points1year = (record.getSlovakChampionship1points() != null) ? record
                            .getSlovakChampionship1points().toString() : "";

                    String tournamentPoint2year1 = (record.getTournamentPoints2().size() > 0 && record
                            .getTournamentPoints2().get(0) != null) ? record.getTournamentPoints2().get(0).toString()
                            : "";
                    String tournamentPoint2year2 = (record.getTournamentPoints2().size() > 1 && record
                            .getTournamentPoints2().get(0) != null) ? record.getTournamentPoints2().get(1).toString()
                            : "";
                    String tournamentPoint2year3 = (record.getTournamentPoints2().size() > 2 && record
                            .getTournamentPoints2().get(0) != null) ? record.getTournamentPoints2().get(2).toString()
                            : "";
                    String points2year = (record.getSlovakChampionship2points() != null) ? record
                            .getSlovakChampionship2points().toString() : "";

                    listItem.add(new Label("index", listItem.getIndex() + 1 + ""));
                    listItem.add(new Label("name", record.getName()));
                    listItem.add(new Label("tournamentPoint1year1", tournamentPoint1year1));
                    listItem.add(new Label("tournamentPoint1year2", tournamentPoint1year2));
                    listItem.add(new Label("tournamentPoint1year3", tournamentPoint1year3));
                    listItem.add(new Label("points1year", points1year));
                    listItem.add(new Label("tournamentPoint2year1", tournamentPoint2year1));
                    listItem.add(new Label("tournamentPoint2year2", tournamentPoint2year2));
                    listItem.add(new Label("tournamentPoint2year3", tournamentPoint2year3));
                    listItem.add(new Label("points2year", points2year));
                    listItem.add(new Label("total", record.getTotal().toString()));
                    listItem.add(new Label("rank", listItem.getIndex() + 1 + ""));

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
    }

}