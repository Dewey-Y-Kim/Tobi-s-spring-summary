package com.example.demo.User.DAO;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class DAOFactory {
    @Bean
    public UserDAO userDAO() throws ClassNotFoundException, SQLException{
        ConnectionMaker connectionMaker = new UserDetailDAO_D();
        return new UserDAO(connectionMaker());
    }
    @Bean
    public UserDAO accountDAO() throws ClassNotFoundException, SQLException{
        return new UserDAO(connectionMaker());
    }
    @Bean
    public UserDAO messageDAO() throws ClassNotFoundException, SQLException{
        return new UserDAO(connectionMaker());
    }
    @Bean
    public UserDAO boardDAO() throws ClassNotFoundException, SQLException {
        return new UserDAO(connectionMaker());
    }
    @Bean
    public ConnectionMaker connectionMaker(){
        return  new UserDetailDAO_D();
    }
}
