package com.demo.form.auth;

import org.apache.struts.action.ActionForm;

/**
 * LoginForm - Struts ActionForm for user login
 * 
 * This form bean captures:
 * - username: The user's login name
 * - password: The user's password (plain text input)
 * 
 * Used with: /auth/login.do action mapping
 */
public class LoginForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    // The username entered by user
    private String username;

    // The password entered by user (plain text, will be hashed for comparison)
    private String password;

    /**
     * Default constructor
     */
    public LoginForm() {
        super();
    }

    // ========================
    // Getters and Setters
    // ========================

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Reset form fields - called by Struts before populating form
     */
    public void reset() {
        this.username = null;
        this.password = null;
    }
}
