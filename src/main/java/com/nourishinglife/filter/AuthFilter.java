package com.nourishinglife.filter;

import com.nourishinglife.bean.AuthBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Server-side route protection.
 * - signin.xhtml / signup.xhtml / error pages are always public.
 * - Everything else requires a logged-in user (redirects to /signin.xhtml).
 * - /admin/* additionally requires the ADMIN role (redirects to /home.xhtml).
 *
 * The original React app only hid the admin nav link client-side and never
 * enforced this on the server - this filter closes that gap.
 */
public class AuthFilter implements Filter {

    private static final String[] PUBLIC_PAGES = {
            "/signin.xhtml", "/signup.xhtml", "/error/404.xhtml", "/error/403.xhtml", "/index.xhtml"
    };

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        if (isPublic(path) || path.startsWith("/javax.faces.resource/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        AuthBean authBean = session == null ? null : (AuthBean) session.getAttribute("authBean");

        if (authBean == null || !authBean.isAuthenticated()) {
            res.sendRedirect(contextPath + "/signin.xhtml");
            return;
        }

        if (path.startsWith("/admin/") && !authBean.isAdmin()) {
            res.sendRedirect(contextPath + "/home.xhtml");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isPublic(String path) {
        for (String p : PUBLIC_PAGES) {
            if (path.equals(p)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
    }
}
