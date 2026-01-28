package com.demo.service;

import com.demo.dto.UserDTO;
import com.demo.dao.UserDAO;

public interface UserService {
	boolean createUser(UserDTO user);

	UserDTO login(String username, String password);
}
