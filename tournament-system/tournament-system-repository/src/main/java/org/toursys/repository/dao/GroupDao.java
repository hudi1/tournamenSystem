package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.form.GroupForm;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Tournament;

public interface GroupDao {

    public Tournament createGroup(Tournament tournament, Groups... groups);

    public Groups updateGroup(Groups group);

    public boolean deleteGroup(Groups group);

    public Groups getGroup(Groups group);

    public List<Groups> findGroups(GroupForm groupForm);
}
