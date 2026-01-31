package com.demo.service.impl;

import java.math.BigDecimal;

import com.demo.constant.Constants;
import com.demo.dao.TransactionDAO;
import com.demo.dao.impl.TransactionDAOImpl;
import com.demo.dto.TransactionDTO;
import com.demo.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {
	TransactionDAO transDAO = new TransactionDAOImpl(); 
	@Override
	public boolean create(TransactionDTO trans) {
		if(trans == null) return false;
		
		if(trans.getTransType() == null || trans.getCardId() == null || trans.getTransAmount() == null) {
			return false;
		}
		
		
		return transDAO.create(trans);
	}	
}
