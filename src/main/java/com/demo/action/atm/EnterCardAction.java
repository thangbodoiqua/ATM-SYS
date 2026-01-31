package com.demo.action.atm;

import javax.servlet.http.*;

import org.apache.struts.action.*;

import com.demo.constant.Constants;
import com.demo.form.atm.EnterCardForm;
import com.demo.form.auth.*;
import com.demo.service.CardService;
import com.demo.service.impl.CardServiceImpl;

public class EnterCardAction extends Action {
	CardService cardService = new CardServiceImpl();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	if("GET".equalsIgnoreCase(request.getMethod())){
    		return mapping.findForward("enter-card");
    	}
    	
        EnterCardForm f = (EnterCardForm) form;
        HttpSession session = request.getSession(false);
        
        Long userId = (Long) session.getAttribute(Constants.AUTH_USER_ID);
        String cardNumber = f.getCardNumber();

        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            request.setAttribute("error", "please enter card number");
            return mapping.getInputForward();
        }
        
        if(!cardNumber.matches("[0-9]+")) {
        	request.setAttribute("error", "Card number only contains numbers");
            return mapping.getInputForward();
        }
        
        if (!cardNumber.matches("\\d{16}$")) { 
        	
            request.setAttribute("error", "Card number must be exactly 16 digits");
            return mapping.getInputForward();
        }
        
        
        
        boolean isValidCardNumber = cardService.validCardNumber(userId, cardNumber);
        
        
        
        if(!isValidCardNumber){
        	request.setAttribute("error", "Card not exist");
        	return mapping.getInputForward();
        }

        session.setAttribute(Constants.ATM_CARD_NUMBER, cardNumber);   
        return mapping.findForward("enter-pin");
    }
}
