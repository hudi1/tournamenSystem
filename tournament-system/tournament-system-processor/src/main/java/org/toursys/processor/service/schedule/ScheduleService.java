package org.toursys.processor.service.schedule;

import java.util.List;

import javax.inject.Inject;

import org.toursys.processor.schedule.AdvancedRoundRobinSchedule;
import org.toursys.processor.schedule.BasicLessHockeyRoundRobinSchedule;
import org.toursys.processor.schedule.BasicRoundRobinSchedule;
import org.toursys.processor.schedule.EmptyRoundRobinSchedule;
import org.toursys.processor.schedule.RoundRobinSchedule;
import org.toursys.processor.service.participant.ParticipantService;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Tournament;

public class ScheduleService {

    @Inject
    private ParticipantService participantService;

    public RoundRobinSchedule getSchedule(Tournament tournament, Groups group, List<Participant> participants) {
        RoundRobinSchedule roundRobinSchedule;

        // TODO optimalizacia
        if (group.getType() != GroupsType.BASIC && group.getCopyResult()) {
            roundRobinSchedule = new AdvancedRoundRobinSchedule(group, participantService.getAdvancedPlayersByGroup(
                    group, tournament, participants));
        } else {
            if (participants.size() < 2) {
                roundRobinSchedule = new EmptyRoundRobinSchedule();
            } else if (participants.size() / 2 <= group.getNumberOfHockey()) {
                roundRobinSchedule = new BasicRoundRobinSchedule(group, participants);
            } else {
                roundRobinSchedule = new BasicLessHockeyRoundRobinSchedule(group, participants);
            }
        }

        return roundRobinSchedule;
    }

}