package util;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    static Connection connection;
    public static Connection getDBConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String filePath = "C:\\Users\\ayush\\Desktop\\JDBC\\Assignment 2\\src\\util\\db.properties";
            FileInputStream fis = new FileInputStream(filePath);
            Properties properties = new Properties(); 
            properties.load(fis);
            String url = properties.getProperty("url");
	        String username = properties.getProperty("username");
	        String password = properties.getProperty("password");

	        connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}