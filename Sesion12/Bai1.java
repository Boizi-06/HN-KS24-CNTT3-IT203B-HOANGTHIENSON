package Sesion12;

import java.sql.*;

public class Bai1 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hospital";
        String user = "root";
        String password = "123456";

        String doctorCode = "input_code"; // giả lập input
        String doctorPass = "input_pass";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT * FROM doctor WHERE doctor_code = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            // Gán giá trị vào dấu ?
            ps.setString(1, doctorCode);
            ps.setString(2, doctorPass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Đăng nhập thành công!");
            } else {
                System.out.println("Sai tài khoản hoặc mật khẩu!");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}