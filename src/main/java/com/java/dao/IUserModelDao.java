package com.java.dao;

import com.java.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface IUserModelDao extends IDataModelDao<User,Integer>  {
    int updatePassword(Connection con, Integer id, String password) throws SQLException;
    int updateUser(Connection con,User user,Integer id) throws SQLException;
    boolean userEmailExists(Connection con, String email) throws SQLException;
    boolean userPhoneExists(Connection con, String phone) throws SQLException;
    User findUserByPhone(Connection con, String phone) throws SQLException;
    User findUserByEmail(Connection con, String email) throws SQLException;
}
