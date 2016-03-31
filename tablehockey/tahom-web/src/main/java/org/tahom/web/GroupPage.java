package org.tahom.web;

import java.util.Set;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.tahom.processor.callable.GroupPdfCallable;
import org.tahom.processor.callable.SchedulePdfCallable;
import org.tahom.processor.callable.SheetsPdfCallable;
import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.processor.service.group.dto.GroupPageDto;
import org.tahom.processor.service.participant.dto.ParticipantDto;
import org.tahom.repository.model.Groups;
import org.tahom.web.behavior.CloseOnESCBehavior;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentAjaxResourceButton;
import org.tahom.web.components.TournamentButton;
import org.tahom.web.components.TournamentResourceButton;
import org.tahom.web.link.DownloadModelLink;
import org.tahom.web.model.EvenOddReplaceModel;
import org.tahom.web.model.TournamentFileReadOnlyModel;

@AuthorizeInstantiation(Roles.USER)
public class GroupPage extends TournamentHomePage {

	private static final long serialVersionUID = 1L;

	private ModalWindow modalWindow;
	private GroupPageDto groupPageDto;

	public GroupPage() {
		this(new PageParameters());
	}

	public GroupPage(PageParameters parameters) {
		super(parameters);

		prepareData(parameters);
		clearPageParameters();
		createPage();
	}

	private void prepareData(PageParameters parameters) {
		Groups group = getGroup(parameters, getTournament(parameters));
		groupPageDto = groupService.getGroupPageDto(group, tournament);
	}

	protected void createPage() {
		if (!groupPageDto.getGoldGoalParticipants().isEmpty()) {
			createModalWindow(groupPageDto.getGoldGoalParticipants());
		}
		addModalWindow();
		if (modalWindow != null) {
			add(new CloseOnESCBehavior(modalWindow));
		}
		add(new GroupForm());
	}

	private void clearPageParameters() {
		getPageParameters().remove(UPDATE);
	}

	private void addModalWindow() {
		if (modalWindow == null) {
			add(new ModalWindow("modalWindow"));
		} else {
			add(modalWindow);
		}
	}

	private class GroupForm extends Form<GroupPageDto> {

		private static final long serialVersionUID = 1L;

		public GroupForm() {
			super("groupForm", new CompoundPropertyModel<GroupPageDto>(groupPageDto));

			addGroupTable();
			addGroupsButton();
			addGroupOptionsButton();
		}

		private void addGroupTable() {
			addGroupTableHeader();
			addParticipantsGroupListView();
		}

		private void addGroupTableHeader() {
			addGroupHeaderPlayerIndex();
			add(new ResourceLabel("name"));
			add(new ResourceLabel("rank"));
			add(new ResourceLabel("points"));
			add(new ResourceLabel("score"));
		}

		private void addGroupOptionsButton() {
			addScheduleButton();
			addOptionsButton();
			addFinalGroupButton();
			addCopyResultButton();
			addPrintSheetsButton();
			addPrintScheduleButton();
			addPrintGroupButton();
			addEqualRankButton();
		}

		private void addEqualRankButton() {
			add(new TournamentAjaxResourceButton("editEqualRank") {

				private static final long serialVersionUID = 1L;

				protected void submit(AjaxRequestTarget target, Form<?> form) {
					modalWindow.show(target);
				}
			}.setVisible(modalWindow != null));
		}

