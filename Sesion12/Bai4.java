package Sesion12;

import java.sql.*;

public class Bai4 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hospital";
        String user = "root";
        String password = "123456";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "INSERT INTO lab_result(patient_id, test_name, result_value) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 1; i <= 1000; i++) {
                ps.setInt(1, i);
                ps.setString(2, "Blood Test");
                ps.setDouble(3, Math.random() * 100);

                ps.executeUpdate();
            }

            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}