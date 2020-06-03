package com.java.web.auth.provider;

import com.java.model.User;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public interface IAuthProvider {
    public void authenticate(ServletRequest request, ServletResponse response) throws IOException;
}
