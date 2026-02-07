package com.demo.action.atm;

import javax.servlet.http.*;

import org.apache.struts.Globals;
import org.apache.struts.action.*;

import com.demo.constant.Constants;
import com.demo.form.atm.EnterPinForm;
import com.demo.form.auth.*;
import com.demo.service.CardService;
import com.demo.service.impl.CardServiceImpl;

public class EnterPinAction extends Action {
	CardService cardService = new CardServiceImpl();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if("GET".equalsIgnoreCase(request.getMethod())) {
			ActionMessages msgs = (ActionMessages) request.getSession().getAttribute(Globals.MESSAGE_KEY);
		    if (msgs != null) {
		        saveMessages(request, msgs);
		        request.getSession().removeAttribute(Globals.MESSAGE_KEY);
		    }
			return mapping.findForward("enter-pin");
		}
		
		EnterPinForm f = (EnterPinForm ) form;
		String pin = f.getPin();
		
		if(pin == null || pin.trim().isEmpty()) {
			request.setAttribute("error", "please enter pin");
			return mapping.getInputForward();
		}
		
		if(!pin.matches("[0-9]+")){
			request.setAttribute("error", "pin only contains digits");
			return mapping.getInputForward();
		}
		
		if(!pin.matches("\\d{4}$")){
			request.setAttribute("error", "Pin must be 4 digits");
			return mapping.getInputForward();
		}
		
		HttpSession session = request.getSession(true);
        String cardNumber = (String) session.getAttribute(Constants.ATM_CARD_NUMBER);
        boolean isValidPin = cardService.verifyPin(cardNumber, pin);

        if(!isValidPin) {
        	request.setAttribute("error", "Invalid pin please try again");
			return mapping.getInputForward();
        }
		
        session.setAttribute(Constants.ATM_CARD_PIN, pin);
		return mapping.findForward("menu");
	}
}
