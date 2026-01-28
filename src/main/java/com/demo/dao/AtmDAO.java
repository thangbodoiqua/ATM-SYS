package com.demo.dao;

import java.util.List;

import com.demo.dto.AtmDTO;

public interface AtmDAO {
	AtmDTO findById(Long atmId);

	boolean create(AtmDTO atm);

	boolean update(AtmDTO atm);

//	boolean deleteById(long id);
	List<AtmDTO> findAll();
}
