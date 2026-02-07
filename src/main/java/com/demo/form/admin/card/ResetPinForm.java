package com.demo.form.admin.card;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ResetPinForm extends ActionForm{
	private String cardNumber;
	
	private String oldPin;
	
	private String newPin;
	private String confirmPin;


	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
		if("GET".equalsIgnoreCase(request.getMethod())) {
			return errors;
		}
		if (isEmpty(cardNumber)) {
			errors.add("error", new ActionMessage("error.card-number.required"));
		}
		
		if(oldPin == null || oldPin.trim().isEmpty() || !oldPin.matches("\\d{4}$")) {
			errors.add("oldPin", new ActionMessage("error.pin.invalid"));
			return errors;
		}
		if(newPin == null || newPin.trim().isEmpty() || !newPin.matches("\\d{4}$")) {
			errors.add("newPin", new ActionMessage("error.pin.invalid"));
			return errors;
		}
		if(oldPin.equals(confirmPin)) {
			errors.add("error", new ActionMessage("error.new-pin.equal"));
		}
		if(confirmPin == null || confirmPin.trim().isEmpty() || !confirmPin.matches("\\d{4}$") || !confirmPin.equals(newPin)) {
			errors.add("confirmPin", new ActionMessage("error.new-pin.unmatch"));
			return errors;
		}
		
		
		
		return errors;
	}
	private boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	public String getOldPin() {
		return oldPin;
	}

	public void setOldPin(String oldPin) {
		this.oldPin = oldPin;
	}

	public String getNewPin() {
		return newPin;
	}

	public void setNewPin(String newPin) {
		this.newPin = newPin;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getConfirmPin() {
		return confirmPin;
	}
	public void setConfirmPin(String confirmPin) {
		this.confirmPin = confirmPin;
	}
	
	

}
