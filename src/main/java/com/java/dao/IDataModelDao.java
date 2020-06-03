package com.java.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IDataModelDao<T,I> {
    int save(Connection con,T t) throws SQLException;
    List<T> findAll(Connection con) throws SQLException;
    T findUserById(Connection con, I i) throws SQLException;
}
