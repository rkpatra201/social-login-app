package com.java.web.filter;

import com.java.errors.AppException;
import com.java.errors.AuthenticationException;
import com.java.errors.DataValidationException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (AppException e) {
            if (e instanceof DataValidationException) {
                ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_CONFLICT);
                servletResponse.getWriter().write(e.getMessage());
            } else if (e instanceof AuthenticationException) {
                AuthenticationException ae = (AuthenticationException) e;
                ((HttpServletResponse) servletResponse).setStatus(ae.getStatusCode());
                servletResponse.getWriter().write(ae.getMessage());
            } else {
                throw e;
            }
        }
    }

    @Override
    public void destroy() {

    }
}
