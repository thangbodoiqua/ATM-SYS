package com.demo.service;

import com.demo.dto.UserDTO;

import java.util.List;

import com.demo.dao.UserDAO;

public interface UserService {
	boolean create(UserDTO user);

	UserDTO verify(String username, String password);
	
	List<UserDTO> findAll();
	
	UserDTO findByUsername(String username);
	
	UserDTO findById(String id);
	
	boolean updatePassword(String username, String password, String newPassword);

	boolean updateUser(UserDTO user);
}
