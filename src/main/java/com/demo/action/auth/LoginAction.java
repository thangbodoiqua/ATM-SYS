package com.demo.action.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.demo.dao.UserDAO;
import com.demo.dao.impl.UserDAOImpl;
import com.demo.dto.UserDTO;
import com.demo.form.auth.LoginForm;
import com.demo.util.PasswordUtil;

/**
 * LoginAction - Handles user authentication
 * 
 * Flow:
 * 1. GET request -> Display login form
 * 2. POST request -> Validate credentials
 *    - If valid: Create session with user info, redirect to home
 *    - If invalid: Show error message, return to login form
 * 
 * Session attributes set on successful login:
 * - LOGGED_IN_USER: UserDTO object of the logged-in user
 * - USER_ID: The user's ID
 * - USERNAME: The user's username
 * 
 * URL mapping: /auth/login.do
 */
public class LoginAction extends Action {

    // Session attribute keys
    public static final String SESSION_USER = "LOGGED_IN_USER";
    public static final String SESSION_USER_ID = "USER_ID";
    public static final String SESSION_USERNAME = "USERNAME";

    // Data Access Object for user operations
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Check if this is a GET request (show login form)
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            // Check if user is already logged in
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute(SESSION_USER) != null) {
                // User already logged in, redirect to home
                return mapping.findForward("home");
            }
            // Show login form
            return mapping.findForward("showLogin");
        }

        // POST request - process login
        LoginForm loginForm = (LoginForm) form;
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        // ========================
        // Validation
        // ========================

        // Check if username is empty
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("error", "Username is required");
            return mapping.getInputForward();
        }

        // Check if password is empty
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Password is required");
            return mapping.getInputForward();
        }

        // Trim username
        username = username.trim();

        // ========================
        // Authentication
        // ========================

        // Find user by username
        UserDTO user = new UserDTO();
        
        		/*userDAO.findByUsername(username);*/

        // Check if user exists
        if (user == null) {
            request.setAttribute("error", "Invalid username or password");
            return mapping.getInputForward();
        }

        // Verify password using stored hash and salt
        boolean isPasswordValid = true; 
		/*
		 * PasswordUtil.verifyPassword( password, user.getPassword(), // stored hash
		 * user.getSalt() // stored salt );
		 */
        if (!isPasswordValid) {
            request.setAttribute("error", "Invalid username or password");
            return mapping.getInputForward();
        }

        // ========================
        // Create Session
        // ========================

        // Invalidate any existing session to prevent session fixation
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }

        // Create new session
        HttpSession newSession = request.getSession(true);

        // Store user information in session
        // Clear sensitive data before storing
        user.setPassword(null);
        user.setSalt(null);

        newSession.setAttribute(SESSION_USER, user);
        newSession.setAttribute(SESSION_USER_ID, user.getUserId());
        newSession.setAttribute(SESSION_USERNAME, user.getUsername());

        // Set session timeout (30 minutes)
        newSession.setMaxInactiveInterval(30 * 60);

        // Redirect to home page after successful login
        return mapping.findForward("success");
    }
}
