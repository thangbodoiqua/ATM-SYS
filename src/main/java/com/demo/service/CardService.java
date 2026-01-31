package com.demo.service;

import com.demo.dto.CardDTO;
import com.demo.dto.TransactionDTO;

public interface CardService {
	boolean createCard(CardDTO card);
	
	boolean changePin(String cardNumber, String pin);
	
	boolean validCardNumber(Long userId, String cardNumber);
	
	boolean validPin(String cardNumber, String pin);
	
	boolean createTransaction(TransactionDTO trans);
	
	Long findCardIdByCardNumber(String cardNumber);
	

}
