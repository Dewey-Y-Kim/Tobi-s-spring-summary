package User.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDetailDAO_N extends UserDAO{

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // N사용 getConnection
        Class.forName("com.mysql.jdbc.Driver");

        Connection connect = DriverManager.getConnection(
                "jdbc:mysql://localhost/DB_URL","ID","Password"
        );
        return connect;
    }
}
