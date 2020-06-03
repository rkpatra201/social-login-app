package com.java.web.filter;

import com.java.errors.AuthenticationException;
import com.java.utils.ServletUtils;

import javax.servlet.*;
import java.io.IOException;

public class ApiFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean sessionExists = ServletUtils.getExistingSession(request)!=null;
        if(!sessionExists)
        {
            throw new AuthenticationException(401,"your session is invalid");
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
