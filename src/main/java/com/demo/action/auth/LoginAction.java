package com.demo.action.auth;
import javax.servlet.http.*;


import org.apache.struts.action.*;

import com.demo.constant.Constants;
import com.demo.dto.UserDTO;
import com.demo.form.auth.*;
import com.demo.service.impl.UserServiceImpl;
import com.demo.service.UserService;

public class LoginAction extends Action{
	private UserService userService = new UserServiceImpl();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if("GET".equalsIgnoreCase(request.getMethod())) {
			HttpSession existSession = request.getSession(false);
			if(existSession != null && (
					existSession.getAttribute(Constants.AUTH_USER_ID) != null ||
					existSession.getAttribute(Constants.AUTH_USERNAME) != null)) {
				return mapping.findForward("home");
			}
			return mapping.findForward("login");
		}
		
		LoginForm f = (LoginForm) form;
		String username = f.getUsername();
		String password = f.getPassword();
		
		UserDTO user = userService.verify(username, password);
		
		if(user == null) {
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionMessage("error.login.fail"));
			saveErrors(request, errors);
			return mapping.findForward("login");
		}
		
		HttpSession oldSession = request.getSession(false);
		
        if (oldSession != null) {
            oldSession.invalidate();
        }
        
		HttpSession newSession =  request.getSession(true);

		newSession.setAttribute(Constants.AUTH_USER_ID, user.getUserId());
		newSession.setAttribute(Constants.AUTH_USERNAME, user.getUsername());
		newSession.setAttribute(Constants.AUTH_ROLE, user.getRole());
		newSession.setMaxInactiveInterval(30 * 60);
		


		return mapping.findForward("home");
	}

	
}
