
package com.demo.dao;

import com.demo.dto.CardDTO;
import java.util.List;

public interface CardDAO {
	CardDTO findByCardNumber(String cardNumber);
	
	CardDTO findByUserIdAndCardNumber(Long userId, String cardNumber);

	List<CardDTO> findCardsByUserId(Long userId);

	CardDTO findById(Long cardId);

	boolean create(CardDTO card);

	boolean update(CardDTO card);

	List<CardDTO> findAll();
	
}
