package com.java.web.auth.factory;

import com.java.model.Provider;
import com.java.web.auth.provider.DatabaseAuthProvider;
import com.java.web.auth.provider.GitAuthProvider;
import com.java.web.auth.provider.GitCallbackAuthProvider;
import com.java.web.auth.provider.IAuthProvider;
import com.java.web.auth.registry.AuthStateRegistry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class AuthProviderFactory {

    public static IAuthProvider getAuthProvider(ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Provider provider = Provider.valueOf(httpServletRequest.getParameter("provider"));
        switch (provider) {
            case DATABASE:
                return new DatabaseAuthProvider();
            case GITHUB:
                return new GitAuthProvider();
        }
        return null;
    }

    public static IAuthProvider getCallbackAuthProvider(ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String state = httpServletRequest.getParameter("state");
        Provider provider = AuthStateRegistry.getInstance().getProvider(state);
        switch (provider) {
            case GITHUB:
                return new GitCallbackAuthProvider();
            default:
                throw new IllegalArgumentException("callback provider not supported");
        }
    }
}
