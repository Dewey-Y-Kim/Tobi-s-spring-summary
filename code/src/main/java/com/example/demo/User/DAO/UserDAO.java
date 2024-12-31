package com.example.demo.User.DAO;


import com.example.demo.User.Domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO  {
    private ConnectionMaker connectionMaker;

    public UserDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection connect = connectionMaker.makeNewConnection();
        // use interface method name will not change.

        PreparedStatement ps = connect.prepareStatement(
                "insert into users(id, name, password) value(?,?,?)"
        );
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeQuery();
        connect.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection connect = connectionMaker.makeNewConnection();

        PreparedStatement ps = connect.prepareStatement(
                "select id, name, password from users where id = ?"
        );
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        connect.close();

        return user;
    }
}
