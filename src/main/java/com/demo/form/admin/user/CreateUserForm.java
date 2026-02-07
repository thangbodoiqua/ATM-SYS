package com.demo.form.admin.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.*;

public class CreateUserForm extends ActionForm {

	private String username;
	private String password;
	private String email;
	private String phone;
	private String address;
	private String gender;
	private String dob;

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if ("GET".equalsIgnoreCase(request.getMethod())) {
			return errors;
		}

		if (isEmpty(username)) {
			errors.add("username", new ActionMessage("error.username.required"));
		} else if (!username.matches("^[A-Za-z][A-Za-z0-9_]{8,19}$")) {
			errors.add("username", new ActionMessage("error.username.invalid"));
		}

		if (isEmpty(password)) {
			errors.add("password", new ActionMessage("error.password.required"));
		} else if (!password.matches("^\\S{8,19}$")) {
			errors.add("password", new ActionMessage("error.password.invalid"));
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

		if (isEmpty(gender)) {
			errors.add("gender", new ActionMessage("error.gender.required"));
		}

		if (isEmpty(dob)) {
			errors.add("dob", new ActionMessage("error.dob.required"));
		} else {
			try {
				LocalDate dobDate = LocalDate.parse(dob, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				LocalDate validAge = LocalDate.now().minusYears(18);

				if (dobDate.isAfter(validAge)) {
					errors.add("dob", new ActionMessage("error.dob.valid-age"));
				}
			} catch (Exception e) {
				errors.add("dob", new ActionMessage("error.dob.format"));
			}
		}

		return errors;
	}

	private boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	public CreateUserForm() {
		super();
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

}
