package com.demo.service.impl;

import com.demo.dto.UserDTO;
import com.demo.service.UserService;
import com.demo.util.PasswordUtil;

import java.sql.Date;

import com.demo.dao.UserDAO;
import com.demo.dao.impl.UserDAOImpl;

public class UserServiceImpl implements UserService {
	UserDAO userDAO = new UserDAOImpl();

	@Override
	public boolean createUser(UserDTO user) {
		UserDTO existUser = userDAO.findByUsername(user.getUsername());
		if (existUser != null) {
			return false;
		}
		String salt = PasswordUtil.generateSalt();
		String hashedPass = PasswordUtil.hashPassword(user.getPassword(), salt);

		user.setSalt(salt);
		user.setPassword(hashedPass);

		return userDAO.create(user);
	}

	@Override
	public UserDTO login(String username, String password) {
		UserDTO existUser = userDAO.findByUsername(username);
		if (existUser == null) {
			return null;
		}

		boolean isValid = PasswordUtil.verifyPassword(password, existUser.getPassword(), existUser.getSalt());
		if (isValid != true) {
			return null;
		}

		return existUser;
	}
}
