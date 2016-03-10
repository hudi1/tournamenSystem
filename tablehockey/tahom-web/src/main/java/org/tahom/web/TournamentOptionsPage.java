package org.tahom.web;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.value.ValueMap;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.GroupsPlayOffType;
import org.tahom.repository.model.GroupsType;
import org.tahom.repository.model.Tournament;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentBackResourceButton;
import org.tahom.web.components.TournamentResourceButton;

@AuthorizeInstantiation(Roles.USER)
public class TournamentOptionsPage extends TournamentHomePage {

	private static final long serialVersionUID = 1L;
	private static final List<GroupsType> TYPES = Arrays
	        .asList(new GroupsType[] { GroupsType.BASIC, GroupsType.FINAL });
	private static final List<GroupsPlayOffType> GROUP_PLAY_OFF_TYPE = Arrays.asList(new GroupsPlayOffType[] {
	        GroupsPlayOffType.CROSS, GroupsPlayOffType.FINAL, GroupsPlayOffType.LOWER });

	private Tournament tournament;
	private Groups group;
	boolean isTournamentOptionsOn;
	boolean isTableOptionsOn;

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
		add(new GroupOptionsForm().setVisible(isTableOptionsOn));
		add(new TournamentOptionsForm().setVisible(isTournamentOptionsOn));
		add(new SelectOptionsForm());
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
			add(new ResourceLabel("numberOfHockeyLabel"));
			add(new RequiredTextField<Integer>("numberOfHockey"));

			add(new ResourceLabel("indexOfFirstHockeyLabel"));
			add(new RequiredTextField<Integer>("indexOfFirstHockey"));

			boolean visible = group.getType().equals(GroupsType.FINAL);

			add(new ResourceLabel("copyResultLabel").setVisible(visible));
			add(new CheckBox("copyResult").setVisible(visible));

			add(new ResourceLabel("playThirdPlaceLabel").setVisible(visible));
			add(new CheckBox("playThirdPlace").setVisible(visible));

			add(new ResourceLabel("groupsPlayOffTypeLabel").setVisible(visible));
			add(new DropDownChoice<GroupsPlayOffType>("playOffType", GROUP_PLAY_OFF_TYPE,
			        new IChoiceRenderer<GroupsPlayOffType>() {

				        private static final long serialVersionUID = 1L;

				        @Override
				        public Object getDisplayValue(GroupsPlayOffType object) {
					        return getString(object.toString() + "PLAYOFFTYPE");
				        }

				        @Override
				        public String getIdValue(GroupsPlayOffType object, int index) {
					        return index + "";
				        }

				        @Override
				        public GroupsPlayOffType getObject(String id,
				                IModel<? extends List<? extends GroupsPlayOffType>> choices) {
					        List<? extends GroupsPlayOffType> _choices = choices.getObject();
					        for (int index = 0; index < _choices.size(); index++) {
						        final GroupsPlayOffType choice = _choices.get(index);
						        if (getIdValue(choice, index) != null && getIdValue(choice, index).equals(id)) {
							        return choice;
						        }
					        }
					        return null;
				        }
			        }).setVisible(visible));

