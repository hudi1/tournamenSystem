package org.tahom.repository.dao;

import org.tahom.repository.dao.GroupsDao;
import org.tahom.repository.model.Tournament;

public interface GroupsExtDao extends GroupsDao {

    public int deleteFinalGroups(Tournament tournament);

}
