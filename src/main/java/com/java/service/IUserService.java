package com.java.service;

import com.java.model.User;

import java.util.List;

public interface IUserService {
     int registerUser(User user);
     int updateUser(User user, Integer id);
     int updatePassword(Integer id,String password);
     List<User> retrieveAllUsers();
     User findUserByPhone(String phone);
     User findUserByEmail(String email);
     User findUserById(Integer id);
}
