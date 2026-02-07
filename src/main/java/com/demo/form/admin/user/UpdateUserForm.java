package com.demo.form.admin.user;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

public class UpdateUserForm extends ActionForm {
	private String username;
	private String email;
	private String phone;
	private String address;

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if ("GET".equalsIgnoreCase(request.getMethod())) {
			return errors;
		}

		if (isEmpty(username)) {
			errors.add("error", new ActionMessage("error.username.missing"));
		}

		if (isEmpty(email)) {
			errors.add("email", new ActionMessage("error.email.required"));

		} else if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
			errors.add("email", new ActionMessage("error.email.invalid"));
		}

		if (isEmpty(phone)) {
			errors.add("phone", new ActionMessage("error.phone.required"));

		} else if (!phone.matches("^[0-9+\\-()\\s]{8,20}$")) {
			errors.add("phone", new ActionMessage("error.phone.invalid"));
		}

		if (isEmpty(address)) {
			errors.add("address", new ActionMessage("error.address.required"));
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}