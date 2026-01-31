package com.demo.service.impl;

import com.demo.dto.UserDTO;
import com.demo.service.UserService;
import com.demo.util.PasswordUtil;

import java.sql.Date;

import org.apache.log4j.Logger;

import com.demo.dao.UserDAO;
import com.demo.dao.impl.UserDAOImpl;

public class UserServiceImpl implements UserService {
	private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	UserDAO userDAO = new UserDAOImpl();

	@Override
	public boolean createUser(UserDTO user) {
		log.info("[UserService] Creating user: " + user.getUsername());
		
		UserDTO existUser = userDAO.findByUsername(user.getUsername());
		if (existUser != null) {
			log.warn("[UserService] User creation failed - Username already exists: " + user.getUsername());
			return false;
		}
		
		String salt = PasswordUtil.generateSalt();
		String hashedPass = PasswordUtil.hashPassword(user.getPassword(), salt);
		
		user.setSalt(salt);
		user.setPassword(hashedPass);

		boolean result = userDAO.create(user);
		
		if (result) {
			log.info("[UserService] User created successfully: " + user.getUsername());
		} else {
			log.error("[UserService] Failed to create user: " + user.getUsername());
		}
		
		return result;
	}
	
	
	@Override
	public UserDTO login(String username, String password) {
		log.info("[UserService] Processing login for username: " + username);
		
		UserDTO existUser = userDAO.findByUsername(username);
		if (existUser == null) {
			log.warn("[UserService] Login failed - User not found: " + username);
			return null;
		}
		
		boolean isValid = PasswordUtil.verifyPassword(password, existUser.getPassword(), existUser.getSalt());
		if (isValid != true) {
			log.warn("[UserService] Login failed - Invalid password for username: " + username);
			return null;
		}

		log.info("[UserService] Login successful for username: " + username + " | Role: " + existUser.getRole());
		return existUser;
	}
	
	
}
