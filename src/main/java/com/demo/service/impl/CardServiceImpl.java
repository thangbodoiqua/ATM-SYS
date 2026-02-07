package com.demo.service.impl;


import com.demo.dao.CardDAO;
import com.demo.dao.TransactionDAO;
import com.demo.dao.impl.CardDAOImpl;
import com.demo.dao.impl.TransactionDAOImpl;
import com.demo.dto.CardDTO;
import com.demo.service.CardService;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class CardServiceImpl implements CardService {

    private final CardDAO cardDAO = new CardDAOImpl();
    private final TransactionDAO transDAO = new TransactionDAOImpl();
	@Override
    public Long findCardIdByCardNumber(String cardNumber) {
		if(cardNumber == null) return null;
		
		CardDTO card = cardDAO.findByCardNumber(cardNumber);
		if(card == null) return null;
		
		return card.getCardId();
	}


	@Override
	public CardDTO findByCardNumber(String cardNumber) {
		if(cardNumber == null) return null;
		
		CardDTO card = cardDAO.findByCardNumber(cardNumber);
		if(card == null) return null;
		
		return card;
	}
    
    @Override

    public boolean createCard(CardDTO card) {
        if (card == null) return false;
        if (card.getUserId() == null) return false;

        String cardNumber = card.getCardNumber().trim();
        String pin        = card.getPinCode().trim();
        
    	if(cardNumber == null || cardNumber.isEmpty() || !isValidCardNumber(cardNumber)) return false;
        if(pin == null || pin.isEmpty() ||!isValidPin(pin)) return false;

        CardDTO existed = cardDAO.findByCardNumber(cardNumber);
        if (existed != null) {
            return false;
        }

        if (card.getCardStatus() == null) {
            card.setCardStatus("ACTIVE"); 
        }
        if (card.getCreatedDate() == null) {
            card.setCreatedDate(Date.valueOf(LocalDate.now()));
        }
        if (card.getExpiredDate() == null) {
            card.setExpiredDate(Date.valueOf(LocalDate.now().plusYears(4)));
        }
        if (card.getBalance() == null) {
            card.setBalance(BigDecimal.ZERO);
        }
        if (card.getCardType() == null) {
            card.setCardType("DEBIT");
        }

        card.setCardNumber(cardNumber);
        card.setPinCode(pin);

        return cardDAO.save(card);
    }


    @Override
    public boolean changePin(String cardNumber, String pin) {
    	cardNumber = cardNumber.trim();
    	pin = pin.trim();
    	
    	if(pin == null || pin.isEmpty() || !isValidPin(pin)) return false;
    	if(cardNumber == null || cardNumber.isEmpty()) return false;

        CardDTO card = cardDAO.findByCardNumber(cardNumber);
        
        if (card == null) {
            return false;
        }
        
        card.setPinCode(pin);

        return cardDAO.update(card);
    }

    @Override
	public boolean verifyCardNumber(Long userId, String cardNumber) {
		cardNumber = cardNumber.trim();
		
		if(cardNumber == null || cardNumber.isEmpty() || !isValidCardNumber(cardNumber)) return false;
		
		if(userId == null) return false;
		
		CardDTO card = cardDAO.findByCardNumber(cardNumber);
		
		if(card == null) return false;
		
		if(!card.getUserId().equals(userId)) return false;
		
		return true;
	}


    private static boolean isValidCardNumber(String s) {
        return s.matches("^\\d{16}$");
    }

    private static boolean isValidPin(String s) {
        return s.matches("^\\d{4}$");
    }

    public boolean verifyPin(String cardNumber, String pin) {
    	cardNumber = cardNumber.trim();
    	pin = pin.trim();
    	if(pin == null || pin.isEmpty()) return false;
    	if(cardNumber == null || cardNumber.isEmpty()) return false;
    	
		CardDTO card = cardDAO.findByCardNumber(cardNumber);
    	
		if(card == null) return false;
		if(!card.getPinCode().equals(pin)) return false;
		
    	return true;
    }


	@Override
	public List<CardDTO> findAll() {
		return cardDAO.findAll();		
	}


	@Override
	public CardDTO findByCardId(Long id) {
if(id == null) return null;
		
		CardDTO card = cardDAO.findById(id);
		if(card == null) return null;
		
		return card;
	}	
}