package com.demo.action.admin.card;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.*;

import com.demo.constant.Constants;
import com.demo.dto.CardDTO;
import com.demo.dto.UserDTO;
import com.demo.form.admin.card.CreateCardForm;
import com.demo.service.CardService;
import com.demo.service.UserService;
import com.demo.service.impl.CardServiceImpl;
import com.demo.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateCardAction extends Action {

    private CardService cardService = new CardServiceImpl();
    private  UserService userService = new UserServiceImpl();
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {


    	if ("GET".equalsIgnoreCase(request.getMethod())) {
            return mapping.findForward("create-card");
        }

        CreateCardForm f = (CreateCardForm) form;
        String username = f.getUsername().trim();
        String cardNumber = f.getCardNumber().trim();
        String pin = f.getPinCode().trim();
        String cardType = f.getCardType().trim();
        String cardStatus = Constants.CARD_STATUS_ACTIVE;
        
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Date expiredDate = Date.valueOf(LocalDate.parse(f.getExpiredDate(), df));
		
		UserDTO user = userService.findByUsername(username);
        
		if(user == null) {
        	ActionErrors errors = new ActionErrors();
        	errors.add("error", new ActionMessage("error.username.not-found"));
        	saveErrors(request, errors);
            return mapping.getInputForward();
        }
		
		CardDTO cardExist = cardService.findByCardNumber(cardNumber);
        if(cardExist != null) {
        	ActionErrors errors = new ActionErrors();
        	errors.add("error", new ActionMessage("error.create-card.existed"));
        	saveErrors(request, errors);
            return mapping.getInputForward();
        }
        
        BigDecimal balance = null;
        balance = new BigDecimal(f.getBalance().trim());
       
        CardDTO dto = new CardDTO();
        dto.setUserId(user.getUserId());
        dto.setCardNumber(cardNumber);
        dto.setPinCode(pin);
        dto.setCardType(cardType);
        dto.setCardStatus(cardStatus);
        dto.setExpiredDate(expiredDate);
        dto.setBalance(balance);
        
        boolean ok = cardService.createCard(dto);

        if (!ok) {
        	ActionErrors errors = new ActionErrors();
        	errors.add("error", new ActionMessage("error.create-card.fail"));
        	saveErrors(request, errors);
            return mapping.getInputForward();
        }

        ActionMessages messages = new ActionMessages();
        messages.add("message", new ActionMessage("message.create-card.success"));
        saveMessages(request, messages);

        f.setUsername(null);
        f.setCardNumber(null);
        f.setPinCode(null);
        f.setCardType(null);
        f.setExpiredDate(null);
        f.setBalance(null);

        return mapping.findForward("create-card");
    }
}