package io.github.computeruser88.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBLauncher {

    public static void main(String[] args) {
        String connectionUrl = "jdbc:mysql://localhost:3306/ormproject";
        try (Connection conn = DriverManager.getConnection(connectionUrl,"root", "pluralsight");){
             System.out.println("DB launched");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
