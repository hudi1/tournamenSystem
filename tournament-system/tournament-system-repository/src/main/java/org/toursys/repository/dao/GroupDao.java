package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.Groups;

public interface GroupDao {

    public Groups createGroup(Groups group);

    public Groups updateGroup(Groups group);

    public boolean deleteGroup(Groups group);

    public Groups getGroup(Groups group);

    public List<Groups> getListGroups(Groups group);
}
