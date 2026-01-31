package com.demo.form.admin.card;

import org.apache.struts.action.ActionForm;

public class CreateCardForm extends ActionForm {
    private String userId;     
    private String cardNumber;
    private String pinCode;
    private String cardType;   
    private String cardStatus;
    private String expiredDate;
    private String balance;
    
    // ACTIVE/LOCKED/EXPIRED
 
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getPinCode() { return pinCode; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }

    public String getCardStatus() { return cardStatus; }
    public void setCardStatus(String cardStatus) { this.cardStatus = cardStatus; }
	public String getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
    
}