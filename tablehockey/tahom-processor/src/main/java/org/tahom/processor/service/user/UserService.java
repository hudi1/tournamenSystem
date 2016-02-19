package org.tahom.processor.service.user;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.tahom.repository.dao.UserDao;
import org.tahom.repository.model.User;

public class UserService {

    @Inject
    private UserDao userDao;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.list(new User());
    }

    public User getUser(User user) {
        return userDao.get(user);
    }

    public int updateUser(User user) {
        return userDao.update(user);
    }

    public User createUser(User user) {
        return userDao.insert(user);
    }

}
