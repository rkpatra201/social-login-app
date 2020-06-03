package com.java.web.auth.provider;

import com.java.model.User;
import com.java.model.UserSession;
import com.java.service.IUserService;
import com.java.service.UserServiceImpl;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public abstract class SocialCallBackAuthProvider extends AbstractAuthProvider{

    private IUserService userService = new UserServiceImpl();
    private String accessToken;
    private String email;
    private String socialUserMeta;
    @Override
    public void authenticate(ServletRequest request, ServletResponse response) throws IOException {
        super.authenticate(request,response);
        retrieveAccessToken();
        retrieveSocialUserMeta();
        saveSocialUserMeta();
        onAuthenticationSuccess();
    }

    public void onAuthenticationSuccess() throws IOException {
        UserSession userSession = new UserSession();
        userSession.setUsername(getUser().getName());
        userSession.setId(getUser().getId());
        userSession.setAccessToken(getAccessToken());
        super.createSession(userSession);
    }

    protected abstract void retrieveAccessToken() throws IOException;
    protected abstract void retrieveSocialUserMeta() throws IOException;
    protected abstract void saveSocialUserMeta() throws IOException;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSocialUserMeta() {
        return socialUserMeta;
    }

    public void setSocialUserMeta(String socialUserMeta) {
        this.socialUserMeta = socialUserMeta;
    }

    public IUserService getUserService() {
        return userService;
    }
}
