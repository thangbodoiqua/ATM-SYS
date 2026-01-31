package com.demo.action.auth;
import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;

import com.demo.constant.Constants;
import com.demo.dto.UserDTO;
import com.demo.form.auth.*;
import com.demo.service.impl.UserServiceImpl;
import com.demo.service.UserService;

public class LoginAction extends Action{
	private static final Logger log = Logger.getLogger(LoginAction.class);
	private UserService userService = new UserServiceImpl();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if("GET".equalsIgnoreCase(request.getMethod())) {
			HttpSession existSession = request.getSession(false);
			if(existSession != null && (
					existSession.getAttribute(Constants.AUTH_USER_ID) != null ||
					existSession.getAttribute(Constants.AUTH_USERNAME) != null)) {
				log.debug("[LoginAction] User already logged in, redirecting to home");
				return mapping.findForward("home");
			}
			log.debug("[LoginAction] Displaying login page");
			return mapping.findForward("login");
		}
		
		LoginForm f = (LoginForm) form;
		String username = f.getUsername();
		String password = f.getPassword();
		
		log.info("[LoginAction] Login attempt for username: " + username);
		
		UserDTO user = userService.login(username, password);
		
		if(user == null) {
			log.warn("[LoginAction] Login failed for username: " + username + " - Invalid credentials");
			request.setAttribute("error", "username or password is incorrect");
			return mapping.getInputForward();
		}
		
		HttpSession oldSession = request.getSession(false);
		
        if (oldSession != null) {
        	log.debug("[LoginAction] Invalidating old session for user: " + username);
            oldSession.invalidate();
        }
        
		HttpSession newSession =  request.getSession(true);

		newSession.setAttribute(Constants.AUTH_USER_ID, user.getUserId());
		newSession.setAttribute(Constants.AUTH_USERNAME, user.getUsername());
		newSession.setAttribute(Constants.AUTH_ROLE, user.getRole());
		
		log.info("[LoginAction] Login successful for user: " + username + " | Role: " + user.getRole() + " | Session ID: " + newSession.getId());
		
        newSession.setMaxInactiveInterval(30 * 60);
		return mapping.findForward("home");
	}

	
}
