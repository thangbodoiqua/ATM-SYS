package com.demo.security;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import com.demo.constant.Constants;

import java.io.IOException;

public class AuthFilter implements Filter {
	
	private static final Logger log = Logger.getLogger(AuthFilter.class);
	
    private String loginPage;
    private String adminOnlyPrefix;
    private String atmPrefix;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        loginPage = filterConfig.getInitParameter("loginPage");
        if (loginPage == null || loginPage.trim().isEmpty()) {
            loginPage = "/auth/login.do";
        }
        
        adminOnlyPrefix = filterConfig.getInitParameter("adminOnlyPrefix");
        if (adminOnlyPrefix == null) {
            adminOnlyPrefix = "/admin";
        }
        
        atmPrefix = filterConfig.getInitParameter("atmPrefix");
        
        log.info("[AuthFilter] Initialized - loginPage: " + loginPage + ", adminPrefix: " + adminOnlyPrefix + ", atmPrefix: " + atmPrefix);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        String ctx  = request.getContextPath();
        String relative = path.substring(ctx.length());

        HttpSession session = request.getSession(false);

        Object username   = (session != null) ? session.getAttribute(Constants.AUTH_USERNAME) : null;
        Object cardNumber = (session != null) ? session.getAttribute(Constants.ATM_CARD_NUMBER) : null;
        Object pin        = (session != null) ? session.getAttribute(Constants.ATM_CARD_PIN) : null;

        log.debug("[AuthFilter] Request: " + relative + " | User: " + (username != null ? username : "anonymous"));
    
        if (isPublicResource(relative)) {
            log.debug("[AuthFilter] Public resource accessed: " + relative);
            chain.doFilter(req, res);
            return;
        }

      
        if (username == null) {
            log.warn("[AuthFilter] Unauthorized access attempt to: " + relative + " - Redirecting to login");
            response.sendRedirect(ctx + loginPage);
            return;
        }

       
        if (relative.startsWith(atmPrefix)) {

            if (relative.equals(atmPrefix + "/enter-card.do")) {
                log.debug("[AuthFilter] ATM enter-card page accessed by user: " + username);
                chain.doFilter(req, res);
                return;
            }

            if (relative.equals(atmPrefix + "/enter-pin.do")) {
                if (cardNumber == null) {
                    log.warn("[AuthFilter] ATM pin page accessed without card number by user: " + username);
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                    return;
                }
                log.debug("[AuthFilter] ATM enter-pin page accessed by user: " + username + " with card: " + cardNumber);
                chain.doFilter(req, res);
                return;
            }
            if (cardNumber == null || pin == null) {
                log.warn("[AuthFilter] ATM resource accessed without card/pin by user: " + username + " - " + relative);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }

            log.debug("[AuthFilter] ATM resource accessed: " + relative + " by user: " + username);
            chain.doFilter(req, res);
            return;
        }

 
        if (session != null) {
            if (session.getAttribute(Constants.ATM_CARD_NUMBER) != null)
                session.removeAttribute(Constants.ATM_CARD_NUMBER);

            if (session.getAttribute(Constants.ATM_CARD_PIN) != null)
                session.removeAttribute(Constants.ATM_CARD_PIN);
        }

       
        if (relative.startsWith(adminOnlyPrefix)) { 
            Object role = session.getAttribute(Constants.AUTH_ROLE); 
            if (!"ADMIN".equals(role)) {
                log.warn("[AuthFilter] Non-admin user '" + username + "' attempted to access admin resource: " + relative);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin only"); 
                return; 
            }
            log.debug("[AuthFilter] Admin resource accessed: " + relative + " by admin: " + username);
        }

        chain.doFilter(req, res);
    }
        
        

    private boolean isPublicResource(String path) {
       
        if (path.equals("/") || path.equals("/auth/login.do") || path.equals("/alo.do") || path.equals("/ping.do.do")) {
      
            return true;
        }

        return false;
    }

    @Override
    public void destroy() {}
}