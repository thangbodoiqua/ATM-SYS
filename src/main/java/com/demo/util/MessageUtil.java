package com.demo.util;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class MessageUtil {
	private ActionErrors errors;
	private ActionMessages messages;

	public void errorMessages(String errorName, String errorContent) {
		if(this.errors == null) errors = new ActionErrors();
		ActionMessage message = new ActionMessage(errorContent);
		errors.add(errorName, message);
	}
	
	public void Messages(String msg, String msgContent) {
		if(this.messages == null) messages = new ActionMessages();
		ActionMessage message = new ActionMessage(msgContent);
		errors.add(msg, message);
	}
	//
	
	public static ActionErrors error(String errorContent) {
		ActionErrors errors = new ActionErrors();
		ActionMessage message = new ActionMessage(errorContent);
		errors.add("error", message);
		
		return errors;
	}	
	
	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	public ActionMessages getMessages() {
		return messages;
	}

	public void setMessages(ActionMessages messages) {
		this.messages = messages;
	}
		
	

	
}
