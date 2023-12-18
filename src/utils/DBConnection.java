package utils;

import java.sql.*;

public class DBConnection {
    private static final String url="jdbc:mysql://localhost:3306/online_shopping_system";
    private static final String username="root";
    private static final String password="";
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url,username,password);
    }

}
