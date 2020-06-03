package com.java.dao.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.java.model.Provider;
import com.java.model.User;
import com.java.utils.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;


public class UserRowMapper implements IRowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, Set<String> skipColumns) throws SQLException {
        User user = new User();
        user.setName(resultSet.getString("NAME"));
        user.setEmail(resultSet.getString("EMAIL"));
        user.setPhone(resultSet.getString("PHONE"));
        user.setId(resultSet.getInt("ID"));
        if(!skipColumns.contains("PASSWORD")) {
            user.setPassword(resultSet.getString("PASSWORD"));
        }
        user.setMeta(JsonUtils.readString(resultSet.getString("META"), new TypeReference<HashMap<Provider,String>>() {
        }));
        return user;
    }
}
