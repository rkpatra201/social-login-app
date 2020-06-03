package com.java.web.controller;

import com.java.dao.IUserModelDao;
import com.java.dao.UserModelDaoImpl;
import com.java.model.User;
import com.java.model.UserSession;
import com.java.service.IUserService;
import com.java.service.UserServiceImpl;
import com.java.utils.DbUtils;
import com.java.utils.JsonUtils;
import com.java.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UsersController extends HttpServlet {

    private IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("list".equals(getAction(req))) {
            Object o = userService.retrieveAllUsers();
            JsonUtils.writeObject(resp, o);
        } else if ("one".equals(getAction(req))) {
            Integer id = Integer.valueOf(req.getParameter("id"));
            User user = userService.findUserById(id);
            if (user != null)
                user.setPassword(null);
            JsonUtils.writeObject(resp, user);
        } else if ("search".equals(getAction(req))) {
            String phone = req.getParameter("phone");
            User user = userService.findUserByPhone(phone);
            if (user != null)
                user.setPassword(null);
            JsonUtils.writeObject(resp, user);
        } else {
            HttpSession httpSession = ServletUtils.getExistingSession(req);
            UserSession userSession = (UserSession) httpSession.getAttribute("user");
            Integer id = userSession.getId();
            User user = userService.findUserById(id);
            if (user != null)
                user.setPassword(null);
            ServletUtils.writeMessage(resp, "logged in user details: ");
            JsonUtils.writeObject(resp, user);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        User user = JsonUtils.readObject(req, User.class);
//        userService.registerUser(user);
//        JsonUtils.writeObject(resp, "SUCCESS");
        HttpSession httpSession = ServletUtils.getExistingSession(req);
        UserSession userSession = (UserSession) httpSession.getAttribute("user");

        Integer id = userSession.getId();
        String password = req.getParameter("password");
        userService.updatePassword(id, password);

        ServletUtils.writeMessage(resp, "password updated successfully");
    }

    private String getAction(HttpServletRequest req) {
        return req.getParameter("action");
    }


}
