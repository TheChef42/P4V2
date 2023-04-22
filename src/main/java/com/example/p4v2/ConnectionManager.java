package com.example.p4v2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static String url = "jdbc:mysql://localhost:3306/selfservice?characterEncoding=utf8";
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String username; // = "root";

    private static String password;  //= "";
    private static Connection con;

    static {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(".env")) {
            prop.load(input);
            username = prop.getProperty("DB_USERNAME");
            password = prop.getProperty("DB_PASSWORD");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection."); 
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found."); 
        }
        return con;
    }
}
