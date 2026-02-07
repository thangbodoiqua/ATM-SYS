package com.demo.security;

import javax.servlet.*;
import javax.servlet.http.*;

import com.demo.constant.Constants;

import java.io.IOException;

public class AuthFilter implements Filter {

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

        if (isPublicResource(relative)) {
            chain.doFilter(req, res);
            return;
        }
      
        if (username == null) {
            response.sendRedirect(ctx + loginPage);
            return;
        }
       
        if (relative.startsWith(atmPrefix)) {
            if (relative.equals(atmPrefix + "/enter-card.do")) {
                chain.doFilter(req, res);
                return;
            }
            if (relative.equals(atmPrefix + "/enter-pin.do")) {
                if (cardNumber == null) {
                    response.sendError(403, "Access denied");
                    return;
                }
                chain.doFilter(req, res);
                return;
            }
            if (cardNumber == null || pin == null) {
                response.sendError(403, "Access denied");
                return;
            }

            chain.doFilter(req, res);
            return;
        }

		
		  if (session != null) { if (session.getAttribute(Constants.ATM_CARD_NUMBER) !=
		  null) session.removeAttribute(Constants.ATM_CARD_NUMBER);
		  
		  if (session.getAttribute(Constants.ATM_CARD_PIN) != null)
		  session.removeAttribute(Constants.ATM_CARD_PIN); }
		 

        if (relative.startsWith(adminOnlyPrefix)) {
            Object role = session.getAttribute(Constants.AUTH_ROLE);
            if (!"ADMIN".equals(role)) {
                response.sendError(403, "Access denied");
                return;
            }
        }
        chain.doFilter(req, res);
        return;
    }
        

    private boolean isPublicResource(String path) {
        if (path.equals("/") || path.equals("/auth/login.do")) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {}
}
