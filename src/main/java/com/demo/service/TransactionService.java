package com.demo.service;

import java.sql.Timestamp;
import java.util.List;

import com.demo.dto.TransactionDTO;

public interface TransactionService {
	boolean create(TransactionDTO trans);
	
	List<TransactionDTO> last5UserTrans(Long userId);
	
	List<TransactionDTO> last5CardTrans(Long cardId);
	
	boolean createTransaction(TransactionDTO trans);
	
	List<TransactionDTO> findCardTransBw(Timestamp from, Timestamp to, Long cardId);
	
	List<TransactionDTO> findUserTransBw(Timestamp from, Timestamp to, Long userId);

}
