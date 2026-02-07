package com.demo.service.impl;

import com.demo.dto.UserDTO;
import com.demo.service.UserService;
import com.demo.util.PasswordUtil;

import java.sql.Date;
import java.util.List;

import org.apache.commons.logging.impl.Log4JLogger;

import com.demo.dao.CardDAO;
import com.demo.dao.UserDAO;
import com.demo.dao.impl.UserDAOImpl;

public class UserServiceImpl implements UserService {
	UserDAO userDAO = new UserDAOImpl();

	@Override
	public boolean create(UserDTO user) {
		UserDTO existUser = userDAO.findByUsername(user.getUsername());
		if (existUser != null) {
			return false;
		}
		String salt = PasswordUtil.generateSalt();
		String hashedPass = PasswordUtil.hashPassword(user.getPassword(), salt);
		
		user.setSalt(salt);
		user.setPassword(hashedPass);

		return userDAO.save(user);
	}
	
	
	@Override
	public UserDTO verify(String username, String password) {
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
	
	public List<UserDTO> findAll(){
		return userDAO.findAll();
	}

	@Override
	public UserDTO findByUsername(String username) {
		return userDAO.findByUsername(username);
	}


	@Override
	public UserDTO findById(String id) {
		return userDAO.findById(null);
	}


	@Override
	public boolean updatePassword(String username, String password, String newPassword) {
		if(username == null || password == null || newPassword == null 
				|| username.trim().isEmpty() || password.trim().isEmpty() || newPassword.trim().isEmpty()) {
			return false;
		}
		
		UserDTO user = userDAO.findByUsername(username);
		if(user == null) return false;
		
		boolean verifyPass = PasswordUtil.verifyPassword(password, user.getPassword(), user.getSalt());
		
		if(!verifyPass) return false;
		
		String newSalt = PasswordUtil.generateSalt();
		String newHashedPass = PasswordUtil.hashPassword(newPassword, newSalt);
		
		user.setSalt(newSalt);
		user.setPassword(newHashedPass);
				
		return userDAO.update(user);
	}


	@Override
	public boolean updateUser(UserDTO user) {
		if(user == null) return false;
		
		String username = user.getUsername();
		if(userDAO.findByUsername(username) == null) return false;
		
		return userDAO.update(user);
	}
}
