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
import com.demo.form.atm.TransferForm;
import com.demo.service.CardService;
import com.demo.service.TransactionService;
import com.demo.service.impl.CardServiceImpl;
import com.demo.service.impl.TransactionServiceImpl;
import com.demo.util.MessageUtil;

public class TransferAction extends Action {
	CardService cardService = new CardServiceImpl();
	TransactionService transService = new TransactionServiceImpl();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if ("GET".equalsIgnoreCase(request.getMethod())) {
			return mapping.findForward("transfer");
		}

		TransferForm f = (TransferForm) form;
		String receiverCardNumber = f.getReceiverCardNumber().trim();
		String amountStr = f.getAmount().trim();

		BigDecimal amount = null;

		if (amountStr != null) {
			try {
				amount = new BigDecimal(amountStr);
			} catch (Exception e) {
				ActionErrors errors = new ActionErrors();
				errors.add("error", new ActionMessage("error.amount.number"));
				saveErrors(request, errors);
				return mapping.getInputForward();
			}
		}

		HttpSession session = request.getSession(false);
		String cardNumber = session != null ? (String) session.getAttribute(Constants.ATM_CARD_NUMBER) : null;
		
		if(cardNumber != null && receiverCardNumber.equals(cardNumber)) {
			ActionErrors errors = new ActionErrors();
			errors.add("receiverCardNumber", new ActionMessage("error.receiver-card.diff"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		
		CardDTO receiverCard= cardService.findByCardNumber(receiverCardNumber);
		if(receiverCard == null) {
			saveErrors(request, MessageUtil.error("error.card_number.not_found"));
			return mapping.getInputForward();
		}
		if (receiverCard.getCardStatus().equalsIgnoreCase(Constants.CARD_STATUS_EXPIRED) || receiverCard.getCardStatus().equalsIgnoreCase(Constants.CARD_STATUS_BLOCKED)) {
			ActionErrors errors = new ActionErrors();
			errors.add("receiverCardNumber", new ActionMessage("error.card.inactive"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		CardDTO card = cardService.findByCardNumber(cardNumber);
		if (card == null) {
			ActionErrors errors = new ActionErrors();
			errors.add("receiverCardNumber", new ActionMessage("error.card_number.not_found"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		
		if (card.getBalance().compareTo(amount) < 0) {
			ActionErrors errors = new ActionErrors();
			errors.add("receiverCardNumber", new ActionMessage("error.balance.enough"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		
		/*
		 * Long cardId = cardService.findCardIdByCardNumber(cardNumber); if
		 * (receiverCardId == null) { ActionErrors errors = new ActionErrors();
		 * errors.add("error", new ActionMessage("error.card_number.not_found"));
		 * saveErrors(request, errors); return mapping.getInputForward(); }
		 */
		TransactionDTO trans = new TransactionDTO();
		trans.setAtmId(Long.valueOf(4));
		trans.setCardId(card.getCardId());
		trans.setTransType(Constants.TRANS_TYPE_WITHDRAW);
		trans.setTransAmount(amount);
		trans.setRefTransId(receiverCard.getCardId());

		boolean success = transService.createTransaction(trans);

		if (!success) {
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("error.trans.fail"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		ActionMessages messages = new ActionMessages();
		messages.add(Globals.MESSAGE_KEY, new ActionMessage("trans.success"));
		saveMessages(session, messages);

		session.removeAttribute(Constants.ATM_CARD_PIN);
		return mapping.findForward("success");
	}

}