		private void addCopyResultButton() {
			add(new TournamentResourceButton("copyResult") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					groupService.copyResult(tournament);
					setResponsePage(GroupPage.class, getPageParameters());
				}
			}.setVisible(groupPageDto.getGroup() != null));
		}

		private void addFinalGroupButton() {
			add(new TournamentResourceButton("finalGroup") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					groupService.processFinalGroup(tournament);
					setResponsePage(GroupPage.class, getPageParameters());
				}
			}.setVisible(groupPageDto.getGroup() != null));
		}

		private void addScheduleButton() {
			add(new TournamentResourceButton("schedule") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					getPageParameters().set(GID, groupPageDto.getGroup().getId());
					setResponsePage(new SchedulePage(getPageParameters(), groupPageDto.getSchedule()));
				}
			}.setVisible(groupPageDto.getGroup() != null));
		}

		private void addOptionsButton() {
			add(new TournamentResourceButton("options") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					getPageParameters().set(GID, groupPageDto.getGroup().getId());
					getPageParameters().set(SHOW_TABLE_OPTIONS, true);
					getPageParameters().set(SHOW_TOURNAMENT_OPTIONS, false);
					setResponsePage(TournamentOptionsPage.class, getPageParameters());
				}
			}.setVisible(groupPageDto.getGroup() != null));
		}

		private void addGroupsButton() {
			add(new PropertyListView<Groups>("groups") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<Groups> item) {
					final Groups group = item.getModelObject();

					Button button = new TournamentButton("name", group.getName()) {

						private static final long serialVersionUID = 1L;

						@Override
						public void submit() {
							getPageParameters().set(GID, group.getId());
							setResponsePage(GroupPage.class, getPageParameters());
						}
					};

					if (group.equals(groupPageDto.getGroup())) {
						button.add(new AttributeModifier("class", "activeTournamentButton"));
					}

					item.add(button);
				}

			});

		}

		private void addPrintGroupButton() {
			add(new DownloadModelLink("printGroup", new TournamentFileReadOnlyModel<GroupPageDto>(callableService,
			        groupPageDto, GroupPdfCallable.class)));
		}

		private void addPrintScheduleButton() {
			add(new DownloadModelLink("printSchedule", new TournamentFileReadOnlyModel<GroupPageDto>(callableService,
			        groupPageDto, SchedulePdfCallable.class)));
		}

		private void addPrintSheetsButton() {
			add(new DownloadModelLink("printSheets", new TournamentFileReadOnlyModel<GroupPageDto>(callableService,
			        groupPageDto, SheetsPdfCallable.class)));
		}

		private void addParticipantsGroupListView() {
			add(new PropertyListView<ParticipantDto>("participants") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final ListItem<ParticipantDto> listItem) {
					listItem.add(new Label("index", listItem.getIndex() + 1 + ""));
					listItem.add(new Label("name"));
					listItem.add(new Label("score"));
					listItem.add(new Label("points"));
					listItem.add(new Label("rank"));

					ListView<GameDto> games = new PropertyListView<GameDto>("games") {

						private static final long serialVersionUID = 1L;

						@Override
						protected void populateItem(ListItem<GameDto> gameItem) {
							gameItem.add(new Label("result"));
						}
					};
					listItem.add(games);
					listItem.add(new AttributeModifier("class", new EvenOddReplaceModel(listItem.getIndex())));
				}
			});
		}

		private void addGroupHeaderPlayerIndex() {
			add(new PropertyListView<ParticipantDto>("participantsHeader") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<ParticipantDto> listItem) {
					listItem.add(new Label("name", listItem.getIndex() + 1 + ""));
				}
			});
		}
	}

	private void createModalWindow(final Set<ParticipantDto> samePlayerRankParticipants) {

		modalWindow = createDefaultModalWindow();
		modalWindow.setPageCreator(new ModalWindow.PageCreator() {

			private static final long serialVersionUID = 1L;

			public Page createPage() {
				return new ComparePage(groupPageDto.getGroup(), modalWindow, samePlayerRankParticipants);
			}
		});

	}

	private ModalWindow createDefaultModalWindow() {

		modalWindow = new ModalWindow("modalWindow");
		modalWindow.setResizable(false);
		modalWindow.setCssClassName(ModalWindow.CSS_CLASS_BLUE);
		modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

			private static final long serialVersionUID = 1L;

			public void onClose(AjaxRequestTarget target) {
				setResponsePage(GroupPage.class, getPageParameters());
			}
		});

		modalWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

			private static final long serialVersionUID = 1L;

			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				return true;
			}
		});
		return modalWindow;
	}
}