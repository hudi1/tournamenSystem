package org.toursys.processor.schedule;

import java.util.ArrayList;

import org.toursys.repository.model.GameImpl;

public class EmptyRoundRobinSchedule extends RoundRobinSchedule {

    public EmptyRoundRobinSchedule() {
        super(null);
    }

    @Override
    protected void createSchedule() {
        schedule = new ArrayList<GameImpl>();
    }

}
