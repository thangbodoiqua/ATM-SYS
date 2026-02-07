package com.demo.action.auth;
import javax.servlet.http.*;


import org.apache.struts.action.*;
public class LogoutAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(false);
		
		if (session != null) {
            session.invalidate();
        }
		ActionMessages messages = new ActionMessages();
		messages.add("message", new ActionMessage("message.logout.success"));
		saveMessages(request, messages);
		
		return mapping.findForward("login");
	}
}
