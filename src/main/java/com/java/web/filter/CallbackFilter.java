package com.java.web.filter;

import com.java.web.auth.factory.AuthProviderFactory;
import com.java.web.auth.provider.IAuthProvider;

import javax.servlet.*;
import java.io.IOException;

public class CallbackFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        IAuthProvider authProvider = AuthProviderFactory.getCallbackAuthProvider(request);
        authProvider.authenticate(request, response);
        return;
    }

    @Override
    public void destroy() {

    }
}
