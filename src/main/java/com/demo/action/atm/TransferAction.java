package com.demo.action.atm;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.*;

import com.demo.constant.Constants;
import com.demo.dto.TransactionDTO;
import com.demo.form.atm.TransferForm;
import com.demo.service.CardService;
import com.demo.service.impl.CardServiceImpl;

public class TransferAction extends Action {
	CardService cardService = new CardServiceImpl();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if ("GET".equalsIgnoreCase(request.getMethod())) {
            return mapping.findForward("transfer");
        }
		
		TransferForm f = (TransferForm) form;
		String receiverCardNumber = f.getReceiverCardNumber().trim();
		String amountStr = f.getAmount().trim();
		
		if(amountStr == null || receiverCardNumber == null) {
			request.setAttribute("error", "Both receiver card number and amount is required");
			return mapping.getInputForward();
		}
		 
		BigDecimal amount = null;
		
		if (amountStr != null) {
		    try {
		    	amount = new BigDecimal(amountStr);
		    } catch (Exception e) {
		        request.setAttribute("error", "amount must be a valid number");
				return mapping.getInputForward();
		    }
		}
		
		HttpSession session = request.getSession(false);
		String cardNumber = session!=null?(String) session.getAttribute(Constants.ATM_CARD_NUMBER): null;
		
		Long cardId = cardService.findCardIdByCardNumber(cardNumber);
		Long receiverCardId = cardService.findCardIdByCardNumber(receiverCardNumber);
		if(receiverCardId == null) {
			request.setAttribute("error", "Cannot find receiver card number");
			return mapping.getInputForward();
		}
		
		TransactionDTO trans = new TransactionDTO();
		trans.setAtmId(4L);
		trans.setCardId(cardId);
		trans.setTransType(Constants.TRANS_TYPE_WITHDRAW);
		trans.setTransAmount(amount);
		trans.setRefTransId(receiverCardId);
		
		boolean success = cardService.createTransaction(trans);
		if(!success) {
			request.setAttribute("error", "Failed transaction");
			return mapping.getInputForward();
		}
		// xóa pin trong session ở đây
		return mapping.findForward("success");
	}



}
