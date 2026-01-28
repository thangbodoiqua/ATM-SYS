package com.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.action.auth.LoginAction;

/**
 * AuthenticationFilter - Servlet Filter for access control
 * 
 * This filter protects secured resources by checking if user is logged in.
 * 
 * Protected paths:
 * - /atm/* : ATM operations (withdraw, deposit, transfer, etc.)
 * - /admin/* : Admin operations (user management, reports, etc.)
 * 
 * Excluded paths (public access):
 * - /auth/login.do : Login page
 * - /auth/logout.do : Logout action
 * - /auth/enter-card.do : Enter card page (ATM card authentication)
 * - /auth/enter-pin.do : Enter PIN page (ATM card authentication)
 * - /home.do : Home page
 * - Static resources (.css, .js, .png, .jpg, etc.)
 * 
 * Behavior:
 * - If user is not logged in and tries to access protected resource:
 *   -> Redirect to login page with error message
 * - If user is logged in:
 *   -> Allow request to proceed
 */
public class AuthenticationFilter implements Filter {

    // Login page URL (for redirect)
    private static final String LOGIN_PAGE = "/auth/login.do";

    // List of paths that don't require authentication
    private List<String> excludedPaths;

    // Context path of the application
    private String contextPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Get context path from filter config
        contextPath = filterConfig.getServletContext().getContextPath();

        // Initialize excluded paths
        excludedPaths = new ArrayList<String>();

        // Public authentication pages
        excludedPaths.add("/auth/login");
        excludedPaths.add("/auth/logout");

        // Public pages
        excludedPaths.add("/home");
        excludedPaths.add("/ping");
        excludedPaths.add("/alo");

        // Static resources patterns will be checked separately
        System.out.println("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get request URI
        String requestURI = httpRequest.getRequestURI();

        // Remove context path to get the path within application
        String path = requestURI.substring(contextPath.length());

        // Remove .do extension for path matching
        String pathWithoutExtension = path;
        if (path.endsWith(".do")) {
            pathWithoutExtension = path.substring(0, path.length() - 3);
        }

        // ========================
        // Check if path is excluded
        // ========================

        // Check if it's a static resource
        if (isStaticResource(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Check if path is in excluded list
        if (isExcludedPath(pathWithoutExtension)) {
            chain.doFilter(request, response);
            return;
        }

        // ========================
        // Check authentication
        // ========================

        HttpSession session = httpRequest.getSession(false);

        // Check if user is logged in (session exists and has user info)
        boolean isLoggedIn = false;
        if (session != null) {
            Object user = session.getAttribute(LoginAction.SESSION_USER);
            isLoggedIn = (user != null);
        }

        if (isLoggedIn) {
            // User is authenticated, allow request to proceed
            chain.doFilter(request, response);
        } else {
            // User is NOT authenticated
            // Save the requested URL for redirect after login (optional)
            if (session == null) {
                session = httpRequest.getSession(true);
            }
            session.setAttribute("REDIRECT_URL", requestURI);

            // Set error message
            session.setAttribute("loginError", "Please login to access this page");

            // Redirect to login page
            httpResponse.sendRedirect(contextPath + LOGIN_PAGE);
        }
    }

    /**
     * Check if the path is in the excluded list
     */
    private boolean isExcludedPath(String path) {
        for (String excluded : excludedPaths) {
            if (path.equals(excluded) || path.startsWith(excluded + "/")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the request is for a static resource
     * Static resources don't need authentication
     */
    private boolean isStaticResource(String path) {
        // Common static resource extensions
        String[] staticExtensions = {
            ".css", ".js", ".png", ".jpg", ".jpeg", ".gif", 
            ".ico", ".svg", ".woff", ".woff2", ".ttf", ".eot"
        };

        String lowerPath = path.toLowerCase();
        for (String ext : staticExtensions) {
            if (lowerPath.endsWith(ext)) {
                return true;
            }
        }

        // Check for common static resource directories
        if (lowerPath.startsWith("/static/") || 
            lowerPath.startsWith("/resources/") ||
            lowerPath.startsWith("/assets/") ||
            lowerPath.startsWith("/css/") ||
            lowerPath.startsWith("/js/") ||
            lowerPath.startsWith("/images/")) {
            return true;
        }

        return false;
    }

    @Override
    public void destroy() {
        // Cleanup if needed
        excludedPaths = null;
        System.out.println("AuthenticationFilter destroyed");
    }
}
