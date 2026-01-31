package com.demo.form.atm;

import org.apache.struts.action.ActionForm;

public class EnterCardForm extends ActionForm{
	private String cardNumber;

	public EnterCardForm() {
		super();
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	
	
	
}
