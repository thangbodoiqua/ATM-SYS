package com.demo.action.admin.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.demo.dto.UserDTO;
import com.demo.form.admin.user.ResetPasswordForm;
import com.demo.service.UserService;
import com.demo.service.impl.UserServiceImpl;

public class ResetPasswordAction extends Action {
	private final UserService userService = new UserServiceImpl();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			String userParam = request.getParameter("username");
			if (userParam == null) {
				return mapping.getInputForward();
			}
			ResetPasswordForm f = (ResetPasswordForm) form;
			f.setUsername(userParam);
			return mapping.getInputForward();
		}
		ResetPasswordForm f = (ResetPasswordForm) form;
		UserDTO user = userService.findByUsername(f.getUsername());
		if (user == null) {
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("error.username.not-found"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
		boolean ok = userService.updatePassword(f.getUsername(), f.getOldPassword(), f.getNewPassword());
		if (!ok) {
			ActionErrors errs = new ActionErrors();
			errs.add("error", new ActionMessage("error.user.reset-pass.fail"));
			saveErrors(request, errs);
			return mapping.getInputForward();
		}
		ActionMessages msgs = new ActionMessages();
		msgs.add(Globals.MESSAGE_KEY, new ActionMessage("message.user.reset-pass.success"));
		saveMessages(request.getSession(), msgs);
		return mapping.findForward("user-report");
	}

}
