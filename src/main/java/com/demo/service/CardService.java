package com.demo.service;

import java.util.List;

import com.demo.dto.CardDTO;
import com.demo.dto.TransactionDTO;

public interface CardService {
	boolean createCard(CardDTO card);
	
	boolean changePin(String cardNumber, String pin);
	
	boolean verifyCardNumber(Long userId, String cardNumber);
	
	boolean verifyPin(String cardNumber, String pin);
		
	Long findCardIdByCardNumber(String cardNumber);
	
	CardDTO findByCardNumber(String cardNumber);
	
	CardDTO findByCardId(Long id);
	
	List<CardDTO> findAll();
	
}
