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
		return userDao.update(user._setNull_(User.Attribute.name, User.Attribute.surname));
	}

	public User createUser(User user) {
		return userDao.insert(user);
	}
	
	public User getUserWithSeason(User user) {
		user._setInit_(User.Association.seasons);
		return userDao.get(user);
	}

}
