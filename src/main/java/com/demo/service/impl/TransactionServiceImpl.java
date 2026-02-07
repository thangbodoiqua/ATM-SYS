package com.demo.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.demo.constant.Constants;
import com.demo.dao.CardDAO;
import com.demo.dao.TransactionDAO;
import com.demo.dao.impl.CardDAOImpl;
import com.demo.dao.impl.TransactionDAOImpl;
import com.demo.dto.CardDTO;
import com.demo.dto.TransactionDTO;
import com.demo.service.TransactionService;
import com.demo.util.DBUtil;

public class TransactionServiceImpl implements TransactionService {
	TransactionDAO transDAO = new TransactionDAOImpl();
    private final CardDAO cardDAO = new CardDAOImpl();

	@Override
	public boolean create(TransactionDTO trans) {
		if(trans == null) return false;
		
		if(trans.getTransType() == null || trans.getCardId() == null || trans.getTransAmount() == null) {
			return false;
		}
		
		return transDAO.save(trans);
	}
	@Override
	public List<TransactionDTO> findCardTransBw(Timestamp from, Timestamp to, Long cardId) {
		if(from == null || to == null || cardId == null) return null;
		
		if(!from.before(to)) return null;
			
		return transDAO.findCardTransInRange(from, to, cardId);
	}
	
	@Override
	public List<TransactionDTO> findUserTransBw(Timestamp from, Timestamp to, Long userId) {
		if(from == null || to == null || userId == null) return null;
		
		if(!from.before(to)) return null;
		
		return transDAO.findUserTransInRange(from, to, userId);
	}
	@Override
	public List<TransactionDTO> last5UserTrans(Long userId) {
		if(userId == null) return null;
		
		return transDAO.findLast5UserTransByUserId(userId);
	}
	
	@Override
	public List<TransactionDTO> last5CardTrans(Long cardId) {
		if(cardId == null) return null;
	
		return transDAO.findLast5CardTransByCardId(cardId);
	}


	public boolean createTransaction(TransactionDTO trans) {
	    if (trans == null) return false;
	    
	    Connection con = null;
	    boolean ok = false;
	
	    try {
	        con = DBUtil.getConnection();
	        con.setAutoCommit(false);
	
	        if (trans.getRefTransId() == null) {
	            if (Constants.TRANS_TYPE_DEPOSIT.equalsIgnoreCase(trans.getTransType())) {
	                ok = deposit(con, trans);      
	            } else {
	                ok = withdraw(con, trans);     
	            }
	        } else {
	            ok = transfer(con, trans);         
	        }
	
	        if (ok) {
	            con.commit();
	        } else {
	            con.rollback();
	            return false;
	        }
	        return ok;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (con != null) {
	            try { con.rollback(); } catch (Exception ignore) {}
	        }
	        return false;
	    } finally {
	        if (con != null) {
	            try { con.setAutoCommit(true); } catch (Exception ignore) {}
	            try { con.close(); } catch (Exception ignore) {}
	        }
	    }
	}

	
	
	public boolean transfer(Connection con, TransactionDTO trans) {
		if(trans.getCardId() == null) return false;
		
		if(trans.getRefTransId() == null) return false;
		
		CardDTO card = cardDAO.findById(trans.getCardId());
		if(card == null) return false;
		
		
		CardDTO cardRef = cardDAO.findById(trans.getRefTransId());
		if(cardRef == null) return false;

		
		BigDecimal cardBalance = card.getBalance();
		if(cardBalance.compareTo(trans.getTransAmount()) < 0) return false;
		BigDecimal cardRefBalance = cardRef.getBalance();
		
		cardBalance = cardBalance.subtract(trans.getTransAmount());
		cardRefBalance = cardRefBalance.add(trans.getTransAmount());
		
		card.setBalance(cardBalance);
		cardRef.setBalance(cardRefBalance);
		
	    TransactionDTO withdrawTrans = new TransactionDTO();
		withdrawTrans.setAtmId(trans.getAtmId());             
	    withdrawTrans.setCardId(trans.getCardId());          
	    withdrawTrans.setTransAmount(trans.getTransAmount()); 
	    withdrawTrans.setTransType(Constants.TRANS_TYPE_WITHDRAW);
	    withdrawTrans.setRefTransId(trans.getRefTransId());
	    
	    TransactionDTO depositTrans = new TransactionDTO();
	    depositTrans.setAtmId(trans.getAtmId());
	    depositTrans.setCardId(trans.getRefTransId());        
	    depositTrans.setTransAmount(trans.getTransAmount());
	    depositTrans.setTransType(Constants.TRANS_TYPE_DEPOSIT);
	    depositTrans.setRefTransId(trans.getCardId()); 
	    
	    
	    boolean transferOk;
		try {
			transferOk = transDAO.saveTransfer(con, withdrawTrans, depositTrans);
			if(!transferOk) {
		    	return false;
		    }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	    
	    boolean updateBalanceCard;
		try {
			updateBalanceCard = cardDAO.update(con, card);
		    boolean updateBalanceRef = cardDAO.update(con, cardRef);

			if(!updateBalanceCard || !updateBalanceRef) {
		    	return false;
		    }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}
		
		return true;
	}
	
	public boolean withdraw(Connection con, TransactionDTO trans) {
		if(trans.getCardId() == null) return false;
		
		CardDTO card = cardDAO.findById(trans.getCardId());
		if(card == null) return false;
						
		BigDecimal balance = card.getBalance();
		BigDecimal transAmount = trans.getTransAmount();
		if(balance.compareTo(transAmount) < 0) {
			return false;
		}
		
		balance = balance.subtract(transAmount);
		card.setBalance(balance);
		
		boolean updatedCard;
		try {
			updatedCard = cardDAO.update(con, card);
			if(!updatedCard) return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		boolean transferOk;
		try {
			transferOk = transDAO.save(con, trans);
			if(!transferOk) {
		    	return false;
		    }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean deposit(Connection con, TransactionDTO trans) {
		Long cardId = trans.getCardId();
		if(cardId == null) return false;
		
		CardDTO card = cardDAO.findById(cardId);
		if(card == null) return false;
		
		BigDecimal balance = card.getBalance();
		BigDecimal transAmount = trans.getTransAmount();

		balance = balance.add(transAmount);
		
		card.setBalance(balance);
		
		boolean updatedCard;
		try {
			updatedCard = cardDAO.update(con, card);
			if(!updatedCard) return false;

		} catch (SQLException e) {

			e.printStackTrace();
		}
	
		boolean transferOk;
		try {
			transferOk = transDAO.save(con, trans);
			if(!transferOk) {
		    	return false;
		    }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	

	

	
}
