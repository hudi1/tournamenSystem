package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.UserDao;
import org.toursys.repository.model.User;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    @Override
    public User createUser(User user) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("INSERT_USER").insert(session, user);
        logger.info("insert user: " + count + ": " + user);
        return (count > 0 ? user : null);
    }

    @Override
    public User updateUser(User user) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("UPDATE_USER").update(session, user);
        logger.info("update user: " + count + ": " + user);
        return (count > 0 ? user : null);
    }

    @Override
    public boolean deleteUser(User user) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("DELETE_USER").delete(session, user);
        logger.info("delete user: " + count + ": " + user);
        return (count > 0);
    }

    @Override
    public User getUser(User user) {
        SqlSession session = getSqlSession();
        User s = getCrudEngine("GET_USER").get(session, User.class, user);
        logger.info("get user: " + s);
        return s;
    }

    @Override
    public List<User> getAllUsers() {
        SqlSession session = getSqlSession();
        logger.info("get all seasons");
        return getQueryEngine("GET_ALL_USERS").query(session, User.class);
    }

}
