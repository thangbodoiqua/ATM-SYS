package com.demo.dao;

import com.demo.dto.UserDTO;
import java.util.List;

public interface UserDAO {
	UserDTO findById(Long userId);

	UserDTO findByUsername(String username);

	boolean save(UserDTO user);

	boolean update(UserDTO user);

	List<UserDTO> findAll();
}
