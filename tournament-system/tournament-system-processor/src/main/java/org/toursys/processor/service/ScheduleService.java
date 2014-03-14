package org.toursys.processor.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.toursys.processor.schedule.AdvancedRoundRobinSchedule;
import org.toursys.processor.schedule.BasicLessHockeyRoundRobinSchedule;
import org.toursys.processor.schedule.BasicRoundRobinSchedule;
import org.toursys.processor.schedule.EmptyRoundRobinSchedule;
import org.toursys.processor.schedule.RoundRobinSchedule;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Tournament;

public class ScheduleService extends AbstractService {

    ParticipantService participantService;

    public RoundRobinSchedule getSchedule(Tournament tournament, Groups group, List<Participant> participants) {
        long time = System.currentTimeMillis();
        logger.debug("Get schedule: " + Arrays.toString(participants.toArray()));
        RoundRobinSchedule roundRobinSchedule;

        if (group.getType() != GroupsType.BASIC && group.getCopyResult()) {
            logger.trace("Advanced schedule");
            roundRobinSchedule = new AdvancedRoundRobinSchedule(group, participantService.getAdvancedPlayersByGroup(
                    group, tournament, participants));
        } else {
            logger.trace("Basic schedule");
            if (participants.size() < 2) {
                roundRobinSchedule = new EmptyRoundRobinSchedule();
            } else if (participants.size() / 2 <= group.getNumberOfHockey()) {
                roundRobinSchedule = new BasicRoundRobinSchedule(group, participants);
            } else {
                roundRobinSchedule = new BasicLessHockeyRoundRobinSchedule(group, participants);
            }
        }

        time = System.currentTimeMillis() - time;
        logger.debug("End: Get schedule: " + time + " ms");
        return roundRobinSchedule;
    }

    @Required
    public void setParticipantService(ParticipantService participantService) {
        this.participantService = participantService;
    }

}