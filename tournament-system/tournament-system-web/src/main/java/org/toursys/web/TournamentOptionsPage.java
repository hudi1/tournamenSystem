package org.toursys.web;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.value.ValueMap;
import org.toursys.repository.model.GroupType;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.TournamentImpl;

public class TournamentOptionsPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private TournamentImpl tournament;
    private Groups group;
    boolean isTournamentOptionsOn;
    boolean isTableOptionsOn;

    public TournamentOptionsPage() {
        throw new RestartResponseAtInterceptPageException(new SeasonPage());
    }

    public TournamentOptionsPage(TournamentImpl tournament, Groups group, boolean isTableOptionsOn,
            boolean isTournamentOptionsOn) {
        this.tournament = tournament;
        this.group = group;
        this.isTableOptionsOn = isTableOptionsOn;
        this.isTournamentOptionsOn = isTournamentOptionsOn;
        createPage();
    }

    protected void createPage() {
        GroupOptionsForm groupForm = new GroupOptionsForm(group);
        add(groupForm);
        groupForm.setVisible(isTableOptionsOn);

        TournamentOptionsForm tournamentForm = new TournamentOptionsForm(tournament);
        add(tournamentForm);
        tournamentForm.setVisible(isTournamentOptionsOn);

        add(new SelectOptionsForm());
    }

    private class GroupOptionsForm extends Form<Groups> {

        private static final long serialVersionUID = 1L;

        public GroupOptionsForm(final Groups group) {
            super("tableOptionsForm", new CompoundPropertyModel<Groups>(group));
            setOutputMarkupId(true);
            add(new TextField<Integer>("hockey", new PropertyModel<Integer>(group, "numberOfHockey")));
            add(new TextField<Integer>("hockeyIndex", new PropertyModel<Integer>(group, "indexOfFirstHockey")));
            CheckBox checkBox1 = new CheckBox("copyResult", new PropertyModel<Boolean>(group, "copyResult"));
            CheckBox checkBox2 = new CheckBox("playThirdPlace", new PropertyModel<Boolean>(group, "playThirdPlace"));

            add(checkBox1);
            add(checkBox2);

            if (group.getGroupType().equals(GroupType.B.name())) {
                checkBox2.setVisible(false);
                checkBox1.setVisible(false);
            }

            ValueMap map = new ValueMap();
            map.put("tableLegend", group.getName());

            add(new Label("tableLegend", new StringResourceModel("tableLegend", new Model<ValueMap>(map))));

            add(new Button("submit", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    tournamentService.updateGroups(group);
                    setResponsePage(new TournamentOptionsPage(tournament, group, isTableOptionsOn,
                            isTournamentOptionsOn));
                }
            });

            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new GroupPage(tournament, group));
                }
            });
        }
    }

    private class TournamentOptionsForm extends Form<TournamentImpl> {

        private static final long serialVersionUID = 1L;

        public TournamentOptionsForm(final TournamentImpl tournament) {
            super("tournamentOptionsForm", new CompoundPropertyModel<TournamentImpl>(tournament));
            setOutputMarkupId(true);
            add(new TextField<Integer>("promotingA", new PropertyModel<Integer>(tournament, "finalPromoting")));
            add(new TextField<Integer>("promotingLower", new PropertyModel<Integer>(tournament, "lowerPromoting")));
            add(new TextField<Integer>("points", new PropertyModel<Integer>(tournament, "winPoints")));
            add(new TextField<Integer>("playOffA", new PropertyModel<Integer>(tournament, "playOffA")));
            add(new TextField<Integer>("playOffLower", new PropertyModel<Integer>(tournament, "playOffLower")));

            ValueMap map = new ValueMap();
            map.put("tournamentLegend", tournament.getName());

            add(new Label("tournamentLegend", new StringResourceModel("tournamentLegend", new Model<ValueMap>(map))));

            add(new Button("submit", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    tournamentService.updateTournament(tournament);
                    setResponsePage(new TournamentOptionsPage(tournament, group, isTableOptionsOn,
                            isTournamentOptionsOn));
                }
            });

            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new GroupPage(tournament, group));
                }
            });
        }
    }

    private class SelectOptionsForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        public SelectOptionsForm() {
            super("selectOptionsForm");
            setOutputMarkupId(true);

            add(new Button("group", new ResourceModel("group")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new TournamentOptionsPage(tournament, group, true, false));
                }
            });

            add(new Button("tournament", new ResourceModel("tournament")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(new TournamentOptionsPage(tournament, group, false, true));
                }
            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("tournamentOptions");
    }
}
