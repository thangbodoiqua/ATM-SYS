package com.demo.form.admin.user;

import java.util.Collections;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.demo.dto.UserDTO;

public class UserReportForm extends ActionForm {
	List<UserDTO> users = Collections.emptyList();

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}
	
	
	
}
