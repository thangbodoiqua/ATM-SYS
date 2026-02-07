package com.demo.action.atm;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.demo.constant.Constants;
import com.demo.dto.CardDTO;
import com.demo.dto.TransactionDTO;
import com.demo.form.atm.CardHistoryForm;
import com.demo.service.CardService;
import com.demo.service.TransactionService;
import com.demo.service.impl.CardServiceImpl;
import com.demo.service.impl.TransactionServiceImpl;

public class CardHistoryAction extends Action {
	TransactionService transService = new TransactionServiceImpl();
	CardService cardService = new CardServiceImpl();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if("GET".equalsIgnoreCase(request.getMethod())) {
			CardHistoryForm f = (CardHistoryForm) form;	
			HttpSession session = request.getSession();
			String cardNum = (String) session.getAttribute(Constants.ATM_CARD_NUMBER);
			
			if(cardNum == null) {
				ActionErrors errors = new ActionErrors();
				errors.add("error", new ActionMessage("err.card_number.session.exist"));
				saveErrors(request,errors);
				return mapping.findForward("history");
			}
			
			Long cardId = cardService.findCardIdByCardNumber(cardNum);
			if(cardId == null) {
				ActionErrors errors = new ActionErrors();
				errors.add("error", new ActionMessage("error.card-number.not_found"));
				saveErrors(request,errors);
				return mapping.findForward("history");
			}
			
			
			List<TransactionDTO> last5Trans = transService.last5CardTrans(cardId);
			if(last5Trans == null) {
				last5Trans = Collections.emptyList();
			}else {
				last5Trans.forEach(t -> {
					CardDTO card = cardService.findByCardId(t.getCardId());
					if(card == null) {
						return;
					}
					t.setRefCardNumber(card.getCardNumber());
				});	
				f.setTrans(last5Trans);
			}
			return mapping.findForward("history");
		}
		
		CardHistoryForm f = (CardHistoryForm) form;
		HttpSession session = request.getSession();
		String cardNumber = (String) session.getAttribute(Constants.ATM_CARD_NUMBER);
		
		if(cardNumber == null) {
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("err.card-num.session.exist"));
			saveErrors(request,errors);
			return mapping.findForward("history");
		} 
		
		Long cardId = cardService.findCardIdByCardNumber(cardNumber);
		
		if(cardId == null) {
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("error.card-number.not_found"));
			saveErrors(request,errors);
			return mapping.findForward("history");
		}
		
		String from = f.getFrom();
		String to = f.getTo();
		List<TransactionDTO> trans = f.getTrans();
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Timestamp fromTime, toTime;
		
		LocalDate fromDate = LocalDate.parse(from, df); //Lấy Date from
		fromTime = Timestamp.valueOf(fromDate.atStartOfDay()); // parse đầu ngày hôm nay thành Timestamp
		
		LocalDate toDate = LocalDate.parse(to, df); //Lấy Date to
		toTime = Timestamp.valueOf(toDate.atTime(23, 59, 59)); //parse cuối ngày to
		
		
		List<TransactionDTO> transBw = transService.findCardTransBw(fromTime, toTime, cardId);
		if(transBw == null) {
			f.setTrans(Collections.emptyList());
			ActionMessages messages = new ActionMessages();
			messages.add("message", new ActionMessage("message.trans.report.not-found"));
			saveMessages(request, messages);
			return mapping.findForward("history");
		}
		transBw.forEach(t -> {
			CardDTO card = cardService.findByCardId(t.getCardId());
			if(card == null) {
				return;
			}
			t.setRefCardNumber(card.getCardNumber());
		});	
		f.setTrans(transBw);
		return mapping.findForward("history");
	}

	
}
