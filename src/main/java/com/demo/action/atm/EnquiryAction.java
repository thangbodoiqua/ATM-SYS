package com.demo.action.atm;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.demo.constant.Constants;
import com.demo.dto.CardDTO;
import com.demo.service.CardService;
import com.demo.service.impl.CardServiceImpl;

public class EnquiryAction extends Action {
	CardService cardService = new CardServiceImpl();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String cardNumber = (String) session.getAttribute(Constants.ATM_CARD_NUMBER);
		CardDTO card = cardService.findByCardNumber(cardNumber);
		
		if(card == null) {
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("error.card_number.not_found"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		BigDecimal balance = card.getBalance();
		if(balance != null) {
			request.setAttribute("balance", balance);
		}
		return mapping.findForward("menu");
	}
	
	
}
