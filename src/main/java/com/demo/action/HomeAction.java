package com.demo.action;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.demo.constant.Constants;
	
public class HomeAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession existSession = req.getSession(false);
		if(existSession.getAttribute("AUTH_USER_ID") == null || existSession.getAttribute("AUTH_USERNAME") == null) {
			return mapping.findForward("login");
		}
		return mapping.findForward("home");
	}
}
