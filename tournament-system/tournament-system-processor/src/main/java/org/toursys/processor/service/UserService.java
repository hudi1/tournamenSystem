package org.toursys.processor.service;

import java.util.List;

import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.model.User;

public class UserService extends AbstractService {

    // Basic operations

    public User createUser(User user) {
        user.setPassword(TournamentUtil.encryptUserPassword(user.getPassword()));
        return tournamentAggregationDao.createUser(user);
    }

    public User getUser(User user) {
        return tournamentAggregationDao.getUser(user);
    }

    public int updateUser(User user) {
        return tournamentAggregationDao.updateUser(user);
    }

    public int deleteUser(User user) {
        user.setPassword(TournamentUtil.encryptUserPassword(user.getPassword()));
        return tournamentAggregationDao.deleteUser(user);
    }

    public List<User> getUsers(User user) {
        return tournamentAggregationDao.getListUsers(user);
    }

    // Advanced operations

    public List<User> getAllUsers() {
        return tournamentAggregationDao.getListUsers(new User());
    }

}
