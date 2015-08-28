package org.toursys.repository.dao;

import org.toursys.repository.model.Tournament;

public interface GroupsExtDao extends GroupsDao {

    public int deleteFinalGroups(Tournament tournament);

}
