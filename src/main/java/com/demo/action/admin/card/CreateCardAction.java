package com.demo.action.admin.card;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.*;

import com.demo.dto.CardDTO;
import com.demo.form.admin.card.CreateCardForm;
import com.demo.service.CardService;
import com.demo.service.impl.CardServiceImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class CreateCardAction extends Action {

    private final CardService cardService = new CardServiceImpl();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return mapping.findForward("create-card");
        }

        CreateCardForm f = (CreateCardForm) form;

        String userIdStr  = trim(f.getUserId());
        String cardNumber = trim(f.getCardNumber());
        String pin        = trim(f.getPinCode());
        String cardType   = trim(f.getCardType());
        String cardStatus = trim(f.getCardStatus());
        String expiredStr = trim(f.getExpiredDate());
        String balanceStr = trim(f.getBalance());

        boolean hasError = false;



		Long userId = null;
		
		if (isEmpty(userIdStr)) {
		    request.setAttribute("err_userId", "User ID is required");
		    hasError = true;
		
		} else {
		    try {
		        userId = Long.parseLong(userIdStr);
		
		        if (userId <= 0) {
		            request.setAttribute("err_userId", "User ID must be positive");
		            hasError = true;
		        }
		    } catch (NumberFormatException e) {
		        request.setAttribute("err_userId", "User ID must be a valid number");
		        hasError = true;
		    }
		}


        if (isEmpty(cardNumber)) {
            request.setAttribute("err_cardNumber", "Card number is required");
            hasError = true;

        } else if (!cardNumber.matches("^\\d{16}$")) {
            request.setAttribute("err_cardNumber", "Card number must be exactly 16 digits");
            hasError = true;
        }


        if (isEmpty(pin)) {
            request.setAttribute("err_pin", "PIN is required");
            hasError = true;

        } else if (!pin.matches("^\\d{4}$")) {
            request.setAttribute("err_pin", "PIN must be 4 digits");
            hasError = true;
        }


        if (isEmpty(cardType)) {
            request.setAttribute("err_cardType", "Card type is required");
            hasError = true;

        } else if (!("DEBIT".equalsIgnoreCase(cardType) || "CREDIT".equalsIgnoreCase(cardType))) {
            request.setAttribute("err_cardType", "Card type must be DEBIT or CREDIT");
            hasError = true;
        }


        Date expiredDate = null;

        if (isEmpty(expiredStr)) {
            request.setAttribute("err_expiredDate", "Expired date is required");
            hasError = true;

        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);

            try {
                java.util.Date util = sdf.parse(expiredStr);
                expiredDate = new Date(util.getTime());

                if (expiredDate.toLocalDate().isBefore(LocalDate.now())) {
                    cardStatus = "EXPIRED";
                }

            } catch (ParseException e) {
                request.setAttribute("err_expiredDate", "Expired date must be yyyy-MM-dd");
                hasError = true;
            }
        }


        if (isEmpty(cardStatus)) {
            cardStatus = "ACTIVE"; 
        }

        if (!("ACTIVE".equalsIgnoreCase(cardStatus)
                || "LOCKED".equalsIgnoreCase(cardStatus)
                || "EXPIRED".equalsIgnoreCase(cardStatus))) {

            request.setAttribute("err_cardStatus", "Status must be ACTIVE, LOCKED or EXPIRED");
            hasError = true;
        }


		BigDecimal balance = null;
		
		if (!isEmpty(balanceStr)) {
		    try {
		        balance = new BigDecimal(balanceStr.trim());
		    } catch (Exception e) {
		        request.setAttribute("err_balance", "Balance must be a valid number");
		        hasError = true;
		    }
		}
		

        if (hasError) {
            request.setAttribute("userId", userId);
            request.setAttribute("cardNumber", cardNumber);
            request.setAttribute("pinCode", pin);
            request.setAttribute("cardType", cardType);
            request.setAttribute("cardStatus", cardStatus);
            request.setAttribute("expiredDate", expiredDate);
            request.setAttribute("balance", balance);
            return mapping.getInputForward();
        }

        // -------------------------
        // BUILD DTO + CREATE CARD
        // -------------------------
        CardDTO dto = new CardDTO();
        dto.setUserId(userId);
        dto.setCardNumber(cardNumber);
        dto.setPinCode(pin);
        dto.setCardType(cardType);
        dto.setCardStatus(cardStatus);
        dto.setExpiredDate(expiredDate);
        dto.setBalance(balance);

        boolean created = cardService.createCard(dto);

        if (!created) {
            request.setAttribute("error", "Cannot create card. Possibly duplicate card number.");
            return mapping.getInputForward();
        }

        // RESET FORM
        f.setUserId(null);
        f.setCardNumber(null);
        f.setPinCode(null);
        f.setCardType(null);
        f.setCardStatus(null);
        f.setExpiredDate(null);
        f.setBalance(null);

        request.setAttribute("message", "Card created successfully");
        return mapping.findForward("create-card");
    }


    private static String trim(String s) {
        return s == null ? null : s.trim();
    }
    private static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}