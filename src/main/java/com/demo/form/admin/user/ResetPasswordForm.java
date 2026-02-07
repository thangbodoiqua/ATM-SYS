package com.demo.form.admin.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ResetPasswordForm extends ActionForm {
	private String username;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;

	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
		if("GET".equalsIgnoreCase(request.getMethod())) {
			return errors;
		}
		
		if (isEmpty(username)) {
			errors.add("error", new ActionMessage("error.username.required"));
		}
		
		if (isEmpty(oldPassword)) {
			errors.add("oldPassword", new ActionMessage("error.password.required"));
		} else if (!oldPassword.matches("^\\S{8,19}$")) {
			errors.add("oldPassword", new ActionMessage("error.password.invalid"));
		}
		
		if (isEmpty(newPassword)) {
			errors.add("newPassword", new ActionMessage("error.password.required"));
		} else if (!newPassword.matches("^\\S{8,19}$")) {
			errors.add("newPassword", new ActionMessage("error.password.invalid"));
		} else if(newPassword.equals(oldPassword)) {
			errors.add("newPassword", new ActionMessage("error.new-pass.equal"));
		}
		
		if (isEmpty(confirmPassword)) {
			errors.add("confirmPassword", new ActionMessage("error.password.required"));
		} else if (!confirmPassword.equals(newPassword)) {
			errors.add("confirmPassword", new ActionMessage("error.password.invalid"));
		}
		
		return errors;
	}
	private boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
	    this.oldPassword = oldPassword; 
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
}
