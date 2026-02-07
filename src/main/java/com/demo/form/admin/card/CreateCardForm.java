package com.demo.form.admin.card;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.ibm.icu.impl.duration.DateFormatter;

import oracle.net.aso.e;

public class CreateCardForm extends ActionForm {
    private String username;
    private String cardNumber;
    private String pinCode;
    private String cardType;   
    private String cardStatus;
    private String expiredDate;
    private String balance;
    
    
    // ACTIVE/LOCKED/EXPIRED
 
   

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

ActionErrors errors = new ActionErrors();

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return errors;
        }

        if (isEmpty(username)) {
            errors.add("user", new ActionMessage("error.username.required"));
        }

        if (isEmpty(cardNumber)) {
            errors.add("cardNumber", new ActionMessage("error.card-number.required"));
        } else if (!cardNumber.matches("^\\d{16}$")) {
            errors.add("cardNumber", new ActionMessage("error.card-number.invalid"));
        }

        if (isEmpty(pinCode)) {
            errors.add("pinCode", new ActionMessage("error.pin.required"));
        } else if (!pinCode.matches("^\\d{4}$")) {
            errors.add("pinCode", new ActionMessage("error.pin.invalid"));
        }

        if (isEmpty(cardType)) {
            errors.add("cardType", new ActionMessage("error.card-type.required"));
        } else if (!(cardType.equalsIgnoreCase("DEBIT") || cardType.equalsIgnoreCase("CREDIT"))) {
            errors.add("cardType", new ActionMessage("error.card-type.invalid"));
        }
        if (isEmpty(expiredDate)) {
            errors.add("expiredDate", new ActionMessage("error.expired.required"));
        } else {
            try {
            	LocalDate expire = LocalDate.parse(expiredDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            	if(!expire.isAfter(LocalDate.now())) {
                    errors.add("expiredDate", new ActionMessage("error.expired.future"));
            	}
            } catch (Exception e) {
                errors.add("expiredDate", new ActionMessage("error.expired.format"));
            }
        }

        if (!isEmpty(balance)) {
            try {
            	
                if(new BigDecimal(balance).compareTo(BigDecimal.ZERO) < 0) {
                    errors.add("balance", new ActionMessage("error.balance.positive"));
                };
            } catch (Exception e) {
                errors.add("balance", new ActionMessage("error.balance.invalid"));
            }
        }
        return errors;
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getPinCode() { return pinCode; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }

    public String getCardStatus() { return cardStatus; }
    public void setCardStatus(String cardStatus) { this.cardStatus = cardStatus; }
	public String getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	 public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
}