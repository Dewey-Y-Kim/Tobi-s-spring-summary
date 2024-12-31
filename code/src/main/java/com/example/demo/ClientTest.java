package com.example.demo;

import com.example.demo.User.DAO.DAOFactory;
import com.example.demo.User.DAO.UserDAO;
import com.example.demo.User.Domain.User;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class ClientTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        UserDAO dao = new DAOFactory().userDAO();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DAOFactory.class);

        UserDAO dao = context.getBean("UserDAO", UserDAO.class);
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