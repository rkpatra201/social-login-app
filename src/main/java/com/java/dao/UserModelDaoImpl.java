package com.java.dao;

import com.java.dao.mapper.IRowMapper;
import com.java.dao.mapper.UserRowMapper;
import com.java.model.User;
import com.java.utils.JsonUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserModelDaoImpl extends AbstractDataModelDao implements IUserModelDao {

    private static final String INSERT = "INSERT INTO TBL_USER (NAME,EMAIL,PHONE,PASSWORD,META) VALUES(?,?,?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM TBL_USER";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM TBL_USER WHERE EMAIL= ?";
    private static final String SELECT_BY_PHONE = "SELECT * FROM TBL_USER WHERE PHONE = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM TBL_USER WHERE ID = ?";
    private static final String UPDATE_PASSWORD = "UPDATE  TBL_USER SET PASSWORD=? WHERE ID=?";
    private static final String COUNT_BY_EMAIL = "SELECT COUNT(*) CNT FROM TBL_USER WHERE EMAIL =?";
    private static final String COUNT_BY_PHONE = "SELECT COUNT(*) CNT FROM TBL_USER WHERE PHONE =?";
    private static final String UPDATE_USER = "UPDATE TBL_USER SET NAME=?,EMAIL=?,META=? WHERE ID = ?";
    @Override
    public int updatePassword(Connection con, Integer id, String password) throws SQLException {
        PreparedStatement ps = con.prepareStatement(UPDATE_PASSWORD);
        ps.setString(1, password);
        ps.setInt(2, id);
        return executeUpdate(ps);
    }

    @Override
    public int save(Connection con, User user) throws SQLException {
        PreparedStatement ps = con.prepareStatement(INSERT);
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPhone());
        ps.setString(4, user.getPassword());
        ps.setString(5, JsonUtils.toString(user.getMeta()));
        return executeUpdate(ps);
    }

    @Override
    public List<User> findAll(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(SELECT_ALL);
        ResultSet resultSet = ps.executeQuery();
        List<User> users = new ArrayList<>();
        Set<String> ignoreColumns = new HashSet<String>();
        ignoreColumns.add("PASSWORD");
        while (resultSet.next()) {
            IRowMapper<User> userRowMapper = new UserRowMapper();
            User user = userRowMapper.mapRow(resultSet, ignoreColumns);
            users.add(user);
        }
        return users;
    }

    @Override
    public boolean userEmailExists(Connection con, String email) throws SQLException {
        PreparedStatement ps = con.prepareStatement(COUNT_BY_EMAIL);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int count = rs.getInt("CNT");
            return count == 1;
        }
        return false;
    }

    @Override
    public boolean userPhoneExists(Connection con, String phone) throws SQLException {
        PreparedStatement ps = con.prepareStatement(COUNT_BY_PHONE);
        ps.setString(1, phone);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int count = rs.getInt("CNT");
            return count == 1;
        }
        return false;
    }

    @Override
    public User findUserByPhone(Connection con, String phone) throws SQLException {
        PreparedStatement ps = con.prepareStatement(SELECT_BY_PHONE);
        ps.setString(1, phone);
        ResultSet resultSet = ps.executeQuery();
        Set<String> ignoreColumns = new HashSet<String>();
       // ignoreColumns.add("PASSWORD");
        if (resultSet.next()) {
            return findOne(resultSet, new UserRowMapper(), ignoreColumns);
        }
        return null;
    }

    @Override
    public User findUserByEmail(Connection con, String email) throws SQLException {
        PreparedStatement ps = con.prepareStatement(SELECT_BY_EMAIL);
        ps.setString(1, email);
        ResultSet resultSet = ps.executeQuery();
        Set<String> ignoreColumns = new HashSet<String>();
        //ignoreColumns.add("PASSWORD");
        if (resultSet.next()) {
            return findOne(resultSet, new UserRowMapper(), ignoreColumns);
        }
        return null;
    }

    @Override
    public int updateUser(Connection con, User user, Integer id) throws SQLException {
        PreparedStatement ps = con.prepareStatement(UPDATE_USER);
        ps.setString(1,user.getName());
        ps.setString(2,user.getEmail());
        ps.setString(3, JsonUtils.toString(user.getMeta()));
        ps.setInt(4,id);
        return executeUpdate(ps);
    }

    @Override
    public User findUserById(Connection con, Integer integer) throws SQLException {
        PreparedStatement ps = con.prepareStatement(SELECT_BY_ID);
        ps.setInt(1, integer);
        ResultSet resultSet = ps.executeQuery();
        Set<String> ignoreColumns = new HashSet<String>();
         ignoreColumns.add("PASSWORD");
        if (resultSet.next()) {
            return findOne(resultSet, new UserRowMapper(), ignoreColumns);
        }
        return null;
    }
}
