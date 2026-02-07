package com.demo.form.atm;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.demo.dto.TransactionDTO;

public class SuccessTransactionForm extends ActionForm {
	private TransactionDTO trans;

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.validate(mapping, request);
	}

	public TransactionDTO getTrans() {
		return trans;
	}

	public void setTrans(TransactionDTO trans) {
		this.trans = trans;
	}
	
}
