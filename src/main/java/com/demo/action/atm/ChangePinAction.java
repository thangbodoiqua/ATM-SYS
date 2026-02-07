package com.demo.action.atm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.*;

import com.demo.constant.Constants;
import com.demo.dto.CardDTO;
import com.demo.form.atm.ChangePinForm;
import com.demo.service.CardService;
import com.demo.service.impl.CardServiceImpl;

public class ChangePinAction extends Action {
	CardService cardService = new CardServiceImpl();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if("GET".equalsIgnoreCase(request.getMethod())) {
			return mapping.findForward("change-pin");
		}
		ChangePinForm f = (ChangePinForm)form;
		String inputPin = f.getOldPin();
		String newPin = f.getNewPin();
		
		HttpSession session = request.getSession();
		String pin = (String )session.getAttribute(Constants.ATM_CARD_PIN);
		String cardNumber = (String) session.getAttribute(Constants.ATM_CARD_NUMBER);
		
		if(!inputPin.equals(pin)){
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("error.pin.incorrect"));
			saveErrors(request,errors);
			return mapping.findForward("change-pin");
		}
		
		CardDTO card = cardService.findByCardNumber(cardNumber);
		
		if(card == null) {
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("error.card_number.not_found"));
			saveErrors(request,errors);
			return mapping.findForward("change-pin");
		}
		
		boolean success = cardService.changePin(cardNumber, newPin);
		
		if(!success) {
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("error.change-pin.fail"));
			saveErrors(request,errors);
			return mapping.findForward("change-pin");
		}
		
		
		ActionMessages messages = new ActionMessages();
		messages.add(Globals.MESSAGE_KEY, new ActionMessage("message.change-pin.success"));
		saveMessages(session, messages);
		
		session.removeAttribute(Constants.ATM_CARD_PIN);
		return mapping.findForward("success");
		}

}
