package Sesion11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Bai1 {

    // ===== Hằng số cấu hình =====
    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    // ===== Lấy connection =====
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ===== Đóng connection =====
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Lỗi khi đóng connection: " + e.getMessage());
            }
        }
    }
}