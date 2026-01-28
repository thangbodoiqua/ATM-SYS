package com.demo.form;

import org.apache.struts.action.*;

public class AdminForm extends ActionForm {
	private String username;

	public AdminForm() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
