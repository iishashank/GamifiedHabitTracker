package com.habittracker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/HabitTrackerDB";
    private static final String USER = "root";
    private static final String PASSWORD = "brah";

    private static boolean connectedOnce = false;

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (!connectedOnce) {
                System.out.println("Connected to MySQL database successfully!");
                connectedOnce = true;
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
        return conn;
    }
}
