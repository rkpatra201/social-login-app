package com.java.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public interface IRowMapper<T> {
    T mapRow(ResultSet resultSet, Set<String> columnNames) throws SQLException;
}
