package utils;

import java.sql.*;

public class DBConnection {
    private static final String url="jdbc:sqlite:shopping_system_db.db";
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
    }

}
