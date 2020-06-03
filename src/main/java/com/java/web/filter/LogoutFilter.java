package com.java.web.filter;

import com.java.errors.AuthenticationException;
import com.java.utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession httpSession = ServletUtils.getExistingSession(request);
        if (httpSession != null) {
            httpSession.removeAttribute("uname");
            httpSession.invalidate();
            ServletUtils.writeMessage(response, "logged out successfully");
        }
        return;
    }

    @Override
    public void destroy() {

    }
}
