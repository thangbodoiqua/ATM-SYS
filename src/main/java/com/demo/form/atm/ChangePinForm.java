package com.demo.form.atm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.demo.constant.Constants;

public class ChangePinForm extends ActionForm {
	private String oldPin;
	private String newPin;
	private String confirmPin;
	
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if("GET".equalsIgnoreCase(request.getMethod())) {
			return errors;
		}
		
		if(oldPin == null || oldPin.trim().isEmpty() || !oldPin.matches("\\d{4}$")) {
			errors.add("error", new ActionMessage("error.pin.invalid"));
			return errors;
		}
		
		if(newPin == null || newPin.trim().isEmpty() || !newPin.matches("\\d{4}$")) {
			errors.add("error", new ActionMessage("error.pin.invalid"));
			return errors;
		}
		
		if(confirmPin == null || confirmPin.trim().isEmpty() || !confirmPin.matches("\\d{4}$") || !confirmPin.equals(newPin)) {
			errors.add("error", new ActionMessage("error.new-pin.unmatch"));
			return errors;
		}
		
		return errors;
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
	public String getConfirmPin() {
		return confirmPin;
	}
	public void setConfirmPin(String confirmPin) {
		this.confirmPin = confirmPin;
	}
	
	
}
