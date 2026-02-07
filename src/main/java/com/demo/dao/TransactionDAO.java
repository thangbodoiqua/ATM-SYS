package com.demo.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.demo.dto.TransactionDTO;

public interface TransactionDAO {
	TransactionDTO findById(Long transId);

	/*
	 * TransactionDTO findByCardId(Long cardId);
	 * 
	 * TransactionDTO findByAtmId(Long atmId);
	 */
	List<TransactionDTO> findCardTransInRange(Timestamp from, Timestamp to, Long cardId);
	
	List<TransactionDTO> findUserTransInRange(Timestamp from, Timestamp to, Long userId);
	
	boolean save(TransactionDTO trans);
	
	boolean save(Connection con, TransactionDTO trans) throws SQLException;

	boolean update(TransactionDTO trans);
	
	List<TransactionDTO> findLast5CardTransByCardId(Long cardId);
	
	List<TransactionDTO> findLast5UserTransByUserId(Long userId);
	
	 boolean saveTransfer(Connection con, TransactionDTO withdraw, TransactionDTO deposit) throws SQLException;
}
