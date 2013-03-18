package org.toursys.processor.schedule;

import java.util.List;

import org.toursys.repository.model.GameImpl;

public interface RoundRobinSchedule {

    public List<GameImpl> getSchedule();

}
