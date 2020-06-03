package com.java.web.filter;

import com.java.utils.ServletUtils;
import com.java.web.auth.factory.AuthProviderFactory;
import com.java.web.auth.provider.IAuthProvider;

import javax.servlet.*;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean sessionExists = ServletUtils.getExistingSession(servletRequest)!=null;
        if (sessionExists) {
            ServletUtils.writeMessage(servletResponse,"you are logged in, access your API");
        } else {
            IAuthProvider authProvider = AuthProviderFactory.getAuthProvider(servletRequest);
            authProvider.authenticate(servletRequest,servletResponse);
        }
        return;
    }

    @Override
    public void destroy() {

    }
}