			add(new ResourceLabel("groupsTypeLabel"));
			add(new DropDownChoice<GroupsType>("type", TYPES, new IChoiceRenderer<GroupsType>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Object getDisplayValue(GroupsType object) {
					return getString(object.toString() + "TYPE");
				}

				@Override
				public String getIdValue(GroupsType object, int index) {
					return index + "";
				}

				@Override
				public GroupsType getObject(String id, IModel<? extends List<? extends GroupsType>> choices) {
					List<? extends GroupsType> _choices = choices.getObject();
					for (int index = 0; index < _choices.size(); index++) {
						final GroupsType choice = _choices.get(index);
						if (getIdValue(choice, index) != null && getIdValue(choice, index).equals(id)) {
							return choice;
						}
					}
					return null;
				}
			}));
			/*
			 * add(new RadioChoice<GroupsType>("type", TYPES, new IChoiceRenderer<GroupsType>() {
			 * 
			 * private static final long serialVersionUID = 1L;
			 * 
			 * @Override public Object getDisplayValue(GroupsType object) { return getString(object.toString() +
			 * "GROUP"); }
			 * 
			 * @Override public String getIdValue(GroupsType object, int index) { return index + ""; }
			 * 
			 * @Override public GroupsType getObject(String id, IModel<? extends List<? extends GroupsType>> choices) {
			 * List<? extends GroupsType> _choices = choices.getObject(); for (int index = 0; index < _choices.size();
			 * index++) { final GroupsType choice = _choices.get(index); if (getIdValue(choice, index) != null &&
			 * getIdValue(choice, index).equals(id)) { return choice; } } return null; } }));
			 */
		}

		private void addLegendLabel() {
			ValueMap map = new ValueMap();
			map.put("tableLegend", group.getName());
			add(new Label("tableLegend", new StringResourceModel("tableLegend", new Model<ValueMap>(map))));
		}

		private void addSubmitButton() {
			add(new TournamentResourceButton("submit") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					groupService.updateGroup(group);
					getFeedbackMessages().clear();
					getSession().info(getString("updateGroupOptionsInfo"));
					setResponsePage(TournamentOptionsPage.class, getPageParameters());
				}
			});
		}

		private void addBackButton() {
			add(new TournamentBackResourceButton("back") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					clearPageParameters();
					setResponsePage(GroupPage.class, getPageParameters());
				};
			});
		}
	}

	private class TournamentOptionsForm extends Form<Tournament> {

		private static final long serialVersionUID = 1L;

		public TournamentOptionsForm() {
			super("tournamentOptionsForm", new CompoundPropertyModel<Tournament>(tournament));

			addTournamentsTextFields(tournament);
			addLegendLabel();
			addSubmitButton();
			addBackButton();
		}

		private void addTournamentsTextFields(final Tournament tournament) {
			add(new ResourceLabel("finalPromotingLabel"));
			add(new RequiredTextField<Integer>("finalPromoting"));

			add(new ResourceLabel("lowerPromotingLabel"));
			add(new RequiredTextField<Integer>("lowerPromoting"));

			add(new ResourceLabel("winPointsLabel"));
			add(new RequiredTextField<Integer>("winPoints"));

			add(new ResourceLabel("playOffFinalLabel"));
			add(new RequiredTextField<Integer>("playOffFinal"));

			add(new ResourceLabel("playOffLowerLabel"));
			add(new RequiredTextField<Integer>("playOffLower"));

			add(new ResourceLabel("minPlayersInGroupLabel"));
			add(new RequiredTextField<Integer>("minPlayersInGroup"));

			add(new ResourceLabel("publicLabel"));
			add(new CheckBox("public", new PropertyModel<Boolean>(tournament, "open")));
		}

		private void addLegendLabel() {
			ValueMap map = new ValueMap();
			map.put("tournamentLegend", tournament.getName());
			add(new Label("tournamentLegend", new StringResourceModel("tournamentLegend", new Model<ValueMap>(map))));
		}

		private void addBackButton() {
			add(new TournamentBackResourceButton("back") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					clearPageParameters();
					setResponsePage(GroupPage.class, getPageParameters());
				};
			});
		}

		private void addSubmitButton() {
			add(new TournamentResourceButton("submit") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					tournamentService.updateTournament(tournament);
					getFeedbackMessages().clear();
					getSession().info(getString("updateTournamentOptionsInfo"));
					setResponsePage(TournamentOptionsPage.class, getPageParameters());
				}
			});
		}
	}

	private class SelectOptionsForm extends Form<Void> {

		private static final long serialVersionUID = 1L;

		public SelectOptionsForm() {
			super("selectOptionsForm");

			add(new TournamentResourceButton("groupOptions") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					getPageParameters().set(SHOW_TABLE_OPTIONS, true);
					getPageParameters().set(SHOW_TOURNAMENT_OPTIONS, false);
					setResponsePage(TournamentOptionsPage.class, getPageParameters());
				}
			});

			add(new TournamentResourceButton("tournamentOptions") {

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