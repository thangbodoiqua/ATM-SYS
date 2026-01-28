package com.demo.action.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * LogoutAction - Handles user logout
 * 
 * Flow:
 * 1. Invalidate current session (destroys all session data)
 * 2. Redirect to login page with success message
 * 
 * URL mapping: /auth/logout.do
 * 
 * Security considerations:
 * - Always invalidate session on logout to prevent session hijacking
 * - Clear any cached user data
 * - Redirect to login page (not home) to prevent access to protected resources
 */
public class LogoutAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // ========================
        // Session Invalidation
        // ========================

        // Get current session (don't create if doesn't exist)
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Log username before invalidating (for logging purposes)
            String username = (String) session.getAttribute(LoginAction.SESSION_USERNAME);
            if (username != null) {
                System.out.println("User logged out: " + username);
            }

            // Invalidate session - this removes ALL session attributes
            // and marks the session as invalid
            session.invalidate();
        }

        // ========================
        // Redirect to Login Page
        // ========================

        // Set logout success message (will be displayed on login page)
        // Note: Since session is invalidated, we use request attribute
        // and redirect to a page that can display it
        request.setAttribute("message", "You have been logged out successfully");

        // Redirect to login page
        return mapping.findForward("login");
    }
}
