package com.netcracker.lab3webApp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionInitializer {

    private static final String URL      = "jdbc:postgresql://localhost:5432/lab3";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "m9LHQbTU";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
