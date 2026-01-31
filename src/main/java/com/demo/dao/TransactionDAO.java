package com.demo.dao;

import java.util.List;

import java.sql.Date;
import com.demo.dto.TransactionDTO;

public interface TransactionDAO {
	TransactionDTO findById(Long transId);

	TransactionDTO findByCardId(Long cardId);

	TransactionDTO findByAtmId(Long atmId);

	List<TransactionDTO> findInRange(Date from, Date to, TransactionDTO trans);
	
	boolean create(TransactionDTO trans);
	
}
