package io.github.computeruser88.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlEntityManager extends AbstractEntityManager {
    public Connection buildConnection() throws SQLException {
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/ormproject","root", "pluralsight");
        return connection;
    }
}
