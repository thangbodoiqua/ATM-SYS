package com.demo.form.auth;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.*;

public class LoginForm extends ActionForm {

	private String username;
	private String password;

	public LoginForm() {
		super();
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			return errors;
		}

		if (username == null || username.trim().isEmpty()) {
			errors.add("error", new ActionMessage("error.username.required"));
			return errors;
		}

		if (password == null || password.trim().isEmpty()) {
			errors.add("error", new ActionMessage("error.password.required"));
			return errors;
		}

		return errors;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
