package com.java.utils;

import com.java.constants.AppConsts;
import com.java.dao.IUserModelDao;
import com.java.dao.UserModelDaoImpl;
import com.java.errors.DatabaseException;
import com.java.model.DatabaseType;
import com.java.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbUtils {

    private static Properties dbProperties = null;

    static {
        dbProperties = getDatabaseProperties(DatabaseType.MYSQL);
        loadDatabaseDriver();
    }

    private static void loadDatabaseDriver() {
        String className = dbProperties.getProperty("DRIVER_CLASS");
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("error while loading database driver: " + className, e);
        }
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            Properties p = dbProperties;
            con = DriverManager.getConnection(p.getProperty("URL"), p.getProperty("USERNAME"), p.getProperty("PASSWORD"));
            con.setAutoCommit(false);
            return con;
        } catch (SQLException e) {
            throw new DatabaseException("error while conneting with database", e);
        }
    }

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException throwables) {
            }
        }
    }

    private static Properties getDatabaseProperties(DatabaseType dataBaseType) {
        String filePath = String.format(AppConsts.DB_FILE, dataBaseType.name().toLowerCase());
        File file = new File(filePath);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            throw new DatabaseException("error while reading connection properties", e);
        }
        return properties;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(dbProperties);
        Connection con = DbUtils.getConnection();
        User u = new User();
        u.setName("rkp1");
        u.setEmail("rkp@gmail.com1");
        u.setPhone("p1");
        u.setPassword("p123");

        IUserModelDao modelDao = new UserModelDaoImpl();
        //  modelDao.save(con,u);
        //   modelDao.updatePassword(con,"p1","p1234");
        System.out.println(modelDao.findUserByPhone(con, "p1"));
        System.out.println(modelDao.findAll(con));
    }

    public static void commit(Connection con) {
        if (con != null) {
            try {
                con.commit();
            } catch (SQLException throwables) {
                throw new DatabaseException("error while committing the transaction", throwables);
            }
        }
    }


    public static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException throwables) {
                throw new DatabaseException("error while rollback the transaction", throwables);
            }
        }
    }
}
