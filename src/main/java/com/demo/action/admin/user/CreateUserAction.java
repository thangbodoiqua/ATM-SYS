package com.demo.action.admin.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.*;

import com.demo.dto.UserDTO;
import com.demo.form.admin.user.CreateUserForm;
import com.demo.service.UserService;
import com.demo.service.impl.UserServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

public class CreateUserAction extends Action {

	private final UserService userService = new UserServiceImpl();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if ("GET".equalsIgnoreCase(request.getMethod())) {
			return mapping.findForward("create-user");
		}

		CreateUserForm f = (CreateUserForm) form;

		String username = trim(f.getUsername());
		String password = trim(f.getPassword());
		String email = trim(f.getEmail());
		String phone = trim(f.getPhone());
		String address = trim(f.getAddress());
		String gender = trim(f.getGender());

		String dobStr = trim(f.getDob());
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Date dob = Date.valueOf(LocalDate.parse(dobStr, df));

		UserDTO dto = new UserDTO();
		dto.setUsername(username);
		dto.setPassword(password);
		dto.setEmail(email);
		dto.setPhone(phone);
		dto.setAddress(address);
		dto.setGender(gender);
		dto.setDob(dob);

		boolean ok = userService.create(dto);
		if (!ok) {
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("error.create.username.exist"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		f.setUsername(null);
		f.setEmail(null);
		f.setPassword(null);
		f.setGender(null);
		f.setPhone(null);
		f.setAddress(null);
		f.setDob(null);
		ActionMessages messages = new ActionMessages();
		messages.add("message", new ActionMessage("message.create-user.success"));
		saveMessages(request, messages);
		return mapping.findForward("create-user");
	}

	private static String trim(String s) {
		return s == null ? null : s.trim();
	}

}