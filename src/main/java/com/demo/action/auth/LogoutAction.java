package com.demo.action.auth;
import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;

import com.demo.constant.Constants;

public class LogoutAction extends Action{
	private static final Logger log = Logger.getLogger(LogoutAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			String username = (String) session.getAttribute(Constants.AUTH_USERNAME);
			String sessionId = session.getId();
			
			log.info("[LogoutAction] User logout: " + (username != null ? username : "unknown") + " | Session ID: " + sessionId);
			
            session.invalidate();
            
            log.debug("[LogoutAction] Session invalidated successfully");
        } else {
        	log.debug("[LogoutAction] Logout attempted but no active session found");
        }
        
        request.setAttribute("message", "You have been logged out successfully");

		return mapping.findForward("login");
	}
}
