package org.toursys.web;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.value.ValueMap;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsPlayOffType;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Tournament;
import org.toursys.web.components.TournamentButton;

@AuthorizeInstantiation(Roles.USER)
public class TournamentOptionsPage extends TournamentHomePage {

    private static final long serialVersionUID = 1L;

    private Tournament tournament;
    private Groups group;
    boolean isTournamentOptionsOn;
    boolean isTableOptionsOn;

    private final List<GroupsPlayOffType> groupPlayOffType = Arrays.asList(new GroupsPlayOffType[] {
            GroupsPlayOffType.CROSS, GroupsPlayOffType.FINAL, GroupsPlayOffType.LOWER });

    public TournamentOptionsPage() {
        this(new PageParameters());
    }

    public TournamentOptionsPage(PageParameters parameters) {
        tournament = getTournament(parameters);
        group = getGroup(parameters, tournament);
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
        GroupOptionsForm groupForm = new GroupOptionsForm();
        add(groupForm);
        groupForm.setVisible(isTableOptionsOn);
    }

    private class GroupOptionsForm extends Form<Groups> {

        private static final long serialVersionUID = 1L;

        public GroupOptionsForm() {
            super("tableOptionsForm", new CompoundPropertyModel<Groups>(group));

            addGroupTextField();
            addLegendLabel();
            addSubmitButton();
            addBackButton();
        }

        private void addGroupTextField() {
            add(new Label("numberOfHockey", new ResourceModel("numberOfHockey")));
            add(new RequiredTextField<Integer>("numberOfHockeyInput", new PropertyModel<Integer>(group,
                    "numberOfHockey")));

            add(new Label("indexOfFirstHockey", new ResourceModel("indexOfFirstHockey")));
            add(new RequiredTextField<Integer>("indexOfFirstHockeyInput", new PropertyModel<Integer>(group,
                    "indexOfFirstHockey")));

            Component copyResult = new Label("copyResult", new ResourceModel("copyResult")).setVisible(false);
            Component copyResultInput = new CheckBox("copyResultInput", new PropertyModel<Boolean>(group, "copyResult"))
                    .setVisible(false);

            Component playThirdPlace = new Label("playThirdPlace", new ResourceModel("playThirdPlace"))
                    .setVisible(false);
            Component playThirdPlaceInput = new CheckBox("playThirdPlaceInput", new PropertyModel<Boolean>(group,
                    "playThirdPlace")).setVisible(false);

            Component groupsPlayOffType = new Label("groupsPlayOffType", new ResourceModel("groupsPlayOffType"))
                    .setVisible(false);
            Component groupsPlayOffTypeInput = new DropDownChoice<GroupsPlayOffType>("groupsPlayOffTypeInput",
                    new PropertyModel<GroupsPlayOffType>(group, "playOffType"), groupPlayOffType).setVisible(false);

            add(copyResult);
            add(copyResultInput);
            add(playThirdPlace);
            add(playThirdPlaceInput);
            add(groupsPlayOffType);
            add(groupsPlayOffTypeInput);

            if (group.getType().equals(GroupsType.FINAL)) {
                copyResult.setVisible(true);
                copyResultInput.setVisible(true);
                playThirdPlace.setVisible(true);
                playThirdPlaceInput.setVisible(true);
                groupsPlayOffType.setVisible(true);
                groupsPlayOffTypeInput.setVisible(true);
            }
        }

        private void addLegendLabel() {
            ValueMap map = new ValueMap();
            map.put("tableLegend", group.getName());
            add(new Label("tableLegend", new StringResourceModel("tableLegend", new Model<ValueMap>(map))));
        }

        private void addSubmitButton() {
            add(new TournamentButton("submit", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    groupService.updateGroup(group);
                    setResponsePage(TournamentOptionsPage.class, getPageParameters());
                }
            });
        }

        private void addBackButton() {
            add(new TournamentButton("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
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

            addTournamentsTextFields(tournament);
            addLegendLabel();
            addSubmitButton();
            addBackButton();
        }

        private void addTournamentsTextFields(final Tournament tournament) {
            add(new Label("finalPromoting", new ResourceModel("finalPromoting")));
            add(new RequiredTextField<Integer>("finalPromotingInput", new PropertyModel<Integer>(tournament,
                    "finalPromoting")));

            add(new Label("lowerPromoting", new ResourceModel("lowerPromoting")));
            add(new RequiredTextField<Integer>("lowerPromotingInput", new PropertyModel<Integer>(tournament,
                    "lowerPromoting")));

            add(new Label("winPoints", new ResourceModel("winPoints")));
            add(new RequiredTextField<Integer>("winPointsInput", new PropertyModel<Integer>(tournament, "winPoints")));

            add(new Label("playOffFinal", new ResourceModel("playOffFinal")));
            add(new RequiredTextField<Integer>("playOffFinalInput", new PropertyModel<Integer>(tournament,
                    "playOffFinal")));

            add(new Label("playOffLower", new ResourceModel("playOffLower")));
            add(new RequiredTextField<Integer>("playOffLowerInput", new PropertyModel<Integer>(tournament,
                    "playOffLower")));

            add(new Label("minPlayersInGroup", new ResourceModel("minPlayersInGroup")));
            add(new RequiredTextField<Integer>("minPlayersInGroupInput", new PropertyModel<Integer>(tournament,
                    "minPlayersInGroup")));
        }

        private void addLegendLabel() {
            ValueMap map = new ValueMap();
            map.put("tournamentLegend", tournament.getName());
            add(new Label("tournamentLegend", new StringResourceModel("tournamentLegend", new Model<ValueMap>(map))));
        }

        private void addBackButton() {
            add(new TournamentButton("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    clearPageParameters();
                    setResponsePage(GroupPage.class, getPageParameters());
                };
            }.setDefaultFormProcessing(false));
        }

        private void addSubmitButton() {
            add(new TournamentButton("submit", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
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

            add(new TournamentButton("group", new ResourceModel("group")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    getPageParameters().set(SHOW_TABLE_OPTIONS, true);
                    getPageParameters().set(SHOW_TOURNAMENT_OPTIONS, false);
                    setResponsePage(TournamentOptionsPage.class, getPageParameters());
                }
            });

            add(new TournamentButton("tournament", new ResourceModel("tournament")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit() {
                    getPageParameters().set(SHOW_TABLE_OPTIONS, false);
                    getPageParameters().set(SHOW_TOURNAMENT_OPTIONS, true);
                    setResponsePage(TournamentOptionsPage.class, getPageParameters());
                }
            });
        }
    }

}
