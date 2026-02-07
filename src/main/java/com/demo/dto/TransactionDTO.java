package com.demo.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionDTO {
	private Long transId;
	private Long atmId;
	private Long cardId;
	private String transType;
	private BigDecimal transAmount;
	private Timestamp transTime;
	private Long refTransId;
	private String refCardNumber = "";

	public String getRefCardNumber() {
		return refCardNumber;
	}

	public void setRefCardNumber(String refCardNumber) {
		this.refCardNumber = refCardNumber;
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	public Long getAtmId() {
		return atmId;
	}

	public void setAtmId(Long atmId) {
		this.atmId = atmId;
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public Timestamp getTransTime() {
		return transTime;
	}

	public void setTransTime(Timestamp transTime) {
		this.transTime = transTime;
	}

	public Long getRefTransId() {
		return refTransId;
	}

	public void setRefTransId(Long refTransId) {
		this.refTransId = refTransId;
	}

}
