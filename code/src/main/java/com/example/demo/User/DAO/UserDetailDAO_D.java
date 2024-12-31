package com.example.demo.User.DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDetailDAO_D implements ConnectionMaker {

    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        // D사용 getConnection
        Class.forName("com.mysql.jdbc.Driver");

        Connection connect = DriverManager.getConnection(
                "jdbc:mysql://localhost/DB_URL", "ID", "Password"
        );
        return connect;
    }
}
