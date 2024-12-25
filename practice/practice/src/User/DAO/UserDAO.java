package User.DAO;


import User.Domain.User;

import java.sql.*;

public class UserDAO  {
    public Connection getConnection(){
        Class.forName("com.mysql.jdbc.Driver");

        Connection connect = DriverManager.getConnection(
                "jdbc:mysql://localhost/DB_URL","ID","Password"
        );
        return connect;
    }
    public void add(User user) throws ClassNotFoundException, SQLException{
//        Class.forName("com.mysql.jdbc.Driver");
//
//        Connection connect = DriverManager.getConnection(
//            "jdbc:mysql://localhost/DB_URL","ID","Password"
//        );
        Connection connect = getConnection();

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
//        Class.forName("com.mysql.jdbc.Driver");
//
//        Connection connect = DriverManager.getConnection(
//                "jdbc:mysql://localhost/DB_URL", "ID", "Password"
//        );
        Connection connect = getConnection();

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
