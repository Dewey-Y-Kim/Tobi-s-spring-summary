import User.DAO.UserDAO;
import User.DAO.UserDetailDAO_N;
import User.Domain.User;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDAO dao = new UserDetailDAO_N();

        User user = new User();
        user.setId("001");
        user.setName("dewey");
        user.setPassword("testPassword");

        dao.add(user);

        System.out.println(user.getId()+" is added");

        User user2 = dao.get(user.getId());

        System.out.println(user2.getName() + " is loaded");

        System.out.println(user2.getId() + "`s password is " + user2.getPassword());

    }
}