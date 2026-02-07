package com.demo.form.atm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.demo.dto.TransactionDTO;

import oracle.sql.DATE;

public class CardHistoryForm extends ActionForm{
	private String from;
	private String to;
	private List<TransactionDTO> trans = Collections.emptyList();
	
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
		if("GET".equalsIgnoreCase(request.getMethod())) {
			return errors;
		}
		
		if(from == null || from.trim().isEmpty() || to == null || to.trim().isEmpty()) {
			errors.add("error", new ActionMessage("error.date.required"));
			return errors;
		}

	    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate fromDate, toDate;
	    try {
	    	fromDate = LocalDate.parse(from, df);
	    	toDate = LocalDate.parse(to, df);
	    }catch(Exception e) {
	    	errors.add("error", new ActionMessage("error.date.format.invalid"));
	    	return errors;
	    }
	    
		if(!fromDate.isBefore(toDate) && !fromDate.isEqual(toDate)) {
			errors.add("error", new ActionMessage("error.date.from.future"));
			return errors;
		}
		
		LocalDate today = LocalDate.now();
		if(toDate.isAfter(today)) {
			errors.add("error", new ActionMessage("error.date.from.before"));
			return errors;
		}
		
		return errors;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public List<TransactionDTO> getTrans() {
		return trans;
	}
	public void setTrans(List<TransactionDTO> trans) {
		this.trans = trans;
	}
}
