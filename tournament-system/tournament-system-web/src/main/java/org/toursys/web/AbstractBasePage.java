package org.toursys.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.processor.service.game.GameService;
import org.toursys.processor.service.group.GroupService;
import org.toursys.processor.service.imports.ImportService;
import org.toursys.processor.service.participant.ParticipantService;
import org.toursys.processor.service.playOffGame.PlayOffGameService;
import org.toursys.processor.service.player.PlayerService;
import org.toursys.processor.service.schedule.ScheduleService;
import org.toursys.processor.service.season.SeasonService;
import org.toursys.processor.service.standing.FinalStandingService;
import org.toursys.processor.service.tournament.TournamentService;
import org.toursys.processor.service.user.UserService;

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
