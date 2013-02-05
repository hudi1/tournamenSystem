package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.User;

public interface UserDao {

    public User createUser(User user);

    public User updateUser(User user);

    public boolean deleteUser(User user);

    public User getUser(User user);

    public List<User> getAllUsers();

}
