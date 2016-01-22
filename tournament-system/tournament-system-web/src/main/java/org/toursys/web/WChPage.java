package org.toursys.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.toursys.processor.service.wch.season.WCh2015Season;
import org.toursys.processor.service.wch.season.WCh2017Season;
import org.toursys.repository.model.User;
import org.toursys.repository.model.wch.WChSeason;
import org.toursys.repository.model.wch.WChTableRecord;
import org.toursys.repository.model.wch.WChTournamentDetailsRecord;
import org.toursys.repository.model.wch.season.WChRecord;
import org.toursys.web.components.TournamentButton;

public class WChPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private WChTableRecord selectedTableRecord;
    private WChSeason selectedSeason;
    private User user;
    private static List<WChSeason> WCH_SEASONS = new ArrayList<WChSeason>();

    static {
        WCH_SEASONS.add(WCh2017Season.getInstance());
        WCH_SEASONS.add(WCh2015Season.getInstance());
    }

    public WChPage() {
        this(new PageParameters());
    }

    public WChPage(PageParameters parameters) {
        super(parameters);
        this.user = getTournamentSession().getUser();
        selectedSeason = WCh2017Season.getInstance();
        selectedTableRecord = wChService.getWchRecords(selectedSeason);

        createPage();
    }

    protected void createPage() {
        add(new WChForm(Model.of(selectedTableRecord)));
    }

    private class WChForm extends Form<WChTableRecord> {

        private static final long serialVersionUID = 1L;

        public WChForm(final IModel<WChTableRecord> model) {
            super("wchForm", new CompoundPropertyModel<WChTableRecord>(model));

            addWChTable();
            addWChButton();
            addSeasonDropDownChoice(Model.of(selectedSeason));
            addSeasonLabel();
        }

        private void addSeasonLabel() {
            add(new Label("season", new ResourceModel("season")));
        }

        private void addSeasonDropDownChoice(final IModel<WChSeason> model) {
            add(new DropDownChoice<WChSeason>("seasons", model, WCH_SEASONS, new IChoiceRenderer<WChSeason>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Object getDisplayValue(WChSeason season) {
                    return season.getLabelName();
                }

                @Override
                public String getIdValue(WChSeason season, int index) {
                    return season.getLabelName();
                }
            }).add(new AjaxFormComponentUpdatingBehavior("onchange") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    selectedSeason = model.getObject();

                    WChTableRecord actualRecord = wChService.getWchRecords(selectedSeason);

                    selectedTableRecord.getRecords().clear();
                    selectedTableRecord.getRecords().addAll(actualRecord.getRecords());
                    selectedTableRecord.setTableHeader(actualRecord.getTableHeader());

                    target.add(WChForm.this);
                }
            }));
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
            addGWChSeasonTableHeader();
            addGWChTournamentTableHeader();
            addWChRecordsGroupListView();
        }

        private void addGWChTournamentTableHeader() {
            ListView<String> seasonList = new ListView<String>("tableHeader.wchTournamentHeaders") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(ListItem<String> listItem) {
                    listItem.add(new Label("tournament", listItem.getModelObject()));
                }
            };
            add(seasonList);
        }

        private void addGWChSeasonTableHeader() {
            add(new Label("name", new ResourceModel("name")));
            add(new Label("rank", new ResourceModel("rank")));
            add(new Label("total", new ResourceModel("total")));

            final int colspan = selectedTableRecord.getTableHeader().getWchTournamentHeaders().size()
                    / Math.max(1, selectedTableRecord.getTableHeader().getWchSeasonHeaders().size());
            ListView<String> tournamentList = new ListView<String>("tableHeader.wchSeasonHeaders") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(ListItem<String> listItem) {
                    listItem.add(new Label("season", listItem.getModelObject()).add(new AttributeModifier("colspan",
                            colspan)));
                }
            };
            add(tournamentList);
        }

        private void addWChRecordsGroupListView() {
            add(new PropertyListView<WChRecord>("records") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<WChRecord> listItem) {
                    final WChRecord record = listItem.getModelObject();

                    listItem.add(new Label("index", listItem.getIndex() + 1 + ""));
                    listItem.add(new Label("name", record.getPlayerName()));

                    ListView<WChTournamentDetailsRecord> seasonList = new ListView<WChTournamentDetailsRecord>(
                            "seasonRecords", record.getTournamentDetails()) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void populateItem(ListItem<WChTournamentDetailsRecord> listItem) {
                            final WChTournamentDetailsRecord record = listItem.getModelObject();
                            listItem.add(new Label("points", record.getPoints()).add(new AttributeModifier("title",
                                    record.getTournament())));
                        }
                    };
                    listItem.add(seasonList);
                    listItem.add(new Label("total", record.getTotalPoints().toString()));
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