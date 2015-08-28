package org.toursys.processor.service.user;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.dao.UserDao;
import org.toursys.repository.model.User;

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

    public User updateUser(User user) {
        userDao.update(user);
        return user;
    }

    public User createUser(User user) {
        return userDao.insert(user);
    }

}
