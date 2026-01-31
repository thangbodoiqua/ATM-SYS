package com.demo.form.atm;

import org.apache.struts.action.ActionForm;

public class EnterPinForm extends ActionForm {
	private String pin;
	
	public EnterPinForm() {
		super();
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

}
