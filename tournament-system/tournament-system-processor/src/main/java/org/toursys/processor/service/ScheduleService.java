package org.toursys.processor.service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.toursys.processor.schedule.AdvancedRoundRobinSchedule;
import org.toursys.processor.schedule.BasicRoundRobinSchedule;
import org.toursys.processor.schedule.RoundRobinSchedule;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Participant;

public class ScheduleService extends AbstractService {

    public List<GameImpl> getSchedule(Groups group, List<Participant> playerResults,
            LinkedList<List<Participant>> playerResultsByGroup) {
        long time = System.currentTimeMillis();
        logger.debug("Get schedule: " + Arrays.toString(playerResults.toArray()));
        RoundRobinSchedule roundRobinSchedule;

        if (group.getType() != GroupsType.BASIC && group.getCopyResult()) {
            logger.trace("Advanced schedule");
            roundRobinSchedule = new AdvancedRoundRobinSchedule(group, playerResultsByGroup);
        } else {
            logger.trace("Basic schedule");
            roundRobinSchedule = new BasicRoundRobinSchedule(group, playerResults);
        }

        time = System.currentTimeMillis() - time;
        logger.debug("End: Get schedule: " + time + " ms");
        return roundRobinSchedule.getSchedule();
    }
}