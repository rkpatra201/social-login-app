package com.java.service;

import com.java.dao.IUserModelDao;
import com.java.dao.UserModelDaoImpl;
import com.java.errors.AppException;
import com.java.errors.DataValidationException;
import com.java.model.User;
import com.java.utils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements IUserService {

    private IUserModelDao modelDao = new UserModelDaoImpl();

    @Override
    public int registerUser(User user) {
        Connection con = DbUtils.getConnection();

        try {
            boolean emailExists = modelDao.userEmailExists(con, user.getEmail());
            if (emailExists) {
                throw new DataValidationException("email already associated with an account. try with another email");
            }
        } catch (SQLException throwables) {
            throw new AppException("error while verifying email exists", throwables);
        }

        try {
            boolean phoneExists = modelDao.userPhoneExists(con, user.getPhone());
            if (phoneExists) {
                throw new DataValidationException("phone already associated with an account. try with another phone");
            }
        } catch (SQLException throwables) {
            throw new AppException("error while verifying phone exists", throwables);
        }

        int result = 0;
        try {
            result = modelDao.save(con, user);
            DbUtils.commit(con);
        } catch (SQLException throwables) {
            DbUtils.rollback(con);
            throw new AppException("error while creating employee", throwables);
        } finally {
            DbUtils.close(con);
        }
        return result;
    }

    @Override
    public int updatePassword(Integer id, String password) {

        int result = 0;
        Connection con = DbUtils.getConnection();
        try {
            result = modelDao.updatePassword(con,id,password);
            DbUtils.commit(con);
        } catch (SQLException throwables) {
            DbUtils.rollback(con);
            throw new AppException("error while creating employee", throwables);
        } finally {
            DbUtils.close(con);
        }
        return result;
    }

    @Override
    public List<User> retrieveAllUsers() {
        try {
            return modelDao.findAll(DbUtils.getConnection());
        } catch (SQLException throwables) {
            throw new AppException("error while getting all users", throwables);
        }
    }

    @Override
    public User findUserByPhone(String phone) {
        try {
            return modelDao.findUserByPhone(DbUtils.getConnection(), phone);
        } catch (SQLException throwables) {
            throw new AppException("error while reading user by phone", throwables);
        }
    }


    @Override
    public User findUserByEmail(String email) {
        try {
            return modelDao.findUserByEmail(DbUtils.getConnection(), email);
        } catch (SQLException throwables) {
            throw new AppException("error while reading user by email", throwables);
        }
    }

    @Override
    public User findUserById(Integer id) {
        try {
            return modelDao.findUserById(DbUtils.getConnection(), id);
        } catch (SQLException throwables) {
            throw new AppException("error while reading user by email", throwables);
        }
    }
    @Override
    public int updateUser(User user, Integer id) {
        int result = 0;
        Connection con = DbUtils.getConnection();
        try {
            result = modelDao.updateUser(con, user, id);
            DbUtils.commit(con);
        } catch (SQLException throwables) {
            DbUtils.rollback(con);
            throw new AppException("error while updating employee", throwables);
        } finally {
            DbUtils.close(con);
        }
        return result;
    }


}
