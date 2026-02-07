package com.demo.form.atm;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class WithdrawForm extends ActionForm {
	private String amount;

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
		if ("GET".equalsIgnoreCase(request.getMethod())) {
            return errors;
        }		
		
		if(amount == null || amount.trim().isEmpty()) {
			errors.add("error", new ActionMessage("error.amount.required"));
			return errors;
		}
		
		if(!amount.matches("\\d+")) {
			errors.add("error", new ActionMessage("error.amount.number"));
			return errors;
		}
		
		if(new BigDecimal(amount).compareTo(BigDecimal.ZERO) <= 0) {
			errors.add("error", new ActionMessage("error.amount.positive"));
			return errors;
		}
		
		return errors;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	} 
	
}
