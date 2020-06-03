package com.java.web.auth.provider;

import com.java.model.Provider;
import com.java.model.User;
import com.java.model.UserSession;
import com.java.utils.ServletUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract class AbstractAuthProvider implements IAuthProvider {

    private Provider provider;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private User user;
    @Override
    public void authenticate(ServletRequest request, ServletResponse response) throws IOException {
        this.httpServletRequest = (HttpServletRequest)request;
        this.httpServletResponse = (HttpServletResponse)response;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    protected void createSession(UserSession userSession) throws IOException
    {
        HttpSession httpSession = getHttpServletRequest().getSession(true);
        userSession.setProvider(getProvider());
        httpSession.setAttribute("user",userSession);
        ServletUtils.writeMessage(getHttpServletResponse(), "your session created with provider: "+userSession.getProvider()+". Access your API");
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
