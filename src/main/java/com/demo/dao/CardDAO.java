
package com.demo.dao;

import com.demo.dto.CardDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CardDAO {
	CardDTO findByCardNumber(String cardNumber);
	
	CardDTO findById(Long cardId);

	boolean save(CardDTO card);

	boolean update(CardDTO card);

	List<CardDTO> findAll();
	
	boolean update(Connection con, CardDTO card) throws SQLException;
	
}
