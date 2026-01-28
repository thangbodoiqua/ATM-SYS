package com.demo.dao;

import com.demo.dto.UserDTO;
import java.util.List;

public interface UserDAO {
	UserDTO findById(Long userId);

	UserDTO findByUsername(String username);

	boolean existsByUsername(String username);

	boolean create(UserDTO user);

	boolean update(UserDTO user);

//	boolean deleteById(long id);
	List<UserDTO> findAll();
}
