package ConnectionProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionProvider {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static{
        ResourceBundle rb = ResourceBundle.getBundle("DBdetail");
        driver = rb.getString("db.drivername");
        url = rb.getString("db.url");
        username = rb.getString("db.username");
        password = rb.getString("db.password");
    }


    public static Connection getConnection(){


        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return connection;


    }
}
