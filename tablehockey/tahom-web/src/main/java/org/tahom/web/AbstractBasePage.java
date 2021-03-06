package org.tahom.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahom.processor.service.callable.CallableService;
import org.tahom.processor.service.event.EventService;
import org.tahom.processor.service.finalStanding.FinalStandingService;
import org.tahom.processor.service.game.GameService;
import org.tahom.processor.service.group.GroupService;
import org.tahom.processor.service.imports.ImportService;
import org.tahom.processor.service.ithf.IthfService;
import org.tahom.processor.service.participant.ParticipantService;
import org.tahom.processor.service.playOffGame.PlayOffGameService;
import org.tahom.processor.service.player.PlayerService;
import org.tahom.processor.service.registration.RegistrationService;
import org.tahom.processor.service.schedule.ScheduleService;
import org.tahom.processor.service.season.SeasonService;
import org.tahom.processor.service.statistic.StatisticService;
import org.tahom.processor.service.tournament.TournamentService;
import org.tahom.processor.service.user.UserService;

public class AbstractBasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	@SpringBean(name = "playerService")
	protected PlayerService playerService;

	@SpringBean(name = "gameService")
	protected GameService gameService;

	@SpringBean(name = "scheduleService")
	protected ScheduleService scheduleService;

	@SpringBean(name = "seasonService")
	protected SeasonService seasonService;

	@SpringBean(name = "userService")
	protected UserService userService;

	@SpringBean(name = "groupService")
	protected GroupService groupService;

	@SpringBean(name = "participantService")
	protected ParticipantService participantService;

	@SpringBean(name = "playOffGameService")
	protected PlayOffGameService playOffGameService;

	@SpringBean(name = "finalStandingService")
	protected FinalStandingService finalStandingService;

	@SpringBean(name = "tournamentService")
	protected TournamentService tournamentService;

	@SpringBean(name = "importService")
	protected ImportService importService;

	@SpringBean(name = "ithfService")
	protected IthfService ithfService;

	@SpringBean(name = "statisticService")
	protected StatisticService statisticService;

	@SpringBean(name = "callableService")
	protected CallableService callableService;

	@SpringBean(name = "registrationService")
	protected RegistrationService registrationService;

	@SpringBean(name = "eventService")
	protected EventService eventService;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public AbstractBasePage() {
		super();
	}

	public AbstractBasePage(IModel<?> model) {
		super(model);
	}

	public AbstractBasePage(PageParameters parameters) {
		super(parameters);
	}

}
