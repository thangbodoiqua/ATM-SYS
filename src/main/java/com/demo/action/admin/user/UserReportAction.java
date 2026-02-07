package com.demo.action.admin.user;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.demo.dto.UserDTO;
import com.demo.form.admin.user.UserReportForm;
import com.demo.service.UserService;
import com.demo.service.impl.UserServiceImpl;

public class UserReportAction extends Action {
	UserService userService = new UserServiceImpl();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages msgs = (ActionMessages) request.getSession().getAttribute(Globals.MESSAGE_KEY);
		if (msgs != null) {
			saveMessages(request, msgs);
			request.getSession().removeAttribute(Globals.MESSAGE_KEY);
		}

		UserReportForm f = (UserReportForm) form;
		List<UserDTO> users = userService.findAll();

		if (users == null) {
			f.setUsers(Collections.emptyList());
		} else {
			f.setUsers(users);
		}

		return mapping.findForward("user");
	}

}
