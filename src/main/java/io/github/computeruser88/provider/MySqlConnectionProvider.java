package io.github.computeruser88.provider;

import io.github.computeruser88.annotation.Provides;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnectionProvider {

    @Provides
    public Connection buildConnection() throws SQLException {
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/ormproject","root", "pluralsight");
        return connection;
    }
}
