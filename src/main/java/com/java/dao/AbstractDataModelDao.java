package com.java.dao;

import com.java.dao.mapper.IRowMapper;
import com.java.dao.mapper.UserRowMapper;
import com.java.model.User;
import com.java.utils.DbUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public abstract class AbstractDataModelDao {

    protected int executeUpdate(PreparedStatement st) throws SQLException {
        try {
            return st.executeUpdate();
        } catch (SQLException throwables) {
            throw throwables;
        } finally {
            DbUtils.close(st);
        }
    }

    public <T> T findOne(ResultSet resultSet, IRowMapper<T> rowMapper, Set<String> ignoreColumns) throws SQLException {
        return rowMapper.mapRow(resultSet, ignoreColumns);
    }
}
