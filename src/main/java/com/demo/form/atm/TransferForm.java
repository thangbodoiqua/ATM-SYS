package com.demo.form.atm;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class TransferForm extends ActionForm {
	private String amount;
	private String receiverCardNumber;

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			return errors;
		}

		if (receiverCardNumber == null || receiverCardNumber.trim().isEmpty()) {
			errors.add("receiverCardNumber", new ActionMessage("error.card-number.required"));
			return errors;
		}
		if (!receiverCardNumber.matches("^\\d{16}$")) {
			errors.add("receiverCardNumber", new ActionMessage("error.card-number.invalid"));
			return errors;
		}
		if (amount == null || amount.trim().isEmpty()) {
			errors.add("amount", new ActionMessage("error.amount.required"));
			return errors;
		}
		if (!amount.matches("\\d+")) {
			errors.add("amount", new ActionMessage("error.amount.number"));
			return errors;
		}

		if (new BigDecimal(amount).compareTo(BigDecimal.ZERO) <= 0) {
			errors.add("amount", new ActionMessage("error.amount.positive"));
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

	public String getReceiverCardNumber() {
		return receiverCardNumber;
	}

	public void setReceiverCardNumber(String receiverCardNumber) {
		this.receiverCardNumber = receiverCardNumber;
	}
}