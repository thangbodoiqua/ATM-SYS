package com.demo.action.atm;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.*;

import com.demo.constant.Constants;
import com.demo.dto.CardDTO;
import com.demo.dto.TransactionDTO;
import com.demo.form.atm.DepositForm;
import com.demo.form.atm.WithdrawForm;
import com.demo.service.CardService;
import com.demo.service.TransactionService;
import com.demo.service.impl.CardServiceImpl;
import com.demo.service.impl.TransactionServiceImpl;

public class DepositAction extends Action{
	CardService cardService = new CardServiceImpl();
	TransactionService transDAO = new TransactionServiceImpl();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if ("GET".equalsIgnoreCase(request.getMethod())) {
            return mapping.findForward("deposit");
        }
		
		DepositForm f = (DepositForm) form;
		BigDecimal amount = new BigDecimal(f.getAmount().trim());
		
		HttpSession session = request.getSession();
		String cardNumber = (String) session.getAttribute(Constants.ATM_CARD_NUMBER);
		CardDTO card = cardService.findByCardNumber(cardNumber);
		if(card == null) {
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("error.card_number.not_found"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		
		/*
		 * Long cardId = cardService.findCardIdByCardNumber(cardNumber);
		 * 
		 * if(cardId == null) { ActionErrors errors = new ActionErrors();
		 * errors.add("error", new ActionMessage("error.card_number.not_found"));
		 * saveErrors(request, errors); return mapping.getInputForward(); }
		 */
		
		TransactionDTO trans = new TransactionDTO();
		trans.setAtmId(Long.valueOf(4));
		trans.setCardId(card.getCardId());
		trans.setTransAmount(amount);
		trans.setTransType(Constants.TRANS_TYPE_DEPOSIT);
		
		boolean success = transDAO.createTransaction(trans);
		
		if(!success) {
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("error.trans.fail"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		

		ActionMessages messages = new ActionMessages();
		messages.add(Globals.MESSAGE_KEY, new ActionMessage("trans.success"));
		saveMessages(session, messages);
		f.setAmount(null);
		
		session.removeAttribute(Constants.ATM_CARD_PIN);
		return mapping.findForward("success");
		
	}

	
}
