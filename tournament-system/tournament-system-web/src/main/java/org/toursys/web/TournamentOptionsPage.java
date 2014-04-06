package org.toursys.web;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.value.ValueMap;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Tournament;

@AuthorizeInstantiation(Roles.USER)
public class TournamentOptionsPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private Tournament tournament;
    private Groups group;
    boolean isTournamentOptionsOn;
    boolean isTableOptionsOn;

    public TournamentOptionsPage() {
        this(new PageParameters());
    }

    public TournamentOptionsPage(PageParameters parameters) {
        tournament = getTournament(parameters);
        group = getGroup(parameters);
        setTournamentOption(parameters);
        setTableOption(parameters);
        createPage();
    }

    private void clearPageParameters() {
        getPageParameters().remove(SHOW_TABLE_OPTIONS).remove(SHOW_TOURNAMENT_OPTIONS).remove(UPDATE);
    }

    private void setTableOption(PageParameters parameters) {
        this.isTableOptionsOn = parameters.get(SHOW_TABLE_OPTIONS).toBoolean(true);
    }

    private void setTournamentOption(PageParameters parameters) {
        this.isTournamentOptionsOn = parameters.get(SHOW_TOURNAMENT_OPTIONS).toBoolean(false);
    }

    protected void createPage() {
        addGroupForm();
        addTournamentForm();
        add(new SelectOptionsForm());
    }

    private void addTournamentForm() {
        TournamentOptionsForm tournamentForm = new TournamentOptionsForm(tournament);
        add(tournamentForm);
        tournamentForm.setVisible(isTournamentOptionsOn);
    }

    private void addGroupForm() {
        GroupOptionsForm groupForm = new GroupOptionsForm(group);
        add(groupForm);
        groupForm.setVisible(isTableOptionsOn);
    }

    private class GroupOptionsForm extends Form<Groups> {

        private static final long serialVersionUID = 1L;

        public GroupOptionsForm(final Groups group) {
            super("tableOptionsForm", new CompoundPropertyModel<Groups>(group));

            addGroupTextField();
            addLegendLabel();
            addSubmitButton();
            addBackButton();
        }

        private void addGroupTextField() {
            add(new RequiredTextField<Integer>("numberOfHockey"));
            add(new RequiredTextField<Integer>("indexOfFirstHockey"));
            Component copyResult = new CheckBox("copyResult").setVisible(false);
            Component playThirdPlace = new CheckBox("playThirdPlace").setVisible(false);

            add(copyResult);
            add(playThirdPlace);

            if (group.getType().equals(GroupsType.FINAL)) {
                copyResult.setVisible(true);
                playThirdPlace.setVisible(true);
            }
        }

        private void addLegendLabel() {
            ValueMap map = new ValueMap();
            map.put("tableLegend", group.getName());
            add(new Label("tableLegend", new StringResourceModel("tableLegend", new Model<ValueMap>(map))));
        }

        private void addSubmitButton() {
            add(new Button("submit", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    groupService.updateGroup(group);
                    setResponsePage(TournamentOptionsPage.class, getPageParameters());
                }
            });
        }

        private void addBackButton() {
            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    clearPageParameters();
                    setResponsePage(GroupPage.class, getPageParameters());
                };
            }.setDefaultFormProcessing(false));
        }
    }

    private class TournamentOptionsForm extends Form<Tournament> {

        private static final long serialVersionUID = 1L;

        public TournamentOptionsForm(final Tournament tournament) {
            super("tournamentOptionsForm", new CompoundPropertyModel<Tournament>(tournament));

            addTournamentsTextFields();
            addLegendLabel();
            addSubmitButton();
            addBackButton();
        }

        private void addTournamentsTextFields() {
            add(new RequiredTextField<Integer>("finalPromoting"));
            add(new RequiredTextField<Integer>("lowerPromoting"));
            add(new RequiredTextField<Integer>("winPoints"));
            add(new RequiredTextField<Integer>("playOffA"));
            add(new RequiredTextField<Integer>("playOffLower"));
            add(new RequiredTextField<Integer>("minPlayersInGroup"));
        }

        private void addLegendLabel() {
            ValueMap map = new ValueMap();
            map.put("tournamentLegend", tournament.getName());
            add(new Label("tournamentLegend", new StringResourceModel("tournamentLegend", new Model<ValueMap>(map))));
        }

        private void addBackButton() {
            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    clearPageParameters();
                    setResponsePage(GroupPage.class, getPageParameters());
                };
            }.setDefaultFormProcessing(false));
        }

        private void addSubmitButton() {
            add(new Button("submit", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    tournamentService.updateTournament(tournament);
                    setResponsePage(TournamentOptionsPage.class, getPageParameters());
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
                    getPageParameters().set(SHOW_TABLE_OPTIONS, true);
                    getPageParameters().set(SHOW_TOURNAMENT_OPTIONS, false);
                    setResponsePage(TournamentOptionsPage.class, getPageParameters());
                }
            });

            add(new Button("tournament", new ResourceModel("tournament")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    getPageParameters().set(SHOW_TABLE_OPTIONS, false);
                    getPageParameters().set(SHOW_TOURNAMENT_OPTIONS, true);
                    setResponsePage(TournamentOptionsPage.class, getPageParameters());
                }
            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("tournamentOptions");
    }
}
