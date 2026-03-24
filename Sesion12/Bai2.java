package Sesion12;

import java.sql.*;

public class Bai2 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hospital";
        String user = "root";
        String password = "123456";

        int patientId = 1;
        double temperature = 37.5;
        int heartRate = 80;

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "UPDATE patient SET temperature = ?, heart_rate = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDouble(1, temperature);
            ps.setInt(2, heartRate);
            ps.setInt(3, patientId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không tìm thấy bệnh nhân!");
            }

            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}