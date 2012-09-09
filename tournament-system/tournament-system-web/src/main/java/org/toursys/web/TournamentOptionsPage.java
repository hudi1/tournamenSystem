package org.toursys.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.value.ValueMap;
import org.toursys.repository.model.Table;
import org.toursys.repository.model.Tournament;

public abstract class TournamentOptionsPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private Tournament tournament;
    private Table table;
    private BasePage basePage;

    private class TableOptionsForm extends Form<Table> {

        private static final long serialVersionUID = 1L;

        public TableOptionsForm(final Table table) {
            super("tableOptionsForm", new CompoundPropertyModel<Table>(table));
            setOutputMarkupId(true);
            add(new TextField<Integer>("hockey", new PropertyModel<Integer>(table, "numberOfHockey")));
            add(new TextField<Integer>("hockeyIndex", new PropertyModel<Integer>(table, "indexOfFirstHockey")));

            ValueMap map = new ValueMap();
            map.put("tableLegend", table.getName());

            add(new Label("tableLegend", new StringResourceModel("tableLegend", new Model<ValueMap>(map))));

            add(new Button("submit", new StringResourceModel("save", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    tournamentService.updateTable(table);
                    tournamentService.updateTournament(tournament);
                    setResponsePage(new TournamentOptionsPage(tournament, table, basePage) {

                        private static final long serialVersionUID = 1L;
                    });
                }

            });

            add(new Button("back", new StringResourceModel("back", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new GroupPage(tournament, table, basePage));
                }

            });
        }
    }

    private class TournamentOptionsForm extends Form<Tournament> {

        private static final long serialVersionUID = 1L;

        public TournamentOptionsForm(final Tournament tournament) {
            super("tournamentOptionsForm", new CompoundPropertyModel<Tournament>(tournament));
            setOutputMarkupId(true);
            add(new TextField<Integer>("promotingA", new PropertyModel<Integer>(tournament, "promotingA")));
            add(new TextField<Integer>("promotingLower", new PropertyModel<Integer>(tournament, "promotingLower")));
            add(new TextField<Integer>("points", new PropertyModel<Integer>(tournament, "winPoints")));
            add(new TextField<Integer>("playOffA", new PropertyModel<Integer>(tournament, "playOffA")));
            add(new TextField<Integer>("playOffLower", new PropertyModel<Integer>(tournament, "playOffLower")));

            ValueMap map = new ValueMap();
            map.put("tournamentLegend", tournament.getName());

            add(new Label("tournamentLegend", new StringResourceModel("tournamentLegend", new Model<ValueMap>(map))));

            add(new Button("submit", new StringResourceModel("save", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    tournamentService.updateTournament(tournament);
                    setResponsePage(new TournamentOptionsPage(tournament, table, basePage) {

                        private static final long serialVersionUID = 1L;
                    });
                }

            });

            add(new Button("back", new StringResourceModel("back", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new GroupPage(tournament, table, basePage));
                }

            });
        }
    }

    public TournamentOptionsPage(Tournament tournament, Table table, BasePage basePage) {
        this.basePage = basePage;
        this.tournament = tournament;
        this.table = table;
        add(new TableOptionsForm(table));
        add(new TournamentOptionsForm(tournament));
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new StringResourceModel("tournamentOptions", null);
    }
}
