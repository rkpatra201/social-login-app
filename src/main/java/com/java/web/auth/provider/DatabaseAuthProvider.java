package com.java.web.auth.provider;

import com.java.errors.AuthenticationException;
import com.java.model.Provider;
import com.java.model.User;
import com.java.model.UserSession;
import com.java.service.IUserService;
import com.java.service.UserServiceImpl;
import com.java.utils.JsonUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class DatabaseAuthProvider extends AbstractAuthProvider {

    @Override
    public void authenticate(ServletRequest request, ServletResponse response) throws IOException {
        super.authenticate(request, response);
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String contentType =httpServletRequest.getHeader("Content-Type");
        User user = null;
        if(contentType.equals("application/x-www-form-urlencoded")) {
            String phone =httpServletRequest.getParameter("phone");
            String password = httpServletRequest.getParameter("password");
            user = new User();
            user.setPhone(phone);
            user.setPassword(password);
        }else {
            user = JsonUtils.readObject(httpServletRequest, User.class);
        }
        IUserService userService = new UserServiceImpl();

        User dbUser = userService.findUserByPhone(user.getPhone());

        if(dbUser == null)
        {
            throw new AuthenticationException(404, "user not found");
        }
        if(!Objects.equals(user.getPassword(), dbUser.getPassword()))
        {
            throw new AuthenticationException(500, "password not matched");
        }
        super.setUser(dbUser);
        onAuthenticationSuccess();
    }

    public void onAuthenticationSuccess()  throws IOException{
        super.setProvider(Provider.DATABASE);
        UserSession userSession = new UserSession();
        userSession.setId(getUser().getId());
        userSession.setUsername(getUser().getName());
        super.createSession(userSession);
    }
}
