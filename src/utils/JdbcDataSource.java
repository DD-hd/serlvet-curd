package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcDataSource {
    private static Connection conn;

    public static Connection getConn() {
        try {
            if (conn != null) {
                return conn;
            }
            Class.forName("com.mysql.jdbc.Driver");
            String filePath=JdbcDataSource.class.getClassLoader().getResource("system.properties").getPath();
            System.out.println("filePath:"+filePath);
            FileInputStream fileInputStream = new FileInputStream(filePath);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            String username = properties.getProperty("mysql.username");
            String password = properties.getProperty("mysql.password");
            Connection conn = null;
            conn = DriverManager.getConnection(properties.getProperty("mysql.url"), username, password);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("数据库配置有误");
    }

    public static void destory() throws SQLException {
        conn.close();
    }

}
