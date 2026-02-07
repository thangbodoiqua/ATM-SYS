package com.demo.action;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
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
import com.demo.form.UserHistoryForm;import com.demo.service.CardService;
import com.demo.service.TransactionService;
import com.demo.service.impl.CardServiceImpl;
import com.demo.service.impl.TransactionServiceImpl;

public class UserHistoryAction extends Action {
	TransactionService transService = new TransactionServiceImpl();
	CardService cardService = new CardServiceImpl();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if("GET".equalsIgnoreCase(request.getMethod())) {
			UserHistoryForm f = (UserHistoryForm) form;
			
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute(Constants.AUTH_USER_ID);
			
			if(userId == null) {
				ActionErrors errors = new ActionErrors();
				errors.add("error", new ActionMessage("err.user.session.exist"));
				saveErrors(request,errors);
				return mapping.findForward("history");
			}
			
			List<TransactionDTO> last5Trans = transService.last5UserTrans(userId);
			if(last5Trans == null) {
				f.setTrans(Collections.emptyList());
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
		UserHistoryForm f = (UserHistoryForm) form;
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(Constants.AUTH_USER_ID);
		
		if(userId == null) {
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("err.user.session.exist"));
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
		
		
		List<TransactionDTO> transBw = transService.findUserTransBw(fromTime, toTime, userId);
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
