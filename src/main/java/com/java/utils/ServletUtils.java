package com.java.utils;

import com.java.errors.AppException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServletUtils {

    public static HttpSession getExistingSession(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpServletRequest.getSession(false);
        if (httpSession == null) {
            return null;
        }
        if (httpSession.getAttribute("user") != null) {
            return httpSession;
        }
        return null;
    }

    public static void writeMessage(ServletResponse servletResponse, String message) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.getWriter().write(message);
    }

    public static void redirect(ServletResponse response, String url) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            httpServletResponse.sendRedirect(url);
        } catch (IOException e) {
            throw new AppException("error while redirecting to the url: " + url, e);
        }
    }
}
