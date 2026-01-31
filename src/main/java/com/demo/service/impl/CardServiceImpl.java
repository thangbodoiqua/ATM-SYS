package com.demo.service.impl;

import com.demo.constant.Constants;
import com.demo.dao.CardDAO;
import com.demo.dao.TransactionDAO;
import com.demo.dao.impl.CardDAOImpl;
import com.demo.dao.impl.TransactionDAOImpl;
import com.demo.dto.CardDTO;
import com.demo.dto.TransactionDTO;
import com.demo.service.CardService;
import com.demo.util.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.SpringLayout.Constraints;

public class CardServiceImpl implements CardService {

    private final CardDAO cardDAO = new CardDAOImpl();
    private final TransactionDAO transDAO = new TransactionDAOImpl();
    
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

        return cardDAO.create(card);
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
	public boolean validCardNumber(Long userId, String cardNumber) {
		cardNumber = cardNumber.trim();
		
		if(cardNumber == null || cardNumber.isEmpty() || !isValidCardNumber(cardNumber)) return false;
		if(userId == null) return false;
		
		CardDTO card = cardDAO.findByUserIdAndCardNumber(userId, cardNumber);
		if(card == null) return false;
		
		return true;
	}


    private static boolean isValidCardNumber(String s) {
        return s.matches("^\\d{16}$");
    }

    private static boolean isValidPin(String s) {
        return s.matches("^\\d{4}$");
    }

    public boolean validPin(String cardNumber, String pin) {
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
	public boolean createTransaction(TransactionDTO trans) {
		if(trans == null) return false;
		
		if (trans.getRefTransId() == null) {
		    if (trans.getTransType().equalsIgnoreCase(Constants.TRANS_TYPE_DEPOSIT)) 
		    	return deposit(trans);
		    else 
		    	return withdraw(trans);
		} else {
		    return transfer(trans);
		}

		
	}
	
	public boolean withdraw(TransactionDTO trans) {
		Long cardId = trans.getCardId();
		if(cardId == null) return false;
		
		CardDTO card = cardDAO.findById(cardId);
		if(card == null) return false;
		
		BigDecimal balance = card.getBalance();
		BigDecimal transAmount = trans.getTransAmount();
		System.out.println("<withdraw>: balance - " + balance + ", transAmount" + transAmount);
		if(balance.compareTo(transAmount) < 0) {
			return false;
		}
		
		balance = balance.subtract(transAmount);
		
		System.out.println("<withdraw - after>: balance - " + balance);
		card.setBalance(balance);
		
		boolean updatedCard = cardDAO.update(card);
		if(!updatedCard) return false;
		
		return transDAO.create(trans);
	}
	
	public boolean deposit(TransactionDTO trans) {
		Long cardId = trans.getCardId();
		if(cardId == null) return false;
		
		CardDTO card = cardDAO.findById(cardId);
		if(card == null) return false;
		
		BigDecimal balance = card.getBalance();
		BigDecimal transAmount = trans.getTransAmount();
		System.out.println("<Deposit>: balance - " + balance + ", transAmount" + transAmount);
		if(balance.compareTo(transAmount) < 0) {
			return false;
		}
		
		balance = balance.add(transAmount);
		
		System.out.println("<Deposit - after>: balance - " + balance);
		card.setBalance(balance);
		
		boolean updatedCard = cardDAO.update(card);
		if(!updatedCard) return false;
		
		return transDAO.create(trans);	
		}
	

	public boolean transfer(TransactionDTO trans) {
	    TransactionDTO withdrawTrans = new TransactionDTO();
	    withdrawTrans.setAtmId(trans.getAtmId());             
	    withdrawTrans.setCardId(trans.getCardId());          
	    withdrawTrans.setTransAmount(trans.getTransAmount()); 
	    withdrawTrans.setTransType(Constants.TRANS_TYPE_WITHDRAW);
	    withdrawTrans.setRefTransId(trans.getRefTransId());                    

	    boolean w = withdraw(withdrawTrans);
	    if (!w) {
	        System.out.println("TRANSFER: WITHDRAW FAILED");
	        return false;
	    }
	
	    boolean createdWithdraw = transDAO.create(withdrawTrans);
	    if (!createdWithdraw) {
	        System.out.println("TRANSFER: FAILED TO SAVE WITHDRAW TRANSACTION");
	        return false;
	    }
	
	    Long withdrawId = withdrawTrans.getTransId();
		
	    TransactionDTO depositTrans = new TransactionDTO();
	    depositTrans.setAtmId(trans.getAtmId());
	    depositTrans.setCardId(trans.getRefTransId());        
	    depositTrans.setTransAmount(trans.getTransAmount());
	    depositTrans.setTransType(Constants.TRANS_TYPE_DEPOSIT);
	    depositTrans.setRefTransId(withdrawId);              
	
	
	    boolean d = deposit(depositTrans);
	
	    if (d) {
	        boolean createdDeposit = transDAO.create(depositTrans);
	        if (!createdDeposit) {
	            System.out.println("TRANSFER: FAILED TO SAVE DEPOSIT → REVERSING...");
	            compensateReverse(withdrawTrans);
	            return false;
	        }
	
	        return true;
	
	    } else {
	        System.out.println("TRANSFER: DEPOSIT FAILED → REVERSING...");
	        compensateReverse(withdrawTrans);
	        return false;
	    }
	}


	private void compensateReverse(TransactionDTO withdrawTrans) {
	
	    TransactionDTO reverseTrans = new TransactionDTO();
	    reverseTrans.setAtmId(withdrawTrans.getAtmId());
	    reverseTrans.setCardId(withdrawTrans.getCardId());     
	    reverseTrans.setTransAmount(withdrawTrans.getTransAmount());
	    reverseTrans.setTransType(Constants.TRANS_TYPE_DEPOSIT); 
	    reverseTrans.setRefTransId(withdrawTrans.getTransId());  
	
	    boolean ok = deposit(reverseTrans);
	    if (!ok) {
	        System.out.println("REVERSE FAILED: CRITICAL ERROR - MANUAL FIX NEEDED");
	        return;
	    }
	
	    transDAO.create(reverseTrans);
	    System.out.println("REVERSE DONE SUCCESSFULLY");
	}


	@Override
	public Long findCardIdByCardNumber(String cardNumber) {
		if(cardNumber == null) return null;
		
		CardDTO card = cardDAO.findByCardNumber(cardNumber);
		if(card == null) return null;
		
		return card.getCardId();
	}

	

}