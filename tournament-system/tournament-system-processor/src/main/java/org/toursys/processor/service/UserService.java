package org.toursys.processor.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.model.User;

public class UserService extends AbstractService {

    // Basic operations

    @Transactional
    public User createUser(User user) {
        user.setPassword(TournamentUtil.encryptUserPassword(user.getPassword()));
        return tournamentAggregationDao.createUser(user);
    }

    @Transactional(readOnly = true)
    public User getUser(User user) {
        return tournamentAggregationDao.getUser(user);
    }

    @Transactional
    public int updateUser(User user) {
        return tournamentAggregationDao.updateUser(user);
    }

    @Transactional
    public int deleteUser(User user) {
        user.setPassword(TournamentUtil.encryptUserPassword(user.getPassword()));
        return tournamentAggregationDao.deleteUser(user);
    }

    @Transactional(readOnly = true)
    public List<User> getUsers(User user) {
        return tournamentAggregationDao.getListUsers(user);
    }

    // Advanced operations

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return tournamentAggregationDao.getListUsers(new User());
    }

}
