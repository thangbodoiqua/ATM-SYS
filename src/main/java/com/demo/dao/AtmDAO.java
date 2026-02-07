package com.demo.dao;

import java.util.List;

import com.demo.dto.AtmDTO;

public interface AtmDAO {
	AtmDTO findById(Long atmId);

	boolean save(AtmDTO atm);

	boolean update(AtmDTO atm);

	List<AtmDTO> findAll();
}
