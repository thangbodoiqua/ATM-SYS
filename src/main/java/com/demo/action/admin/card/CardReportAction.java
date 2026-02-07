package com.demo.action.admin.card;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.demo.dto.CardDTO;
import com.demo.dto.UserDTO;
import com.demo.form.admin.card.CardReportForm;
import com.demo.form.admin.user.UserReportForm;
import com.demo.service.CardService;
import com.demo.service.impl.CardServiceImpl;

public class CardReportAction extends Action {
	CardService cardService = new CardServiceImpl();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages msgs = (ActionMessages) request.getSession().getAttribute(Globals.MESSAGE_KEY);
	    if (msgs != null) {
	        saveMessages(request, msgs);
	        request.getSession().removeAttribute(Globals.MESSAGE_KEY);
	    }

	CardReportForm f =(CardReportForm) form;
	List<CardDTO> cards = cardService.findAll();
	
	if(cards == null) {
		f.setCards(Collections.emptyList());
	}else{
		f.setCards(cards);

	}
	
	return mapping.findForward("card");
	}

}
