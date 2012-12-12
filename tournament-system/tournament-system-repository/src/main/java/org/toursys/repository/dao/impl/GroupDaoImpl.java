package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.GroupDao;
import org.toursys.repository.form.GroupForm;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Tournament;

public class GroupDaoImpl extends BaseDaoImpl implements GroupDao {

    @Override
    public Tournament createGroup(Tournament tournament, Groups... groups) {
        if (groups != null) {
            SqlSession session = getSqlSession();
            for (Groups group : groups) {
                group.setTournament(tournament);
                int count = getCrudEngine("INSERT_GROUPS").insert(session, group);
                logger.info("insert tournament: " + count + ": " + group);
                if (count > 0)
                    tournament.getGroups().add(group);
            }
        }
        return tournament;
    }

    @Override
    public Groups updateGroup(Groups group) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("UPDATE_GROUPS").update(session, group);
        logger.info("update group: " + count + ": " + group);
        return (count > 0 ? group : null);
    }

    @Override
    public boolean deleteGroup(Groups group) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("DELETE_GROUPS").delete(session, group);
        logger.info("delete group: " + count + ": " + group);
        return (count > 0);
    }

    @Override
    public Groups getGroup(Groups group) {
        SqlSession session = getSqlSession();
        Groups g = getCrudEngine("GET_GROUPS").get(session, Groups.class, group);
        logger.info("get group: " + g);
        return g;
    }

    @Override
    public List<Groups> findGroups(GroupForm groupForm) {
        SqlSession session = getSqlSession();
        logger.info("find groups");
        return getQueryEngine("FIND_GROUPS").query(session, Groups.class, groupForm);
    }

}
