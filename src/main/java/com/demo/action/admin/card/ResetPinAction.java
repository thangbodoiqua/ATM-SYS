package com.demo.action.admin.card;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.demo.dto.CardDTO;
import com.demo.dto.UserDTO;
import com.demo.form.admin.card.ResetPinForm;
import com.demo.form.admin.user.ResetPasswordForm;
import com.demo.service.CardService;
import com.demo.service.impl.CardServiceImpl;
import com.demo.util.MessageUtil;

public class ResetPinAction extends Action{
	CardService cardService = new CardServiceImpl();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			String cardNumber = request.getParameter("cardId");
			if (cardNumber == null) {
				return mapping.getInputForward();
			}
			ResetPinForm f = (ResetPinForm) form;
			
			f.setCardNumber(cardNumber);
			return mapping.getInputForward();
		}
		ResetPinForm f = (ResetPinForm) form;
		CardDTO card = cardService.findByCardNumber(f.getCardNumber());
		
		if (card == null) {
			saveErrors(request, MessageUtil.error("error.card_number.not_found"));
			return mapping.getInputForward();
		}
		
		if(!card.getPinCode().equals(f.getOldPin())) {
			/*ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("error.pin.incorrect"));
			saveErrors(request, errors);*/
			
			saveErrors(request, MessageUtil.error("error.pin.incorrect"));

			return mapping.getInputForward();
		}
		boolean ok = cardService.changePin(card.getCardNumber(), f.getNewPin());
		
		if (!ok) {
			ActionErrors errs = new ActionErrors();
			errs.add("error", new ActionMessage("error.change-pin.fail"));
			saveErrors(request, errs);
			return mapping.getInputForward();
		}
		ActionMessages msgs = new ActionMessages();
		msgs.add(Globals.MESSAGE_KEY, new ActionMessage("message.change-pin.success"));
		saveMessages(request.getSession(), msgs);
		return mapping.findForward("card-report");
			}
	

}
