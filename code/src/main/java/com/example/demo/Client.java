package com.example.demo;


import com.example.demo.User.DAO.UserDAO;
import com.example.demo.User.Domain.User;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Client {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {


        UserDAO dao = new UserDAO();

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